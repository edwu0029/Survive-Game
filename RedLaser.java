import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Graphics;

class RedLaser extends Projectile implements Drawable{
    private String path;
    private int orientation;
    private int direction;

    //Variables for vertical blue laser
    private BufferedImage sprite;
    private int width;
    private int height;

    RedLaser(int id, GamePanel gamePanel, TileMap tileMap, int orientation, int direction, int x, int y, int speedMultiplier, String path){
        super(id, gamePanel, tileMap, x, y);
        this.path = path;
        this.orientation = orientation;
        this.direction = direction;
        loadSprites();
        super.setBoundingBox(width, height);
        //TEMPORARY
        super.setDamage(100);
        if(orientation==0){ //Vertical
            super.setVelocityX(0);
            super.setVelocityY(30*direction);
        }else{ //Horizontal
            super.setVelocityX(30*direction);
            super.setVelocityY(0);
        }
    }

    public void loadSprites(){
        try{
            if(orientation==0){ //Vertical
                sprite = ImageIO.read(new File(path+"Sprites\\Projectile\\Blue Laser\\bluelaser_01.png"));
                width = sprite.getWidth();
                height = sprite.getHeight();
            }else{ //Horizontal
                sprite = ImageIO.read(new File(path+"Sprites\\Projectile\\Blue Laser\\bluelaser_02.png"));
                width = sprite.getWidth();
                height = sprite.getHeight();
            }
        }catch(Exception e){
            System.out.println("Error loading blue laser sprites");
        }
    }

    //From Drawable interface
    public void draw(Graphics g){
        super.draw(g);
        // g.drawRect(this.getX(), this.getY(), width, height);
        g.drawImage(sprite, this.getX(), this.getY(), null);
    }
}
