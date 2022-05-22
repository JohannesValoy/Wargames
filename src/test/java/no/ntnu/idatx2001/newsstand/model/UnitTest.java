package no.ntnu.idatx2001.newsstand.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     */

    @Test
    void getAttackBonus() {
        Unit Unit = new DummyUnit("Unit",1,1,1);
        assertEquals(0, Unit.getAttackBonus(true));
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     */

    @Test
    void getResistBonus() {
        Unit Unit = new DummyUnit("Unit",1,1,1);
        assertEquals(0, Unit.getResistBonus());
    }

    @Test
    void getName() {
        DummyUnit unit = new DummyUnit("name", 2, 3, 4);
        assertEquals("name", unit.getName());
    }
}