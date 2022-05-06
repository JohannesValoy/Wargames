package org.johakv.stud.ntnu.no;

import Old.model.RangeUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     */

    @Test
    void getAttackBonus() {
        RangeUnit unit = new RangeUnit("name", 2, 3, 4);
        assertEquals(3, unit.getAttackBonus());
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     */

    @Test
    void getResistBonus() {
        RangeUnit unit = new RangeUnit("name", 2, 3, 4);
        assertEquals(6, unit.getResistBonus());
    }


    /**
     * Tests setHealth by setting health and comparing to expected outcome.
     */
    @Test
    void setHealth() {
        RangeUnit unit = new RangeUnit("name", 2, 3, 4);
        unit.setHealth(7);
        assertEquals(7, unit.getHealth());
        unit.setHealth(-7);
        assertEquals(0, unit.getHealth());
    }
}