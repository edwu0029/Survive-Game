import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

/**
 * [InstructionsPanel.java]
 * A class that represents the instructions panel for this game.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class InstructionsPanel extends JPanel{
    /*-----References to other objects-----*/
    private MainFrame mainFrame;
    private PanelManager panelManager;

    /*-----Variables for this start panel-----*/
    private String path;
    private GraphicsButton backButton;

    /**
     * InstructionsPanel
     * A constructor that consutructs a InstructionsPanel object and displays it to the main frame.
     * @param mainFrame The main frame that this instructions panel will be displayed on. 
     * @param panelManager The panel manager for this instructions panel.
     */
    InstructionsPanel(MainFrame mainFrame, PanelManager panelManager){
        this.mainFrame = mainFrame;
        this.panelManager = panelManager;
        panelManager.setInstructionsPanel(this);
        
        this.backButton = new GraphicsButton(20, 950, 100, 50);
        this.backButton.setColor(new Color(255, 0, 0));
        this.backButton.setText("Back");
        this.backButton.setTextColor(new Color(255, 255, 255));
        this.backButton.setFont("Arial", 20);

        InstructionsMouseListener mouseListener = new InstructionsMouseListener(mainFrame, panelManager, this);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow();

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    
    /**
     * animate
     * A method that uses java threads to run the game loop for this instructions panel.
     */
    public void animate(){
        while(true){
            //delay
            try{
                Thread.sleep(60);
            }catch(Exception exc){
              System.out.println("Thread Error");
            }
            //Repaint request
            this.repaint();
          }   
    }
    /**
     * getBackButton
     * Returns a reference to the back button for this instructions panel.
     * @return A reference to the back button for this instructions panel.
     */
    public GraphicsButton getBackButton(){
        return backButton;
    }

    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics object in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        //Draw instruction
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("A zombie apocalpyse has broken out! After travelling around, you find a computer in a house.", 100, 100);
        g.drawString("However, you must first survive the night. For some mysterious reason, the zombies have", 100, 120);
        g.drawString("been attracted to your computer! With it being the last chance of hope, protect", 100, 140);
        g.drawString("your computer and survive!", 100, 160);

        g.drawString("Use the 'W', 'A', 'S', 'D' keys to move up, left, down, right respectively.", 100, 200);
        g.drawString("Press 'F' to pick up items on the map.", 100, 220);
        g.drawString("Press 'I' and 'C' to switch to the Inventory and Crafting menus respsectively.", 100, 240);

        g.drawString("Press '1' and '2' to toggle between player modes.", 100, 280);
        g.drawString("NOTE you must pick up AND consume a blue laser gun to use mode 2.", 100, 300);
        g.drawString("The blue laser gun can be found near the player's spawn point!", 100, 320);

        g.drawString("To select other items in your inventory, use the arrow keys.", 100, 360);
        
        g.drawString("Click the left mouse button to shoot lasers if in mode 2.", 100, 380);
        
        //Display back button
        backButton.draw(g);
    }

}
