import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class CraftingMouseListener implements MouseListener{
    private PanelManager panelManager;
    private CraftingPanel craftingPanel;

    CraftingMouseListener(PanelManager pm, CraftingPanel cp){
        this.panelManager = pm;
        this.craftingPanel = cp;
    }
    public void mouseClicked(MouseEvent e) {
        craftingPanel.nextRecipe();
    }

    public void mousePressed(MouseEvent e) {
        
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
}
