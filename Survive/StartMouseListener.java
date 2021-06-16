import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [StartMouseListener.java]
 * A class that represents a mouse listener for the start panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class StartMouseListener implements MouseListener{
    /*-----References to other objects-----*/
    private MainFrame mainFrame;
    private PanelManager panelManager;
    private StartPanel startPanel;
    private GraphicsButton startButton;
    private GraphicsButton instructionsButton;
    private GraphicsButton quitButton;

    /**
     * StartMousListener
     * A constructor that constructs a start mouse listener for a specifiedl start panel.
     * @param mainFrame The main frame for this game.
     * @param panelManager The panel manager for this game.
     * @param startPanel The start panel that this start mouse lisntener is created for.
     */
    StartMouseListener (MainFrame mainFrame, PanelManager panelManager, StartPanel startPanel){
        this.mainFrame = mainFrame;
        this.panelManager = panelManager;
        this.startPanel = startPanel;
        this.startButton = startPanel.getStartButton();
        this.instructionsButton = startPanel.getInstructionsButton();
        this.quitButton = startPanel.getQuitButton();
    }

    /**
     * mouseClicked
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has been clicked. In this case, clicked means pressed and then released.
     */
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * mousePressed
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has been pressed
     */
    public void mousePressed(MouseEvent e) {
        if(startButton.isInside(e.getX(), e.getY())){ //Check if start button was clicked
            new Level(mainFrame, panelManager);
        }else if(instructionsButton.isInside(e.getX(), e.getY())){ //Check if instructions button was clicked
            panelManager.setActivePanel("InstructionsPanel");
        }else if(quitButton.isInside(e.getX(), e.getY())){ //Check if quit button was clicked
            //Exit program by terminating JVM
            System.exit(0);
        }
    }
    /**
     * mouseReleased
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has been released.
     */
    public void mouseReleased(MouseEvent e) {
    }
    /**
     * mouseEntered
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has entered some component.
     */
    public void mouseEntered(MouseEvent e) {
    }
    /**
     * mouseExited
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has exited some component.
     */
    public void mouseExited(MouseEvent e) {
    }
}
