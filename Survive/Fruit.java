import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/**
 * [Fruit.java]
 * A class that represents a fruit item.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
public class Fruit extends Item{
    /*-----References to other objects-----*/
    private Level level;
    private GamePanel gamePanel;
    private Player player;
    private BufferedImage sprite;

    private int healAmount;
    /**
     * Fruit
     * A constructor that constructs a fruit item with a specifed level and position
     * @param level The level that this fruit is crated for.
     * @param x The x-coordinate of the top-left corner of this fruit relative to the window.
     * @param y The y-coordinate of the top-left corner of this fruit relative to the window.
     */
    Fruit(Level level, int x, int y){
        super(level, "Fruit", x, y);
        this.level = level;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.player = level.getPlayer();
        loadSprites();
        this.healAmount = 100;
        super.setBoundingBox(sprite.getWidth(), sprite.getHeight());
    }

    /**
     * loadSprites
     * Loads the fruit sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(level.getPath()+"\\Sprites\\Item\\fruit.png"));
        }catch(Exception e){
            System.out.println("Error loading fruit sprite");
        }
    }
    //Overwridden abstract methods from Item class
    /**
     * consume
     * An overwridden method from the Item class that intiates the consumption if this item.
     * @return true if this item can be consumed, false otherwise.
     */
    public boolean consume(){
        player.getHealthManager().heal(healAmount);
        gamePanel.destroyItem(getID());
        return true;
    }
    /**
     * draw
     * An overwritten method from the Item class that draws this fruit item on screen.
     * @param g The graphics object that this fruit item is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
