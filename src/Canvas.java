import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;


public class Canvas extends JComponent implements Observer
{
    // Background color object.
    Color backgroundColor;

    // Point object for the mouse point.
    Point M = new Point();

    // Point object for the mouse click.
    Point C = new Point();

    // Drawing boolean
    boolean nowDrawing = false;

    PaintModel model;

    Canvas(PaintModel model, Color backgroundColor)
    {
        // Invoke the parent constructor.
        super();

        this.model = model;

        model.addObserver(this);

        this.backgroundColor = backgroundColor;
        this.setBackground(Color.WHITE);


        /**
         * Listening for mouse press.
         */
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                C.x = e.getX();
                C.y = e.getY();

                if (model.currentTool == PaintModel.Tool.SelectTool)
                {
                    // The list of drawnShapes is iteratively checked in a
                    // reverse order to prioritize the most recent objects.
                    for (int i = model.drawnShapes.size() - 1; i >= 0; i--)
                    {
                        SketchShape drawnShape = model.drawnShapes.get(i);

                        if (drawnShape.isClicked(C.x, C.y))
                        {
                            model.deselectAllShapes();
                            model.selectShape(drawnShape);

                            break;
                        }
                    }
                } else if (model.currentTool == PaintModel.Tool.EraseTool)
                {
                    for (int i = model.drawnShapes.size() - 1; i >= 0; i--)
                    {
                        SketchShape drawnShape = model.drawnShapes.get(i);

                        if (drawnShape.isBounding(C.x, C.y))
                        {
                            model.deselectAllShapes();
                            model.eraseShape(drawnShape);

                            break;
                        }
                    }
                } else if (model.currentTool == PaintModel.Tool.FillTool)
                {
                    for (int i = model.drawnShapes.size() - 1; i >= 0; i--)
                    {
                        SketchShape drawnShape = model.drawnShapes.get(i);
                        drawnShape.isBounding(C.x, C.y);

                        break;
                    }
                } else
                {
                    nowDrawing = true;
                }
            }
        });


        /**
         * Listener for mouse release.
         */
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                M.x = e.getX();
                M.y = e.getY();

                nowDrawing = false;

                SketchShape newShape;

                switch(model.currentTool)
                {
                    case SelectTool:
                    {
                        if (model.selectedShape != null)
                        {
                            int xMoved = model.selectedShape.xTranslate;
                            int yMoved = model.selectedShape.yTranslate;

                            AffineTransform transform =
                                    AffineTransform.getTranslateInstance(xMoved, yMoved);

                            model.selectedShape.applyTranslation(
                                    (int) transform.getTranslateX(),
                                    (int) transform.getTranslateY());

                            model.selectedShape.xTranslate = 0;
                            model.selectedShape.yTranslate = 0;
                        }

                        break;
                    }

                    case LineDrawTool:
                    {
                        newShape = new LineSketchShape(model.currentColor, model.currentLineThickness,
                                                        C.x, C.y, M.x, M.y);
                        model.addShape(newShape);

                        break;
                    }

                    case CircleDrawTool:
                    {
                        newShape = new CircleSketchShape(model.currentColor, model.currentLineThickness, false,
                                                        Math.min(C.x, M.x), Math.min(C.y, M.y),
                                                        Math.abs(M.x - C.x), Math.abs(M.y - C.y));

                        model.addShape(newShape);

                        break;
                    }

                    case RectangleDrawTool:
                    {
                        newShape  = new RectangleSketchShape(model.currentColor, model.currentLineThickness, false,
                                                            Math.min(C.x, M.x), Math.min(C.y, M.y),
                                                            Math.abs(M.x - C.x), Math.abs(M.y - C.y));

                        model.addShape(newShape);

                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                model.notifyAllObservers();
            }
        });


        /**
         * Listener for mouse drag.
         */
        this.addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                M.x = e.getX();
                M.y = e.getY();

                if (model.currentTool == PaintModel.Tool.SelectTool
                    && model.selectedShape.isClicked(C.x, C.y))
                {
                    model.selectedShape.xTranslate = M.x - C.x;
                    model.selectedShape.yTranslate = M.y - C.y;

                    repaint();
                }
                // Repaint using the current translation in
                // the x and y coordinates.
                repaint();
            }
        });
    }

    /**
     * Member functions
     * Drawing methods.
     */
    void paintSketchShape(SketchShape shape, Graphics2D g2)
    {
        g2.setColor(shape.color);
        g2.setStroke(new BasicStroke(shape.thickness));
        switch(shape.shapeType)
        {
            case Line:
            {
                paintLineSketchShape((LineSketchShape) shape, g2);
                break;
            }
            case Circle:
            {
                paintCircleSketchShape((CircleSketchShape) shape, g2);
                break;
            }
            case Rectangle:
            {
                paintRectangleSketchShape((RectangleSketchShape) shape, g2);
                break;
            }
        }
    }

    /**
     * Line paint function.
     */
    void paintLineSketchShape(LineSketchShape line, Graphics2D g2)
    {
        g2.translate(line.xTranslate, line.yTranslate);

        g2.drawLine(line.x1, line.y1, line.x2, line.y2);
    }

    /**
     * Circle paint function.
     */
    void paintCircleSketchShape(CircleSketchShape circle, Graphics2D g2)
    {
        g2.translate(circle.xTranslate, circle.yTranslate);

        if (circle.isFilled)
        {
            g2.fillOval(circle.x, circle.y, circle.width, circle.height);
        } else
        {
            g2.drawOval(circle.x, circle.y, circle.width, circle.height);
        }
    }

    /**
     * Rectangle paint function.
     */
    void paintRectangleSketchShape(RectangleSketchShape rectangle, Graphics2D g2)
    {
        g2.translate(rectangle.xTranslate, rectangle.yTranslate);

        if (rectangle.isFilled)
        {
            g2.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        } else
        {
            g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    /**
     *
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Cast the Graphics object, g as a Graphics2d object g2.
        Graphics2D g2 = (Graphics2D) g;

        setOpaque(true);
        g2.setColor(Color.WHITE);
        g2.setBackground(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (SketchShape sketchShape : model.drawnShapes)
        {
            paintSketchShape(sketchShape, g2);
        }

        g2.setColor(model.currentColor);
        g2.setStroke(new BasicStroke(model.currentLineThickness));

        if(nowDrawing == true) {
            switch (model.currentTool) {
                case LineDrawTool:
                    g2.drawLine(C.x, C.y, M.x, M.y);
                    break;

                case CircleDrawTool:
                    g2.drawOval(Math.min(C.x, M.x), Math.min(C.y, M.y),
                            Math.abs(M.x - C.x), Math.abs(M.y - C.y));
                    break;

                case RectangleDrawTool:
                    g2.drawRect(Math.min(C.x, M.x), Math.min(C.y, M.y),
                            Math.abs(M.x - C.x), Math.abs(M.y - C.y));
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object observable)
    {
        repaint();
    }
}



