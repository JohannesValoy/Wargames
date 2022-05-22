package no.ntnu.idata2001.wargames.model;

import no.ntnu.idata2001.wargames.units.RangedUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangedUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     *
     * @since 1.0
     */

    @Test
    public void getAttackBonus() {
        RangedUnit unit = new RangedUnit("name", 2, 3, 4);
        assertEquals(3, unit.getAttackBonus(true));
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     *
     * @since 1.0
     */

    @Test
    public void getResistBonus() {
        RangedUnit unit = new RangedUnit("name", 2, 3, 4);
        assertEquals(6, unit.getResistBonus());
    }

    /**
     * <p>addResistBonus.</p>
     */
    @Test
    public void addResistBonus(){
        RangedUnit rangedUnit = new RangedUnit("name", 2, 3, 4);
        rangedUnit.addResistBonus(50);
        assertEquals(56, rangedUnit.getResistBonus());
    }


    /**
     * Tests setHealth by setting health and comparing to expected outcome.
     *
     * @since 1.0
     */
    @Test
    public void setHealth() {
        RangedUnit unitOne = new RangedUnit("unitOneName", 2, 3, 4);
        RangedUnit unitTwo = new RangedUnit("unitTwoName", 2, 3, 4);
        unitOne.setHealth(7);
        unitTwo.setHealth(-7);
        assertEquals(7, unitOne.getHealth());
        assertEquals(0, unitTwo.getHealth());
    }
}
