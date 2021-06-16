import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * [Tile.java]
 * A class that represents a game tile.
 * @author Edward Wu.
 * @version 1.0, June 15, 2021
 */
class Tile implements Drawable{
    /*-----References to other objects-----*/
    private Level level;

    /*-----Varaibles for this tile-----*/
    private String path;

    private int row; //Row number in TileMap
    private int column; //Column number in TileMap

    //Offsets
    private int offsetX;
    private int offsetY;

    private String groupNumber; //Group of tiles
    private String tileNumber; //Specifi tile number inside of that group

    //Sprites
    BufferedImage sprite;
    private int spriteHeight;
    private int spriteWidth;

    private boolean passable; //Is it possible for players and enemies to walk over it
    private boolean hasDefence; //Does this tile have defence over it

    //Bounding box of this tile
    Rectangle boundingBox;

    /**
     * Tile
     * A constructor that constructs a tile with a specified level, position IN the tile map, group number
     * and tile number in said group.
     * @param level The level that this tile is created in.
     * @param r The row number of this tile in the tile map.
     * @param c The column number of this tile in the tile map.
     * @param groupNumber The group number of this tile.
     * @param tileNumber The tile number of this tile in its group.
     */
    Tile(Level level, int r, int c, String groupNumber, String tileNumber){
        this.level = level;
        this.path = level.getPath()+"Sprites\\Tiles\\";
        this.row = r;
        this.column = c;

        this.offsetX = 0;
        this.offsetY = 0;

        this.groupNumber = groupNumber;
        this.tileNumber = tileNumber;

        //All tiles in group 00 and 04 are non-passable
        if(groupNumber.equals("00") || groupNumber.equals("04")){
            this.passable = false;
        }else{
            this.passable = true;
        }
        this.hasDefence = false;

        loadSprites();
        this.spriteHeight = sprite.getHeight();
        this.spriteWidth = sprite.getWidth();

        boundingBox = new Rectangle(offsetX+column*64, offsetY+row*64, spriteWidth, spriteHeight);
    }
    /**
     * loadSprites
     * Loads this tile's sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        String newPath = path+groupNumber+"\\tile_"+groupNumber+"_"+tileNumber+".png";
        try{
            sprite = ImageIO.read(new File(newPath));
        }catch(Exception e){
            System.out.println("Error loading sprite");
        }
    }
    /**
     * Get the row number of this tile in the tile map.
     * @return The row number of this tile.
     */
    public int getRow(){
        return row;
    }
    /**
     * Get the column number of this tile in the tile map.
     * @return The column number of this tile.
     */
    public int getColumn(){
        return column;
    }
    /**
     * getPassable
     * Returns a boolean value if this tile is passable or not.
     * @return true if it is passable, false otherwise.
     */
    public boolean getPassable(){
        return passable;
    }
    /**
     * setHasDefence
     * Sets the boolean value on if there is a defence on this tile or not.
     * @param value The new value on of there is a defence on this tile or not.
     */
    public void setHasDefence(boolean value){
        hasDefence = value;
    }
    /**
     * getHasDefence
     * Returns a boolean value on if this tile has a defence on it or not.
     * @return true if it has a defence on it, false otherwise.
     */
    public boolean getHasDefence(){
        return hasDefence;
    }
    /**
     * addOffset
     * Adds some amount of offset to this tile.
     * @param amountX The amount of offset in the x-direction that is to be added.
     * @param amountY The amount of offset in the y-direction that is to be added.
     */
    public void addOffset(int amountX, int amountY){
        //Update offset
        offsetX+=amountX;
        offsetY+=amountY;

        //Update bounding box coordiantes
        boundingBox.x+=amountX;
        boundingBox.y+=amountY;
    }
    /**
     * getAbsoluteX
     * Returns the absolute x-coordinate of the top-left corner of this tile. Relative to the tile map.
     * @return The absolute x-coordinate of the top-left corner of this tile.
     */
    public int getAbsoluteX(){
        return column*64;
    }
    /**
     * getAbsoluteY
     * Returns the absolute y-coordinate of the top-left corner of this tile. Relative to the tile map.
     * @return The absolute y-coordinate of the top-left corner of this tile.
     */
    public int getAbsoluteY(){
        return row*64;
    }
    /**
     * getRelativeX
     * Returns the relative x-coordinate of the top-left corner of this tile. Relative to the window.
     * @return The relative x-coordinate of the top-left corner of this tile.
     */
    public int getRelativeX(){
        return offsetX+column*64;
    }
    /**
     * getRelativeY
     * Returns the relative y-coordinate of the top-left corner of this tile. Relative to the window.
     * @return The relative y-coordinate of the top-left corner of this tile.
     */
    public int getRelativeY(){
        return offsetY+row*64;
    }
    /**
     * checkCollision
     * Checks if a specifed box has collided with the bounding box of this tile
     * @param box The box that is to be checked
     * @return true if it has collided, false otherwise.
     */
    public boolean checkCollision(Rectangle box){
        return boundingBox.intersects(box);
    }

    //From Drawable interface
    /**
     * draw
     * Draws this tile on screen.
     * @param g The graphics objec that this tile is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, offsetX+column*64, offsetY+row*64, null);
    }
}
