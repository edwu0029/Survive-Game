import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Graphics;
/**
 * [Projectile.java]
 * An abstract class that represents a game projectile object.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
abstract class Projectile implements Drawable{
    /*-----References to other objects-----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;

    /*-----Variables for this projectlile-----*/
    private final int ID;
    private int damage; //The damage this projectile does

    //Coordinate Variables relative to the top-left corner of the window
    private int x;
    private int y;

    //Variables for the TileMap offsets when this projectile is spawned
    //Used when drawing this projectile
    private int spawnOffsetX;
    private int spawnOffsetY;

    //Variables for the current offset relative to the spawn offset
    private int offsetX;
    private int offsetY;

    //Velocity variables
    private int velocityX;
    private int velocityY;

    //Variables for sprites
    private BufferedImage projectileSprite;
    BufferedImage projectileHorizontal;
    BufferedImage projectleVertical;

    //Bounding box of this projectile, used for collision detection
    Rectangle boundingBox;

    /**
     * Projectile
     * A constructor that constructs a projectile object with a specified level and position.
     * @param level The level that this projectile is created on.
     * @param x The x-coordinate of the top-left corner of this projectile. This is relative to the tileMap.
     * @param y The y-coordinate of the top-left corner of this projectile. This is relative to the tileMap.
     */
    Projectile(Level level, int x, int y){
        this.level = level;
        this.gamePanel = level.getGamePanel();
        this.tileMap = level.getTileMap();

        this.ID = level.getNextProjectileID();
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;

        //Set up spawn offsets
        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();

        //Default damage is 0, this can be set using the setter method
        this.damage = 0;
    }
    /**
     * getID
     * Returns this projectile's unique projectile ID number.
     * @return This projectile's unique projectile ID number.
     */
    public int getID(){
        return ID;
    }
    /**
     * getRelativeX
     * Returns the x-coordinate of the top-left corner of this projectile relative
     * to the window.
     * @return The x-coordinate of the top-left corner of this projectile relative to the window.
     */
    public int getRelativeX(){
        return x+offsetX;
    }
    /**
     * getRelativeY
     * Returns the y-coordinate of the top-left corner of this projectile relative
     * to the window.
     * @return The y-coordinate of the top-left corner of this projectile relative to the window.
     */
    public int getRelativeY(){
        return y+offsetY;
    }
    /**
     * setVelocityX
     * Sets the x-velocity of this projectile. Note positive is right and negative is left.
     * @param vx The new x-velocity.
     */
    public void setVelocityX(int vx){
        this.velocityX = vx;
    }
    /**
     * setVelocityY
     * Sets the y-velocity of this projectile. Note positive is down and negative is up.
     * @param vy The new x-velocity.
     */
    public void setVelocityY(int vy){
        this.velocityY = vy;
    }
    /**
     * setDamage
     * Sets the damage of this projectile to some new specified value.
     * @param newDamage The new damage value for this projectile.
     */
    public void setDamage(int newDamage){
        this.damage = newDamage;
    }
    /**
     * setBoundingBox
     * Sets this projectile's bounding box with a specified width and height.
     * @param width The width of this projectile's bounding box.
     * @param height The height of this projectile's bounding box.
     */
    public void setBoundingBox(int width, int height){
        boundingBox = new Rectangle(x+tileMap.getOffsetX(), y+tileMap.getOffsetY(), width, height);
    }
    /**
     * update
     * Updates all of this projectile's data that cahnges over time. This includes position, and collision detection.
     */
    public void update(){
        //Check collision with non-passable wall
        if(tileMap.checkCollision(boundingBox)){
            destroy();
        }
        //Check collision with enemies
        ArrayList<Enemy>enemies = level.getEnemies();
        for(int i = 0;i<enemies.size();i++){
            if(enemies.get(i).checkCollision(boundingBox)){
                enemies.get(i).getHealthManager().takeDamage(damage);
                destroy();
                break;
            }
        }

        //Update Position
        x+=velocityX;
        y+=velocityY;

        //Update offset
        offsetX = (tileMap.getOffsetX()-spawnOffsetX);
        offsetY = (tileMap.getOffsetY()-spawnOffsetY);

        //Update boundingBox coordinates
        boundingBox.x = x;
        boundingBox.y = y;
    }
    /**
     * destroy
     * A method that destroy's this projectile from the game scene. Used upon collision.
     */
    public void destroy(){
        gamePanel.destroyProjectile(ID);
    }

    //Drawable interface
    /**
     * draw
     * An abstract method from the Drawable interface that draws this projectile on screen.
     * @param g The graphics object that this projectile is to be drawn on.
     */
    abstract public void draw(Graphics g);
}
