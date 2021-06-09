import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Graphics;

class FenceHorizontal extends Defence{

    private String path;
    private BufferedImage sprite;

    FenceHorizontal(int id, GamePanel gp, TileMap tm, Player p, String path, int x, int y){
        super(id, gp, tm, p, "Fence Horizontal", x, y);
        this.path = path;
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(path+"\\Sprites\\Defence\\01\\fence_02.png"));
        }catch(Exception e){
            System.out.println("Could not load horizontal fence sprite");
        }
    }

    public void death(){

    }

    //Overwrriden from Defence class
    public void draw(Graphics g){
        super.draw(g);
        g.drawImage(sprite, this.getX(), this.getY(), null);
    }
}
