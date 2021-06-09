import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

class GameOverPanel extends JPanel{

    private PanelManager panelManager;
    
    private boolean pause;

    GameOverPanel(PanelManager pm){
        this.panelManager = pm;

        this.pause = false;

        GameOverMouseListener mouseListener = new GameOverMouseListener(panelManager);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
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
    public void togglePause(boolean value){
        pause = value;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawString("Game Over", 500, 500);
    }
}
