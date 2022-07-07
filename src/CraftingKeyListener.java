import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * [CraftingKeyListener.java]
 * A class that represents a key listener for the crafting panel.
 * @author Edward Wu
 * @version 1,0, June 15, 2021
 */
class CraftingKeyListener implements KeyListener{
    /*-----Reference to other objects-----*/
    private Level level;
    private PanelManager panelManager;
    private CraftingPanel craftingPanel;

    /**
     * CraftingPanel
     * A constructor that constructs a crafting key listener for a certain level.
     * @param level The level that this crafting key listener is created for.
     */
    CraftingKeyListener(Level level){
        this.level = level;
        this.panelManager = level.getPanelManager();
        this.craftingPanel = panelManager.getCraftingPanel();
    }
    /**
     * keyPressed
     * An overwridden method from the KeyListener method that executes code based on the key pressed.
     */
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_LEFT){ //Return to GamePanel
            craftingPanel.previousRecipe();
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            craftingPanel.nextRecipe();
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
