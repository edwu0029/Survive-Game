import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [GameOverMouseListener.java]
 * A class that represents a mouse listener for the game over panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class GameOverMouseListener implements MouseListener{
    /*----References to other objects-----*/
    private Level level;

    /**
     * GameOverMouseListener
     * A constructor that constructs a game over mouse listener for a specified level.
     * @param level The level that this game over mouse listener is created for.
     */
    GameOverMouseListener(Level level){
        this.level = level;
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
        //TEMPORARY
        level.resetPanels();
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
