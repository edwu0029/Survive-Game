import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.Scanner;

class TileMap implements Drawable{

    private String[][] tileMapDetails;
    private String path;
    private int height;
    private int width;
    private int offsetX;
    private int offsetY;

    Tile[][] tileMap;

    TileMap(MainFrame mf, String path){
        this.path = path;

        this.offsetX = 0;
        this.offsetY = 0;

        getTileDetails();

        this.tileMap = new Tile[height][width];
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                String currentTile = tileMapDetails[i][j];
                String groupNumber;
                String tileNumber;

                //tileDetails[h][w] can be split using a '_' character
                groupNumber = currentTile.substring(0, currentTile.indexOf('_'));
                tileNumber = currentTile.substring(currentTile.indexOf('_')+1);
                
                tileMap[i][j] = new Tile(path+"Sprites\\Tiles\\", i, j, groupNumber, tileNumber);
            }
        }
    }

    //TEMPORARY
    //Might want to convert this to interface
    public boolean checkCollision(Rectangle box){
        //Only need to check collision with non-passable tiles
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                if(!tileMap[i][j].getPassable() && tileMap[i][j].checkCollision(box)){
                    return true;
                }
            }
        }
        return false;
    }
    public Tile pointInTile(int x, int y){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                if(tileMap[i][j].getRelativeX()<=x && x<=tileMap[i][j].getRelativeX()+64 &&
                    tileMap[i][j].getRelativeY()<=y && y<=tileMap[i][j].getRelativeY()+64 &&
                    tileMap[i][j].getPassable() && !tileMap[i][j].getHasDefence()){
                    return tileMap[i][j];
                }
            }
        }
        return null;
    }
    public void translate(int amountX, int amountY){
        offsetX+=amountX;
        offsetY+=amountY;
        //Update offset of each tile
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                tileMap[i][j].addOffset(amountX, amountY);
            }
        }
    }
    public int getOffsetX(){
        return offsetX;
    }
    public int getOffsetY(){
        return offsetY;
    }

    public int getHeight(){
        return height;
    }  
    public int getWidth(){
        return width;
    }

    public void getTileDetails(){
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
    public void draw(Graphics g){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                tileMap[i][j].draw(g);
            }
        }
    }
    public void loadSprites(){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                tileMap[i][j].loadSprites();;
            }
        }
    }
}
