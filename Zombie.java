import java.io.File;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage; 
import java.awt.Graphics;

class Zombie extends Enemy{

    private TileMap tileMap;
    private Player player;
    private String path;

    private int x;
    private int y;

    private int damage;

    private BufferedImage[] sprites;
    private BufferedImage activeSprite;

    private int spriteHeight;
    private int spriteWidth;

    private Rectangle topRange;
    private Rectangle leftRange;
    private Rectangle bottomRange;
    private Rectangle rightRange;

    Zombie(int id, GamePanel gamePanel, TileMap tileMap, Player player, int maxHealth, String path, int x, int y, int dmg){
        super(id, gamePanel, tileMap, player, maxHealth, x, y, path);
        this.tileMap = tileMap;
        this.path = path;
        this.player = player;
        this.damage = dmg;

        this.sprites = new BufferedImage[4];
        loadSprites();
        activeSprite = sprites[0];
        this.spriteHeight = activeSprite.getHeight();
        this.spriteWidth = activeSprite.getWidth();

        super.setHitBox(40, 40);

        this.topRange = new Rectangle(this.getX()-15, this.getY()-(spriteHeight/2), 30, 10);
    }
    //Overwridden mthod from Enemy class
    public void update(){
        super.update();
        topRange.x = this.getX();
        topRange.y = this.getY();
        if(player.hitBox.intersects(topRange) && this.getAttackDelay()>=2.0){
            Attack();
            this.setAttackDelay(0.0);
        }
    }

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
    public void loadSpriteOffset(){

    }
    public void Death(){
        super.Death();
    }
    public void Attack(){
        System.out.println("Attack");
        player.takeDamage(damage);
    }

    //From Drawable interface
    public void draw(Graphics g) {
        g.drawImage(activeSprite, this.getX()-(spriteWidth/2), this.getY()-(spriteHeight/2), null);
        g.drawRect(this.getX()-15, this.getY()-(spriteHeight/2), 30, 10);
        g.drawRect(this.getX()-activeSprite.getWidth()/2, this.getY()-activeSprite.getHeight()/2, activeSprite.getWidth(), activeSprite.getHeight());
    }
}
