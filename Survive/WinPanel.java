import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * [WinPanel.java]
 * A class that represents the win panel for this game, which is shown once the game has been won.
 * the game over stage.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class WinPanel extends JPanel{
    /*-----References to to other objects-----*/
    private Level level;
    private PanelManager panelManager;
    /**
     * WinPanel
     * A constructor that constructs a win panel for a specifed level.
     * @param level The level that this win panel is created for.
     */
    WinPanel(Level level){
        this.level = level;
        this.panelManager = level.getPanelManager();
        panelManager.setWinPanel(this);

        EndMouseListener mouseListener = new EndMouseListener(level);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    /**
     * animate
     * A method that uses java threads to run the game loop for this win panel.
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
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, 1080, 1080);

        //Draw text
        g.setColor(new Color(0, 255, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 60));
        g.drawString("You Survived!", 330, 200);

        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Click anywhere to continue", 300, 500);
    }
}
