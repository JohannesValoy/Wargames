package no.ntnu.idatx2001.newsstand.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests Constructor in CommanderUnit.
 */

class CommanderUnitTest {
    @Test
    void getName() {
        CommanderUnit unit = new CommanderUnit("name", 2, 3, 4);
        assertEquals("name", unit.getName());
        assertEquals(2, unit.getHealth());
        assertEquals(3, unit.getAttack());
        assertEquals(4, unit.getArmor());
    }
}