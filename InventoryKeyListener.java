import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class InventoryKeyListener implements KeyListener{
    private PanelManager panelManager;
    private InventoryPanel inventoryPanel;
    private Player player;

    InventoryKeyListener(PanelManager panelManager, InventoryPanel ip, Player p){
        this.panelManager = panelManager;
        this.inventoryPanel = ip;
        this.player = p;
    }

    public void keyPressed(KeyEvent e){
        // System.out.println("key pressed");
        if(e.getKeyCode()==KeyEvent.VK_I){
            panelManager.setActivePanel("Game Panel");
        }else if(e.getKeyCode()==KeyEvent.VK_UP){
            inventoryPanel.moveSelectionUp();
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            inventoryPanel.moveSelectionDown();
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            inventoryPanel.toggleColumnSelection(0);
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            inventoryPanel.toggleColumnSelection(1);
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            inventoryPanel.consumeSelected();
        }
    }
    public void keyTyped(KeyEvent e){
        
    }
    public void keyReleased(KeyEvent e){
        
    }
}
