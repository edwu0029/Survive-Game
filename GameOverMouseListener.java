import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameOverMouseListener implements MouseListener{

    private PanelManager panelManager;

    GameOverMouseListener(PanelManager pm){
        this.panelManager = pm;

    }
    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        panelManager.reset();
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
}
