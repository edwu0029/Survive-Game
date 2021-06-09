
class Recipe {

    private Player player;
    private String[] ingredients;
    private Item itemProduct;
    private Defence defenceProduct;

    Recipe(Player p, String[] ingredients, Item iP, Defence dP){
        this.player = p;
        this.ingredients = ingredients;
        this.itemProduct = iP;
        this.defenceProduct = dP;
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
            player.defences.add(defenceProduct);
        }

    }
}
