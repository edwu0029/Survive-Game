import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [InventoryMouseListener.java]
 * A class that represents a mouse listener for the inventory panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class InventoryMouseListener implements MouseListener{
    /*-----References to other objects-----*/
    private Level level;
    private PanelManager panelManager;
    private InventoryPanel inventoryPanel;

    private GraphicsButton backButton;
    private GraphicsButton consumeButton;

    /**
     * InventoryMouseListener
     * A constructor that constructs a inventory mouse listener for a specified inventory panel.
     * @param level The level that this invetory mouse listener is created on.
     */
    InventoryMouseListener (Level level){
        this.level = level;
        this.panelManager = level.getPanelManager();
        this.inventoryPanel = panelManager.getInventoryPanel();
        this.panelManager = panelManager;
        this.inventoryPanel = inventoryPanel;

        this.backButton = inventoryPanel.getBackButton();
        this.consumeButton = inventoryPanel.getConsumeButton();
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
            panelManager.setActivePanel("GamePanel");
        }else if(consumeButton.isInside(e.getX(), e.getY())){
            inventoryPanel.consumeSelected();
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
