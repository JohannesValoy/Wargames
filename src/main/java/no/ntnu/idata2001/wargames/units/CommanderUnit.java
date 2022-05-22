package no.ntnu.idata2001.wargames.units;

/**
 * custom version of CavalryUnit.
 *
 * @author johan
 * @version $Id: $Id
 */
public class CommanderUnit extends CavalryUnit {

    /**
     * initialises CommanderUnit with custom Parameters.
     *
     * @param name as String
     * @param health as int
     * @param attack as int
     * @param armor as int
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * initialises CommanderUnit with default parameters.
     *
     * @param name as String
     * @param health as int
     */
    public CommanderUnit(String name, int health) {
        super(name, health, 25, 15);
    }
}

