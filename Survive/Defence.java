import java.awt.Graphics;
import java.awt.Rectangle;
import java.rmi.MarshalException;

/**
 * [Defence.java]
 * A class that represents a defence object.
 * @author Edward Wu
 * @version 1.0, June 11, 2021
 */
abstract class Defence implements Drawable{

    /*-----References to other objects-----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private Player player;

    /*-----Variables for this Defence-----*/
    private final int ID;
    private String type;

    //Coordinate variables relative to top-left corner of window
    private int x;
    private int y;

    //Variables for the TileMap offsets when this defence is spawned
    //Used when drawing this defence
    private int spawnOffsetX;
    private int spawnOffsetY;

    //Variables for the current offset relative to the spawn offset
    private int offsetX;
    private int offsetY;

    Rectangle boundingBox;
    HealthManager healthManager;
    private int maxHealth;
    

    /**
     * Defence
     * Constructs a defence object with for a specified level with some specified name, and coordinate.
     * @param level The level that this defence is being constructed for.
     * @param type The type defence.
     * @param x The x-coordinate of the top-left corner of this defence. This is relative to the tileMap, not window.
     * @param y The y-coordinate of the top-left corner of this defence. This is relative to the tileMap, not window.
     * @param maxHealth The maximum health of this defence.
     */
    Defence(Level level, String type, int x, int y, int maxHealth){
        this.level = level;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.tileMap = level.getTileMap();
        this.player = level.getPlayer();

        this.ID = level.getNextDefenceID();
        this.type = type;
        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;

        //Set spawn offsets
        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();

        //Set HealthManager
        this.healthManager = new HealthManager(maxHealth);
    }
    /**
     * getID
     * Returns this defence's unique defence ID number.
     * @return This defence's unique defence ID number.
     */
    public int getID(){
        return ID;
    }
    /**
     * getType
     * Returns the type of defence of this defence.
     * @return The type of defence of this defence.
     */
    public String getType(){
        return type;
    }
    /**
     * setX
     * Sets the absolute x-coordinate of the top-left corner of this defence.
     * @param newX
     */
    public void setX(int newX){
        x = newX;
    }
    /**
     * getAbsoluteX
     * Returns the absolute x-coordinate of the top-left corner of this defence.
     * @return Returns the absolute x-coordinate of the top-left corner of this defence.
     */
    public int getAbsoluteX(){
        return x;
    }
    /**
     * getRelativeX
     * Returns the x-coordinate of the top-left corner of this defence relative
     * to the window.
     * @return The x-coordinate of the top-left corner of this defence relative to the window.
     */
    public int getRelativeX(){
        return x+offsetX;
    }
    
    /**
     * setY
     * Sets the absolute y-coordinate of the top-left corner of this defence.
     * @param newY
     */
    public void setY(int newY){
        y = newY;
    }
    /**
     * getAbsoluteY
     * Returns the absolute y-coordinate of the top-left corner of this defence.
     * @return Returns the absolute y-coordinate of the top-left corner of this defence.
     */
    public int getAbsoluteY(){
        return y;
    }
    /**
     * getRelativeY
     * Returns the y-coordinate of the top-left corner of this defence relative
     * to the window.
     * @return The y-coordinate of the top-left corner of this defence relative to the window.
     */
    public int getRelativeY(){
        return y+offsetY;
    }
    /**
     * setSpawnOffsetX
     * Sets the spawn x offset of this defence to some specified value.
     * @param newSpawnOffsetX The new spawn x offset.
     */
    public void setSpawnOffsetX(int newSpawnOffsetX){
        spawnOffsetX = newSpawnOffsetX;
    }
    /**
     * setSpawnOffsetY
     * Sets the spawn y offset of this defence to some specified value.
     * @param newSpawnOffsetX The new spawn y offset.
     */
    public void setSpawnOffsetY(int newSpawnOffsetY){
        spawnOffsetY = newSpawnOffsetY;
    }
    /**
     * getHealthManager
     * Returns the reference to this defence's health manager.
     * @return The reference to this defence's health manager.
     */
    public HealthManager getHealthManager(){
        return healthManager;
    }
    /**
     * setBoundingBox
     * Sets this defence's bounding box with a specified width and height.
     * @param width The width of this defence's bounding box.
     * @param height The height of this defence's bounding box.
     */
    public void setBoundingBox(int width, int height){
        boundingBox = new Rectangle(x, y, width, height);
    }
    /**
     * update
     * Updates all of this defence's data that changes over time. This includes offsets
     * and the bounding box coordinates.
     */
    public void update(){
        //Update offset data for drawing
        offsetX = tileMap.getOffsetX()-spawnOffsetX;
        offsetY = tileMap.getOffsetY()-spawnOffsetY;

        //Updat bounidng box coordinates
        boundingBox.x = this.getRelativeX();
        boundingBox.y = this.getRelativeY();

        if(healthManager.getHealth()<=0){
            death();
        }
    }
    /**
     * checkCollision
     * Checks if the specified box has collided with the bounding box of this defence.
     * @param box The box that is to be checked.
     * @return true if the box collides with this defence, false otherwise.
     */
    public boolean checkCollision(Rectangle box){
        return boundingBox.intersects(box);
    }
    /**
     * death
     * A method that intiates the death of this defence by deleting this.
     */
    public void death(){
        gamePanel.destroyDefence(ID);
    }

    //From Drawable interface
    /**
     * draw
     * An overwritten abstract method from the Drawable interface that draws this defence on screen.
     * @param g The graphics object that this defence is to be drawn on.
     */
    abstract public void draw(Graphics g);
}
