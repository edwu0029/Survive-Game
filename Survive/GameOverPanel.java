import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * [GameOverPanel.java]
 * A class that represents the game over panel for this game, which is shown once the game has reached
 * the game over stage.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class GameOverPanel extends JPanel{
    /*-----References to other objects-----*/
    private Level level;

    /**
     * GameOverPanel
     * A constructor that constructs a game over panel for a specifed level.
     * @param level The level that this game over panel is created for.
     */
    GameOverPanel(Level level){
        this.level = level;
        level.setGameOverPanel(this);

        GameOverMouseListener mouseListener = new GameOverMouseListener(level);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    /**
     * animate
     * A method that uses java threads to run the game loop for this game over panel.
     */
    public void animate(){
        while(true){
            //delay
            try{
                Thread.sleep(10);
            }catch(Exception exc){
              System.out.println("Thread Error");
            }
            
            //Repaint request
            this.repaint();
          }   
    }
    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics object in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //Draw background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 1080, 1080);

        //Draw text
        g.setColor(new Color(255, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 60));
        g.drawString("Game Over", 375, 200);

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Click anywhere to continue", 300, 500);
    }
}
