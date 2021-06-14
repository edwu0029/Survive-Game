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
    private CraftingPanel craftingPanel;

    /**
     * CraftingPanel
     * A constructor that constructs a crafting key listener for a certain level.
     * @param level The level that this crafting key listener is created for.
     */
    CraftingKeyListener(Level level){
        this.level = level;
        this.craftingPanel = level.getCraftingPanel();
    }
    
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){ //Return to GamePanel
            level.setActivePanel("GamePanel");
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            craftingPanel.craftRecipe();
        }
    }
    public void keyTyped(KeyEvent e){
        
    }
    public void keyReleased(KeyEvent e){
        
    }
}
