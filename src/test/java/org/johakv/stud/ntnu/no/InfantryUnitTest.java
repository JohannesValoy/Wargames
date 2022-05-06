package org.johakv.stud.ntnu.no;

import Old.model.InfantryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     */

    @Test
    void getAttackBonus() {
        InfantryUnit infantryUnit = new InfantryUnit("Unit",1,1,1);
        assertEquals(6, infantryUnit.getAttackBonus());
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     */

    @Test
    void getResistBonus() {
        InfantryUnit infantryUnit = new InfantryUnit("Unit",1,1,1);
        assertEquals(1, infantryUnit.getResistBonus());
    }
}