import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class StartPanel extends MenuPanel{
    private MainFrame mainFrame;
    private PanelManager panelManager;
    private String path;

    StartPanel(MainFrame mainFrame, PanelManager panelManager, String path){
        super(mainFrame, panelManager, path);
        
        //Start Button
        GraphicsButton startButton = new GraphicsButton(panelManager, 290, 50, 500, 100, "Game Panel");
        startButton.setColor(new Color(0,128,0));
        startButton.setText("Start");
        startButton.setTextColor(new Color(255, 255, 255));
        startButton.setFont("Arial", 50);
        this.buttons.add(startButton);
        
        panelManager.addPanel(new GamePanel(mainFrame, panelManager, path));

        StartMouseListener mouseListener = new StartMouseListener(panelManager, this);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 
    }
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
    public void paintComponent(Graphics g){
        for(int i = 0;i<buttons.size();i++){
            buttons.get(i).draw(g);
        }
    }

}
