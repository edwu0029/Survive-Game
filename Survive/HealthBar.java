import java.awt.Color;
import java.awt.Graphics;

/**
 * [HealthBar.java]
 * A class that represents a health bar object for some object with health.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class HealthBar implements Drawable{
    /*-----References to other objects-----*/
    private Level level;
    private TileMap tileMap;
    private HealthManager healthManager;

    /*-----Variables for this health bar-----*/
    //Position
    private int x;
    private int y;

    //Dimensions
    private int width;
    private int height;
    private int healthWidth; //Width of the displayed health's bar
    /**
     * HealthBar
     * A constructor that constructs a health bar ibject with a specified level, health manager and position.
     * @param level The level that this health bar is created in.
     * @param healthManager The health manager that this health bar is created for.
     * @param x The x-coordinate of the top-left corner of the health bar relative to the window.
     * @param y The y-coordinate of the top-left corner of the health bar relative to the window.
     */
    HealthBar(Level level, HealthManager healthManager, int x, int y){
        this.level = level;
        this.healthManager = healthManager;

        this.x = x;
        this.y = y;

        //All health bars have a width of 40 and height of 5
        this.width = 40;
        this.height = 5;
    }
    /**
     * update
     * Updates the data in this health bar that changes over time. This includes the width of the health part.
     */
    public void update(){
        //Find percentage of health left
        double percentage = ((double)healthManager.getHealth()/healthManager.getMaxHealth());

        //Using that percent, find the equivalent width;
        healthWidth = (int)(width*percentage);
    }
    /**
     * draw
     * The graphics 
     * @param g The graphics object tht this health bar is drawn on.
     */
    public void draw(Graphics g){
        //Draw outline
        g.setColor(new Color(36, 36, 36));
        //Has border has a thickness of 2
        g.fillRect(x, y, width+4, height+4);

        //Draw health
        g.setColor(new Color(255, 0, 0));
        g.fillRect(x+2, y+2, healthWidth, height);

        //Set color back to black to prevent any inconsistencies when other objects are drawn
        g.setColor(new Color(0, 0, 0));
    }

}
