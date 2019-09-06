import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;


/**
 * Box class is a lightweight container that uses box layout manager. It contains classes for
 * creating invisible components for defining glue, strut,and rigid areas.
 */
public class SelectionPanel extends Box implements Observer
{
    //Declare tool buttons.
    JButton selectButton; JButton eraseButton; JButton lineButton;
    JButton circleButton; JButton rectangleButton; JButton fillButton;

    // Declare color buttons.
    JButton color1Button; JButton color2Button; JButton color3Button;
    JButton color4Button; JButton color5Button; JButton color6Button;

    // Declare line thickness buttons.
    JButton thickness1Button; JButton thickness2Button;
    JButton thickness3Button; JButton thickness4Button;

    // Declare button groups.
    ButtonGroup toolButtonGroup;
    ButtonGroup colorButtonGroup;
    ButtonGroup lineThicknessButtonGroup;

    // Declare sub-panels.
    JPanel toolPanel;
    JPanel colorPanel;
    JPanel lineThicknessPanel;

    PaintModel model;

    public SelectionPanel(PaintModel model)
    {
        super(BoxLayout.Y_AXIS);

        this.model = model;

        model.addObserver(this);

        /**
         * Instantiate the tool buttons, color buttons, and line thickness buttons.
         */
        // Instantiate tool buttons.
        selectButton = new JButton("Select"); eraseButton = new JButton("Erase");
        lineButton = new JButton("Line"); circleButton = new JButton("Circle");
        rectangleButton = new JButton("Rect."); fillButton = new JButton("Fill");

        // Instantiate color buttons.
        color1Button = new JButton("Black"); color2Button = new JButton("White");
        color3Button = new JButton("Red"); color4Button = new JButton("Green");
        color5Button = new JButton("Blue"); color6Button = new JButton("Pink");

        // Instantiate line thickness buttons.
        thickness1Button = new JButton(("Thickness Level 1")); thickness2Button = new JButton(("Thickness Level 2"));
        thickness3Button = new JButton(("Thickness Level 3")); thickness4Button = new JButton(("Thickness Level 4"));

        // Instantiate button groups.
        toolButtonGroup = new ButtonGroup();
        colorButtonGroup = new ButtonGroup();
        lineThicknessButtonGroup = new ButtonGroup();

        /**
         * Instantiate the tool panel, color panel, and the line thickness panel.
         */
        toolPanel = new JPanel( new GridLayout(3,2,5,5) );
        colorPanel = new JPanel( new GridLayout(3,2,5,5) );
        lineThicknessPanel = new JPanel( new GridLayout(4,1,5,5) );

        /**
         * ToolButton panel.
         */
        // Highlight default tool selection.
        setButtonBorder(selectButton);

        /**
         * Add action listeners to the tool buttons.
         */
        // Add action listener to selection tool.
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentTool = PaintModel.Tool.SelectTool;
                defaultBorderToolButtonGroup();
                setButtonBorder(selectButton);
            }
        });

        // Add action listener to erase tool.
        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentTool = PaintModel.Tool.EraseTool;
                defaultBorderToolButtonGroup();
                setButtonBorder(eraseButton);
            }
        });

        // Add action listener to line tool.
        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentTool = PaintModel.Tool.LineDrawTool;
                defaultBorderToolButtonGroup();
                setButtonBorder(lineButton);
            }
        });

        // Add action listener to circle tool.
        circleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentTool = PaintModel.Tool.CircleDrawTool;
                defaultBorderToolButtonGroup();
                setButtonBorder(circleButton);
            }
        });

        // Add action listener to rectangle tool.
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentTool = PaintModel.Tool.RectangleDrawTool;
                defaultBorderToolButtonGroup();
                setButtonBorder(rectangleButton);
            }
        });

        // Add action listener to fill tool.
        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentTool = PaintModel.Tool.FillTool;
                defaultBorderToolButtonGroup();
                setButtonBorder(fillButton);
            }
        });

        // Add tool buttons to a ButtonGroup.
        toolButtonGroup.add(selectButton);
        toolButtonGroup.add(eraseButton);
        toolButtonGroup.add(lineButton);
        toolButtonGroup.add(circleButton);
        toolButtonGroup.add(rectangleButton);
        toolButtonGroup.add(fillButton);

        // Add tool buttons to tool panel.
        toolPanel.add(selectButton);
        toolPanel.add(eraseButton);
        toolPanel.add(lineButton);
        toolPanel.add(circleButton);
        toolPanel.add(rectangleButton);
        toolPanel.add(fillButton);

        /**
         * ColorButton panel.
         */
        // Highlight default color option.
        setButtonBorder(color1Button);

        /**
         * Add action listeners to the color button.
         */
        // Add action listener to the black color button.
        color1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentColor = model.colorOptions[0];
                model.changeSelectedShapeColor(model.currentColor);
                defaultBorderColorButtonGroup();
                setButtonBorder(color1Button);
            }
        });

        // Add action listener to the white color button.
        color2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentColor = model.colorOptions[1];
                model.changeSelectedShapeColor(model.currentColor);
                defaultBorderColorButtonGroup();
                setButtonBorder(color2Button);
            }
        });

        // Add action listener to the red color button.
        color3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentColor = model.colorOptions[2];
                model.changeSelectedShapeColor(model.currentColor);
                defaultBorderColorButtonGroup();
                setButtonBorder(color3Button);
            }
        });

        // Add action listener to the green color button.
        color4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentColor = model.colorOptions[3];
                model.changeSelectedShapeColor(model.currentColor);
                defaultBorderColorButtonGroup();
                setButtonBorder(color4Button);
            }
        });

        // Add action listener to the blue color button.
        color5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentColor = model.colorOptions[4];
                model.changeSelectedShapeColor(model.currentColor);
                defaultBorderColorButtonGroup();
                setButtonBorder(color5Button);
            }
        });

        // Add action listener to the pink color button.
        color6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentColor = model.colorOptions[5];
                model.changeSelectedShapeColor(model.currentColor);
                defaultBorderColorButtonGroup();
                setButtonBorder(color6Button);
            }
        });

        // Add color buttons to a ButtonGroup.
        colorButtonGroup.add(color1Button);
        colorButtonGroup.add(color2Button);
        colorButtonGroup.add(color3Button);
        colorButtonGroup.add(color4Button);
        colorButtonGroup.add(color5Button);
        colorButtonGroup.add(color6Button);

        // Add color buttons to color panel.
        colorPanel.add(color1Button);
        colorPanel.add(color2Button);
        colorPanel.add(color3Button);
        colorPanel.add(color4Button);
        colorPanel.add(color5Button);
        colorPanel.add(color6Button);

        /**
         * LineThickness panel.
         */
        // Highlight default line thickness.
        setButtonBorder(thickness1Button);

        /**
         * Add action listeners to the line thickness buttons.
         */

        // Add action listener to the thickness level 1 button.
        thickness1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentLineThickness = model.linethicknessOptions[0];
                model.changeSelectedShapeThickness(model.currentLineThickness);
                defaultBorderThicknessButtonGroup();
                setButtonBorder(thickness1Button);
            }
        });

        // Add action listener to the thickness level 2 button.
        thickness2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentLineThickness = model.linethicknessOptions[1];
                model.changeSelectedShapeThickness(model.currentLineThickness);
                defaultBorderThicknessButtonGroup();
                setButtonBorder(thickness2Button);
            }
        });

        // Add action listener to the thickness level 3 button.
        thickness3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.currentLineThickness = model.linethicknessOptions[2];
                model.changeSelectedShapeThickness(model.currentLineThickness);
                defaultBorderThicknessButtonGroup();
                setButtonBorder(thickness3Button);
            }
        });

        // Add action listener to the thickness level 4 button.
        thickness4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultBorderThicknessButtonGroup();
                model.currentLineThickness = model.linethicknessOptions[3];
                model.changeSelectedShapeThickness(model.currentLineThickness);
                setButtonBorder(thickness4Button);
            }
        });

        // Add line thickness buttons to a ButtonGroup.
        lineThicknessButtonGroup.add(thickness1Button);
        lineThicknessButtonGroup.add(thickness2Button);
        lineThicknessButtonGroup.add(thickness3Button);
        lineThicknessButtonGroup.add(thickness4Button);

        // Add line thickness buttons to line thickness panel.
        lineThicknessPanel.add(thickness1Button);
        lineThicknessPanel.add(thickness2Button);
        lineThicknessPanel.add(thickness3Button);
        lineThicknessPanel.add(thickness4Button);


        /**
         * Construct selectionPanel.
         */
        this.add(Box.createVerticalStrut(10));
        this.add(toolPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(colorPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(lineThicknessPanel);
        this.add(Box.createVerticalStrut(10));

    }


    /**
     * Member functions.
     */
    public void setButtonBorder(JButton button) {
        button.setBorder(new LineBorder(Color.MAGENTA, 3));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
    }

    public void defaultBorderToolButtonGroup() {
        Border defaultBorder = UIManager.getBorder("Button.border");
        selectButton.setBorder(defaultBorder);
        eraseButton.setBorder(defaultBorder);
        lineButton.setBorder(defaultBorder);
        circleButton.setBorder(defaultBorder);
        rectangleButton.setBorder(defaultBorder);
        fillButton.setBorder(defaultBorder);
    }

    public void defaultBorderColorButtonGroup() {
        Border defaultBorder = UIManager.getBorder("Button.border");
        color1Button.setBorder(defaultBorder);
        color2Button.setBorder(defaultBorder);
        color3Button.setBorder(defaultBorder);
        color4Button.setBorder(defaultBorder);
        color5Button.setBorder(defaultBorder);
        color6Button.setBorder(defaultBorder);
    }

    public void defaultBorderThicknessButtonGroup() {
        Border defaultBorder = UIManager.getBorder("Button.border");
        thickness1Button.setBorder(defaultBorder);
        thickness2Button.setBorder(defaultBorder);
        thickness3Button.setBorder(defaultBorder);
        thickness4Button.setBorder(defaultBorder);
    }

    @Override
    public void update(Object observable)
    {
//        if (model.currentTool != PaintModel.Tool.EraseTool) {
//            if (model.currentColor == model.colorOptions[0]) {
//                defaultBorderColorButtonGroup();
//                setButtonBorder(color1Button);
//            } else if (model.currentColor == model.colorOptions[1]) {
//                defaultBorderColorButtonGroup();
//                setButtonBorder(color1Button);
//            } else if (model.currentColor == model.colorOptions[2]) {
//                defaultBorderColorButtonGroup();
//                setButtonBorder(color2Button);
//            } else if (model.currentColor == model.colorOptions[3]) {
//                defaultBorderColorButtonGroup();
//                setButtonBorder(color3Button);
//            } else if (model.currentColor == model.colorOptions[4]) {
//                defaultBorderColorButtonGroup();
//                setButtonBorder(color4Button);
//            } else if (model.currentColor == model.colorOptions[5]) {
//                defaultBorderColorButtonGroup();
//                setButtonBorder(color5Button);
//            }
//        }
//
//        repaint();
    }

}

