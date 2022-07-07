/**
 * [Moveable.java]
 * A interface that allows things to move around in the four overall directions(up, down, left, right).
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
interface Moveable {
    /**
     * moveUp
     * A method that moves this object upward.
     */
    public void moveUp();
    /**
     * moveDown
     * A method that moves this object downward.
     */
    public void moveDown();
    /**
     * moveLeft
     * A method that moves this object leftward.
     */
    public void moveLeft();
    /**
     * moveRight
     * A method that moves this object rightward.
     */
    public void moveRight();
}
