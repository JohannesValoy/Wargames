package org.johakv.stud.ntnu.no;

import Old.model.CavalryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CavalryUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     */
    @Test
    void getAttackBonus() {
        CavalryUnit unit = new CavalryUnit("name", 2, 3, 4);
        assertEquals(6, unit.getAttackBonus());
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     */
    @Test
    void getResistBonus() {
        CavalryUnit unit = new CavalryUnit("name", 2, 3, 4);
        assertEquals(1, unit.getResistBonus());
    }

    /**
     * tests getName() comparing it to given name.
     */

    @Test
    void getName() {
        CavalryUnit unit = new CavalryUnit("name", 2, 3, 4);
        assertEquals("name", unit.getName());
    }
}