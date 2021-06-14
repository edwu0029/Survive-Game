import java.util.ArrayList;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [StartMouseListener.java]
 * A class that represents a mouse listener for the start panel.
 */
class StartMouseListener implements MouseListener{

    private StartPanel startPanel;
    private MenuPanel menuPanel;

    StartMouseListener (PanelManager panelManager, MenuPanel menuPanel){
        this.panelManager = panelManager;
        this.menuPanel = menuPanel;
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!!");
        System.out.println("X:"+e.getX() + " y:"+e.getY());
    }

    public void mousePressed(MouseEvent e) {
        ArrayList<GraphicsButton> buttons = menuPanel.getButtons();
        for(int i = 0;i<buttons.size();i++){
            GraphicsButton currentButton = buttons.get(i);
            if(currentButton.isPressed(e.getX(), e.getY())){
                currentButton.pressed();
                break;
            }
        }
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
}
