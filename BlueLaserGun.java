import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

class BlueLaserGun extends Item {
    
    private BufferedImage sprite;

    BlueLaserGun(int id, GamePanel gamePanel, TileMap tileMap, int x, int y, String path){
        super(id, gamePanel, tileMap, x, y, "Blue Laser Gun", path);
        loadSprites();
        super.setBoundingBox(sprite.getWidth(), sprite.getHeight());
    }

    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(this.getPath()+"\\Sprites\\Item\\bluelasergun.png"));
        }catch(Exception e){
            System.out.println("Error loading blue laser gun sprite");
        }
    }
    //Overwridden abstract methods from Item class
    public void consume(){
        
    }
    public void draw(Graphics g){
        g.drawImage(sprite, this.getX(), this.getY(), null);
        g.drawRect(this.getX(), this.getY(), sprite.getWidth(), sprite.getHeight());
    }
    
}
