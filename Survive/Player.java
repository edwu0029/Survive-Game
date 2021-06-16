import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * [Player.java]
 * A class that represents a player object.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Player implements Drawable, Moveable{
    /*-----References to other objects----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;

    /*------Properties of this player-----*/
    //Health
    HealthManager healthManager;
    HealthBar healthBar;
    
    //Coordinate Variables relative to the top-left corner of the window
    private int x; //The x-coordinate of the CENTRE of the player
    private int y; //The y-coordinate of the CENTRE of the player

    //Varibales for sprites
    private int mode; //1 = idle, 2 = blue laser gun holding, 3 = red laser gun holding
    private boolean[] possibleModes; //Some modes require a item to be used first
    private int orientation;
    private int direction; //0 = Vertical, Horizontal = 1
    private BufferedImage[] spritesIdle;
    private int[] idleOffsetInX; //x-axis offset for idle sprites
    private int[] idleOffsetInY; //y-axis offset for idle sprites
    
    private BufferedImage[] spritesBlueGunHolding;
    private int[] blueGunHoldingOffsetInX; //x-axis offset for weapon holding sprites
    private int[] blueGunHoldingOffsetInY; //y-axis offset for weapon holding sprites

    private BufferedImage[] activeModeSprites; //Reference to the the player's current mode's sprites
    private int[] activeModeSpriteOffsetInX; //Reference to the the player's current mode's sprite offsetX
    private int[] activeModeSpriteOffsetInY; //Reference to the the player's current mode's sprite offsetY

    private BufferedImage activeSprite; //Current sprite
    private int activeSpriteOffsetInX;
    private int activeSpriteOffsetInY;
    private int spriteWidth;
    private int spriteHeight;

    //Variables for inventory
    private ArrayList<Item> items;
    private ArrayList<Defence> defences;

    //Variables for Movement
    private int moveSpeed;
    private Rectangle topBoundingBox;
    private Rectangle leftBoundingBox;
    private Rectangle bottomBoundingBox;
    private Rectangle rightBoundingBox;

    //For Getting attacking and hitbox
    private Rectangle hitBox;

    private long previousTime; //Time in mileseconds of previous update
    private long currentTime; //Time in mileseconds of current update
    private double timeDifference;
    private double attackDelay;

    /**
     * Player
     * A constructor that constructs a player object for a certain level.
     * @param level The level that this player will constructed for.
     */
    Player(Level level){
        /*-----Set up references to other objects-----*/
        this.level = level;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.tileMap = level.getTileMap();

        /*-----Set up Player variables-----*/
        this.mode = 1; //Default mode is idle
        this.possibleModes = new boolean[4]; //Currently only 3 modes
        possibleModes[mode] = true; //Idle mode is possible


        //Default position of player is in the middle of the screen
        this.x = 540-(spriteWidth/2);
        this.y = 540-(spriteHeight/2);

        this.healthManager = new HealthManager(300);
        this.healthBar = new HealthBar(level, healthManager, x-20, y+40);

        //Set up sprites
        this.spritesIdle = new BufferedImage[4];
        this.idleOffsetInX = new int[4];
        this.idleOffsetInY = new int[4];

        this.spritesBlueGunHolding = new BufferedImage[4];
        this.blueGunHoldingOffsetInX = new int[4];
        this.blueGunHoldingOffsetInY = new int[4];
        loadSprites();
        loadSpriteOffset();

        //Default sprite is idle mode and facing up
        this.orientation = 0;
        this.direction = -1;
        this.activeModeSprites = spritesIdle;
        this.activeModeSpriteOffsetInX = idleOffsetInX;
        this.activeModeSpriteOffsetInY = idleOffsetInY;

        this.activeSprite = spritesIdle[0];
        this.activeSpriteOffsetInX = idleOffsetInX[0];
        this.activeSpriteOffsetInY = idleOffsetInY[0];

        this.spriteHeight = activeSprite.getHeight();
        this.spriteWidth = activeSprite.getWidth();

        this.items = new ArrayList<Item>();
        this.defences = new ArrayList<Defence>();

        this.moveSpeed = 5;
        this.previousTime = System.currentTimeMillis();
        this.attackDelay = 0.0;
        
        //Set up bounding boxes; used for collision detection with non-passable tiles
        //Bounding boxes have a thickness of 3 pixels and length of 30 pixels
        this.topBoundingBox = new Rectangle(x-15, y-(spriteHeight/2), 30, 3);
        this.leftBoundingBox = new Rectangle(x-(spriteWidth/2), y-15, 3, 30);
        this.bottomBoundingBox = new Rectangle(x-15, y+(spriteHeight/2), 30, 3);
        this.rightBoundingBox = new Rectangle(x+(spriteWidth/2), y-15, 3, 30);
        
        //Set up Hitbox
        this.hitBox = new Rectangle(x-(spriteWidth/2)+activeSpriteOffsetInX, y-(spriteHeight/2)+activeSpriteOffsetInY, spriteWidth, spriteHeight);
    }
    /**
     * getHealthManager
     * Returns the reference to this player's HealthManager object.
     * @return The reference to this player's HealthManager object.
     */
    public HealthManager getHealthManager(){
        return healthManager;
    }
    /**
     * getPossibleModes
     * Returns the reference to this player's possibleModes array of strings.
     * @return The reference to this player's possibleModes array.
     */
    public boolean[] getPossibleModes(){
        return possibleModes;
    }
    /**
     * getPlayerItems
     * Returns a reference to the player's items in their inventory.
     * @return An arraylist of the items in the player's inventory.
     */
    public ArrayList<Item> getPlayerItems(){
        return items;
    }
    /**
     * getPlayerDefences
     * Returns a reference to the player's defences in their inventory.
     * @return An arraylist of the defences in the player's inventory.
     */
    public ArrayList<Defence> getPlayerDefences(){
        return defences;
    }
    /**
     * getHitBox
     * Returns a reference to this player's hitbox as a Rectangle object.
     * @return This player's hitbox.
     */
    public Rectangle getHitBox(){
        return hitBox;
    }
    /**
     * update
     * Updates all of the player's data that changes over time. This includes checking for health/death and
     * calculating delay times.
     */
    public void update(){
        //Update attack delay times
        currentTime = System.currentTimeMillis();
        timeDifference = (double)(currentTime-previousTime)/1000.0;
        attackDelay+=timeDifference;
        previousTime = currentTime;

        //Check for death
        if(healthManager.getHealth()<=0){
            death();
        }

        //Update health bar
        healthBar.update();
    }
    /**
     * loadSprites
     * Loads the player sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        //Loop through all modes (idle, holding blue laser gun, holding red laser gun)
        for(int modeNumber = 1;modeNumber<=3;modeNumber++){
            //Loop through each sprite (facing up, left, down, right)
            for(int spriteNumber = 1;spriteNumber<=4;spriteNumber++){
                String modeNumberString = "0"+Integer.toString(modeNumber);
                String spriteNumberString = "0"+Integer.toString(spriteNumber);
                String spritePath = level.getPath()+"\\Sprites\\Player\\";

                spritePath+=modeNumberString+"\\"+"player_"+modeNumberString+"_"+spriteNumberString+".png";
                
                try{
                    if(modeNumber==1){ //Idle
                        //spriteNumber-1 since the array spritesIdle is 0-indexed
                        spritesIdle[spriteNumber-1] = ImageIO.read(new File(spritePath));
                    }else if(modeNumber==2){ //Holding Blue Laser Gun
                        //spriteNumber-1 since the array spritesBlueGunHolding is 0-indexed
                        spritesBlueGunHolding[spriteNumber-1] = ImageIO.read(new File(spritePath));
                    }else if(modeNumber==3){ //TEMPORARY
                        
                    }
                }catch(Exception e){
                    System.out.println("Error loading player sprite");
                }
            }   
        }
    }
    /**
     * loadSpriteOffset
     * Loads the sprite offset data for each player sprite.
     */
    private void loadSpriteOffset(){
        //Certain sprites must be offset by some amount when drawn on screen to be centered and consistent with the other sprites.
        //This is the reason for this method. This info is stored in .txt files with the same names as the sprites

        //Loop through all modes (idle, holding blue laser gun, holding red laser gun)
        for(int modeNumber = 1;modeNumber<=3;modeNumber++){
            //Loop through each sprite (facing up, left, down, right)
            for(int spriteNumber = 1;spriteNumber<=4;spriteNumber++){
                String modeNumberString = "0"+Integer.toString(modeNumber);
                String spriteNumberString = "0"+Integer.toString(spriteNumber);
                String offsetPath = level.getPath()+"\\Sprites\\Player\\"+modeNumberString+"\\"+"offset_"+modeNumberString+"_"+spriteNumberString+".txt";
                //Get .txt file with the offset information
                try{
                    int offsetX = 0;
                    int offsetY = 0;
                    Scanner offsetInput = new Scanner(new File(offsetPath));
                    while(offsetInput.hasNext()){ //Input from file
                        offsetX = offsetInput.nextInt();
                        offsetY = offsetInput.nextInt();
                    }
                    offsetInput.close(); //Close input to file
                    
                    if(modeNumber==1){ //Idle
                        //spriteNumber-1 since the arrays are 0-indexed
                        idleOffsetInX[spriteNumber-1] = offsetX;
                        idleOffsetInY[spriteNumber-1] = offsetY;
                    }else if(modeNumber==2){ //Blue laser gun holding
                        //spriteNumber-1 since the arrays are 0-indexed
                        blueGunHoldingOffsetInX[spriteNumber-1] = offsetX;
                        blueGunHoldingOffsetInY[spriteNumber-1] = offsetY;
                    }else if(modeNumber==3){
                        //TEMPORARY
                    }

                }catch(Exception e){
                    System.out.println("Error loading sprite offset data");
                }
            }   
        }
    }
    /**
     * pickUpItem
     * Checks if the player is in range to pick up any items and if so, picks them up.
     */
    public void pickUpItem(){
        int itemID = gamePanel.checkItemCollision(hitBox);
        if(itemID!=-1 && items.size()<10){ //Maximum inventory space of 10 items
            gamePanel.pickUpItem(itemID);
        }
    }
    //Abstract classes from Character
    /**
     * death
     * Intiates the death of this player object by loading the game over panel.
     */
    public void death(){
        level.getPanelManager().setActivePanel("GameOverPanel");
    }
    /**
     * attack
     * A method that intiates for this player to attack based on its current mode.
     */
    public void attack(){
        //Check for attack delay of 0.3 seconds
        if(attackDelay>=0.3 && mode==2){
            //Create blue laser
            if(orientation==0 && direction==-1){ //Facing Up
                BlueLaser blueLaser = new BlueLaser(level, orientation, direction, x+7, y);
                level.getProjectiles().add(blueLaser);
            }else if(orientation==0 && direction==1){ //Facing Down
                BlueLaser blueLaser = new BlueLaser(level, orientation, direction, x-7, y);
                level.getProjectiles().add(blueLaser);
            }else if(orientation==1 && direction==-1){ //Facing Left
                BlueLaser blueLaser = new BlueLaser(level, orientation, direction, x, y-7);
                level.getProjectiles().add(blueLaser);
            }else if(orientation==1 && direction==1){ //Facing Right
                BlueLaser blueLaser = new BlueLaser(level, orientation, direction, x, y+7);
                level.getProjectiles().add(blueLaser);
            }
            //Reset attack delay
            attackDelay = 0.0;
        }
    }
    /**
     * setMode
     * Sets the mode of this player object to a specified new mode.
     * @param newMode The new mode of this player.
     */
    public void setMode(int newMode){
        mode = newMode;
        //Update sprites
        updateActiveModeSprites();
    }

    /**
     * updateActiveModeSprites
     * Updates the active mode sprites for this player based on the current mode.
     */
    public void updateActiveModeSprites(){
        if(mode==1){ //Idle
            activeModeSprites = spritesIdle;
            activeModeSpriteOffsetInX = idleOffsetInX;
            activeModeSpriteOffsetInY = idleOffsetInY;
        }else if(mode==2){ //Blue laser gun holding
            activeModeSprites = spritesBlueGunHolding;
            activeModeSpriteOffsetInX = blueGunHoldingOffsetInX;
            activeModeSpriteOffsetInY = blueGunHoldingOffsetInY;
        }
    }
    /**
     * setOrientation
     * Sets the orientation of this player to some new orientation and updates the active data accourdingly.
     * @param newOrientation The new orientation of this player as a char.
     */
    public void setOrientation(char newOrientation){
        if(newOrientation=='W'){ //Facing Up
            orientation = 0;
            direction = -1;
            activeSprite = activeModeSprites[0];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[0];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[0];
        }else if(newOrientation=='A'){ //Facing Left
            orientation = 1;
            direction = -1;
            activeSprite = activeModeSprites[1];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[1];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[1];
        }else if(newOrientation=='S'){ //Facing Down
            orientation = 0;
            direction = 1;
            activeSprite = activeModeSprites[2];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[2];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[2];
        }else if(newOrientation=='D'){ //Facing Right
            orientation = 1;
            direction = 1;
            activeSprite = activeModeSprites[3];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[3];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[3];
        }
    }

    //From Moveable
    /**
     * moveUp
     * A overwridden method from the Moveable interface that moves the player up.
     */
    public void moveUp(){
        setOrientation('W');
        //Move if there is NO Collision with non-passable tile or defence
        if(!tileMap.checkCollision(topBoundingBox) && (gamePanel.checkDefenceCollision(topBoundingBox)==-1) ){
            tileMap.translate(0, moveSpeed);
        }
    }
    /**
     * moveLeft
     * A overwridden method from the Moveable interface that moves the player left.
     */
    public void moveLeft(){
        setOrientation('A');
        if(!tileMap.checkCollision(leftBoundingBox) && (gamePanel.checkDefenceCollision(leftBoundingBox)==-1)){
            tileMap.translate(moveSpeed, 0);
        }
    }
    /**
     * moveDown
     * A overwridden method from the Moveable interface that moves the player down.
     */
    public void moveDown(){
        setOrientation('S');
        if(!tileMap.checkCollision(bottomBoundingBox) && (gamePanel.checkDefenceCollision(bottomBoundingBox)==-1)){
            tileMap.translate(0, -moveSpeed);
        }
    }
    /**
     * moveRight
     * A overwridden method from the Moveable interface that moves the player right.
     */
    public void moveRight(){
        setOrientation('D');
        if(!tileMap.checkCollision(rightBoundingBox) && (gamePanel.checkDefenceCollision(rightBoundingBox)==-1)){
            tileMap.translate(-moveSpeed, 0);
        }
    }
    
    //From Drawable
    /**
     * draw
     * An overwritten method from Drawable interface that draws this player on screen.
     * @param g The graphics object that this player is to be drawn on.
     */
    public void draw(Graphics g){
        //TEMPORARY
        g.drawImage(activeSprite, x-(spriteWidth/2)+activeSpriteOffsetInX, y-(spriteHeight/2)+activeSpriteOffsetInY, null);

        //Draw health bar
        healthBar.draw(g);
    }
}
