import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/**
 * [Wood.java]
 * A class that represents a wood item.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Wood extends Item{

    private String path;
    private char sep;
    private BufferedImage sprite;

    Wood(Level level, int x, int y){
        super(level, "Wood", x, y);
        this.path = level.getPath();
        this.sep = level.getSep();
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    /**
     * loadSprites
     * Loads the wood sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        try{
            sprite = ImageIO.read(new File(path+sep+"Sprites"+sep+"Item"+sep+"wood.png"));
        }catch(Exception e){
            System.out.println("Error wood sprite not loaded properly");
        }
    }
    //Overwridden abstract methods from Item class
    /**
     * consume
     * An overwridden method from the Item class that intiates the consumption if this item.
     * @return true if this item can be consumed, false otherwise.
     */
    public boolean consume(){
        //Return false since wood item can't be consumed on its own
        return false;
    }
    /**
     * draw
     * An overwritten method from the Item class that draws this wood item on screen.
     * @param g The graphics object that this wood item is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
