import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerKeyListener implements KeyListener{
    private Player player;
    private PanelManager panelManager;

    private boolean activeW;
    private boolean activeA;
    private boolean activeS;
    private boolean activeD;

    PlayerKeyListener(PanelManager panelManager, Player p){
        this.player = p;
        this.panelManager = panelManager;

        activeW = false;
        activeA = false;
        activeS = false;
        activeD = false;

        //Using a timer for faster reponse input
        new Timer(20, new ActionListener(){
            public void actionPerformed(ActionEvent arg){
                if(activeW){
                    player.moveUp();
                }else if(activeA){
                    player.moveLeft();
                }else if(activeS){
                    player.moveDown();
                }else if(activeD){
                    player.moveRight();
                }
            }
        }).start(); 
    }

    public void keyPressed(KeyEvent e){
        
        // System.out.println("key pressed");
        if(e.getKeyCode()==KeyEvent.VK_W){
            activeW = true;
            //Only allow one direction of movement
            activeA = activeS = activeD = false;
            player.moveUp();
        }else if(e.getKeyCode()==KeyEvent.VK_A){
            activeA = true;
            //Only allow one direction of movement
            activeW = activeS = activeD = false;
            player.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            activeS = true;
            //Only allow one direction of movement
            activeW = activeA = activeD = false;
            player.moveDown();
        }else if(e.getKeyCode()==KeyEvent.VK_D){
            activeD = true;
            //Only allow one direction of movement
            activeW = activeA = activeS = false;
            player.moveRight();
        }else if(e.getKeyCode()==KeyEvent.VK_F){
            player.pickUpItem();
        }else if(e.getKeyCode()==KeyEvent.VK_I){
            panelManager.setActivePanel("Inventory Panel");
            System.out.println("Key i pressed Pressed");
            activeW = activeA = activeS = activeD = false;
        }else if(e.getKeyCode()==KeyEvent.VK_C){
            panelManager.setActivePanel("Crafting Panel");
            System.out.println("Key c pressed Pressed");
            activeW = activeA = activeS = activeD = false;
        }else if(e.getKeyCode()==KeyEvent.VK_1){
            player.setMode(1);
        }else if(e.getKeyCode()==KeyEvent.VK_2){
            player.setMode(2);
        }else if(e.getKeyCode()==KeyEvent.VK_3){
            player.setMode(3);
        }
    }
    public void keyTyped(KeyEvent e){
        
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_W){
            activeW = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            activeA = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            activeS = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            activeD = false;
        }
    }
}
