import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [PlacingMouseListener.java]
 * A class that represents a mouse listener for the placing panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class PlacingMouseListener implements MouseListener{
    /*-----References to other objects-----*/
    private Level level;
    private PlacingPanel placingPanel;

    /**
     * PlacingMouseListener
     * A constructor that constructs a placing mouse listener for a specifed level.
     * @param level The level that this placing mouse listener is created for.
     */
    PlacingMouseListener(Level level){
        this.level = level;
        this.placingPanel = level.getPlacingPanel();
    }
    /**
     * mouseClicked
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has been clicked. In this case, clicked means pressed and then released.
     */
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!!");
        System.out.println("X:"+e.getX() + " y:"+e.getY());
        
        placingPanel.placeDefence();
    }
    /**
     * mousePressed
     * An overwridden method from the MouseListener interface that executes code when the mouse
     * has been pressed
     */
    public void mousePressed(MouseEvent e) {
        
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
