import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

class InventoryPanel extends JPanel{
    private PanelManager panelManager;
    private PlacingPanel placingPanel;
    private Player player;

    private ArrayList<Item> playerItems;
    private ArrayList<Defence> playerDefences;
    private int rows;
    
    private int selectedColumn;
    private int selectedRow;

    InventoryPanel(PanelManager panelManager, PlacingPanel pp, Player player){
        this.panelManager = panelManager;
        this.placingPanel = pp;
        this.player = player;

        InventoryKeyListener keyListener = new InventoryKeyListener(panelManager, this, player);
        this.addKeyListener(keyListener);

        this.playerItems = player.inventory;
        this.playerDefences = player.defences;

        this.rows = 10;
        this.selectedColumn = 0;
        this.selectedRow = 0;

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
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
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(150, 75, 0));
        g.fillRect(0, 0, 1080, 1080);

        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Inventory", 450, 50);
        
        //Left columns
        for(int i = 0;i<rows;i++){
            g.drawRect(50, 64*i+100, 400, 64);
        }
        //Right columns
        for(int i = 0;i<rows;i++){
            g.drawRect(500, 64*i+100, 400, 64);
        }

        //Display items in player's inventory
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        for(int i = 0;i<playerItems.size();i++){
            Item currentItem = playerItems.get(i);
            g.drawString(currentItem.getName(), 50, 64*i+164);
        }

        //Display defences in player's inventory
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        for(int i = 0;i<playerDefences.size();i++){
            Defence currentDefence = playerDefences.get(i);
            g.drawString(currentDefence.getName(), 500, 64*i+164);
        }

        //Draw a green rectangle for the selectedd item/defence
        g.setColor(new Color(0, 255, 0));
        if(selectedColumn==0){ //Items
            g.drawRect(50, 64*selectedRow+100, 400, 64);
        }else if(selectedColumn==1){ //Defence
            g.drawRect(500, 64*selectedRow+100, 400, 64);
        }

    }
    public void moveSelectionUp(){
        selectedRow = Math.max(0, selectedRow-1);
    }
    public void moveSelectionDown(){
        selectedRow = Math.min(rows-1, selectedRow+1);
    }
    public void toggleColumnSelection(int value){
        selectedColumn = value;
    }
    
    public void consumeSelected(){
        if(selectedColumn==0 && selectedRow<playerItems.size()){
            playerItems.get(selectedRow).consume();
            playerItems.remove(selectedRow);
        }else if(selectedColumn==1 && selectedRow<playerDefences.size()){
            // playerDefences.get(selectedRow).place();
            placingPanel.setSelectedDefence(playerDefences.get(selectedRow));
            playerDefences.remove(selectedRow);
            panelManager.setActivePanel("Placing Panel");
        }
    }
}
