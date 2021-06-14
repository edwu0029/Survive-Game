import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/**
 * [Rock.java]
 * A class that represents a rock item.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Rock extends Item{
    /*-----References to other objects-----*/
    private Level level;
    /*-----Variables for this rock-----*/
    private BufferedImage sprite;
    /**
     * Rock
     * A constructor that constructs a rock object with a specified level and position.
     * @param level The level that this rock is crated for.
     * @param x The x-coordinate of the top-left corner of this rock relative to the window.
     * @param y The y-coordinate of the top-left corner of this rock relative to the window.
     */
    Rock(Level level, int x, int y){
        super(level, "Rock", x, y);
        this.level = level;
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    /**
     * loadSprites
     * Loads the rock sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(level.getPath()+"\\Sprites\\Item\\rock.png"));
        }catch(Exception e){
            System.out.println("Error rock sprite not loaded properly");
        }
    }
    //Overwridden abstract methods from Item class
    /**
     * consume
     * An overwridden method from the Item class that intiates the consumption if this item.
     * @return true if this item can be consumed, false otherwise.
     */
    public boolean consume(){
        //Return false since this rock item's can't be consumed
        return false;
    }
    /**
     * draw
     * An overwritten method from the Item class that draws this rock item on screen.
     * @param g The graphics object that this rock item is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
