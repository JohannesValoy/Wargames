package no.ntnu.idata2001.wargames.units;

import java.io.Serializable;

/**
 * Super class responsible for units in army
 * has modified toString(). Does not check for valid input
 *
 * @author johan
 * @version $Id: $Id
 */
public abstract class Unit implements Serializable{
    protected String name;
    protected int health;
    protected int attack;
    protected int armor;
    protected int attackBonus;
    protected int resistBonus;

    /**
     * Initiates Unit using name, health, attack and armor.
     *
     * @param name as string
     * @param health int
     * @param attack int
     * @param armor int
     */
    protected Unit(String name, int health, int attack, int armor) {
        this.name = name;
        if(health > 0){
            this.health = health;
        } else {
            throw new IllegalArgumentException("Health must be more than 0");}

        if(attack > 0){
            this.attack = attack;
        } else {
            throw new IllegalArgumentException("Attack must be more than 0");
        }
        if(armor >= 0){
            this.armor = armor;
        } else {
            throw new IllegalArgumentException("armor must be 0 or more");
        }

    }

    /**
     * Attacks opponent.
     * Takes health from opponent with:
     * (attack + attackBonus) - (armor + resistBonus)
     *
     * @param opponent as Army
     */
    public void attack(Unit opponent) {
        opponent.health = opponent.health - (this.getAttack() + this.getAttackBonus(true))
            + (opponent.getArmor() + opponent.getResistBonus());}

    //TODO: check if supposed to have this function (related to the terrain function)
    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * returns name of Unit as String.
     *
     * @return name of unit as String
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns Health of Unit as int.
     *
     * @return  Health of opponent as int
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * Returns attack as int.
     *
     * @return attack as int
     */
    public int getAttack(){
        return attack;
    }

    /**
     * <p>Getter for the field <code>armor</code>.</p>
     *
     * @return a int
     */
    public int getArmor() {
        return armor + resistBonus;
    }

    /**
     * Sets health using parameter int Health.
     *
     * @param health as int
     */
    public void setHealth(int health) {
        if(health > 0) {
            this.health = Math.max(health, 0);
        } else {this.health = 0;}
    }

    /**
     * {@inheritDoc}
     *
     * Returns details as String. Overrides toString.
     */

    @Override
    public String toString() {
        return getClass().getSimpleName() + "," + name + "," + health;
    }

    /**
     * Returns attackBonus as int.
     *
     * @return attackBonus as int
     * @param editable a boolean
     */
    public abstract int getAttackBonus(boolean editable);

    /**
     * Returns resistBonus as int.
     *
     * @return resistBonus as int
     */
    public abstract int getResistBonus();

    //TODO: check if supposed to have this function (related to the terrain function)
    /**
     * <p>addAttackBonus.</p>
     *
     * @param attackBonus a int
     */
    public void addAttackBonus(int attackBonus){
        if(attackBonus >= 0){
            this.attackBonus = attackBonus;
        } else {
            throw new IllegalArgumentException("Attack-bonus must be 0 or more");
        }
    }

    //TODO: check if supposed to have this function (related to the terrain function)
    /**
     * <p>addResistBonus.</p>
     *
     * @param resistBonus a int
     */
    public void addResistBonus(int resistBonus){
        if(attackBonus >= 0){
            this.resistBonus = resistBonus;
        } else {
            throw new IllegalArgumentException("Resist-bonus must be 0 or more");
        }
    }


}
