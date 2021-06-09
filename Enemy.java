import java.awt.Graphics;
import java.awt.Rectangle;

abstract class Enemy extends Character implements Drawable{

    private GamePanel gamePanel;
    private TileMap tileMap;
    private Player player;

    private int spawnOffsetX;
    private int spawnOffsetY;

    private int offsetX;
    private int offsetY;

    private int x;
    private int y;

    private double attackDelay;

    private long previousTime;
    private long currentTime;
    private double timeDifference;

    private boolean idle;

    private Rectangle hitBox;
    private Rectangle sightBox;
    private int width;
    private int height;

    Enemy(int id, GamePanel gamePanel, TileMap tileMap, Player player, int maxHealth, int x, int y, String path){
        super(id, path, maxHealth);
        this.gamePanel = gamePanel;
        this.tileMap = tileMap;
        this.player = player;
        this.x = x;
        this.y = y;

        this.previousTime = System.currentTimeMillis();
        this.attackDelay = 0.0;

        this.idle = false;
        
        this.spawnOffsetX = tileMap.getOffsetX();
        this.spawnOffsetY = tileMap.getOffsetY();

        
    }
    public void setHitBox(int width, int height){
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
    }
    public int getX(){
        return x+offsetX;
    }
    public int getY(){
        return y+offsetY;
    }
    public double getAttackDelay(){
        return attackDelay;
    }
    public void setAttackDelay(double newValue){
        attackDelay = newValue;
    }
    public boolean checkCollision(Rectangle box){
        return hitBox.intersects(box);
    }
    public void update(){
        currentTime = System.currentTimeMillis();
        timeDifference = (double)(currentTime-previousTime)/1000.0;
        attackDelay+=timeDifference;
        previousTime = currentTime;

        offsetX = tileMap.getOffsetX()-spawnOffsetX;
        offsetY = tileMap.getOffsetY()-spawnOffsetY;
        
        //Update hitbox
        hitBox.x = x+offsetX-width/2;
        hitBox.y = y+offsetY-height/2;
    }
    abstract public void draw(Graphics g);
    abstract public void loadSprites();
    abstract public void loadSpriteOffset();
    public void Death(){
        gamePanel.destroyEnemy(this.getID());
    }
    abstract public void Attack();
}
