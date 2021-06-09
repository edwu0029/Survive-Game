import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class PlacingMouseListener implements MouseListener{

    private PanelManager panelManager;
    private PlacingPanel placingPanel;

    PlacingMouseListener(PanelManager pm, PlacingPanel pp){
        this.panelManager = pm;
        this.placingPanel = pp;

    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!!");
        System.out.println("X:"+e.getX() + " y:"+e.getY());
        
        placingPanel.placeDefence();
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
