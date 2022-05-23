package no.ntnu.idata.wargames.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfantryUnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     *
     * @since 1.0
     */

    @Test
    public void getAttackBonus() {
        InfantryUnit infantryUnit = new InfantryUnit("Unit",1,1,1);
        assertEquals(6, infantryUnit.getAttackBonus(true));
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     *
     * @since 1.0
     */

    @Test
    public void getResistBonus() {
        InfantryUnit infantryUnit = new InfantryUnit("Unit",1,1,1);
        assertEquals(1, infantryUnit.getResistBonus());
    }
}
