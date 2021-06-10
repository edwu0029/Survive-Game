import java.awt.Graphics;
import java.awt.Rectangle;

abstract class Defence implements Drawable{

    private final int ID;

    private int health;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private Player player;
    private String name;

    private int x;
    private int y;
    private int spawnOffsetX;
    private int spawnOffsetY;

    private int offsetX;
    private int offsetY;

    Rectangle boundingBox;

    Defence(int id, GamePanel gamePanel, TileMap tileMap, Player player, String name, int x, int y){
        this.ID = id;
        this.gamePanel = gamePanel;
        this.tileMap = tileMap;
        this.player = player;
        this.name = name;
        this.x = x;
        this.y = y;

        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();
    }
    public int getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
    public void setBoundingBox(int width, int height){
        boundingBox = new Rectangle(x, y, width, height);
    }
    public void update(){
        offsetX = tileMap.getOffsetX()-spawnOffsetX;
        offsetY = tileMap.getOffsetY()-spawnOffsetY;

        boundingBox.x = this.getX();
        boundingBox.y = this.getY();
    }
    public void setX(int newX){
        x = newX;
    }
    public void setY(int newY){
        y = newY;
    }
    public void setSpawnOffsetX(int newValue){
        spawnOffsetX = newValue;
    }
    public void setSpawnOffsetY(int newValue){
        spawnOffsetY = newValue;
    }
    public int getX(){
        return x+offsetX;
    }
    public int getY(){
        return y+offsetY;
    }
    public void takeDamage(int amount){
        health-=amount;
        System.out.println("Defence took damage!");
        if(health<=0){
            death();
        }
    }
    public boolean checkCollision(Rectangle box){
        return boundingBox.intersects(box);
    }
    abstract public void death();
    public void draw(Graphics g){
        g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
