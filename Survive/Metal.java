import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/**
 * [Metal.java]
 * A class that represents a metal item.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Metal extends Item{
    /*-----Variables for this metal item-----*/
    private BufferedImage sprite;
    private String path;

    /**
     * Metal
     * A constructor that constructs a metal item with a specified level and position.
     * @param level The level that this metal is created for.
     * @param x The x-coordinate of the top-left corner of this metal relative to the window.
     * @param y The y-coordinate of the top-left corner of this metal relative to the window.
     */
    Metal(Level level, int x, int y){
        super(level, "Metal", x, y);
        this.path = level.getPath();
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    /**
     * loadSprites
     * Loads the metal sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(path+"\\Sprites\\Item\\metal.png"));
        }catch(Exception e){
            System.out.println("Error rock sprite not loaded properly");
        }
    }
    //Overwridden abstract methods from Item class
    /**
     * consume
     * An overwridden method from the Item class that intiates the consumption of this mteal item.
     * @return true if this metal item can be consumed, false otherwise.
     */
    public boolean consume(){
        //Metal items can't be consumed without crafting
        return false;
    }
    /**
     * draw
     * An overwritten method from the Item class that draws this metal item on screen.
     * @param g The graphics object that this metal item is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
