import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage; 
import java.awt.Graphics;
import java.awt.Rectangle;

class Player extends Character implements Drawable, Moveable{
    private GamePanel gamePanel;
    private TileMap tileMap;
    private int x;
    private int y;

    private int moveSpeed;
    private int mode; //1 = idle, 2 = Holding gun, 3 = Reload
    private int orientation;
    private int direction;

    private long previousTime;
    private long currentTime;
    private double timeDifference;
    private double attackDelay;

    static ArrayList<Item> inventory;
    static ArrayList<Defence> defences;

    //Variables for the active sprites
    BufferedImage[] activeModeSprites;
    BufferedImage activeSprite;
    int[] activeModeSpriteOffsetInX;
    int[] activeModeSpriteOffsetInY;
    int activeSpriteOffsetInX;
    int activeSpriteOffsetInY;
    private int spriteWidth;
    private int spriteHeight;

    //Variables for Movement
    Rectangle topBoundingBox;
    Rectangle leftBoundingBox;
    Rectangle bottomBoundingBox;
    Rectangle rightBoundingBox;

    //For Getting hit
    public Rectangle hitBox;

    /*-----Variables for caching Sprites-----*/
    BufferedImage[] spritesIdle;
    int[] idleOffsetInX; //x-axis offset for idle sprites
    int[] idleOffsetInY; //y-axis offset for idle sprites
    
    BufferedImage[] spritesBlueGunHolding;
    int[] blueGunHoldingOffsetInX; //x-axis offset for weapon holding sprites
    int[] blueGunHoldingOffsetInY; //y-axis offset for weapon holding sprites

    Player(GamePanel gamePanel, TileMap map, String path){
        super(1, path, 200); //Player has ID = 1, an arbitary number

        this.gamePanel = gamePanel;
        this.tileMap = map;

        this.moveSpeed = 5;
        this.mode = 1;

        this.previousTime = System.currentTimeMillis();
        this.attackDelay = 0.0;

        inventory = new ArrayList<Item>();
        defences = new ArrayList<Defence>();

        this.spritesIdle = new BufferedImage[4];
        this.idleOffsetInX = new int[4];
        this.idleOffsetInY = new int[4];

        this.spritesBlueGunHolding = new BufferedImage[4];
        this.blueGunHoldingOffsetInX = new int[4];
        this.blueGunHoldingOffsetInY = new int[4];

        loadSprites();
        loadSpriteOffset();

        this.orientation = 0; //Vertical
        this.direction = -1; //Up direction
        this.activeModeSprites = spritesIdle; //Default mode is in idle
        this.activeModeSpriteOffsetInX = idleOffsetInX;
        this.activeModeSpriteOffsetInY = idleOffsetInY;
        this.activeSprite = spritesIdle[0]; //Default sprite is idle mode and facing up
        this.activeSpriteOffsetInX = idleOffsetInX[0];
        this.activeSpriteOffsetInY = idleOffsetInY[0];

        this.spriteHeight = activeSprite.getHeight(); //All player spirtes have the same height
        this.spriteWidth = activeSprite.getWidth(); //All player sprites have the same width
      
        this.x = 540-(spriteWidth/2);
        this.y = 540-(spriteHeight/2);

        //TEMPORARY
        //Add comments to show that the numbers aren't arbitary
        //Bounding boxes have a thickness of 3 pixels and length of 30 pixels
        this.topBoundingBox = new Rectangle(x-15, y-(spriteHeight/2), 30, 3);
        this.leftBoundingBox = new Rectangle(x-(spriteWidth/2), y-15, 3, 30);
        this.bottomBoundingBox = new Rectangle(x-15, y+(spriteHeight/2), 30, 3);
        this.rightBoundingBox = new Rectangle(x+(spriteWidth/2), y-15, 3, 30);
        
        this.hitBox = new Rectangle(x-(spriteWidth/2)+activeSpriteOffsetInX, y-(spriteHeight/2)+activeSpriteOffsetInY, spriteWidth, spriteHeight);
    }

    public void pickUpItem(){
        int itemID = gamePanel.checkItemCollision(hitBox);
        if(itemID!=-1){
            gamePanel.pickUpItem(itemID);
            System.out.println("Item picked up!");
            System.out.println(inventory.size()+" items with player!");
        }
    }
    //Overriden abstract methods from Character class
    public void loadSprites(){
        for(int modeNumber = 1;modeNumber<=3;modeNumber++){
            for(int spriteNumber = 1;spriteNumber<=4;spriteNumber++){
                String modeNumberAsString = "0"+Integer.toString(modeNumber);
                String spriteNumberAsString = "0"+Integer.toString(spriteNumber);
                String spritePath = this.getPath()+"\\Sprites\\Player\\";
                spritePath+=modeNumberAsString+"\\"+"player_"+modeNumberAsString+"_"+spriteNumberAsString+".png";
                try{
                    if(modeNumber==1){ //Idle
                        //spriteNumber-1 since the array spritesIdle is 0-indexed
                        spritesIdle[spriteNumber-1] = ImageIO.read(new File(spritePath));
                    }else if(modeNumber==2){ //Gun Holding
                        //spriteNumber-1 since the array spritesBlueGunHolding is 0-indexed
                        spritesBlueGunHolding[spriteNumber-1] = ImageIO.read(new File(spritePath));
                    }

                }catch(Exception e){
                    System.out.println("Error loading player sprite");
                }
            }   
        }
    }

    public void loadSpriteOffset(){
        for(int modeNumber = 1;modeNumber<=3;modeNumber++){
            for(int spriteNumber = 1;spriteNumber<=4;spriteNumber++){
                String modeNumberAsString = "0"+Integer.toString(modeNumber);
                String spriteNumberAsString = "0"+Integer.toString(spriteNumber);
                String offsetPath = this.getPath()+"\\Sprites\\Player\\";
                offsetPath+=modeNumberAsString+"\\"+"offset_"+modeNumberAsString+"_"+spriteNumberAsString+".txt";
                try{
                    int offsetX = 0;
                    int offsetY = 0;
                    Scanner offsetInput = new Scanner(new File(offsetPath));

                    while(offsetInput.hasNext()){
                        offsetX = offsetInput.nextInt();
                        offsetY = offsetInput.nextInt();
                    }
                    offsetInput.close(); //Close input to file
                    
                    if(modeNumber==1){ //Idle
                        //spriteNumber-1 since the arrays are 0-indexed
                        idleOffsetInX[spriteNumber-1] = offsetX;
                        idleOffsetInY[spriteNumber-1] = offsetY;
                    }else if(modeNumber==2){ //Gun Holding
                        //spriteNumber-1 since the arrays are 0-indexed
                        blueGunHoldingOffsetInX[spriteNumber-1] = offsetX;
                        blueGunHoldingOffsetInY[spriteNumber-1] = offsetY;
                    } 

                }catch(Exception e){
                    System.out.println("Error loading sprite offset data");
                }
            }   
        }
    }
    public void update(){
        currentTime = System.currentTimeMillis();
        timeDifference = (double)(currentTime-previousTime)/1000.0;
        attackDelay+=timeDifference;
        previousTime = currentTime;

        if(this.getHealth()<=0){
            Death();
        }
    }
    //Abstract classes from Character
    public void Death(){
        gamePanel.gameOver();
    }
    public void Attack(){
        if(attackDelay>=0.75){ //0.75 second delay between consecutive attacks from player
            if(mode==2){ //Blue Laser gun
                BlueLaser blueLaser = new BlueLaser(gamePanel.nextProjectileID(), gamePanel, tileMap, orientation, direction, x, y, 5, this.getPath());
                gamePanel.projectiles.add(blueLaser);
            }
            attackDelay = 0.0;
        }
    }

    public void setMode(int newMode){
        if(this.mode==newMode){ //Toggle back to idle
            this.mode = 1;
            System.out.println("Mode switched to 1");
            updateActiveModeSprites();
        }else{
            System.out.println("Mode switched to "+newMode);
            this.mode = newMode;
            updateActiveModeSprites();
        }
    }

    public void updateActiveModeSprites(){
        if(this.mode==1){ //Idle
            activeModeSprites = spritesIdle;
            activeModeSpriteOffsetInX = idleOffsetInX;
            activeModeSpriteOffsetInY = idleOffsetInY;
        }else if(this.mode==2){ //Gun holding
            activeModeSprites = spritesBlueGunHolding;
            activeModeSpriteOffsetInX = blueGunHoldingOffsetInX;
            activeModeSpriteOffsetInY = blueGunHoldingOffsetInY;
        }
    }

    public void setOrientation(char orient){
        if(orient=='W'){
            //Facing Up
            orientation = 0;
            direction = -1;
            activeSprite = activeModeSprites[0];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[0];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[0];
        }else if(orient=='A'){
            //Facing Left
            orientation = 1;
            direction = -1;
            activeSprite = activeModeSprites[1];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[1];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[1];
        }else if(orient=='S'){
            //Facing Down
            orientation = 0;
            direction = 1;
            activeSprite = activeModeSprites[2];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[2];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[2];
        }else if(orient=='D'){
            //Facing Right
            orientation = 1;
            direction = 1;
            activeSprite = activeModeSprites[3];
            activeSpriteOffsetInX = activeModeSpriteOffsetInX[3];
            activeSpriteOffsetInY = activeModeSpriteOffsetInY[3];
        }
    }

    //From Moveable
    public void moveUp(){
        setOrientation('W');
        //Move if there is NO Collision with non-passable tile
        if(!tileMap.checkCollision(topBoundingBox) && (gamePanel.checkDefenceCollision(topBoundingBox)==-1) ){
            tileMap.translate(0, moveSpeed);
        }
    }
    public void moveLeft(){
        setOrientation('A');
        if(!tileMap.checkCollision(leftBoundingBox) && (gamePanel.checkDefenceCollision(leftBoundingBox)==-1)){
            tileMap.translate(moveSpeed, 0);
        }
    }
    public void moveDown(){
        setOrientation('S');
        if(!tileMap.checkCollision(bottomBoundingBox) && (gamePanel.checkDefenceCollision(bottomBoundingBox)==-1)){
            tileMap.translate(0, -moveSpeed);
        }
    }
    public void moveRight(){
        setOrientation('D');
        if(!tileMap.checkCollision(rightBoundingBox) && (gamePanel.checkDefenceCollision(rightBoundingBox)==-1)){
            tileMap.translate(-moveSpeed, 0);
        }
    }

    //From Drawable
    public void draw(Graphics g){
        g.drawImage(activeSprite, x-(spriteWidth/2)+activeSpriteOffsetInX, y-(spriteHeight/2)+activeSpriteOffsetInY, null);

        ///*
        g.drawRect(x-15, y-(spriteHeight/2), 30, 3);
        g.drawRect(x-(spriteWidth/2), y-15, 3, 30);
        g.drawRect(x-15, y+(spriteHeight/2), 30, 3);
        g.drawRect(x+(spriteWidth/2), y-15, 3, 30);
        //*/
    }
}
