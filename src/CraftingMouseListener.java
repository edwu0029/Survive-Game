import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [CraftingMouseListener.java]
 * A class that represents a mouse listener for the crafting panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class CraftingMouseListener implements MouseListener{
    /*-----References to other objects-----*/
    private Level level;
    private PanelManager panelManager;
    private CraftingPanel craftingPanel;

    private GraphicsButton backButton;
    private GraphicsButton craftButton;

    /**
     * CraftingMouseListener
     * A constructor that constructs a crafting mouse listener for a specified level.
     * @param level The level that this crafting mouse listener is created for.
     */
    CraftingMouseListener(Level level){
        this.level = level;
        this.panelManager = level.getPanelManager();
        this.craftingPanel = panelManager.getCraftingPanel();

        this.backButton = craftingPanel.getBackButton();
        this.craftButton = craftingPanel.getCraftButton();
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
        if(backButton.isInside(e.getX(), e.getY())){ //Check if vack button was clicked
            panelManager.setActivePanel("GamePanel");
        }else if(craftButton.isInside(e.getX(), e.getY())){
            craftingPanel.craftRecipe();
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
