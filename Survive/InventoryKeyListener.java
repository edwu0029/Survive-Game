import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * [InventoryKeyListener.java]
 * A class that represents a key listener for the inventory panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class InventoryKeyListener implements KeyListener{
    /*-----Reference to other objects-----*/
    private Level level;
    private InventoryPanel inventoryPanel;
    private Player player;

    /**
     * InventoryKeyListenener
     * A constructot that constructs a inventory key listener for a specifed level.
     * @param level The level that this inventory key listener is created for.
     */
    InventoryKeyListener(Level level){
        this.level = level;
        this.inventoryPanel = level.getInventoryPanel();
        this.player = level.getPlayer();
    }

    /**
     * keyPressed
     * An overwridden method from the KeyListener method that executes code based on the key pressed.
     */
    public void keyPressed(KeyEvent e){
        // System.out.println("key pressed");
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){ //return to GamePanel
            level.setActivePanel("GamePanel");
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
    /**
     * keyTyped
     * An overwridden method from the KeyListener method that executes code based on the key typed. In this
     * case, typed means pressed and then released.
     */
    public void keyTyped(KeyEvent e){
        
    }
    /**
     * keyReleased
     * An overwridden method from the KeyListener method that executes code based on the key released.
     */
    public void keyReleased(KeyEvent e){
    }
}
