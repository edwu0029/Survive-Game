import java.io.File;

import java.awt.Graphics;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/**
 * [BlueLaserGun.java]
 * A class that represents a blue laser gun item.
 */
class BlueLaserGun extends Item {
    /*-----Reference to other objects-----*/
    private Level level;
    private GamePanel gamePanel;

    /*-----Variables for this blue laser gun-----*/
    private char sep;
    private BufferedImage sprite;

    /**
     * BlueLaserGun
     * A constructor that constructs a blue laser gun item with a specified item and position.
     * @param level The level that this blue laser gun is created for.
     * @param x The x-coordinate of the top-left corner of this blue laser gun relative to the tile map.
     * @param y The y-coordinate of the top-left corner of this blue laser gun relative to the tile map.
     */
    BlueLaserGun(Level level, int x, int y){
        super(level, "Blue Laser Gun", x, y);
        this.level = level;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.sep = level.getSep();
        loadSprites();
        super.setBoundingBox(sprite.getWidth(), sprite.getHeight());
    }

    /**
     * loadSprites
     * Loads the blue laser gun sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        try{
            sprite = ImageIO.read(new File(level.getPath()+""+sep+"Sprites"+sep+"Item"+sep+"bluelasergun.png"));
        }catch(Exception e){
            System.out.println("Error loading blue laser gun sprite");
        }
    }
    //Overwridden abstract methods from Item class
    /**
     * consume
     * An overwridden method from the Item class that intiates the consumption of this blue laser gun item.
     * @return true if this blue laser gun can be consumed, false otherwise.
    */
    public boolean consume(){
        //Blue Laser gun corresponds to mode numer 2 for the player
        //By consuming this item, we need to allow the player to be able to switch to and form mode 2
        boolean[] playerPossibleModes = level.getPlayer().getPossibleModes();
        playerPossibleModes[2] = true;
        //Return true since this particular item can be consumed
        return true;
    }
    /**
     * draw
     * A overwridden method from the Item class that draws this blue laser gun on screen.
     * @param g The graphics object that this blue laser gun will be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
    
}
