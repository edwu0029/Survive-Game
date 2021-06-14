import java.util.ArrayList;

import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * [GamePanel.java]
 * A class that represents the main game panel in which the actual game is to be run with.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class GamePanel extends JPanel{
    /*-----References to other objects-----*/
    private Level level;
    private MainFrame mainFrame;
    private TileMap tileMap;
    private Player player;

    /*-----Variables for this game panel-----*/
    private String path;

    private boolean pause;

    //References to lists stored in leve
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> items;
    private ArrayList<Defence> defences;

    /**
     * GamePanel
     * A constructor that constructs a game panel for a specified level.
     * @param level The level that this game panel is constructed for.
     */
    GamePanel(Level level){
        this.level = level;
        //Set this as the game panel for the level
        level.setGamePanel(this);

        this.mainFrame = level.getMainFrame();
        this.path = level.getPath();

        this.tileMap = new TileMap(level);
        //Set the tile map as the tile map for the level
        level.setTileMap(tileMap);

        this.player = new Player(level);
        //Set the player as the player for the level
        level.setPlayer(player);

        //Get lists from level
        this.items = level.getItems();
        this.defences = level.getDefences();
        this.projectiles = level.getProjectiles();
        this.enemies = level.getEnemies();

        String[] fenceIngredients = {"Wood", "Wood"};
        Defence fenceHorizontal = new HorizontalFence(level, 0, 0);
        Recipe fenceHorizontalRecipe = new Recipe(level, fenceIngredients, fenceHorizontal);
        level.getRecipes().add(fenceHorizontalRecipe);

        String[] fenceIngredients2 = {"Wood", "Metal"};
        Defence fenceVertical = new VerticalFence(level, 0, 0);
        Recipe fenceVerticalRecipe = new Recipe(level, fenceIngredients2, fenceVertical);
        level.getRecipes().add(fenceVerticalRecipe);

        //Create and set other panels for the level
        level.setCraftingPanel(new CraftingPanel(level));
        level.setPlacingPanel(new PlacingPanel(level));
        level.setInventoryPanel(new InventoryPanel(level));
        level.setGameOverPanel(new GameOverPanel(level));

        //Add Key Listener
        GameKeyListener keyListener = new GameKeyListener(level);
        this.addKeyListener(keyListener);

        //Add Mouse Listener
        GameMouseListener mouseListener = new GameMouseListener(level);
        this.addMouseListener(mouseListener);

        //By default, the game panel is NOT paused
        this.pause = false;
        
        //Make visible on screen
        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    /**
     * animate
     * A method that uses java threads to run the game loop for this game panel.
     */
    public void animate(){
        while(true){
            if(!pause){
                for(int i = 0;i<projectiles.size();i++){
                    projectiles.get(i).update();
                }
                for(int i = 0;i<enemies.size();i++){
                    enemies.get(i).update();
                }
    
                for(int i = 0;i<items.size();i++){
                    items.get(i).update();
                }
    
                for(int i = 0;i<defences.size();i++){
                    defences.get(i).update();
                }
                player.update();
                //delay
                try{
                    Thread.sleep(10);
                }catch(Exception exc){
                  System.out.println("Thread Error");
                }
            }
            
            //Repaint request
            this.repaint();
          }   
    }
    /**
     * togglePause
     * A method that toggles the pause of the game panel.
     * @param value The new pause value of this panel.
     */
    public void togglePause(boolean value){
        pause = value;
    }
    /**
     * destroyProjectile
     * Destroys a specific projectile from the game scene.
     * @param id The ID of the projectile that is to be destroyed.
     */
    public void destroyProjectile(int id){
        for(int i = 0;i<projectiles.size();i++){
            if(projectiles.get(i).getID()==id){
                projectiles.remove(i);
            }
        }
    }
    /**
     * destroyEnemy
     * Destroys a specific enemy from the game scene.
     * @param id The ID of the enemy that is to be destroyed.
     */
    public void destroyEnemy(int id){
        for(int i = 0;i<enemies.size();i++){
            if(enemies.get(i).getID()==id){
                enemies.remove(i);
            }
        }
    }
    /**
     * destroyItem
     * Destroys a specific item from the game scene.
     * @param id The ID of the item that is to be destroyed.
     */
    public void destroyItem(int id){
        for(int i = 0;i<items.size();i++){
            if(items.get(i).getID()==id){
                items.remove(i);
            }
        }
    }
    /**
     * destroyDefence
     * Destroys a specific defence from the level and game scene.
     * @param id The ID of the defence that is to be destroyed.
     */
    public void destroyDefence(int id){
        for(int i = 0;i<defences.size();i++){
            if(defences.get(i).getID()==id){
                defences.remove(i);
            }
        }
    }
    /**
     * checkItemCollision
     * Checks if a specified box has collided with any of the items in the game scene and returns the ID
     * of that item.
     * @param box The box that is to be checked
     * @return The ID of the item if the box collides with an item, -1 otherwise
     */
    public int checkItemCollision(Rectangle box){
        for(int i = 0;i<items.size();i++){ //Loop through each item
            Item currentItem = items.get(i);
            if(currentItem.checkCollision(box)){ //Check collision
                return currentItem.getID();
            }
        }
        return -1;
    }
    /**
     * checkDefenceCollision
     * Checks if a specified box has collided with any of the defences in the game scene and returns the ID
     * of that defence.
     * @param box The box that is to be checked
     * @return The ID of the defence if the box collides with an defence, -1 otherwise
     */
    public int checkDefenceCollision(Rectangle box){
        for(int i = 0;i<defences.size();i++){ //Loop through all defences to find the correct ID
            //Get reference to current defence in loop
            Defence currentDefense = defences.get(i);
            if(currentDefense.checkCollision(box)){ //Check collision
                return currentDefense.getID();
            }
        }
        return -1;
    }
    /**
     * pickUpItem
     * Picks up an item from the game scene and moves it from the game scene to the player's inventory.
     * @param id The ID of the item that is to be picked up.
     */
    public void pickUpItem(int id){
        for(int i = 0;i<items.size();i++){ //Loop through all items to find the correct ID
            //Get reference to current item in loop
            Item currentItem = items.get(i);
            if(currentItem.getID()==id){ //Check ID
                items.remove(i);
                player.getPlayerItems().add(currentItem);
            }
        }
    }
    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics object in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //First draw a black background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, mainFrame.getWindowWidth(), mainFrame.getWindowHeight());

        //Draw tile map
        tileMap.draw(g);

        //Draw projectiles
        for(int i = 0;i<projectiles.size();i++){
            projectiles.get(i).draw(g);
        } 

        //Draw items
        for(int i = 0;i<items.size();i++){
            items.get(i).draw(g);
        }

        //Draw player
        player.draw(g);

        //Draw enemies
        for(int i = 0;i<enemies.size();i++){
            enemies.get(i).draw(g);
        }

        //Draw defences
        for(int i = 0;i<defences.size();i++){
            defences.get(i).draw(g);
        }
    }
}
