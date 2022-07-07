import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * [Enemy.java]
 * A class that represents an enemy for this game.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
abstract class Enemy implements Drawable{
    /*-----References to other objects----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private Player player;

    /*-----Variables for this Enemy----*/
    private final int ID;
    private String path;

    //Variables for position and offset
    private int x;
    private int y;
    private int spawnOffsetX;
    private int spawnOffsetY;
    private int offsetX;
    private int offsetY;

    //Variables for attacking
    private long previousTime;
    private long currentTime;
    private double timeDifference;
    private double attackDelay; //Time since last attack was made

    private Rectangle hitBox; //Hitbox of this enemy
    private int width;
    private int height;

    //Variables for health of this enemy
    HealthManager healthManager;

    /**
     * Enemy
     * A constructor that constructs a enemy with a specified level, maximum health, and
     * position.
     * @param level The level that this enemy is created for.
     * @param maxHealth The maximum health of this enemy.
     * @param x The x-coordinate of the centre of this enemy. This is relative to the window.
     * @param y The y-coordinate of the centre corner of this enemy. This is relative to the window.
     */
    Enemy(Level level, int maxHealth, int x, int y){
        this.level = level;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.tileMap = level.getTileMap();
        this.player = level.getPlayer();
        
        this.ID = level.getNextEnemyID();
        this.x = x;
        this.y = y;
        //Set up spawn offsets; all enemies have spawn offsets of 0
        this.spawnOffsetX = 0;
        this.spawnOffsetY = 0;

        this.previousTime = System.currentTimeMillis();
        this.attackDelay = 0.0;

        //Set up health manager
        this.healthManager = new HealthManager(maxHealth);
    }
    /**
     * getID
     * Returns this enemy's unique enemy ID number.
     * @return This enemy's unique enemy ID number.
     */
    public int getID(){
        return ID;
    }
    /**
     * setX 
     * Sets the new absolute x-coordinate of the centre of this enemy to some specified value.
     * @param newX The new absolute x-coordinate of the centre of this enemy.
     */
    public void setX(int newX){
        x = newX;
    }
    /**
     * getAbsoluteX
     * Returns the absolute x-coordinate of the centre of this enemy.
     * @return Returns the absolute x-coordinate of the centre of this enemy.
     */
    public int getAbsoluteX(){
        return x;
    }
    /**
     * getRelativeX
     * Returns the x-coordinate of the top-left corner of this enemy relative
     * to the window.
     * @return The x-coordinate of the top-left corner of this enemy relative to the window.
     */
    public int getRelativeX(){
        return x+offsetX;
    }
    /**
     * setY 
     * Sets the new absolute y-coordinate of the centre of this enemy to some specified value.
     * @param newY The new absolute y-coordinate of the centre of this enemy.
     */
    public void setY(int newY){
        y = newY;
    }
    /**
     * getAbsoluteY
     * Returns the absolute y-coordinate of the centre of this enemy.
     * @return Returns the absolute y-coordinate of the centre of this enemy.
     */
    public int getAbsoluteY(){
        return y;
    }
    /**
     * getRelativeY
     * Returns the y-coordinate of the top-left corner of this enemy relative
     * to the window.
     * @return The y-coordinate of the top-left corner of this enemy relative to the window.
     */
    public int getRelativeY(){
        return y+offsetY;
    }
    /**
     * getHealthManager
     * Returns the reference to this enemy's health manager.
     * @return The reference to this enemy's health manager.
     */
    public HealthManager getHealthManager(){
        return healthManager;
    }
    /**
     * setHitBox
     * Sets this enemy's hit box with a specified width and height.
     * @param width The width of this enemy's hit box.
     * @param height The height of this enemy's hit box.
     */
    public void setHitBox(int width, int height){
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
    }
    /**
     * getHitBox
     * Returns the hitbox for this enemy.
     * @return the hitbox for thie enemy.
     */
    public Rectangle getHitBox(){
        return hitBox;
    }
    /**
     * setAttackDelay
     * Sets the attack delay for this enemy to some new value.
     * @param newAttackDelay The new attack delay of the enemy.
     */
    public void setAttackDelay(double newAttackDelay){
        attackDelay = newAttackDelay;
    }
    /**
     * getAttackDelay
     * Returns the current attack delay for this enemy.
     * @return The current attack delay for this enemy.
     */
    public double getAttackDelay(){
        return attackDelay;
    }
    /**
     * checkCollision
     * Checks if the specified box has collided with the hit box of this enemy.
     * @param box The box that is to be checked.
     * @return true if the box collides with this enemy, false otherwise.
     */
    public boolean checkCollision(Rectangle box){
        return hitBox.intersects(box);
    }
    /**
     * update
     * Updates all of this enemy's data that changes over time. This includes offsets
     * and the hit box coordinates.
     */
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
    /**
     * death
     * A method that intiates the death of this enemy by deleting it.
     */
    public void death(){
        gamePanel.destroyEnemy(ID);
    }
    /**
     * attack
     * An abstract method that intiates the attack of this enemy.
     */
    abstract public void attack();
    /**
     * draw
     * An abstract method from the Drawable interface that draws this enemy on screen.
     * @param g The graphics object that this enemy is to be drawn on.
     */
    abstract public void draw(Graphics g);
}
