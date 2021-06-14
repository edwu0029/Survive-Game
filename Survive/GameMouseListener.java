import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [GameMouseListener.java]
 * A class that represents the mouse listener for the game panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class GameMouseListener implements MouseListener{
    /*-----Reference to other objects-----*/
    private Player player;

    /**
     * GameMouseListener
     * A constructor that constructs a game mouse listener for a specific level.
     * @param level The level this game mouse listener is created for.
     */
    GameMouseListener(Level level){
        this.player = level.getPlayer();
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
        player.attack();
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
