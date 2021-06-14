import java.io.File;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage; 
import java.awt.Graphics;

/**
 * [Zombie.java]
 * A class that represents a zombie enemy object.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Zombie extends Enemy{
    /*-----References to other objects----*/
    private Level level;
    private TileMap tileMap;
    private Player player;

    /*-----Variables for this zombie enemy-----*/
    private String path;
    private int damage; //The damage this zombie does each attack

    //Variables for positions
    private int x;
    private int y;

    //Variables for sprites
    private BufferedImage[] sprites;
    private BufferedImage activeSprite;
    private int spriteHeight;
    private int spriteWidth;

    //Attacking ranges
    private Rectangle topRange;
    private Rectangle leftRange;
    private Rectangle bottomRange;
    private Rectangle rightRange;

    /**
     * Zombie
     * A constructor that constructs a zombie enemy with a specified level, max health, position, and damage.
     * @param level The level that this zombie enemy is created for.
     * @param maxHealth The maximum health of this zombie.
     * @param x 
     * @param y
     * @param dmg The damage this zombie does each attack.
     */
    Zombie(Level level, int maxHealth, int x, int y, int dmg){
        super(level, maxHealth, x, y);
        this.level = level;
        this.tileMap = level.getTileMap();
        this.path = level.getPath();
        this.player = level.getPlayer();
        this.damage = dmg;

        this.sprites = new BufferedImage[4];
        loadSprites();
        activeSprite = sprites[0];
        this.spriteHeight = activeSprite.getHeight();
        this.spriteWidth = activeSprite.getWidth();

        super.setHitBox(40, 40);

        this.topRange = new Rectangle(this.getRelativeX()-15, this.getRelativeY()-(spriteHeight/2), 30, 10);
    }
    /**
     * loadSprites
     * An overwridden method form the Enemy clas that loads the zombie sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
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
    //Overwridden mthod from Enemy class
    /**
     * update
     * An overridden from the Enemy class that updates all of this zombie's data that changes over time. This includes
     * attacking range boxes and attacking.
     */
    public void update(){
        super.update();
        //Update attacking ranges
        topRange.x = this.getRelativeX();
        topRange.y = this.getRelativeY();

        //If in range, attack
        if(player.getHitBox().intersects(topRange) && this.getAttackDelay()>=2.0){
            attack();
            setAttackDelay(0.0);
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
     * An overwritten method from the Enemy class that intiates the attack of this zombie object.
     */
    public void attack(){
        System.out.println("Attack");
        player.getHealthManager().takeDamage(damage);;
    }

    //From Drawable interface
    /**
     * draw
     * An overwritten method from the Enemy class that draws this zombie enemy on screen.
     * @param g The graphics object that this zombie is to be drawn on.
     */
    public void draw(Graphics g) {
        g.drawImage(activeSprite, this.getRelativeX()-(spriteWidth/2), this.getRelativeY()-(spriteHeight/2), null);
        g.drawRect(this.getRelativeX()-15, this.getRelativeY()-(spriteHeight/2), 30, 10);
        g.drawRect(this.getRelativeX()-activeSprite.getWidth()/2, this.getRelativeY()-activeSprite.getHeight()/2, activeSprite.getWidth(), activeSprite.getHeight());
    }
}
