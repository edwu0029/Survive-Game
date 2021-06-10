import java.util.ArrayList;

import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class GamePanel extends JPanel{
    private MainFrame mainFrame;
    private PanelManager panelManager;
    private TileMap tileMap;
    private Player player;
    static String path;

    private boolean pause;

    private int projectileIDCount;
    static ArrayList<Projectile> projectiles;

    private int enemyIDCount;
    private ArrayList<Enemy> enemies;

    private int itemIDCount;
    static ArrayList<Item> items;

    private int defenceIDCount;
    static ArrayList<Defence> defences;

    GamePanel(MainFrame frame, PanelManager panelManager, String p){
        this.mainFrame = frame;
        this.panelManager = panelManager;
        path = p;
        this.tileMap = new TileMap(mainFrame, path);
        this.player = new Player(this, tileMap, path);
        
        this.pause = false;

        //Add other panels TEMPORARY
        String[] fenceIngredients = {"Wood", "Wood"};
        Defence fenceHorizontal = new FenceHorizontal(this.nextDefenceID(), this, tileMap, player, path, 0, 0);
        Recipe fenceHorizontalRecipe = new Recipe(fenceIngredients, fenceHorizontal, this, tileMap, path);
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        recipes.add(fenceHorizontalRecipe);

        String[] fenceIngredients2 = {"Wood", "Metal"};
        Defence fenceVertical = new FenceHorizontal(this.nextDefenceID(), this, tileMap, player, path, 0, 0);
        Recipe fenceVerticalRecipe = new Recipe(fenceIngredients2, fenceVertical, this, tileMap, path);
        recipes.add(fenceVerticalRecipe);

        CraftingPanel craftingPanel = new CraftingPanel(panelManager, player, recipes);
        PlacingPanel placingPanel = new PlacingPanel(mainFrame, panelManager, this, tileMap);
        InventoryPanel inventoryPanel = new InventoryPanel(panelManager, placingPanel, player);
        GameOverPanel gameOverPanel = new GameOverPanel(panelManager);

        panelManager.addPanel(inventoryPanel);
        panelManager.addPanel(craftingPanel);
        panelManager.addPanel(placingPanel);
        panelManager.addPanel(gameOverPanel);
        

        this.projectileIDCount = 1;
        projectiles = new ArrayList<Projectile>();

        this.enemyIDCount = 1;
        enemies = new ArrayList<Enemy>();
        enemies.add(new Zombie(this.nextEnemyID(), this, tileMap, player, 500, path, 200, 200, 100));
        enemies.add(new Zombie(this.nextEnemyID(), this, tileMap, player, 500, path, 400, 200, 100));
        enemies.add(new Zombie(this.nextEnemyID(), this, tileMap, player, 500, path, 600, 200, 100));
        enemies.add(new Zombie(this.nextEnemyID(), this, tileMap, player, 500, path, 800, 200, 100));
        enemies.add(new Tree(this.nextEnemyID(), this, tileMap, player, 200, path, 100, 500));
        enemies.add(new Tree(this.nextEnemyID(), this, tileMap, player, 200, path, 100, 500));
        enemies.add(new Tree(this.nextEnemyID(), this, tileMap, player, 200, path, 100, 500));
        enemies.add(new Tree(this.nextEnemyID(), this, tileMap, player, 200, path, 100, 500));

        this.itemIDCount = 1;
        items = new ArrayList<Item>();
        this.addItem(new BlueLaserGun(this.nextItemID(), this, tileMap, 500, 200, path));
        this.addItem(new BlueLaserGun(this.nextItemID(), this, tileMap, 1000, 600, path));
        this.addItem(new Wood(this.nextItemID(), this, tileMap, 300, 1000, path));
        this.addItem(new Wood(this.nextItemID(), this, tileMap, 400, 1000, path));
        this.addItem(new Rock(this.nextItemID(), this, tileMap, 640, 320, path));
        this.addItem(new Metal(this.nextItemID(), this, tileMap, 504, 320, path));


        this.defenceIDCount = 1;
        defences = new ArrayList<Defence>();

        PlayerKeyListener keyListener = new PlayerKeyListener(panelManager, player);
        this.addKeyListener(keyListener);

        PlayerMouseListener mouseListener = new PlayerMouseListener(player);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow(); 

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    public void animate(){
        while(true){
            //delay
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
    public void togglePause(boolean value){
        pause = value;
    }
    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }
    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }
    public ArrayList<Item> getItems(){
        return items;
    }
    public int nextProjectileID(){
        projectileIDCount++;
        return projectileIDCount;
    }
    public int nextEnemyID(){
        enemyIDCount++;
        return enemyIDCount;
    }
    public int nextItemID(){
        itemIDCount++;
        return itemIDCount;
    }
    public int nextDefenceID(){
        defenceIDCount++;
        return defenceIDCount;
    }
    public void destroyProjectile(int id){
        for(int i = 0;i<projectiles.size();i++){
            if(projectiles.get(i).getID()==id){
                projectiles.remove(i);
            }
        }
    }
    public void destroyEnemy(int id){
        for(int i = 0;i<enemies.size();i++){
            if(enemies.get(i).getID()==id){
                System.out.println(1);
                enemies.remove(i);
            }
        }
    }
    public int checkItemCollision(Rectangle box){
        for(int i = 0;i<items.size();i++){
            Item currentItem = items.get(i);
            if(currentItem.checkCollision(box)){
                return currentItem.getID();
            }
        }
        return -1;
    }
    public int checkDefenceCollision(Rectangle box){
        for(int i = 0;i<defences.size();i++){
            Defence currentDefense = defences.get(i);
            if(currentDefense.checkCollision(box)){
                return currentDefense.getID();
            }
        }
        return -1;
    }
    public void addItem(Item newItem){
        items.add(newItem);
    }
    public void pickUpItem(int id){
        for(int i = 0;i<items.size();i++){
            Item currentItem = items.get(i);
            if(currentItem.getID()==id){
                items.remove(i);
                player.inventory.add(currentItem);
            }
        }
    }
    public void gameOver(){
        panelManager.setActivePanel("Game Over Panel");
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, mainFrame.getScreenWidth(), mainFrame.getScreenHeight());
        tileMap.draw(g);
        for(int i = 0;i<projectiles.size();i++){
            projectiles.get(i).draw(g);
        }
        for(int i = 0;i<items.size();i++){
            items.get(i).draw(g);
        }
        player.draw(g);
        for(int i = 0;i<enemies.size();i++){
            enemies.get(i).draw(g);
        }
        for(int i = 0;i<defences.size();i++){
            defences.get(i).draw(g);
        }
    }
}
