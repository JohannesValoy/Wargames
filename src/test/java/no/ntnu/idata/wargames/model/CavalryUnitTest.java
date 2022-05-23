package no.ntnu.idata.wargames.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CavalryUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     */
    @Test
    public void getAttackBonus() {
        CavalryUnit unit = new CavalryUnit("name", 2, 3, 4);
        assertEquals(6, unit.getAttackBonus(true));
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     */
    @Test
    public void getResistBonus() {
        CavalryUnit unit = new CavalryUnit("name", 2, 3, 4);
        assertEquals(1, unit.getResistBonus());
    }

    /**
     * tests getName() comparing it to given name.
     */

    @Test
    public void getName() {
        CavalryUnit unit = new CavalryUnit("name", 2, 3, 4);
        assertEquals("name", unit.getName());
    }
}
