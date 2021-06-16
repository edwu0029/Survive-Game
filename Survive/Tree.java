import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;
import java.awt.Graphics;

/**
 * [Tree.java]
 * A class that represents a tree enemy.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Tree extends Enemy{
    /*-----References to other objects-----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private Player player;

    /*-----Variables for this Enemy-----*/
    private String path;
    private int damage;

    //Sprites
    private BufferedImage spriteTopLeft;
    private BufferedImage spriteTopRight;
    private BufferedImage spriteBottomRight;
    private BufferedImage spriteBottomLeft;

    /**
     * Tree
     * A constructor that constructs a tree enemy with a specified leve, maxHealth, and position.
     * @param level The level that this tree is created for.
     * @param maxHealth The maximum health of this tree.
     * @param x The x-coordinate of the top-left corner of this tree relative to the window.
     * @param y The y-coordinate of the top-left corner of this tree relative to the window.
     */
    Tree(Level level, int x, int y){
        //All trees have a max health of 100
        super(level, 100, x, y);
        this.level = level;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.tileMap = level.getTileMap();
        this.player = level.getPlayer();
        this.path = level.getPath();

        //All trees have a damage of 10
        this.damage = 10;

        loadSprites();
        super.setHitBox(128, 128);
    }
    /**
     * loadSprites
     * Loads the tree sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        try{
            String spritePath = path+"Sprites\\Enemy\\Tree\\";
            spriteTopLeft = ImageIO.read(new File(spritePath+"tree_01.png"));
            spriteTopRight = ImageIO.read(new File(spritePath+"tree_02.png"));
            spriteBottomRight = ImageIO.read(new File(spritePath+"tree_03.png"));
            spriteBottomLeft = ImageIO.read(new File(spritePath+"tree_04.png"));
        }catch(Exception e){
            System.out.println("Error loading zombie sprites");
        }
    }
    /**
     * death
     * An overwridden method from the Enemy class that intiates the death of this tree enemy.
     */
    public void death(){
        level.getItems().add(new Wood(level, this.getRelativeX(), this.getRelativeY()));
        level.getItems().add(new Fruit(level, this.getRelativeX()+50, this.getRelativeY()-20));
        super.death();
    }
    /**
     * attack
     * An overwridden method form the Enemy class that intiates the attack of this tree enemy.
     */
    public void attack(){
        player.getHealthManager().takeDamage(damage);
    }
    /**
     * update
     * An overridden from the Enemy class that updates all of this tree's data that changes over time. This includes checking for death.
     */
    public void update(){
        super.update();

        //If in range, attack
        if(checkCollision(player.getHitBox()) && this.getAttackDelay()>=2.0){
            attack();
            setAttackDelay(0.0);
        }

        //Check for health and death
        if(getHealthManager().getHealth()<=0){
            death();
        }
    }
    //From Drawable interface
    /**
     * draw
     * An overwritten method from the Enemy class that draws this tree enemy on screen.
     * @param g The graphics object that this tree enemy is to be drawn on.
     */
    public void draw(Graphics g) {
        //Draw top-left corner
        g.drawImage(spriteTopLeft, this.getRelativeX()-64, this.getRelativeY()-64, null);
        //Draw top-left corner
        g.drawImage(spriteTopRight, this.getRelativeX(), this.getRelativeY()-64, null);
        //Draw top-left corner
        g.drawImage(spriteBottomLeft, this.getRelativeX()-64, this.getRelativeY(), null);
        //Draw top-left corner
        g.drawImage(spriteBottomRight, this.getRelativeX(), this.getRelativeY(), null);
    }

}
