package no.ntnu.idata.wargames.model;

/**
 * todo comment here
 *
 * @author johan
 * @version $Id: $Id
 * @since 1.0
 */
public class DummyUnit extends Unit {
    /**
     * Initiates Unit using name, health, attack and armor.
     *
     * @param name   as string
     * @param health int
     * @param attack int
     * @param armor  int
     */
    protected DummyUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /** {@inheritDoc} */
    @Override
    public int getAttackBonus(boolean editable) {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int getResistBonus() {
        return 0;
    }

}
