package no.ntnu.idata.wargames.model;

/**
 * Unit with 20 attack and 12 armor.
 * calculates attack bonus and resist bonus separately.
 *
 * @author johan
 * @version $Id: $Id
 */
public class CavalryUnit extends Unit {
    private boolean chargeReady = true;

    /**
     * Initiates Cavalry using all name, health, attack and armor.
     * uses no default values.
     *
     * @param name as string
     * @param health int
     * @param attack int
     * @param armor int
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Initiates Cavalry using name and health.
     * Uses default Attack and armor.
     *
     * @param name as String
     * @param health as int
     */
    public CavalryUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    /**
     * {@inheritDoc}
     *
     * calculates attackBonus if chargeReady = true.
     * Sets chargeReady to false after first use.
     */
    @Override
    public int getAttackBonus(boolean editable) {
        int attackBonus = 0;
        if (chargeReady) {attackBonus = this.attackBonus + 6;}
        else {attackBonus = this.attackBonus + 2;}
        if(editable){
        chargeReady = false;}
        return attackBonus;
    }

    /**
     * {@inheritDoc}
     *
     * Value is Always 1.
     */

    @Override
    public int getResistBonus() {
        return this.resistBonus + 1;
    }
}
