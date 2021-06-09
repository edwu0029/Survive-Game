abstract class Character {
    private String path;
    private int maxHealth;
    private int health;
    private final int ID;

    Character(int id, String path, int maxHealth){
        this.ID = id;
        this.path = path;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }
    public String getPath(){
        return path;
    }
    public int getID(){
        return ID;
    }
    public void heal(int amount){
        System.out.println("Healed "+amount+" health");
        health+=amount;
        if(health>maxHealth){
            health = maxHealth;
        }
    }

    public void takeDamage(int amount){
        health-=amount;
        // System.out.println("Character took damage!");
        // System.out.println(health);
        if(health<=0){
            Death();
        }
    }
    
    public int getHealth(){
        return health;
    }

    abstract public void loadSprites();
    abstract public void loadSpriteOffset();
    abstract public void Death();
    abstract public void Attack();

}
