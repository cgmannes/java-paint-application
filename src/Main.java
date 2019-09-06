import java.awt.*;
import javax.swing.*;

//https://stackoverflow.com/questions/25851894/what-does-getcontentpane-do-exactly


public class Main
{
    public static void main(String[] args)
    {
        // Instantiate a PaintModel object for storing data.
        PaintModel model = new PaintModel();

        // Instantiate a JFrame object to place the JComponents on.
        JFrame frame = new JFrame("Java Paint Application");
        frame.setSize(1200, 1000);
        frame.setPreferredSize(new Dimension(1200,1000));
        frame.setMinimumSize(new Dimension(600, 500));

        JMenuBar menuBar = new PaintMenuBar(model);
        frame.setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());

        SelectionPanel selectionPanel = new SelectionPanel(model);
        mainPanel.add(selectionPanel, BorderLayout.WEST);

        Canvas canvas = new Canvas(model, Color.WHITE);
        Dimension fixedDim = new Dimension(frame.getWidth(), frame.getHeight());

        ScrollPane canvasScrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        canvasScrollPane.setPreferredSize(fixedDim);

        canvasScrollPane.add(canvas);

        mainPanel.add(canvas, BorderLayout.CENTER);

        frame.add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}