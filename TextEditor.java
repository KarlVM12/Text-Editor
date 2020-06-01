import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class TextEditor implements ActionListener
{
    // Variable holds bounds of the screen
    Rectangle screenBounds;
    
    static JFrame frame;

    // JPanel and its appropriate variables
    JPanel contentPane;
    JTextArea textArea;
    JScrollPane scrollArea;

    // JMenuBar and its appropriate variables
    JMenuBar jMenuBar;
    JMenu jMenuFile, jMenuEdit;
    JMenuItem[] jMenuFileItems;

    JOptionPane dialogPanel;
    String currentFileOpen;

    // Constructor
    public TextEditor()
    {
        screenBounds = getBoundsOfScreen();

        jMenuFileItems = new JMenuItem[2];

        dialogPanel = new JOptionPane();

        currentFileOpen = null;
    }

    // Creates a JPanel with a scrollable text area
    public JPanel createJPanel()
    {
        contentPane = new JPanel();
        textArea = new JTextArea(screenBounds.height / 20, screenBounds.width / 15);
        textArea.setTabSize(2);

        scrollArea = new JScrollPane(textArea);

        contentPane.add(scrollArea);

        return contentPane;

    }  

    // Creates a JMenuBar with options for the text area
    public JMenuBar createJMenuBar()
    {
        // initializes all needed JMenuBar items
        jMenuBar = new JMenuBar();
        jMenuFile = new JMenu("File");
        jMenuEdit = new JMenu("Edit");

        // Creates options and action listeners in the File JMenu
        jMenuFileItems[0] = new JMenuItem("Open File");
        jMenuFileItems[0].addActionListener(this);
        jMenuFileItems[1] = new JMenuItem("Save and Close");
        jMenuFileItems[1].addActionListener(this);

        // adds items to File JMenu
        for (int menuItem = 0; menuItem < jMenuFileItems.length; menuItem++)
            jMenuFile.add(jMenuFileItems[menuItem]);

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuEdit);

        return jMenuBar;

    }

    public static void main(String[] beans)
    {
        TextEditor textEditor = new TextEditor();

        // Sets up frame for text editor
        frame = new JFrame("NoteBook");

        frame.setJMenuBar(textEditor.createJMenuBar());
        frame.setContentPane(textEditor.createJPanel());

        frame.setSize(textEditor.screenBounds.width, textEditor.screenBounds.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    // Any action performed is dealt with here
    public void actionPerformed(ActionEvent e)
    {
        // if action occurs on 'Open File' in File menu
        if (e.getSource() == jMenuFileItems[0])
        {
            try
            {
                openFile(); // attempts to open file
            }
            catch (IOException ioe)
            {
                displayError(ioe); // if file not found, displays an error
            }
            catch (NullPointerException npe)
            {
                displayError(npe);
            }
        } 
        
        // if actions occurs on 'Save and Close' in File menu
        if (e.getSource() == jMenuFileItems[1]) 
        {
            try
            {
                saveAndClose(); // attemps to save file
            }
            catch (IOException ioe)
            {
                displayError(ioe); // if no file found, displays an error
            }
            catch (NullPointerException npe)
            {
                displayError(npe); 
            }

        }

    }

    // Opens a file in this directory when the file name is inputted
    public void openFile() throws IOException
    {
        // asks for file name
        String fileName = dialogPanel.showInputDialog("What file do you want to open?");
        currentFileOpen = fileName;

        Scanner file = new Scanner(new File(fileName));

        // while file has another line
        while (file.hasNext())
        {
            // sets the text area in the text editor to the contents of the file
            String oneLine = file.nextLine();
            textArea.append(oneLine + "\n");
        }
    }

    // Saves all the text in the text area to the file currently open
    public void saveAndClose() throws IOException
    {
        // if no file opened before, throws an exception
        if (currentFileOpen.equals(null))
            throw new IOException("No file name provided.");

        PrintWriter outputStream = new PrintWriter(new FileWriter(currentFileOpen));
        
        Scanner currentFileData = new Scanner(textArea.getText());

        // while there are more lines of data, it is printed to the file
        while (currentFileData.hasNext())
            outputStream.println(currentFileData.nextLine());

        outputStream.close();
        currentFileOpen = null;
        textArea.setText("");

    }

    // displays an error depending on the type of exception passed through
    public void displayError(Exception e)
    {
        if (e instanceof IOException)
            dialogPanel.showMessageDialog(frame, "File does not exist in this directory.");
        else if (e instanceof NullPointerException)
            dialogPanel.showMessageDialog(frame, "Cancelled operation.");
        else 
            dialogPanel.showMessageDialog(frame, "Error occurred.");
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