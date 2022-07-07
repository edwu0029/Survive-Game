import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;

/**
 * [Computer.java]
 * A class that represents a computer defence. The computer defence is what the enemies
 * are attracted to. Upon defeat of this computer, game over is triggered.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Computer extends Defence{
    /*-----Variables for this goal-----*/
    private Level level;
    private String path;
    private char sep;
    private BufferedImage sprite;

    /**
     * Computer
     * A constructor that constructs a goal defence.
     * @param level The leve that this goal is created for.
     * @param x The x-coordinate of the top-left corner of this goal relative to the window.
     * @param y The y-coordinate of the top-left corner of this goal relative to the window.
     */
    Computer(Level level, int x, int y){
        //All fences have a maximum health of 1000
        super(level, "Computer", x, y, 1000);
        this.level = level;
        this.path = level.getPath();
        this.sep = level.getSep();
        loadSprites();
        super.setBoundingBox(64, 64);
    }
    /**
     * loadSprites
     * Loads the goal sprites from the Sprites folder of the game directory.
     */
    private void loadSprites(){
        try{
            sprite = ImageIO.read(new File(path+sep+"Sprites"+sep+"Defence"+sep+"goal.png"));
        }catch(Exception e){
            System.out.println("Could not load horizontal fence sprite");
        }
    }
    /**
     * death
     * An overwridden method from the Defence class that intitates the death of this goal defence.
     */
    public void death(){
        level.getPanelManager().setActivePanel("GameOverPanel");
    }
    //Overwrriden from Defence class
    /**
     * draw
     * An overwridden method from the Defence class that draws this goal defence on screen.
     * @param g The graphics object that this defence is to be drawn on.
     */
    public void draw(Graphics g){
        g.drawImage(sprite, this.getRelativeX(), this.getRelativeY(), null);
        g.setColor(new Color(255, 255, 0));
        //Draw yellow outline around this goal defence
        g.drawRect(this.getRelativeX(), this.getRelativeY(), 64, 64);
    }
}
