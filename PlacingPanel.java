import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Point;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import java.awt.MouseInfo;

class PlacingPanel extends JPanel{
    private MainFrame mainFrame;
    private PanelManager panelManager;
    private GamePanel gamePanel;
    private TileMap tileMap;

    private Tile hoveringTile;
    private int mouseX;
    private int mouseY;

    private boolean pause;

    private Defence selectedDefence;
    
    PlacingPanel(MainFrame mf, PanelManager pm, GamePanel gp, TileMap tm){
        this.mainFrame = mf;
        this.panelManager = pm;
        this.gamePanel = gp;
        this.tileMap = tm;

        this.pause = false;

        PlacingMouseListener mouseListener = new PlacingMouseListener(panelManager, this);
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
    public void setSelectedDefence(Defence newDefence){
        selectedDefence = newDefence;
    }
    public void placeDefence(){
        if(hoveringTile!=null){
            selectedDefence.setX(hoveringTile.getAbsoluteX());
            selectedDefence.setY(hoveringTile.getAbsoluteY());
            hoveringTile.setHasDefence(true);
            gamePanel.defences.add(selectedDefence);
            panelManager.setActivePanel("Game Panel");
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        tileMap.draw(g);
        for(int i = 0;i<gamePanel.defences.size();i++){
            gamePanel.defences.get(i).draw(g);
        }
        if(hoveringTile!=null){
            g.fillRect(hoveringTile.getRelativeX(), hoveringTile.getRelativeY(), 64, 64);
        }
    }
}
