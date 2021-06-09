import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;

class Tile implements Drawable{
    private String path;

    private int row; //Row number in TileMap
    private int column; //Column number in TileMap
    private int offsetInX;
    private int offsetInY;

    private String groupNumber;
    private String tileNumber;

    BufferedImage sprite;
    private int spriteHeight;
    private int spriteWidth;
    private boolean passable;
    private boolean hasDefence;

    Rectangle boundingBox;

    Tile(String path, int r, int c, String groupNumber, String tileNumber){
        this.path = path;
        this.row = r;
        this.column = c;

        this.offsetInX = 0;
        this.offsetInY = 0;

        this.groupNumber = groupNumber;
        this.tileNumber = tileNumber;

        //TEMPORARY
        //Find better way to do this
        if(groupNumber.equals("00") || groupNumber.equals("04")){
            this.passable = false;
        }else{
            this.passable = true;
        }
        this.hasDefence = false;

        loadSprites();
        this.spriteHeight = sprite.getHeight();
        this.spriteWidth = sprite.getWidth();

        boundingBox = new Rectangle(offsetInX+column*64, offsetInY+row*64, spriteWidth, spriteHeight);
    }

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
    public boolean getPassable(){
        return passable;
    }
    public boolean getHasDefence(){
        return hasDefence;
    }
    public void setHasDefence(boolean value){
        hasDefence = value;
    }
    
    public void addOffset(int amountX, int amountY){
        offsetInX+=amountX;
        offsetInY+=amountY;
        boundingBox.x+=amountX;
        boundingBox.y+=amountY;
    }
    public int getAbsoluteX(){
        return column*64;
    }
    public int getAbsoluteY(){
        return row*64;
    }
    public int getRelativeX(){
        return offsetInX+column*64;
    }
    public int getRelativeY(){
        return offsetInY+row*64;
    }
    //TEMPORARY
    //Might want to convert this to interface
    public boolean checkCollision(Rectangle box){
        return boundingBox.intersects(box);
    }

    //From Drawable interface
    public void draw(Graphics g){
        g.drawImage(sprite, offsetInX+column*64, offsetInY+row*64, null);
    }
    public void loadSprites(){
        String newPath = path+groupNumber+"\\tile_"+groupNumber+"_"+tileNumber+".png";
        try{
            sprite = ImageIO.read(new File(newPath));
        }catch(Exception e){
            System.out.println("Error loading sprite");
        }
    }
}
