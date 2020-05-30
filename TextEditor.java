import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextEditor implements ActionListener
{
    // Variable holds bounds of the screen
    Rectangle screenBounds;
    
    // JPanel and its appropriate variables
    JPanel contentPane;
    JTextArea textArea;
    JScrollPane scrollArea;

    // JMenuBar and its appropriate variables
    JMenuBar jMenuBar;
    JMenu jMenuFile, jMenuEdit;

    // Constructor
    public TextEditor()
    {
        screenBounds = getBoundsOfScreen();
    }

    // Creates a JPanel with a scrollable text area
    public JPanel createJPanel()
    {
        contentPane = new JPanel();
        textArea = new JTextArea(screenBounds.height / 20, screenBounds.width / 15);
        scrollArea = new JScrollPane(textArea);

        contentPane.add(scrollArea);

        return contentPane;

    }  

    // Creates a JMenuBar with options for the text area
    public JMenuBar createJMenuBar()
    {
        jMenuBar = new JMenuBar();
        jMenuFile = new JMenu("File");
        jMenuEdit = new JMenu("Edit");

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuEdit);

        return jMenuBar;

    }

    public static void main(String[] beans)
    {
        TextEditor textEditor = new TextEditor();

        // Sets up frame for text editor
        JFrame frame = new JFrame("NoteBook");

        frame.setJMenuBar(textEditor.createJMenuBar());
        frame.setContentPane(textEditor.createJPanel());

        frame.setSize(textEditor.screenBounds.width, textEditor.screenBounds.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    // Any action performed is dealt with here
    public void actionPerformed(ActionEvent e)
    {

    }

    // Method returns a Rectangle containing the width and height of the display
    public Rectangle getBoundsOfScreen()
    {
        Rectangle screenBounds = new Rectangle();
        GraphicsEnvironment localGrapEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] deviceGraphics = localGrapEnv.getScreenDevices();

        for (int i = 0; i < deviceGraphics.length; i++) 
        {
            GraphicsDevice deviceGraph2 = deviceGraphics[i];

            GraphicsConfiguration[] graphicsConfig = deviceGraph2.getConfigurations();
            for (int j = 0; j < graphicsConfig.length; j++)
                screenBounds = screenBounds.union(graphicsConfig[j].getBounds());
        } 

        return screenBounds;
    }
}