import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class CraftingKeyListener implements KeyListener{
    private PanelManager panelManager;
    private CraftingPanel craftingPanel;
    private Player player;

    CraftingKeyListener(PanelManager panelManager, CraftingPanel cp, Player p){
        this.panelManager = panelManager;
        this.craftingPanel = cp;
        this.player = p;
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_C){
            panelManager.setActivePanel("Game Panel");
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            craftingPanel.craftRecipe();
        }
    }
    public void keyTyped(KeyEvent e){
        
    }
    public void keyReleased(KeyEvent e){
        
    }
}
