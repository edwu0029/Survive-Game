import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Graphics;

class Projectile implements Drawable{
    public GamePanel gamePanel;
    private TileMap tileMap;

    private BufferedImage projectileSprite;
    BufferedImage projectileHorizontal;
    BufferedImage projectleVertical;

    private final int ID;
    private int x;
    private int y;
    private int velocityX;
    private int velocityY;

    private final int spawnOffsetX;
    private final int spawnOffsetY;
    private int offsetX;
    private int offsetY;

    private int damage;

    Rectangle boundingBox;

    Projectile(int id, GamePanel gamePanel, TileMap tileMap, int x, int y){
        this.ID = id;
        this.gamePanel = gamePanel;
        this.tileMap = tileMap;
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;

        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();

        this.damage = 0;
    }
    public void setVelocityX(int vx){
        this.velocityX = vx;
    }
    public void setVelocityY(int vy){
        this.velocityY = vy;
    }
    public void setDamage(int dmg){
        this.damage = dmg;
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
    public void setBoundingBox(int width, int height){
        boundingBox = new Rectangle(x+tileMap.getOffsetX(), y+tileMap.getOffsetY(), width, height);
    }
    public void update(){
        if(tileMap.checkCollision(boundingBox)){
            destroy();
        }
        ArrayList<Enemy>enemies = gamePanel.getEnemies();
        for(int i = 0;i<enemies.size();i++){
            if(enemies.get(i).checkCollision(boundingBox)){
                enemies.get(i).takeDamage(damage);
                destroy();
                break;
            }
        }
        x+=velocityX;
        y+=velocityY;
        
        offsetX = (tileMap.getOffsetX()-spawnOffsetX);
        offsetY = (tileMap.getOffsetY()-spawnOffsetY);

        //Update boundingBox coordinates
        boundingBox.x = x;
        boundingBox.y = y;
    }
    public void destroy(){
        gamePanel.destroyProjectile(ID);
    }

    //Drawable interface
    public void draw(Graphics g){
        
    }
}
