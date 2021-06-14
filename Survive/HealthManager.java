/**
 * [HealthManager.java]
 * A class that represents a health manager for some game object that has health.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */

class HealthManager {
    /*-----Varaibles for this HealthManager-----*/
    private int maxHealth;
    private int health; //Current health
    
    /**
     * HealthManager
     * A constructor that constructs a health manager with a specified maxHealth.
     * @param maxHealth The maximum health of for this HealthManager.
     */
    HealthManager(int maxHealth){
        this.maxHealth = maxHealth;
        //By default, health is full when created
        this.health = maxHealth;
    }
    /**
     * getHealth
     * Returns the current health of this HealthManager.
     * @return The health of this HealthManager.
     */
    public int getHealth(){
        return health;
    }
    /**
     * getMaxHealth
     * Returns the maximum health of this HealthManager.
     * @return The maximum health of this HealthManager.
     */
    public int getMaxHealth(){
        return maxHealth;
    }
    /**
     * heal
     * Heals this HealthManager by a specified amount.
     * @param healAmount The amount of health that is to be healed.
     */
    public void heal(int healAmount){
        //Remember that the current health can't go over the maxHealth
        health = Math.min(maxHealth, health+healAmount);
    }
    
    /**
     * takeDamage
     * Makes this HealthManager take some specified amount of damage.
     * @param damageTaken The amount of damage taken.
     */
    public void takeDamage(int damageTaken){
        //Remember health can't go past 0
        health = Math.max(0, health-damageTaken);
    }

}
