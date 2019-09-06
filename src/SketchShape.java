import java.awt.*;
import java.io.Serializable;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;


public abstract class SketchShape implements Serializable {

    // Protected restricts access to sub-classes and classes within the same package.
    // Final indicates the value is constant.
    protected final int HIT_TEST_PADDING = 5;

    enum SketchShapeType
    {
        Line,
        Circle,
        Rectangle
    }

    SketchShapeType shapeType;
    Color color;
    int thickness;
    boolean isFilled;
    boolean isSelected;
    int xTranslate = 0;
    int yTranslate = 0;

    abstract boolean isClicked(int xClick, int yClick);

    abstract boolean isBounding(int xClick, int yClick);

    abstract void applyTranslation(int xTranslate, int yTranslate);
}

class LineSketchShape extends SketchShape
{
    // Initial and final points.
    int x1, y1, x2, y2;

    LineSketchShape(Color color, int thickness,
                    int x1, int y1, int x2, int y2)
    {
        this.shapeType = SketchShapeType.Line;
        this.color = color;
        this.thickness = thickness;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    boolean isClicked(int xClick, int yClick)
    {
        double d2;

        d2 = Line2D.ptSegDist(x1, y1, x2, y2, xClick, yClick);

        return (d2 <= (thickness + HIT_TEST_PADDING));
    }

    @Override
    boolean isBounding(int xClick, int yCLick)
    {
        return isClicked(xClick, yCLick);
    }

    @Override
    void applyTranslation(int xTranslate, int yTranslate)
    {
        this.x1 += xTranslate;
        this.y1 += yTranslate;
        this.x2 += xTranslate;
        this.y2 += yTranslate;
    }
}

class CircleSketchShape extends SketchShape
{
    int x, y, width, height;

    CircleSketchShape(Color color, int thickness, boolean isFilled,
                      int x, int y, int width, int height)
    {
        this.shapeType = SketchShapeType.Circle;
        this.color = color;
        this.thickness = thickness;
        this.isFilled = isFilled;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    boolean isClicked(int xClick, int yClick)
    {
        Ellipse2D circle = new Ellipse2D.Double(x - thickness / 2 - HIT_TEST_PADDING,
                                                y - thickness / 2 - HIT_TEST_PADDING,
                                                width + thickness + 2 * HIT_TEST_PADDING,
                                                height + thickness + 2 * HIT_TEST_PADDING);

        if (isFilled == true)
        {
            return (circle.contains(xClick, yClick));
        } else
        {
            Ellipse2D innerCircle = new Ellipse2D.Double(x + thickness / 2 + HIT_TEST_PADDING,
                                                         y + thickness / 2 + HIT_TEST_PADDING,
                                                         width - thickness - 2 * HIT_TEST_PADDING,
                                                         height - thickness - 2 * HIT_TEST_PADDING);

            return (circle.contains(xClick, yClick)) && !(innerCircle.contains(xClick, yClick));
        }
    }

    @Override
    boolean isBounding(int xClick, int yClick)
    {
        Ellipse2D circle = new Ellipse2D.Double(x - thickness / 2 - HIT_TEST_PADDING,
                                                y - thickness / 2 - HIT_TEST_PADDING,
                                                width + thickness + 2 * HIT_TEST_PADDING,
                                                height + thickness + 2 * HIT_TEST_PADDING);

        return (circle.contains(xClick, yClick));
    }

    @Override
    void applyTranslation(int xTranslate, int yTranslate)
    {
        this.x += xTranslate;
        this.y += yTranslate;
    }
}

class RectangleSketchShape extends SketchShape
{
    int x, y, width, height;

    RectangleSketchShape(Color color, int thickness, boolean isFilled,
                         int x, int y, int width, int height)
    {
        this.shapeType = SketchShapeType.Rectangle;
        this.color = color;
        this.thickness = thickness;
        this.isFilled = isFilled;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    boolean isClicked(int xClick, int yClick)
    {
        Rectangle rectangle = new Rectangle(x - thickness / 2 - HIT_TEST_PADDING,
                                            y - thickness / 2 - HIT_TEST_PADDING,
                                            x + thickness + 2 * HIT_TEST_PADDING,
                                            y + thickness + 2 * HIT_TEST_PADDING);

        if (isFilled == true)
        {
            return (rectangle.contains(xClick, yClick));
        } else
        {
            Rectangle innerRectangle = new Rectangle(x + thickness / 2 + HIT_TEST_PADDING,
                                                     y + thickness / 2 + HIT_TEST_PADDING,
                                                     x - thickness - 2 * HIT_TEST_PADDING,
                                                     y - thickness - 2 * HIT_TEST_PADDING);

            return (rectangle.contains(xClick, yClick)) && !(innerRectangle.contains(xClick, yClick));
        }
    }

    @Override
    boolean isBounding(int xClick, int yClick)
    {
        Rectangle rectangle = new Rectangle(x - thickness / 2 - HIT_TEST_PADDING,
                                            y - thickness / 2 - HIT_TEST_PADDING,
                                            x + thickness + 2 * HIT_TEST_PADDING,
                                            y + thickness + 2 * HIT_TEST_PADDING);

        return (rectangle.contains(xClick, yClick));
    }

    @Override
    void applyTranslation(int xTranslate, int yTranslate)
    {
        this.x += xTranslate;
        this.y += yTranslate;
    }
}
