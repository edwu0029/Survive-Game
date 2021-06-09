import java.util.ArrayList;

import javax.swing.JPanel;

class PanelManager {
    private String path;
    private MainFrame mainFrame;
    private JPanel activePanel;

    ArrayList<JPanel>activePanels;
    
    PanelManager(MainFrame frame, String path){
        this.mainFrame = frame;
        this.path = path;
        this.activePanel = null;

        activePanels = new ArrayList<JPanel>();
    }

    public void addPanel(JPanel panel){
        activePanels.add(panel);
    }

    public void setActivePanel(String type){
        mainFrame.clear();
        for(int i = 0;i<activePanels.size();i++){
            if(activePanels.get(i) instanceof GamePanel){
                ((GamePanel)activePanels.get(i)).togglePause(true);
            }
        }
        for(int i = 0;i<activePanels.size();i++){
            if(type.equals("Game Panel") && activePanels.get(i) instanceof GamePanel){
                activePanel = activePanels.get(i);
                ((GamePanel)activePanels.get(i)).togglePause(false);
            }else if(type.equals("Inventory Panel") && activePanels.get(i) instanceof InventoryPanel){
                activePanel = activePanels.get(i);
            }else if(type.equals("Start Panel") && activePanels.get(i) instanceof StartPanel){
                activePanel = activePanels.get(i);
            }else if(type.equals("Crafting Panel") && activePanels.get(i) instanceof CraftingPanel){
                activePanel = activePanels.get(i);
            }else if(type.equals("Game Over Panel") && activePanels.get(i) instanceof GameOverPanel){
                activePanel = activePanels.get(i);
            }else if(type.equals("Placing Panel") && activePanels.get(i) instanceof PlacingPanel){
                activePanel = activePanels.get(i);
            }
        }
        
        mainFrame.setPanel(activePanel);
    }

    public void reset(){
        mainFrame.clear();
        activePanels.clear();
        activePanels.add(new StartPanel(mainFrame, this, path));
        setActivePanel("Start Panel");
    }
    
}
