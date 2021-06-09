import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

class CraftingPanel extends JPanel{
    private PanelManager panelManager;
    private Player player;
    
    private int currentIndex;
    static ArrayList<Recipe> recipes;
    private String[] currentIngredients;

    CraftingPanel(PanelManager panelManager, Player player, ArrayList<Recipe> r){
        this.panelManager = panelManager;
        this.player = player;
        recipes = r;

        this.currentIndex = 0;
        currentIngredients = recipes.get(currentIndex).getIngredients();

        CraftingMouseListener mouseListener = new CraftingMouseListener(panelManager, this);
        this.addMouseListener(mouseListener);
        CraftingKeyListener keyListener = new CraftingKeyListener(panelManager, this, player);
        this.addKeyListener(keyListener);

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
        
        //Left columns
        for(int i = 0;i<10;i++){
            g.drawRect(50, 64*i+100, 400, 64);
        }
        //Right columns
        for(int i = 0;i<10;i++){
            g.drawRect(500, 64*i+100, 400, 64);
        }

        //Display ingredients
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        for(int i = 0;i<currentIngredients.length;i++){
            g.drawString(currentIngredients[i], 50, 64*i+164);
        }
        //Display products
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        if(recipes.get(currentIndex).getItemProduct()!=null){
            g.drawString(recipes.get(currentIndex).getItemProduct().getName(), 500, 164);
        }
        if(recipes.get(currentIndex).getDefenceProduct()!=null){
            g.drawString(recipes.get(currentIndex).getDefenceProduct().getName(), 500, 164);
        }

    }
    public void nextRecipe(){
        currentIndex++;
        if(currentIndex>=recipes.size()){
            currentIndex = 0;
        }
        currentIngredients = recipes.get(currentIndex).getIngredients();
    }
    public void craftRecipe(){
        if(recipes.get(currentIndex).checkCraftable()){
            System.out.println("Crafted");
            recipes.get(currentIndex).craft();
        }
    }
}
