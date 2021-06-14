import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Graphics;

class HorizontalFence extends Defence{
    /*-----Variables for this Horizontal Fence-----*/
    private BufferedImage sprite;
    private String path;

    /**
     * HorizontalFence
     * A constructor that constructs a horizontal fence denfence with a specified level and position.
     * @param level The leve that this horizontal fence is created for.
     * @param x The x-coordinate of the top-left corner of this horizontal fence relative to the window.
     * @param y The y-coordinate of the top-left corner of this horizontal fence relative to the window.
     */
    HorizontalFence(Level level, int x, int y){ 
        //All fences have a maximum health of 300
        super(level, "Horizontal Fence", x, y, 300);
        this.path = level.getPath();
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    /**
     * loadSprites
     * Loads the horizontal fence sprites from the Sprites folder of the game directory.
     */
    public void loadSprites(){
        try{
            sprite = ImageIO.read(new File(path+"\\Sprites\\Defence\\01\\fence_02.png"));
        }catch(Exception e){
            System.out.println("Could not load horizontal fence sprite");
        }
    }

    //Overwrriden from Defence class
    /**
     * draw
     * An overwridden method from the Defence class that draws this horizontal fence on screen.
     * @param g The graphics object that this defence is to be drawn on.
     */
    public void draw(Graphics g){
        super.draw(g);
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
    }
}
