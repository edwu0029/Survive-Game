
class Recipe {

    private Player player;
    private String[] ingredients;
    private Item itemProduct;
    private Defence defenceProduct;

    private GamePanel gamePanel;
    private TileMap tileMap;
    private String path;

    //For Item Recipe
    Recipe(Player p, String[] ingredients, Item iP, GamePanel gP, TileMap tM, String path){
        this.player = p;
        this.ingredients = ingredients;
        this.itemProduct = iP;
        this.gamePanel = gP;
        this.tileMap = tM;
        this.path = path;
    }
    //For Defence Recipe
    Recipe(String[] ingredients, Defence dP, GamePanel gP, TileMap tM, String path){
        this.ingredients = ingredients;
        this.defenceProduct = dP;
        this.gamePanel = gP;
        this.tileMap = tM;
        this.path = path;
    }
    public Item getItemProduct(){
        return itemProduct;
    }
    public Defence getDefenceProduct(){
        return defenceProduct;
    }
    public boolean checkCraftable(){
        boolean[] alreadyTaken = new boolean[player.inventory.size()];
        for(int i = 0;i<ingredients.length;i++){
            boolean foundIngredient = false;
            for(int j = 0;j<player.inventory.size();j++){
                if(player.inventory.get(j).getName().equals(ingredients[i]) && !alreadyTaken[j]){
                    alreadyTaken[j] = true;
                    foundIngredient = true;
                    break;
                }
            }
            if(!foundIngredient){
                return false;
            }
        }
        return true;
    }
    public String[] getIngredients(){
        return ingredients;
    }
    public void craft(){
        //Remove ingredients from player inventory
        for(int i = 0;i<ingredients.length;i++){
            for(int j = 0;j<player.inventory.size();j++){
                if(player.inventory.get(j).getName().equals(ingredients[i])){
                    player.inventory.remove(j);
                    break;
                }
            }
        }
        if(itemProduct!=null){
            player.inventory.add(itemProduct);
        }
        if(defenceProduct!=null){
            if(defenceProduct.getName().equals("Fence Horizontal")){
                Defence newDefence = new FenceHorizontal(gamePanel.nextEnemyID(), gamePanel, tileMap, player, path, 0, 0);
                newDefence.setSpawnOffsetX(0);
                newDefence.setSpawnOffsetY(0);
                player.defences.add(newDefence);
            }else if(defenceProduct.getName().equals("Fence Vertical")){
                player.defences.add(new FenceVertical(gamePanel.nextEnemyID(), gamePanel, tileMap, player, path, 0, 0));
            }
        }

    }
}
