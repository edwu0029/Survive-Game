import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Point;
import java.awt.Graphics;

import java.awt.MouseInfo;
/**
 * [PlacingPanel.java]
 * A class that represents a placing panel for this game.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class PlacingPanel extends JPanel{
    /*-----References to other objects-----*/
    private Level level;
    private PanelManager panelManager;
    private MainFrame mainFrame;
    private GamePanel gamePanel;
    private TileMap tileMap;

    /*-----Variables for this placing panel-----*/
    private Tile hoveringTile;
    private int mouseX;
    private int mouseY;

    private Defence selectedDefence; //The defence that is being placed; can be set using the setter method
    
    /**
     * PlacingPanel
     * A constructor that constructs a placing panel for a specified level.
     * @param level The level that this placing panel is created for.
     */
    PlacingPanel(Level level){
        this.level = level;
        this.panelManager = level.getPanelManager();
        panelManager.setPlacingPanel(this);

        this.mainFrame = level.getMainFrame();
        this.gamePanel = panelManager.getGamePanel();
        this.tileMap = level.getTileMap();

        PlacingMouseListener mouseListener = new PlacingMouseListener(level);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    /** 
     * animate
     * A method that uses java threads to run the game loop for this placing panel.
     */
    public void animate(){
        while(true){
            //delay
            
            //TEMPORARY
            Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, mainFrame.getComponent(0));
            mouseX = (int)p.getX();
            mouseY = (int)p.getY();
            // System.out.println(mouseX+" "+mouseY);
            hoveringTile = tileMap.pointInTile(mouseX, mouseY);
            
            try{
                Thread.sleep(40);
            }catch(Exception exc){
                System.out.println("Thread Error");
            }
            
            //Repaint request
            this.repaint();
          }   
    }
    /**
     * setSelectedDefence
     * sets the selected defence for this placing panel.
     * @param newDefence
     */
    public void setSelectedDefence(Defence newDefence){
        selectedDefence = newDefence;
    }
    /**
     * placeDefence
     * Places the selected defence in the game scene based on the location of the mouse at that moment.
     */
    public void placeDefence(){
        if(hoveringTile!=null){
            selectedDefence.setX(hoveringTile.getAbsoluteX());
            selectedDefence.setY(hoveringTile.getAbsoluteY());

            System.out.println(hoveringTile.getAbsoluteX());
            System.out.print(hoveringTile.getAbsoluteY());
            
            hoveringTile.setHasDefence(true);
            level.getDefences().add(selectedDefence);
            panelManager.setActivePanel("GamePanel");
        }
    }
    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics object in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        tileMap.draw(g);
        for(int i = 0;i<level.getDefences().size();i++){
            level.getDefences().get(i).draw(g);
        }
        if(hoveringTile!=null){
            g.fillRect(hoveringTile.getRelativeX(), hoveringTile.getRelativeY(), 64, 64);
        }
    }
}
