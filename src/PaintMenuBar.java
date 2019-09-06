import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.List;
import javax.swing.filechooser.*;


public class PaintMenuBar extends JMenuBar implements Observer
{
    PaintModel model;

    public PaintMenuBar(PaintModel model)
    {
        // Invoke parent class constructor.
        super();

        // Extract values from the model.
        this.model = model;

        // File option in menu bar.
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);

        // View option in menu bar.
        JMenu viewMenu = new JMenu("View");
        this.add(viewMenu);

        JMenuItem fileItem;

        /***********************************
         * Create options for the File menu.
         ***********************************/

        // Create a New option in the File menu.
        fileItem = new JMenuItem(new AbstractAction("New")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                newFile();
            }
        });

        // Add current fileItem to fileMenu.
        fileMenu.add(fileItem);


        // Create a Load option in the File menu.
        fileItem = new JMenuItem(new AbstractAction("Load")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadFile();
            }
        });

        // Add current fileItem to fileMenu.
        fileMenu.add(fileItem);


        // Create a Save option in the File
        fileItem = new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveFile();
            }
        });

        // Add current fileItem to File menu.
        fileMenu.add(fileItem);

        /***********************************
         * Create options for the View menu.
         ***********************************/

        JMenuItem viewItem;

        // Create a Full Screen option is the View menu.
        viewItem = new JMenuItem(new AbstractAction("Full Screen")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fullScreen();
            }
        });

        viewMenu.add(viewItem);


        // Create a Fit to Screen option in the View menu.
        viewItem = new JMenuItem(new AbstractAction("Fit to Screen")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fitToScreen();

            }
        });

        viewMenu.add(viewItem);

    }

    /*******************
     * Member functions.
     *******************/

    // Function for creating a new file.
    void newFile()
    {
         model.drawnShapes.clear();
         model.notifyAllObservers();
    }

    // Function for loading a file.
    void loadFile()
    {
        try {
            // Read objects from file.
            FileInputStream fileInput;
            ObjectInputStream objInput = null;

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Serialized Java Objects (.ser)", "ser");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fileInput = new FileInputStream(chooser.getSelectedFile());
                objInput = new ObjectInputStream(fileInput);

                // Deserialization of objects.
                List<SketchShape> loadedList;

                loadedList = listOfSketchShapeCast(objInput.readObject());

                model.drawnShapes = loadedList;
                model.deselectAllShapes();
                model.notifyAllObservers();

                fileInput.close();
                objInput.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // Function for saving the current file.
    void saveFile()
    {
        String fileName = "javaPaintDrawing.ser";

        try {
            FileOutputStream fileOutput;
            ObjectOutputStream objOutput;

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Serialized Java Object (.ser)", "ser");

            chooser.setFileFilter(filter);

            int returnVal = chooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                if (chooser.getSelectedFile().getName().endsWith(".ser"))
                {
                    fileOutput = new FileOutputStream(chooser.getSelectedFile());
                } else
                {
                    fileOutput = new FileOutputStream(chooser.getSelectedFile() + ".ser");
                }

                objOutput = new ObjectOutputStream(fileOutput);
                objOutput.writeObject(model.drawnShapes);
                objOutput.close();
                fileOutput.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    List<SketchShape> listOfSketchShapeCast(Object obj)
    {
        return (List<SketchShape>) obj;
    }

    void fullScreen()
    {
        //
        //
    }

    void fitToScreen()
    {
        //
        //
    }

    @Override
    public void update(Object observable)
    {

    }

}

