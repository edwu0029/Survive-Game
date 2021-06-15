import java.util.ArrayList;

/**
 * [Recipe.java]
 * A class that represents a crafting recipe that can be used to craft a item or defence or object.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Recipe {
    /*-----References to other objects-----*/
    private Level level;
    private GamePanel gamePanel;
    private TileMap tileMap;
    private String path;
    private Player player;
    private ArrayList<Item> playerItems;
    private ArrayList<Defence> playerDefences;

    /*-----Variables for this Recipe-----*/
    private String[] ingredients;
    private Item itemProduct;
    private Defence defenceProduct;

    //For Item Recipe
    /**
     * Recipe
     * A constructor that constructs a crafting recipe with a product that is an Item.
     * @param level The level that this recipe will be constructed for.
     * @param ingredients The ingredients needed to craft this recipe.
     * @param itemProduct The item product that this recipe crafts.
     */
    Recipe(Level level, String[] ingredients, Item itemProduct){
        this.level = level;
        this.player = level.getPlayer();
        this.ingredients = ingredients;
        this.itemProduct = itemProduct;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.tileMap = level.getTileMap();
        this.path = level.getPath();

        this.playerItems = player.getPlayerItems();
        this.playerDefences = player.getPlayerDefences();
    }
    //For Defence Recipe
    /**
     * Recipe
     * A constructor that constructs a crafting recipe with a product that is an Defence.
     * @param level The level that this recipe will be constructed for.
     * @param ingredients Thee ingredients needed to craft this recipe.
     * @param defenceProduct The defence product that this recipe crafts.
     */
    Recipe(Level level, String[] ingredients, Defence defenceProduct){
        this.level = level;
        this.player = level.getPlayer();
        this.ingredients = ingredients;
        this.defenceProduct = defenceProduct;
        this.gamePanel = level.getPanelManager().getGamePanel();
        this.tileMap = level.getTileMap();
        this.path = level.getPath();

        this.playerItems = player.getPlayerItems();
        this.playerDefences = player.getPlayerDefences();
    }
    /**
     * getItemProduct
     * Returns the item product of this recipe.
     * @return The item product of this recipe.
     */
    public Item getItemProduct(){
        return itemProduct;
    }
    /**
     * getDefenceProduct
     * Returns the defence product of this recipe.
     * @return The defence product of this recipe.
     */
    public Defence getDefenceProduct(){
        return defenceProduct;
    }
    /**
     * getIngredients
     * Returns the ingredients of this recipe.
     * @return The ingredients of this recipe.
     */
    public String[] getIngredients(){
        return ingredients;
    }

    /**
     * checkCraftable
     * Checks if this recipe can be crafted based on the items in the player's inventory.
     * @return true if it is possible for this recipe to be crafted, false otherwise
     */
    public boolean checkCraftable(){
        //Array to keep track of already counted items
        boolean[] alreadyTaken = new boolean[playerItems.size()];
        for(int i = 0;i<ingredients.length;i++){
            boolean foundIngredient = false;
            for(int j = 0;j<playerItems.size();j++){
                //Check if this is the correct ingredient and it hasn't already been counted
                if(playerItems.get(j).getType().equals(ingredients[i]) && !alreadyTaken[j]){
                    alreadyTaken[j] = true;
                    foundIngredient = true;
                    //Once found, break out of loop
                    break;
                }
            }
            //If a single ingredient isn't found, the recipe can't be crafted
            if(!foundIngredient){
                return false;
            }
        }
        //All ingredients were found, the recipe can be crafted
        return true;
    }
    /**
     * craft
     * Crafts theis recipe and adds the corresponding product to the player's inventory.
     */
    public void craft(){
        //Remove ingredients from player inventory
        for(int i = 0;i<ingredients.length;i++){
            for(int j = 0;j<playerItems.size();j++){
                if(playerItems.get(j).getType().equals(ingredients[i])){
                    playerItems.remove(j);
                    break;
                }
            }
        }
        if(itemProduct!=null){ //This recipe crafts for a item
            playerItems.add(itemProduct);
        }else if(defenceProduct!=null){ //This recipe crafts for a defence
            Defence newDefence;
            
            if(defenceProduct.getType().equals("Horizontal Fence")){ //Horizontal Fence
                newDefence = new HorizontalFence(level, 0, 0);

                //Set spawn offsets to 0 to make it at the top-left corner. This is needed for the defence to be placed properly.
                newDefence.setSpawnOffsetX(0);
                newDefence.setSpawnOffsetY(0);
                playerDefences.add(newDefence);
            }else if(defenceProduct.getType().equals("Vertical Fence")){ //Vertical Fence
                newDefence = new VerticalFence(level, 0, 0);

                //Set spawn offsets to 0 to make it at the top-left corner. This is needed for the defence to be placed properly.
                newDefence.setSpawnOffsetX(0);
                newDefence.setSpawnOffsetY(0);
                playerDefences.add(newDefence);
            }
        }

    }
}
