import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

/**
 * [Zombie.java]
 * A class that represents a zombie enemy object.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Zombie extends Enemy implements Moveable{
    /*-----References to other objects----*/
    private Level level;
    private TileMap tileMap;
    private Player player;
    private ArrayList<Defence> defences;

    /*-----Variables for this zombie enemy-----*/
    private String path;
    private int damage; //The damage this zombie does each attack
    private int moveSpeed;

    //Variables for sprites
    private BufferedImage[] sprites;
    private BufferedImage activeSprite;
    private int spriteHeight;
    private int spriteWidth;

    //Attacking ranges
    private boolean defenceTouching;
    private Rectangle topRange;
    private Rectangle leftRange;
    private Rectangle bottomRange;
    private Rectangle rightRange;
    

    //Variables for path finding
    private Tile[][] tileMapArray;
    private boolean[][] visited;
    private int goalX; //x-coordinate of the goal
    private int goalY; //y-coordinate of the goal
    private Tile goalTile;
    private ArrayList<Tile> directions; //The tiles that should be followed
    private Tile startTile;
    private Tile[][] backTrack; //Used for backtracking tiles to find the total path

    /**
     * Zombie
     * A constructor that constructs a zombie enemy with a specified level, max health, position, and damage.
     * @param level The level that this zombie enemy is created for.
     * @param maxHealth The maximum health of this zombie.
     * @param x The x-coordinate of the centre of this zombie enemy.
     * @param y The y-coordinate of the centre of this zombie enemy.
     * @param dmg The damage this zombie does each attack.
     */
    Zombie(Level level, int x, int y){
        //All zombies have a health of 300
        super(level, 300, x, y);
        this.level = level;
        this.tileMap = level.getTileMap();
        this.path = level.getPath();
        this.player = level.getPlayer();
        //All zombies deal 50 damage per attack
        this.damage = 50;

        this.moveSpeed = 2;

        this.defences = level.getDefences();

        this.sprites = new BufferedImage[4];
        loadSprites();
        activeSprite = sprites[0];
        this.spriteHeight = activeSprite.getHeight();
        this.spriteWidth = activeSprite.getWidth();
        
        //Set up hit box with width and height of 40 pixels
        super.setHitBox(40, 40);

        //Set up attacking ranges
        this.topRange = new Rectangle(getRelativeX()-15, getRelativeY()-(spriteHeight/2), 30, 10);
        this.bottomRange = new Rectangle(getRelativeX()-15, getRelativeY()+(spriteHeight/2), 30, 10);
        this.leftRange = new Rectangle(getRelativeX()-(spriteWidth/2), getRelativeY()-15, 10, 30);
        this.rightRange = new Rectangle(getRelativeX()+(spriteWidth/2), getRelativeY()-15, 10, 30);

        
        //Set up path finding
        this.tileMapArray = tileMap.getTileMapArray();

        //Find current Tile
        for(int i = 0;i<tileMap.getHeight();i++){
            for(int j = 0;j<tileMap.getWidth();j++){
                if( (tileMapArray[i][j].getAbsoluteX()==this.getAbsoluteX()-32) &&
                    (tileMapArray[i][j].getAbsoluteY()==this.getAbsoluteY()-32) ){ //Check if centre point matches
                    startTile = tileMapArray[i][j];
                }
            }
        }
        //Find the computer/goal that this zombie will move towards
        for(int i = 0;i<defences.size();i++){
            if(defences.get(i).getType().equals("Computer")){
                goalX = defences.get(i).getAbsoluteX();
                goalY = defences.get(i).getAbsoluteY();
                //Once goal is found, break out of loop
                break;
            }
        }

        if(visited==null){
            bfs();
        }
        loadDirections();
    }
    /**
     * loadSprites
     * An overwridden method form the Enemy clas that loads the zombie sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        try{
            for(int i = 1;i<=4;i++){
                String spritePath = path+"Sprites\\Enemy\\Zombie\\zombie_";
                spritePath+="0"+Integer.toString(i)+".png";
                sprites[i-1] = ImageIO.read(new File(spritePath));
            }
        }catch(Exception e){
            System.out.println("Error loading zombie sprites");
        }
    }
    /**
     * bfs
     * A method that intiates a breadth first search on the tile map to find a path
     * for this zombie enemy to get to the goal.
     */
    public void bfs(){
        boolean goalFound = false;
        LinkedList<Tile> queue = new LinkedList<Tile>(); //Queue for bfs

        //Reset variables
        visited = new boolean[tileMap.getHeight()][tileMap.getWidth()];
        backTrack = new Tile[tileMap.getHeight()][tileMap.getWidth()];

        queue.add(startTile);
        while(!queue.isEmpty()){ 
            //Poll(get and remove) top tile from queue
            Tile currentTile = queue.poll();
            int currentRow = currentTile.getRow();
            int currentColumn = currentTile.getColumn();

            //Prevent over calcualtion due to threading
            if(visited[currentRow][currentColumn]){
                continue;
            }

            //Set this tile as visited
            visited[currentRow][currentColumn] = true;
            backTrack[startTile.getRow()][startTile.getColumn()] = startTile;

            //Check if current tile is the goal
            if( (currentTile.getAbsoluteX()==goalX) && (currentTile.getAbsoluteY()==goalY)){
                goalFound = true;
                goalTile = currentTile;
                break;
            }

            //Check neighbours
            //Check top neighbouring tile
            if( (currentRow-1>=0) && tileMapArray[currentRow-1][currentColumn].getPassable() && !visited[currentRow-1][currentColumn]){
                 //Add to queue
                 queue.add(tileMapArray[currentRow-1][currentColumn]);
                 //Mark back track
                 backTrack[currentRow-1][currentColumn] = currentTile;
            }
            //Check left neighbouring tile
            if( (currentColumn-1>=0) && tileMapArray[currentRow][currentColumn-1].getPassable() && !visited[currentRow][currentColumn-1]){
                 //Add to queue
                 queue.add(tileMapArray[currentRow][currentColumn-1]);
                 //Mark back track
                 backTrack[currentRow][currentColumn-1] = currentTile;
            }

            //Check bottom neighbouring tile
            if( (currentRow+1<tileMap.getHeight()) && tileMapArray[currentRow+1][currentColumn].getPassable() && !visited[currentRow+1][currentColumn]){
                //Add to queue
                queue.add(tileMapArray[currentRow+1][currentColumn]);
                //Mark back track
                backTrack[currentRow+1][currentColumn] = currentTile;
            }
            //Check right neighbouring tile
            if( (currentColumn+1<tileMap.getWidth()) && tileMapArray[currentRow][currentColumn+1].getPassable() && !visited[currentRow][currentColumn+1]){
                 //Add to queue
                 queue.add(tileMapArray[currentRow][currentColumn+1]);
                 //Mark back track
                 backTrack[currentRow][currentColumn+1] = currentTile;
            }
        }
    }
    /**
     * loadDirections
     * Loads the directions from the zombie's current point to the goal.
     */
    public void loadDirections(){
        directions = new ArrayList<Tile>();
        Tile currentTile = goalTile;
        //Begin back tracking from goal to start using backTrack array
        while(backTrack[currentTile.getRow()][currentTile.getColumn()] != currentTile){
            directions.add(currentTile);
            currentTile = backTrack[currentTile.getRow()][currentTile.getColumn()];
        }
        //Reverse arraylist for correct order
        Collections.reverse(directions);
    }
    /**
     * moveTowardGoal
     * Moves this zombie toward the goal using directions calculated using path-finding.
     */
    public void moveTowardGoal(){
        if(directions.size()!=0){ //Only move if there are directions left
            Tile nextTile = directions.get(0); //Get next tile
            if((nextTile.getAbsoluteX()+32==getAbsoluteX()) && (nextTile.getAbsoluteY()+32==getAbsoluteY())){
                directions.remove(0); //Remove this direction as it has been reached
            }else{ //Not yet reached
                if(getAbsoluteX()<nextTile.getAbsoluteX()+32){
                    moveRight();
                }else if(getAbsoluteX()>nextTile.getAbsoluteX()+32){
                    moveLeft();
                }else if(getAbsoluteY()<nextTile.getAbsoluteY()+32){
                    moveDown();
                }else if(getAbsoluteY()>nextTile.getAbsoluteY()+32){
                    moveUp();
                }
            }
        }
    }
    /**
     * setOrientation
     * Sets the orientation of this player to some new orientation and updates the active data accourdingly.
     * @param newOrientation The new orientation of this player as a char.
     */
    public void setOrientation(char newOrientation){
        if(newOrientation=='W'){ //Facing Up
            activeSprite = sprites[0];
        }else if(newOrientation=='A'){ //Facing Left
            activeSprite = sprites[1];
        }else if(newOrientation=='S'){ //Facing Down
            activeSprite = sprites[2];
        }else if(newOrientation=='D'){ //Facing Right
            activeSprite = sprites[3];
        }
    }
    //Overwridden mthod from Enemy class
    /**
     * update
     * An overridden from the Enemy class that updates all of this zombie's data that changes over time. This includes
     * attacking range boxes and attacking.
     */
    public void update(){
        super.update();
        //Update attacking ranges
        topRange.x = getRelativeX()-15;
        topRange.y = getRelativeY()-(spriteHeight/2);

        bottomRange.x = getRelativeX()-15;
        bottomRange.y = getRelativeY()+(spriteHeight/2);

        leftRange.x = getRelativeX()-(spriteWidth/2);
        leftRange.y = getRelativeY()-15;

        rightRange.x = getRelativeX()+(spriteWidth/2);
        rightRange.y = getRelativeY()-15;


        //Move toward goal
        //Check if anything is touching attacking ranges
        defenceTouching = false;
        for(int i = 0;i<defences.size();i++){
            boolean attackDefence = false;
            if(defences.get(i).checkCollision(topRange) || defences.get(i).checkCollision(bottomRange) ||
                defences.get(i).checkCollision(leftRange) || defences.get(i).checkCollision(rightRange)){
                attackDefence = true;
                defenceTouching = true;
                //If delay is enough, attack defence
                if(getAttackDelay()>=2.0){
                    attack(defences.get(i));
                }
            }
        }
        moveTowardGoal();

        //If in range, attack
        if( (player.getHitBox().intersects(topRange) || player.getHitBox().intersects(bottomRange) 
            || player.getHitBox().intersects(leftRange) || player.getHitBox().intersects(rightRange))
            && getAttackDelay()>=2.0){
            attack();
        }

        //Check for health and death
        if(getHealthManager().getHealth()<=0){
            death();
        }
    }
    /**
     * death
     * An overwridden method from the Enemy class that intiates the death of this zombie enemy.
     */
    public void death(){
        super.death();
    }
    /**
     * attack
     * An overwritten method from the Enemy class that intiates the attack of this zombie object
     * to a player.
     */
    public void attack(){
        player.getHealthManager().takeDamage(damage);
        setAttackDelay(0.0);
    }
    /**
     * attack
     * An overwritten method from the Enemy class that intiates the attack of this zombie object
     * to a defence.
     */
    public void attack(Defence defence){
        defence.getHealthManager().takeDamage(damage);
        setAttackDelay(0.0);
    }
    //From Moveable interface
    /**
     * moveUp
     * A overwridden method from the Moveable interface that moves the zombie up.
     */
    public void moveUp(){
        if(!defenceTouching){
            setOrientation('W');
            this.setY(getAbsoluteY()-moveSpeed);
        }
    }
    /**
     * moveLeft
     * A overwridden method from the Moveable interface that moves the zombie left.
     */
    public void moveLeft(){
        if(!defenceTouching){
            setOrientation('A');
        this.setX(getAbsoluteX()-moveSpeed);
        }
    }
    /**
     * moveDown
     * A overwridden method from the Moveable interface that moves the zombie down.
     */
    public void moveDown(){
        if(!defenceTouching){
            setOrientation('S');
            this.setY(getAbsoluteY()+moveSpeed);
        }
    }
    /**
     * moveRight
     * A overwridden method from the Moveable interface that moves the zombie right.
     */
    public void moveRight(){
        if(!defenceTouching){
            setOrientation('D');
            this.setX(getAbsoluteX()+moveSpeed);
        }
        
    }
    //From Drawable interface
    /**
     * draw
     * An overwritten method from the Enemy class that draws this zombie enemy on screen.
     * @param g The graphics object that this zombie is to be drawn on.
     */
    public void draw(Graphics g) {
        g.drawImage(activeSprite, this.getRelativeX()-(spriteWidth/2), this.getRelativeY()-(spriteHeight/2), null);
    }
}
