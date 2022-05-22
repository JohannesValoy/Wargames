package no.ntnu.idata2001.wargames.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests Constructor in CommanderUnit.
 */

class CommanderUnitTest {
    /**
     * <p>getName.</p>
     */
    @Test
    public void getName() {
        CommanderUnit unit = new CommanderUnit("name", 2, 3, 4);
        assertEquals("name", unit.getName());
        assertEquals(2, unit.getHealth());
        assertEquals(3, unit.getAttack());
        assertEquals(4, unit.getArmor());
    }
}
