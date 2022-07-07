import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [InstructionsMouseListener.java]
 * A class that represents a mouse listener for the instructions panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class InstructionsMouseListener implements MouseListener{
    /*-----References to other objects-----*/
    private MainFrame mainFrame;
    private PanelManager panelManager;
    private InstructionsPanel instructionsPanel;
    private GraphicsButton backButton;

    /**
     * InstructionsMouseListener
     * A constructor that constructs a instructions mouse listener for a specified instructions panel.
     * @param mainFrame The main frame for this game.
     * @param panelManager The panel manager for this game.
     * @param instructionsPanel The instructions panel that this start mouse lisntener is created for.
     */
    InstructionsMouseListener (MainFrame mainFrame, PanelManager panelManager, InstructionsPanel instructionsPanel){
        this.mainFrame = mainFrame;
        this.panelManager = panelManager;
        this.instructionsPanel = instructionsPanel;
        this.backButton = instructionsPanel.getBackButton();
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
        if(backButton.isInside(e.getX(), e.getY())){ //Check if start button was clicked
            panelManager.setActivePanel("StartPanel");
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
