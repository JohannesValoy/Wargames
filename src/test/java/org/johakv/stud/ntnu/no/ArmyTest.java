package org.johakv.stud.ntnu.no;

import Old.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    /**
     * tests getName() in Army using "name" as name.
     */

    @Test
    void getName() {
        Army unit = new Army("name");
        assertEquals("name", unit.getName());
    }

    /**
     * tests add() in Army adding and removing a unit.
     */
    @Test
    void add() {
        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        army.add(unit);
        assertTrue(army.hasUnits());

    }

    /**
     * Test addALl(), adds list of unit to Army.
     */
    @Test
    void addAll() {

        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        Unit unit2 = new RangeUnit("name", 1);
        ArrayList<Unit> list = new ArrayList<>();
        army.add(unit);
        list.add(unit);
        list.add(unit2);
        army.addAll(list);
        assertEquals(2,army.getAllUnits().size());
    }

    /**
     * Tests remove(), adds and removes units from army.
     */

    @Test
    void remove() {
        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        army.add(unit);
        army.remove(unit);
        assertFalse(army.hasUnits());
    }

    /**
     * test getAllUnits() using getAllUnits().size().
     */

    @Test
    void getAllUnits() {
        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        Unit unit2 = new RangeUnit("name2", 2);
        army.add(unit);
        army.add(unit2);
        assertEquals(2,army.getAllUnits().size());
    }

    /**
     * tests getRandom, adding one unit to Army and checking if it returns it.
     */
    @Test
    void getRandom() {
        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        army.add(unit);
        assertEquals(unit,army.getRandom());

    }

    /**
     * tests toString using previously know output.
     */

    @Test
    void testToString() {

        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        army.add(unit);
        assertEquals("Army{name='name', units=[Unit{name='name', health=1, attack=15, armor=8, attackBonus=0, resistBonus=0}]}",army.toString());
    }

    /**
     * test Equals using army in army.
     */

    @Test
    void testEquals() {
        Army army = new Army("name");
        Army army2 = new Army("name");
        assertTrue(army.equals(army2));
    }

    /**
     * test if hashCode corresponds to previous HashCode.
     */
    @Test
    void testHashCode() {
        Army army = new Army("name");
        Unit unit = new RangeUnit("name", 1);
        army.add(unit);
        assertEquals(1106777261,army.hashCode());
    }

    @Test
    void simulate() {
        CommanderUnit unit1 = new CommanderUnit("name", 2);
        CommanderUnit unit2 = new CommanderUnit("name", 2);
        CavalryUnit unit3 = new CavalryUnit("name", 2);
        InfantryUnit unit4 = new InfantryUnit("name", 2);
        CommanderUnit unit5 = new CommanderUnit("name", 2);
        RangeUnit unit6 = new RangeUnit("name", 2);
        CommanderUnit unit7 = new CommanderUnit("name", 1);


        Army army = new Army("ArmyOne");

        army.add(unit1);
        army.add(unit2);
        army.add(unit3);
        army.add(unit4);
        army.add(unit5);
        army.add(unit6);
        army.add(unit7);

        assertEquals(1, army.getInfantryUnits().size());
        assertEquals(5, army.getCavalryUnits().size());
        assertEquals(1, army.getRangedUnits().size());
        assertEquals(4, army.getCommanderUnits().size());
    }
}