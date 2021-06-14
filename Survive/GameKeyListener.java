import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * [GameKeyListener.java]
 * A class that represents a key listener for the game panel.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class GameKeyListener implements KeyListener{
    /*-----References to other objects-----*/
    private Level level;
    private Player player;

    /*-----Variables for this game key listenern-----*/
    //Boolean value for which movement key is being pressed
    private boolean activeW;
    private boolean activeA;
    private boolean activeS;
    private boolean activeD;

    /**
     * GameKeyListener
     * A constructor that constructs a game key listener for a specified level.
     * @param level The level that this game key listener is created for.
     */
    GameKeyListener(Level level){
        this.level = level;
        this.player = level.getPlayer();

        //By deafault, all movememnt is false
        activeW = false;
        activeA = false;
        activeS = false;
        activeD = false;

        //Using a timer for faster reponse input, this fixes the delay caused by using KeyListener methods
        //Updates faster than the KeyListener
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

    /**
     * keyPressed
     * An overwridden method from the KeyListener method that executes code based on the key pressed.
     */
    public void keyPressed(KeyEvent e){
        boolean[] playerPossibleModes = player.getPossibleModes();
        if(e.getKeyCode()==KeyEvent.VK_W){ //W is pressed
            activeW = true;
            //Only allow one direction of movement
            activeA = activeS = activeD = false;
            player.moveUp();
        }else if(e.getKeyCode()==KeyEvent.VK_A){ //A is pressed
            activeA = true;
            //Only allow one direction of movement
            activeW = activeS = activeD = false;
            player.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_S){ //S is pressed
            activeS = true;
            //Only allow one direction of movement
            activeW = activeA = activeD = false;
            player.moveDown();
        }else if(e.getKeyCode()==KeyEvent.VK_D){ //D is pressed
            activeD = true;
            //Only allow one direction of movement
            activeW = activeA = activeS = false;
            player.moveRight();
        }else if(e.getKeyCode()==KeyEvent.VK_F){ //F is pressed
            //Pick up item
            player.pickUpItem();
        }else if(e.getKeyCode()==KeyEvent.VK_I){ //I is pressed
            //Switch to inventory panel
            level.setActivePanel("InventoryPanel");
            //Stop all movement
            activeW = activeA = activeS = activeD = false;
        }else if(e.getKeyCode()==KeyEvent.VK_C){ //C is pressed
            //Switch to crafting panel
            level.setActivePanel("CraftingPanel");
            //Stop all movement
            activeW = activeA = activeS = activeD = false;
        }else if(e.getKeyCode()==KeyEvent.VK_1){ //1 is pressed
            //Switch player to mode 1
            player.setMode(1);
        }else if(e.getKeyCode()==KeyEvent.VK_2){
            if(playerPossibleModes[2]){ //Check if mode 2 is possible
                //Switch player to mode 2
                player.setMode(2);
            }
        }else if(e.getKeyCode()==KeyEvent.VK_3){
            player.setMode(3);
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
        if(e.getKeyCode()==KeyEvent.VK_W){ //W is pressed
            activeW = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_A){ //A is pressed
            activeA = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_S){ //S is pressed
            activeS = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){ //D is pressed
            activeD = false;
        }
    }
}
