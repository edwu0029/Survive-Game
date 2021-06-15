import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * [VerticalFence.java]
 * A class that represents a vertical fence.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class VerticalFence extends Defence{
    /*-----Variables for this vertical fence-----*/
    private String path;
    private BufferedImage sprite;

    /**
     * FenceVertical
     * A constructor that constructs a vertical fence defence.
     * @param level The leve that this vertical fence is created for.
     * @param x The x-coordinate of the top-left corner of this vertical fence relative to the window.
     * @param y The y-coordinate of the top-left corner of this vertical fence relative to the window.
     */
    VerticalFence(Level level, int x, int y){
        //All fences have a maximum health of 300
        super(level, "Vertical Fence", x, y, 300);
        this.path = level.getPath();
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    /**
     * loadSprites
     * Loads the vertical fence sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(path+"\\Sprites\\Defence\\fence_01.png"));
        }catch(Exception e){
            System.out.println("Could not load horizontal fence sprite");
        }
    }
    //Overwrriden from Defence class
    /**
     * draw
     * An overwridden method from the Defence class that draws this vertical fence on screen.
     * @param g The graphics object that this defence is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
