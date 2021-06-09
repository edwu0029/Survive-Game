import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

class Rock extends Item{

    private BufferedImage sprite;

    Rock(int id, GamePanel gp, TileMap tm, int x, int y, String path){
        super(id, gp, tm, x, y, "Rock", path);
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(this.getPath()+"\\Sprites\\Item\\rock.png"));
        }catch(Exception e){
            System.out.println("Error rock sprite not loaded properly");
        }
    }
    //Overwridden abstract methods from Item class
    public void consume(){
        System.out.println("Wood item can't be consumed");   
    }
    public void draw(Graphics g){
        g.drawImage(sprite, this.getX(), this.getY(), null);
    }
}
