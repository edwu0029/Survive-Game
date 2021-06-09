import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

public class Fruit extends Item{
    private Player player;
    private BufferedImage sprite;

    private int healAmount;

    Fruit(int id, GamePanel gp, TileMap tm, Player p, int x, int y, String path){
        super(id, gp, tm, x, y, "Fruit", path);
        this.player = p;
        loadSprites();
        this.healAmount = 100;
        super.setBoundingBox(sprite.getWidth(), sprite.getHeight());
    }

    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(this.getPath()+"\\Sprites\\Item\\fruit.png"));
        }catch(Exception e){
            System.out.println("Error loading fruit sprite");
        }
    }
    //Overwridden abstract methods from Item class
    public void consume(){
        player.heal(healAmount);
    }
    public void draw(Graphics g){
        g.drawImage(sprite, this.getX(), this.getY(), null);
        g.drawRect(this.getX(), this.getY(), sprite.getWidth(), sprite.getHeight());
    }
}
