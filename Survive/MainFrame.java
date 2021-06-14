import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * [MainFrame.java]
 * A class that represents the JFrame that this game runs on.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class MainFrame extends JFrame{
    //Dimensions of this JFrame
    private int height;
    private int width;
    
    //Reference to JFrame
    private JFrame frame;

    /**
     * MainFrame
     * A constructor that creates a main frame object that can be used to run various panels.
     */
    MainFrame(){
        //TEMPORARY
        super("<Game Name>");
        this.frame = this;

        //Configure window size (1080 x 1080)
        this.height = 1080;
        this.width = 1080;
        this.setSize(width, height);
        this.setLocationRelativeTo(null); //Start the frame in the center of the screen
        this.setResizable (false);

        //When JFrame is exited, the program ends
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set JFrame to be visivle
        this.setFocusable(false);
        this.setVisible(true);
    }
    /**
     * getWindowHeight
     * Returns the height of this window in pixels.
     * @return The height of this window in pixels.
     */
    public int getWindowHeight(){
        return height;
    }
    /**
     * getWindowWidth
     * Returns the width of this window in pixels.
     * @return The width of this window in pixels.
     */
    public int getWindowWidth(){
        return width;
    }
    /**
     * clear
     * Removes all panels currently being displayed on this MainFrame.
     */
    public void clear(){
        frame.getContentPane().removeAll();
    }

    /**
     * setPanel
     * Sets a specific panel to be displayed on this MainFrame.
     * @param newPanel The panel that is to be displayed.
     */
    public void setPanel(JPanel newPanel){
        frame.add(newPanel);

        //Refocus frame
        frame.setVisible(true);
        newPanel.requestFocusInWindow();
    }
}
