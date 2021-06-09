import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import java.awt.Graphics;

class Tree extends Enemy{

    private String path;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private Player player;

    private BufferedImage spriteTopLeft;
    private BufferedImage spriteTopRight;
    private BufferedImage spriteBottomRight;
    private BufferedImage spriteBottomLeft;

    Tree(int id, GamePanel gp, TileMap tm, Player p, int maxHealth, String path, int x, int y){
        super(id, gp, tm, p, maxHealth, x, y, path);
        this.gamePanel = gp;
        this.tileMap = tm;
        this.player = p;
        this.path = path;
        loadSprites();
        super.setHitBox(256, 256);
    }

    public void loadSprites(){
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
    public void loadSpriteOffset(){

    }
    public void Death(){
        gamePanel.addItem(new Wood(gamePanel.nextItemID(), gamePanel, tileMap, this.getX(), this.getY(), path));
        gamePanel.addItem(new Fruit(gamePanel.nextItemID(), gamePanel, tileMap, player, this.getX()+50, this.getY()-20, path));
        super.Death();
        
    }
    public void Attack(){
        System.out.println("Attack");
    }

    //TEMPPORARY
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    //From Drawable interface
    public void draw(Graphics g) {
        g.drawImage(spriteTopLeft, this.getX(), this.getY(), null);
        g.drawImage(spriteTopRight, this.getX()+64, this.getY(), null);
        g.drawImage(spriteBottomLeft, this.getX(), this.getY()+64, null);
        g.drawImage(spriteBottomRight, this.getX()+64, this.getY()+64, null);
    }

}
