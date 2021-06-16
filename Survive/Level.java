import java.io.File;
import java.util.ArrayList;

/**
 * [Level.java]
 * A class that represents a game level. This includes references to all panels and
 * game objects in the level. This class also acts as a panel manager to switch between panels.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Level{

    //Frame Variables
    private MainFrame mainFrame;
    
    //Panel Manager
    private PanelManager panelManager;

    //Objects in game
    private Player player;
    private TileMap tileMap;

    private ArrayList<Item>items;
    private ArrayList<Defence> defences;
    private ArrayList<Enemy> enemies;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Recipe> recipes;

    //Unique ID counters
    private int projectileIDCount;
    private int enemyIDCount;
    private int itemIDCount;
    private int defenceIDCount;
    
    //Miscanaleous variables
    private String path;

    /**
     * Level
     * A constructor that creates a level.
     * @param mainFrame The main frame(JFrame) for this game.
     */
    Level(MainFrame mainFrame, PanelManager panelManager){
        //Load data
        loadPath();

        this.mainFrame = mainFrame;
        this.panelManager = panelManager;
        
        //In the beginning, all ID counters start at 0
        //These IDs help identify objects of the same type from each other
        this.projectileIDCount = 0;
        this.enemyIDCount = 0;
        this.itemIDCount = 0;
        this.defenceIDCount = 0;

        enemies = new ArrayList<Enemy>();
        items = new ArrayList<Item>();
        defences = new ArrayList<Defence>();
        recipes = new ArrayList<Recipe>();
        projectiles = new ArrayList<Projectile>();

        //Create Game Panel
        new GamePanel(this);

        //Create computer
        defences.add(new Computer(this, 1088, 960));

        enemies.add(new Tree(this, 200, 300));
        enemies.add(new Tree(this, 400, 400));
        enemies.add(new Tree(this, 850, 500));
        enemies.add(new Tree(this, 1000, 600));

        //Create items that are on the ground
        items.add(new BlueLaserGun(this, 500, 600));
        items.add(new Wood(this, 300, 1000));
        items.add(new Wood(this, 1000, 400));
        items.add(new Wood(this, 1200, 950));
        items.add(new Wood(this, 400, 1000));
        items.add(new Rock(this, 640, 320));
        items.add(new Metal(this, 504, 320));
        items.add(new Metal(this, 600, 420));
        items.add(new Metal(this, 400, 1000));
        items.add(new Metal(this, 504, 670));

        //Set game panel as active
        panelManager.setActivePanel("GamePanel");
    }
    /**
     * loadPath
     * Finds the value of the path of this game directory and stores it in the private String path.
     */
    public void loadPath(){
        try{
            String absolutePath;
            File file;

            file = new File("Level.java");
            //Find absolute path to THIS file(Level.java)
            absolutePath = file.getAbsolutePath();
            //Cut out Level.java to get directory path
            path = absolutePath.substring(0, absolutePath.length()-"Level.java".length());
        }catch(Exception e){
            System.out.println("Directory path not found");
        }
    }
    /**
     * getPath
     * Returns the path the game directory relative to the user's root directory.
     * @return The path of the game directory relative to the user's root directory.
     */
    public String getPath(){
        return path;
    }
    /**
     * getMainFrame
     * Returns the reference to this level's MainFrame(JFrame).
     * @return A reference to this level's MainFrame(JFrame).
     */
    public MainFrame getMainFrame(){
        return mainFrame;
    }
    /**
     * getPanelManager
     * Returns the reference to this level's panel manager.
     * @return The reference to this level's panel manager.
     */
    public PanelManager getPanelManager(){
        return panelManager;
    }
    /**
     * setPlayer
     * Sets the reference of this level's Player object.
     * @param newPlayer The new Player object.
     */
    public void setPlayer(Player newPlayer){
        player = newPlayer;
    }
    /**
     * getPlayer
     * Returns the reference to this level's Player object.
     * @return A reference to this level's Player object.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * setTileMap
     * Sets the reference of this level's tileMap object.
     * @param newTileMap The new tileMap object.
     */
    public void setTileMap(TileMap newTileMap){
        tileMap = newTileMap;
    }
    /**
     * getTileMap
     * Returns the reference to this level's TileMap object.
     * @return A reference to this level's TileMap object.
     */
    public TileMap getTileMap(){
        return tileMap;
    }
    /**
     * setRecipes
     * Sets the reference of this level's recipes.
     * @return An arraylist of the new recipes for this level.
     */
    public void setRecipes(ArrayList<Recipe> newRecipes){
        recipes = newRecipes;
    }
    /**
     * getRecipes
     * Returns the reference to the array list of recipes for this level.
     * @return An arraylist of the recipes for this level.
     */
    public ArrayList<Recipe> getRecipes(){
        return recipes;
    }
    /**
     * getNextProjectileID
     * Returns the next unique Projectile ID.
     * @return The next unique Projectile ID.
     */
    public int getNextProjectileID(){
        //Increment for next ID
        projectileIDCount++;
        return projectileIDCount;
    }
    /**
     * getProjectiles
     * Returns the reference to the arraylist of projectiles that exist in this level.
     * @return The reference to the arraylist of projectiles that exist in this level.
     */
    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }
    /**
     * getNextEnemyID
     * Returns the next unique Enemy ID.
     * @return The next unique Enemy ID.
     */
    public int getNextEnemyID(){
        //Increment for next ID
        enemyIDCount++;
        return enemyIDCount;
    }
    /**
     * getEnemy
     * Returns the reference to the arraylist of enemies that exist in this level.
     * @return The reference to the arraylist of enemies that exist in this level.
     */
    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }
    /**
     * getNextItemID
     * Returns the next unique Item ID.
     * @return The next unique Item ID.
     */
    public int getNextItemID(){
        //Increment for next ID
        itemIDCount++;
        return itemIDCount;
    }
    /**
     * getItems
     * Returns the reference to the arraylist of items that exist in this level.
     * @return The reference to the arraylist of items that exist in this level.
     */
    public ArrayList<Item> getItems(){
        return items;
    }
    /**
     * getNextDefenceID
     * Returns the next unique Defence ID.
     * @return The next unique Defence ID.
     */
    public int getNextDefenceID(){
        //Increment for next ID
        defenceIDCount++;
        return defenceIDCount;
    }
    /**
     * getDefences
     * Returns the reference to the arraylist of defences that exist in this level.
     * @return The reference to the arraylist of defences that exist in this level.
     */
    public ArrayList<Defence> getDefences(){
        return defences;
    }
}
