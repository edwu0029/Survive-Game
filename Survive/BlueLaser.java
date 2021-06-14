import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Graphics;

/**
 * [BlueLaser.java]
 * A class that represents a blue laser projectile.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class BlueLaser extends Projectile{
    /*-----Reeferences to other objects-----*/
    private Level level;

    /*-----Variables for this blue laser-----*/
    private int orientation; //Vertical=0, Horizontal=1
    private int direction;

    private String path;

    //Sprite variables
    private BufferedImage sprite;
    private int width;
    private int height;
    
    /**
     * BlueLaser
     * A constructor that consutrcts a blue laser with a specified level, orientation, direction, position, and damage.
     * @param level The level that this blue laser is created for.
     * @param orientation The orientation of this blue laser.
     * @param direction The direction of this blue laser.
     * @param x The x-coordinate of the top-left corner of this blue laser relative to the window.
     * @param y The y-coordinate of the top-left corner of this blue laser relative to the window.
     * @param damage The amount of damage this blue laser does.
     */
    BlueLaser(Level level, int orientation, int direction, int x, int y, int damage){
        super(level, x, y);
        this.level = level;
        this.orientation = orientation;
        this.direction = direction;

        this.path = level.getPath();
        loadSprites();
        super.setBoundingBox(width, height);
        //TEMPORARY
        super.setDamage(100);
        if(orientation==0){ //Vertical
            super.setVelocityX(0);
            super.setVelocityY(10*direction);
        }else{ //Horizontal
            super.setVelocityX(10*direction);
            super.setVelocityY(0);
        }
    }
    /**
     * loadSprites
     * Loads the blue laser sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
        try{
            if(orientation==0){ //Vertical
                sprite = ImageIO.read(new File(path+"Sprites\\Projectile\\Blue Laser\\bluelaser_01.png"));
                width = sprite.getWidth();
                height = sprite.getHeight();
            }else{ //Horizontal
                sprite = ImageIO.read(new File(path+"Sprites\\Projectile\\Blue Laser\\bluelaser_02.png"));
                width = sprite.getWidth();
                height = sprite.getHeight();
            }
        }catch(Exception e){
            System.out.println("Error loading blue laser sprites");
        }
    }

    /**
     * draw
     * An overwridden function from the Enemy class that draws this blue laser projectile on screen.
     *@param g The graphics object that this blue laser projectile is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
