import java.awt.*;
import java.util.*;
import java.util.List;


public class PaintModel
{
    private List<Observer> observers;

    public enum Tool
    {
        SelectTool,
        EraseTool,
        LineDrawTool,
        CircleDrawTool,
        RectangleDrawTool,
        FillTool
    };

    Color colorOptions[] = {Color.BLACK,
                            Color.WHITE,
                            Color.RED,
                            Color.GREEN,
                            Color.BLUE,
                            Color.PINK};

    Color presetColor[] = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK};

    int linethicknessOptions[] = {2, 4, 6, 8};

    Tool currentTool;
    Color currentColor;
    int currentLineThickness;

    List<SketchShape> drawnShapes = new ArrayList<SketchShape>();
    SketchShape selectedShape;


    public PaintModel()
    {
        this.observers = new ArrayList<Observer>();

        // Set initial tool, color, and line thickness.
        currentTool = Tool.SelectTool;
        currentColor = colorOptions[0];
        currentLineThickness = linethicknessOptions[0];
    }

    public void addShape(SketchShape shape)
    {
        this.drawnShapes.add(shape);
    }

    public void selectShape(SketchShape shape)
    {
        if (shape != null)
        {
            shape.isSelected = true;
            selectedShape = shape;
            currentColor = shape.color;
            currentLineThickness = shape.thickness;

            drawnShapes.remove(shape);
            drawnShapes.add(shape);

            notifyAllObservers();
        }
    }

    public void deselectAllShapes()
    {
        if (selectedShape != null)
        {
            selectedShape.isSelected = false;
        }

        selectedShape = null;

        notifyAllObservers();
    }

    public void changeSelectedShapeColor(Color color)
    {
        if (selectedShape != null)
        {
            selectedShape.color = color;

            notifyAllObservers();
        }
    }

    public void changeSelectedShapeThickness(int thickness)
    {
        if (selectedShape != null)
        {
            selectedShape.thickness = thickness;

            notifyAllObservers();
        }
    }

    public void eraseShape(SketchShape drawnShape)
    {
        drawnShapes.remove(drawnShape);

        notifyAllObservers();
    }

    public void fillShape(SketchShape drawnShape)
    {
        drawnShape.color = currentColor;
        drawnShape.isFilled = true;

        notifyAllObservers();
    }

    /**
     * Add an observer that is notified when this model changes.
     */
    public void addObserver(Observer observer)
    {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from the list of observes for this model.
     */
    public void removeObserver(Observer observer)
    {
        this.observers.remove(observer);
    }

    /**
     * Notify all observer about changes to this model.
     */
    public void notifyAllObservers()
    {
        for (Observer observer : this.observers)
        {
            observer.update(this);
        }
    }

}
