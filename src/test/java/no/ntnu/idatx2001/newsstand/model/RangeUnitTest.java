package no.ntnu.idatx2001.newsstand.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     */

    @Test
    void getAttackBonus() {
        RangedUnit unit = new RangedUnit("name", 2, 3, 4);
        assertEquals(3, unit.getAttackBonus());
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     */

    @Test
    void getResistBonus() {
        RangedUnit unit = new RangedUnit("name", 2, 3, 4);
        assertEquals(6, unit.getResistBonus());
    }


    /**
     * Tests setHealth by setting health and comparing to expected outcome.
     */
    @Test
    void setHealth() {
        RangedUnit unit = new RangedUnit("name", 2, 3, 4);
        unit.setHealth(7);
        assertEquals(7, unit.getHealth());
        unit.setHealth(-7);
        assertEquals(0, unit.getHealth());
    }
}