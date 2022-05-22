package no.ntnu.idata2001.wargames.model;

import no.ntnu.idata2001.wargames.units.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitTest {

    /**
     * tests getAttackBonus() comparing it to expected attack bonus.
     *
     * @since 1.0
     */

    @Test
    public void getAttackBonus() {
        Unit Unit = new DummyUnit("Unit",1,1,1);
        assertEquals(0, Unit.getAttackBonus(true));
    }

    /**
     * tests getResistBonus() comparing it to expected resist bonus.
     *
     * @since 1.0
     */

    @Test
    public void getResistBonus() {
        Unit Unit = new DummyUnit("Unit",1,1,1);
        assertEquals(0, Unit.getResistBonus());
    }

    /**
     * <p>getName.</p>
     */
    @Test
    public void getName() {
        DummyUnit unit = new DummyUnit("name", 2, 3, 4);
        assertEquals("name", unit.getName());
    }
}
