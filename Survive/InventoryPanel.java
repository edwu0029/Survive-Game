import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import java.awt.BorderLayout;

/**
 * [InventoryPanel.java]
 * A class that represents the inventory panel of this game. Shows information about the player's inventory.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class InventoryPanel extends JPanel{
    /*-----References to other objects-----*/
    private Level level;
    private PlacingPanel placingPanel;
    private Player player;
    private ArrayList<Item> playerItems;
    private ArrayList<Defence> playerDefences;

    /*-----Varaibles for this inventory panel-----*/
    private int rows;
    private int selectedColumn;
    private int selectedRow;

    /**
     * InventoryPanel
     * A constructor that constructs a inventory panel for a specifed level.
     * @param level The level that this inventory panel is created for.
     */
    InventoryPanel(Level level){
        this.level = level;
        level.setInventoryPanel(this);

        this.placingPanel = level.getPlacingPanel();
        this.player = level.getPlayer();

        this.setLayout(new BorderLayout());

        InventoryKeyListener keyListener = new InventoryKeyListener(level);
        this.addKeyListener(keyListener);

        this.playerItems = player.getPlayerItems();
        this.playerDefences = player.getPlayerDefences();

        this.rows = 10;
        this.selectedColumn = 0;
        this.selectedRow = 0;

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    /**
     * animate
     * A method that uses java threads to run the game loop for this inventory panel.
     */
    public void animate(){
        while(true){
            //delay
            try{
                Thread.sleep(10);
            }catch(Exception exc){
              System.out.println("Thread Error");
            }
            
            //Repaint request
            this.repaint();
          }
    }
    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics object in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw backgrounds
        g.setColor(new Color(130, 82, 0));
        g.fillRect(0, 0, 1080, 1080);
        g.setColor(new Color(89, 56, 0));
        g.fillRect(10, 10, 1060-15, 1060-15);
        g.setColor(new Color(130, 82, 0));
        g.fillRect(40, 40, 980, 1080);

        //Reset color back to black
        g.setColor(new Color(0, 0, 0));

        //Draw Heading
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Inventory", 450, 110);
        
        //Draw Left columns
        for(int i = 0;i<rows;i++){
            g.drawRect(80, 64*i+200, 430, 64);
        }
        //Draw Right columns
        for(int i = 0;i<rows;i++){
            g.drawRect(550, 64*i+200, 430, 64);
        }

        //Display items in player's inventory
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        for(int i = 0;i<playerItems.size();i++){
            Item currentItem = playerItems.get(i);
            g.drawString(currentItem.getType(), 80, 64*i+264);
        }

        //Display defences in player's inventory
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        for(int i = 0;i<playerDefences.size();i++){
            Defence currentDefence = playerDefences.get(i);
            g.drawString(currentDefence.getType(), 550, 64*i+264);
        }

        //Draw a green rectangle for the selectedd item/defence
        g.setColor(new Color(0, 255, 0));
        if(selectedColumn==0){ //Items
            g.drawRect(80, 64*selectedRow+200, 430, 64);
        }else if(selectedColumn==1){ //Defence
            g.drawRect(550, 64*selectedRow+200, 430, 64);
        }

    }
    /**
     * moveSelectionUp
     * Moves the selection rectangle up, selecting to item/defence above.
     */
    public void moveSelectionUp(){
        //Remember you can't go past 0
        selectedRow = Math.max(0, selectedRow-1);
    }
    /**
     * moveSelectionDown
     * Moves the selection rectangle down, selecting to item/defence down.
     */
    public void moveSelectionDown(){
        //Remember you can't go past the total number or rows
        selectedRow = Math.min(rows-1, selectedRow+1);
    }
    /**
     * toggleColumnSelection
     * Moves the selection rectangle to the other column. Essentially toggling from items to defences
     * and defences to items.
     * @param value The new column that is selected. Note that 0=item column and 1=defence column
     */
    public void toggleColumnSelection(int value){
        selectedColumn = value;
    }
    /**
     * consumeSelected
     * Consumes the selected item or defence that is currently selected.
     */
    public void consumeSelected(){
        if(selectedColumn==0 && selectedRow<playerItems.size()){
            if(playerItems.get(selectedRow).consume()){ //If returns true, the item is consumbale
                playerItems.remove(selectedRow);
            }
        }else if(selectedColumn==1 && selectedRow<playerDefences.size()){
            // playerDefences.get(selectedRow).place();
            placingPanel.setSelectedDefence(playerDefences.get(selectedRow));
            playerDefences.remove(selectedRow);
            level.setActivePanel("PlacingPanel");
        }
    }
}
