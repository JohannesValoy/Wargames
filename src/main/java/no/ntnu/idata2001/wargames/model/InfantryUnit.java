package no.ntnu.idata2001.wargames.model;

/**
 * represents an infantry unit.
 *
 * @author johan
 * @version $Id: $Id
 */
public class InfantryUnit extends Unit {
    private int attacks = 0;


    /**
     * initialises InfantryUnit with custom parameters.
     *
     * @param name String
     * @param health int
     * @param attack int
     * @param armor int
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * initialises InfantryUnit with default parameters.
     *
     * @param name String
     * @param health int
     */
    public InfantryUnit(String name, int health) {
        super(name, health, 15,10);
    }

    /**
     * {@inheritDoc}
     *
     * overrides Unit methode
     * returns attackBonus as int.
     */

    @Override
    public int getAttackBonus(boolean editable) {
        int attackBonus = 0;
        attackBonus = this.attackBonus + 2;
        if (attacks == 0) {attackBonus = attackBonus + 4;}
        return attackBonus;
    }

    /**
     * {@inheritDoc}
     *
     * overrides Unit methode
     * returns resistBonus as int.
     */

    @Override
    public int getResistBonus() {
        return resistBonus + 1;
    }

}
