package Old.model;

/**
 * Unit with 20 attack and 12 armor.
 * calculates attack bonus and resist bonus separately.
 *
 */

public class CavalryUnit extends Unit {
    private boolean chargeReady = true;

    /**
     * Initiates Cavalry using all name, health, attack and armor.
     * uses no default values.
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
     * @param name as String
     * @param health as int
     */

    public CavalryUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    /**
     * calculates attackBonus if chargeReady = true.
     * Sets chargeReady to false after first use.
     * @return attackBonus.
     */
    @Override
    public int getAttackBonus() {
        if (chargeReady) {attackBonus = 6;}
        else {attackBonus = 2;}
        chargeReady = false;
        return attackBonus;
    }

    /**
     * Value is Always 1.
     * @return resistBonus
     */

    @Override
    public int getResistBonus() {
        return 1;
    }
}
