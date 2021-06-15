import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.Scanner;

/**
 * [TileMap.java]
 * A class that represents the game's tile map.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class TileMap implements Drawable{
    /*-----References to other objects-----*/
    private Level level;

    /*-----Vraibles for this tile map-----*/
    private String[][] tileMapDetails;
    private String path;
    private int height;
    private int width;

    //Offset data
    private int offsetX;
    private int offsetY;
    
    private Tile[][] tileMap; //Actual tile map

    /**
     * TileMap
     * A constructor that constructs a tile map for a specified level.
     * @param level The level that this tile map is created for.
     */
    TileMap(Level level){
        this.level = level;
        this.path = level.getPath();

        this.offsetX = 0;
        this.offsetY = 0;

        loadTileDetails();

        this.tileMap = new Tile[height][width];

        //Instantiate each tile in the tile map
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                String currentTile = tileMapDetails[i][j];
                String groupNumber;
                String tileNumber;

                //tileDetails[h][w] can be split using a '_' character
                groupNumber = currentTile.substring(0, currentTile.indexOf('_'));
                tileNumber = currentTile.substring(currentTile.indexOf('_')+1);
                
                tileMap[i][j] = new Tile(level, i, j, groupNumber, tileNumber);
            }
        }
    }
    /**
     * getOffsetX
     * Returns the amount of offset in the x-direction the tile map currently has.
     * @return The amount of offset in the x-direction the tile map currently has.
     */
    public int getOffsetX(){
        return offsetX;
    }
    /**
     * getOffsetY
     * Returns the amount of offset in the y-direction the tile map currently has.
     * @return The amount of offset in the y-direction the tile map currently has.
     */
    public int getOffsetY(){
        return offsetY;
    }
    /**
     * getHeight
     * Returns the height of this tile map in number of tiles.
     * @return The height of this tile map in number of tiles.
     */
    public int getHeight(){
        return height;
    }
    /**
     * getHeight
     * Returns the height of this tile map in number of tiles.
     * @return The height of this tile map in number of tiles.
     */
    public int getWidth(){
        return width;
    }
    /**
     * getTileMapArray
     * Returns a reference to the actual tile map array which contains all tiles.
     * @return A reference to the actual tile map array which contains all tiles.
     */
    public Tile[][] getTileMapArray(){
        return tileMap;
    }
    /**
     * checkCollision
     * Checks whether a specified bpx has collided with any valid tiles. Note that in this case, valid tile
     * means any tile that is passable and doesn't have a defence on it.
     * @return true if a collision has occured, false otherwise.
     */
    public boolean checkCollision(Rectangle box){
        //Only need to check collision with non-passable tiles
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                //Not valid if the tile is non-passable
                if((!tileMap[i][j].getPassable() && tileMap[i][j].checkCollision(box))){
                    //Return true since collision has occured
                    return true;
                }
            }
        }
        //Return false once checked all tiles
        return false;
    }
    /**
     * pointInTileRelative
     * Checks if a certain point is inside of a valid tile and returns the reference to that tile. Note
     * that in this case, valid tile means any tile that is passable and doesn't have a defence on it.
     * @param x The x-coordinate of the point relative to the window
     * @param y The y-coordinate of the point relative to the window
     * @return The reference to the tile, null otherwise.
     */
    public Tile pointInTileRelative(int x, int y){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                //Note: All tiles have a height and width of 64 pixels
                //Also note that the point from the parameters is relative to the window, hence we need to 
                //check each tile's relattive coordinates.
                if(tileMap[i][j].getRelativeX()<=x && x<=tileMap[i][j].getRelativeX()+64 &&
                    tileMap[i][j].getRelativeY()<=y && y<=tileMap[i][j].getRelativeY()+64 &&
                    tileMap[i][j].getPassable() && !tileMap[i][j].getHasDefence()){
                    return tileMap[i][j];
                }
            }
        }
        //Return null if no valid tile is found
        return null;
    }
    /**
     * pointInTileAbsolute
     * Checks if a certain point is inside of a valid tile and returns the reference to that tile. Note
     * that in this case, valid tile means any tile that is passable and doesn't have a defence on it.
     * @param x The x-coordinate of the point absolutly.
     * @param y The y-coordinate of the point absolutly.
     * @return The reference to the tile, null otherwise.
     */
    public Tile pointInTileAbsolute(int x, int y){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                //Note: All tiles have a height and width of 64 pixels
                //Also note that the point from the parameters is absolute, hence we need to 
                //check each tile's absolute coordinates.
                if(tileMap[i][j].getAbsoluteX()<=x && x<=tileMap[i][j].getAbsoluteX()+64 &&
                    tileMap[i][j].getAbsoluteY()<=y && y<=tileMap[i][j].getAbsoluteY()+64 &&
                    tileMap[i][j].getPassable() && !tileMap[i][j].getHasDefence()){
                    return tileMap[i][j];
                }
            }
        }
        //Return null if no valid tile is found
        return null;
    }
    /**
     * translate
     * Translates this tile map by some amount int he x and y directions.
     * @param amountX The amount that is to be translateed in the x-direction.
     * @param amountY The amount that is to be translated in the y-direction.
     */
    public void translate(int amountX, int amountY){
        offsetX+=amountX;
        offsetY+=amountY;
        //Update offset of each tile in tile map
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                tileMap[i][j].addOffset(amountX, amountY);
            }
        }
    }
    /**
     * loadTileDetails
     * Loads the tile map details from the .txt file called GameMap.txt
     */
    public void loadTileDetails(){
        try{
            File gameMapFile = new File(path+"GameMap.txt");
            Scanner fileInput = new Scanner(gameMapFile);

            this.height = fileInput.nextInt();
            this.width = fileInput.nextInt();
            this.tileMapDetails = new String[height][width];
            
            for(int i = 0;(i<height) && fileInput.hasNextLine();i++){
                for(int j = 0;(j<width) && fileInput.hasNext();j++){
                    tileMapDetails[i][j] = fileInput.next();
                }
            }

            fileInput.close();
        }catch(Exception e){
            System.out.println("Error loading map data");
        }
    }

    //From Drawable interface
    /**
     * draw
     * Draws this tile map on screen.
     * @param g The graphics object that this tile map is to be drawn on.
     */
    public void draw(Graphics g){
        //Draw each tile
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                tileMap[i][j].draw(g);
            }
        }
    }
}
