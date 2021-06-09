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
class MenuPanel extends JPanel{
    public MainFrame mainFrame;
    public PanelManager panelManager;
    private String path;

    private boolean pause;

    public ArrayList<GraphicsButton> buttons;

    MenuPanel(MainFrame mainFrame, PanelManager panelManager, String path){
        this.mainFrame = mainFrame;
        this.panelManager = panelManager;
        this.path = path;

        this.pause = false;
        
        this.buttons = new ArrayList<GraphicsButton>();
    }
    public void animate(){
        while(!pause){
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

    public void togglePause(){
        pause = !pause;
    }

    public void paintComponent(Graphics g){
        for(int i = 0;i<buttons.size();i++){
            buttons.get(i).draw(g);
        }
    }
    public void addButton(GraphicsButton newButton){
        buttons.add(newButton);
    }
    public ArrayList<GraphicsButton> getButtons(){
        return buttons;
    }
}
