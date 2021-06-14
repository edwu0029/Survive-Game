import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * [CraftingPanel.java]
 * A class that represents the CraftingPanel JPanel of this game.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class CraftingPanel extends JPanel{
    /*-----References to other objects-----*/
    private Level level;
    private Player player;
    
    /*-----Variables for this CraftinPanel------*/
    private int currentIndex;
    static ArrayList<Recipe> recipes;
    private String[] currentIngredients;

    //Images/Sprites
    private BufferedImage background;
    private BufferedImage arrow;

    /**
     * CraftingPanel
     * A constructor that creates a CraftingPanel for a specified level.
     * @param level The level that this CraftinPanel is created for.
     */
    CraftingPanel(Level level){
        this.level = level;
        //Set this as the crafting panel for the level
        level.setCraftingPanel(this);

        this.player = level.getPlayer();
        this.recipes = level.getRecipes();

        //Load images
        loadImages();

        //Get first recipe as the default recipe
        this.currentIndex = 0;
        currentIngredients = recipes.get(currentIndex).getIngredients();

        CraftingMouseListener mouseListener = new CraftingMouseListener(level);
        this.addMouseListener(mouseListener);
        CraftingKeyListener keyListener = new CraftingKeyListener(level);
        this.addKeyListener(keyListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    /**
     * animate
     * A method that uses java threads to run the game loop for this panel.
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
     * loadImages
     * A method that loads the necessary images to draw this crafting panel.
     */
    public void loadImages(){
        try{
            File backgroundFile = new File(level.getPath()+"\\Sprites\\Panels\\craftingpanel_background.jpg");
            File arrowFile = new File(level.getPath()+"\\Sprites\\Panels\\arrow.png");

            background = ImageIO.read(backgroundFile);
            arrow = ImageIO.read(arrowFile);
        }catch(Exception e){
            System.out.println("Error loading images for crafting panel.");
        }
    }
    /**
     * nextRecipe
     * A method that changes the active(displayed) recipe to the next recipe in the arraylist.
     */
    public void nextRecipe(){
        currentIndex++;
        if(currentIndex>=recipes.size()){
            currentIndex = 0;
        }
        currentIngredients = recipes.get(currentIndex).getIngredients();
    }
    /**
     * craftRecipe
     * A method that intiates the crafting of the active recipe.
     */
    public void craftRecipe(){
        if(recipes.get(currentIndex).checkCraftable()){
            //DEBUG
            System.out.println("Crafted");
            recipes.get(currentIndex).craft();
        }
    }
    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics objec in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //Draw backgrounds
        g.drawImage(background, 0, 0, null);
        g.setColor(new Color(130, 82, 0));
        g.fillRect(50, 50, 950, 950);

        //Display Heading
        g.setFont(new Font("Lucida", Font.PLAIN, 50));
        g.setColor(new Color(0, 0, 0));
        g.drawString("Crafting", 450, 100);

        //Display ingredients
        g.setFont(new Font("Arial", Font.PLAIN, 35));
        for(int i = 0;i<currentIngredients.length;i++){
            g.drawRect(100, 64*i+200, 300, 64);
            g.drawString(currentIngredients[i], 100, 64*i+264);
        }

        //Draw arrow
        g.drawImage(arrow, 490, 200, null);

        //Display products
        g.setFont(new Font("Arial", Font.PLAIN, 35));
        if(recipes.get(currentIndex).getItemProduct()!=null){
            g.drawRect(650, 264, 300, 64);
            g.drawString(recipes.get(currentIndex).getItemProduct().getType(), 650, 328);
        }else if(recipes.get(currentIndex).getDefenceProduct()!=null){
            g.drawRect(650, 264, 300, 64);
            g.drawString(recipes.get(currentIndex).getDefenceProduct().getType(), 650, 328);
        }

    }
}
