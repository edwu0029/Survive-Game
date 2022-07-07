import java.util.ArrayList;

import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;

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
    private PanelManager panelManager;
    private TileMap tileMap;
    private Player player;

    /*-----Variables for this game panel-----*/
    private String path;

    private boolean pause;
    private double zombieSpawnDelay;

    //References to lists stored in leve
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> items;
    private ArrayList<Defence> defences;

    //Game time(Time before night). Note these values are in seconds
    private long previousTime; //Time in mileseconds of previous update
    private long currentTime; //Time in mileseconds of current update
    private double timeDifference;
    private double dayTime;
    private double nightTime;


    /**
     * GamePanel
     * A constructor that constructs a game panel for a specified level.
     * @param level The level that this game panel is constructed for.
     */
    GamePanel(Level level){
        this.level = level;
        //Set this as the game panel for the level

        this.mainFrame = level.getMainFrame();
        this.panelManager = level.getPanelManager();
        this.path = level.getPath();

        panelManager.setGamePanel(this);

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
        panelManager.setCraftingPanel(new CraftingPanel(level));
        panelManager.setPlacingPanel(new PlacingPanel(level));
        panelManager.setInventoryPanel(new InventoryPanel(level));
        panelManager.setGameOverPanel(new GameOverPanel(level));
        panelManager.setWinPanel(new WinPanel(level));

        //Set up times
        this.previousTime = System.currentTimeMillis();
        
        this.dayTime = 60.0; //Day time lasts 1 minutes = 60 seconds
        this.nightTime = 60.0; //Night time lasts 1 minute = 60 seconds

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
                //Update projectiles
                for(int i = 0;i<projectiles.size();i++){
                    projectiles.get(i).update();
                }
                //Update enemies
                for(int i = 0;i<enemies.size();i++){
                    enemies.get(i).update();
                }
                //Update items
                for(int i = 0;i<items.size();i++){
                    items.get(i).update();
                }
                //Update defences
                for(int i = 0;i<defences.size();i++){
                    defences.get(i).update();
                }
                //Update player
                player.update();

                //Update times
                currentTime = System.currentTimeMillis(); 
                //Convert from miliseconds to seconds
                timeDifference = (double)(currentTime-previousTime)/1000.0;

                if(dayTime>=0.0){ //If it is day time
                    dayTime-=timeDifference;
                }else if(nightTime>=0.0){ //If it is night time
                    nightTime-=timeDifference;

                    //UpdateZombie spawn delay
                    zombieSpawnDelay+=timeDifference;
                }
                //Round values to 2 decimal place
                dayTime = Math.round(dayTime*100.0)/100.0;
                nightTime = Math.round(nightTime*100.0)/100.0;

                //Make update previousTime
                previousTime = currentTime;

                //Spawn zombie if delay reaches 5 seconds
                if(zombieSpawnDelay>=5.0){
                    spawnRandomZombie();
                    //Reset delay
                    zombieSpawnDelay = 0.0;
                }

                //Check for win
                if(nightTime<=0.0){
                    panelManager.setActivePanel("WinPanel");
                }

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
     * spawnRandomZombie
     * Spawns a zombie at a random location in the map. This method also makes sure that this zombie is spawned
     * at a valid position (Not on a non-passable wall or defence).
     */
    public void spawnRandomZombie(){
        boolean validSpawnPoint = false;
        //Do while until valid spawn point is generated
        do{
            //generate random number between 2 and 15 for row
            int randomRow = (int)Math.floor(Math.random()*(14)+2);
            //generate random number between 2 and 25 for column
            int randomColumn = (int)Math.floor(Math.random()*(24)+2);
            //Check valid spawn point
            if(tileMap.pointInTileAbsolute((randomColumn*64)+32, (randomRow*64)+32)!=null){
                //Create zombie with the randomly generated positon
                enemies.add(new Zombie(level, (randomColumn*64)+32, (randomRow*64)+32)); 
                validSpawnPoint = true;
            }
        }while(!validSpawnPoint);
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

        //Variables
        float tintAlpha; //Percent of tint based on how much time is left till night
        Graphics2D g2d = (Graphics2D)g; //Graphics2D version of g

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

        //Draw night tint
        tintAlpha = (float) ((60.0-dayTime)/60.0)*0.4f;
        //Set transparency
        g2d.setComposite(AlphaComposite.SrcOver.derive(tintAlpha));
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 1080, 1080);
        //Reset transparency
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));


        //Draw timer in the top corner
        //Draw rectangle
        g.setColor(new Color(150, 75, 0));
        g.fillRect(0, 0, 203, 103);
        g.setColor(new Color(159, 161, 163));
        g.fillRect(0, 0, 200, 100);

        g.setColor(new Color(0, 0, 0));
        if(dayTime>=0.0){
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(new Color(0, 0, 0));
            g.drawString("Time before night", 25, 40); 

            //Display time left
            g.drawString(Double.toString(dayTime), 70, 70);
        }else{
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(new Color(0, 0, 0));
            g.drawString("Survive!", 60, 40);
            //Display time left
            g.drawString(Double.toString(nightTime), 70, 70);
        }
    }
}
