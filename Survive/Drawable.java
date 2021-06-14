import java.awt.Graphics;

/**
 * [Drawawble.java]
 * A interface that allows for objects to be drawn on screen.
 */

interface Drawable {
    /**
     * draw
     * A method that allows for things to be drawn on screen.
     * @param g The graphics object in which things will be drawn on.
     */
    public void draw(Graphics g);
}
