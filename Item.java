import java.awt.Rectangle;
import java.awt.Graphics;

abstract class Item implements Drawable{
    private final int ID;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private String path;

    private int x;
    private int y;
    private int spawnOffsetX;
    private int spawnOffsetY;
    private int offsetX;
    private int offsetY;

    private String name;
    
    private Rectangle boundingBox;
    Item(int id, GamePanel gamePanel, TileMap tileMap, int x, int y, String name, String path){
        this.ID = id;
        this.gamePanel = gamePanel;
        this.tileMap = tileMap;
        this.x = x;
        this.y = y;
        this.name = name;
        this.path = path;

        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();
    }
    public void setBoundingBox(int width, int height){
        this.boundingBox = new Rectangle(x, y, width, height);
    }
    public String getPath(){
        return path;
    }
    public String getName(){
        return name;
    }
    public int getID(){
        return ID;
    }
    public int getX(){
        return x+offsetX;
    }
    public int getY(){
        return y+offsetY;
    }
    public void update(){
        offsetX = tileMap.getOffsetX()-spawnOffsetX;
        offsetY = tileMap.getOffsetY()-spawnOffsetY;

        boundingBox.x = this.getX();
        boundingBox.y = this.getY();
    }
    public boolean checkCollision(Rectangle box){
        System.out.println(boundingBox.intersects(box));
        return boundingBox.intersects(box);
    }
    public boolean equals(Item otherItem){
        return name.equals(otherItem.getName());
    }
    abstract public void consume();
    abstract public void draw(Graphics g);

}
