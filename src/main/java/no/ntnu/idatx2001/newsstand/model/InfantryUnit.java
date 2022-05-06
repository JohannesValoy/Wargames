package no.ntnu.idatx2001.newsstand.model;

/**
 * represents an infantry unit.
 */

public class InfantryUnit extends Unit {
    private int attacks = 0;


    /**
     * initialises InfantryUnit with custom parameters.
     * @param name String
     * @param health int
     * @param attack int
     * @param armor int
     */
    public InfantryUnit(String name, int health, int attack,
                        int armor) {
        super(name, health, attack, armor);
    }

    /**
     * initialises InfantryUnit with default parameters.
     * @param name String
     * @param health int
     */

    public InfantryUnit(String name, int health) {
        super(name, health, 15,10);
    }

    /**
     * overrides Unit methode
     * returns attackBonus as int.
     * @return attackBonus as int
     */

    @Override
    public int getAttackBonus() {
        attackBonus = 2;
        if (attacks == 0) {attackBonus += 4;}
        return attackBonus;
    }

    /**
     * overrides Unit methode
     * returns resistBonus as int.
     * @return resistBonus as int
     */

    @Override
    public int getResistBonus() {
            resistBonus = 1;
        return resistBonus;
    }

}
