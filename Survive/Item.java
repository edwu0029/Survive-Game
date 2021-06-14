import java.awt.Rectangle;
import java.awt.Graphics;

/**
 * [Item.java]
 * A class that represents a game item object.
 * @author Edward Wu
 * @version 1.0, June 11, 2021
 */
abstract class Item implements Drawable{
    /*-----References to other objects-----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private String path;

    /*-----Variables for this Item-----*/
    private final int ID; //Unique ID

    //Coordinate variables relative to top-left corner of window
    private int x;
    private int y;

    //Variables for the TileMap offsets when this item is spawned
    //Used when drawing this item
    private int spawnOffsetX;
    private int spawnOffsetY;

    //Variables for the current offset relative to the spawn offset
    private int offsetX;
    private int offsetY;

    //Type of item
    private String type;
    private Rectangle boundingBox;

    /**
     * Item
     * A constructor that constructs an item with a specified level, type, and coordinate.
     * @param level The level that this item is to be created for.
     * @param type The type of item.
     * @param x The x-coordinate of the top-left corner of this item. This is relative to the tileMap, not window.
     * @param y The y-coordinate of the top-left corner of this item. This is relative to the tileMap, not window.
     */
     Item(Level level, String type, int x, int y){
        this.level = level;
        this.gamePanel = level.getGamePanel();
        this.tileMap = level.getTileMap();

        this.ID = level.getNextItemID();
        this.x = x;
        this.y = y;
        this.type = type;
        this.path = level.getPath();

        //Set spawn offsets
        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();
    }
    /**
     * getType
     * Returns the type of item of this item.
     * @return The type of item of this item.
     */
    public String getType(){
        return type;
    }
    /**
     * getID
     * Returns this item's unique item ID number.
     * @return This item's unique item ID number.
     */
    public int getID(){
        return ID;
    }
    /**
     * getRelativeX
     * Returns the x-coordinate of the top-left corner of this item relative
     * to the window.
     * @return The x-coordinate of the top-left corner of this item relative to the window.
     */
    public int getRelativeX(){
        return x+offsetX;
    }
    /**
     * getRelativeY
     * Returns the y-coordinate of the top-left corner of this item relative
     * to the window.
     * @return The y-coordinate of the top-left corner of this item relative to the window.
     */
    public int getRelativeY(){
        return y+offsetY;
    }
    /**
     * setBoundingBox
     * Sets this item's bounding box with a specified width and height.
     * @param width The width of this item's bounding box.
     * @param height The height of this item's bounding box.
     */
    public void setBoundingBox(int width, int height){
        this.boundingBox = new Rectangle(x, y, width, height);
    }
    /**
     * update
     * Updates all of this item's data that changes over time. This includes offsets
     * and the bounding box coordinates.
     */
    public void update(){
        offsetX = tileMap.getOffsetX()-spawnOffsetX;
        offsetY = tileMap.getOffsetY()-spawnOffsetY;

        //Update bounding box's coordinates
        boundingBox.x = this.getRelativeX();
        boundingBox.y = this.getRelativeY();
    }
    /**
     * checkCollision
     * Checks if the specified box has collided with the bounding box of this item.
     * @param box The box that is to be checked.
     * @return true if the box collides with this item, false otherwise.
     */
    public boolean checkCollision(Rectangle box){
        return boundingBox.intersects(box);
    }

    /**
     * consume
     * A method that intiates the consumption of this item. This method also returns a boolean value on whether
     * this item can even be consumed or not as not all items can be consumed.
     * @return true if the item can be consumed, false otherwise.
     */
    abstract public boolean consume();

    //From Drawable Interface
    /**
     * draw
     * An abstract method from the Drawable interface that draws this item on screen.
     * @param g The graphic object in which this item will be drawn.
     */
    abstract public void draw(Graphics g);

}
