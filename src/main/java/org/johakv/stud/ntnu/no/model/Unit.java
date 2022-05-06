package org.johakv.stud.ntnu.no.model;

import java.io.Serializable;

/**
 * Super class responsible for units in army
 * has modified toString(). Does not check for valid input
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
     * @param name as string
     * @param health int
     * @param attack int
     * @param armor int
     */

    protected Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Attacks opponent.
     * Takes health from opponent with:
     * (attack + attackBonus) - (armor + resistBonus)
     * @param opponent as Army
     */
    public void attack(Unit opponent)
    {opponent.health = opponent.health - (this.attack + this.attackBonus)
            + (opponent.armor + opponent.resistBonus);}

    //TODO: check if supposed to have this function (related to the terrain function)
    public void setName(String name){
        this.name = name;
    }
    /**
     * returns name of Unit as String.
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

    public int getArmor() {

        return armor;
    }

    //TODO: check if supposed to have this function (related to the terrain function)
    public void setArmor(int armor){
        this.armor = armor;
    }

    //TODO: check if supposed to have this function (related to the terrain function)
    public void setAttackBonus(int attackBonus){
        this.attackBonus = attackBonus;
    }

    /**
     * Sets health using parameter int Health.
     *
     * @param health as int
     */
    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    /**
     * Returns details as String. Overrides toString.
     *
     * @return details of Unit using String.
     */

    @Override
    public String toString() {
        return getClass().getSimpleName() + "," + name + "," + health;
    }

    /**
     * Returns attackBonus as int.
     *
     * @return attackBonus as int
     */

    public abstract int getAttackBonus();

    /**
     * Returns resistBonus as int.
     *
     * @return resistBonus as int
     */

    public abstract int getResistBonus();
}
