package no.ntnu.idatx2001.newsstand.model;

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
     * Tests add() in Army adding and removing a unit.
     */
    @Test
    void add() throws Exception {
        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        Unit badUnit = new RangedUnit("name", -1);
        army.add(unit);
        army.add(badUnit);
        assertTrue(army.hasUnits());

    }

    /**
     * Test addALl(), adds list of unit to Army.
     */
    @Test
    void addAll() throws Exception {

        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        Unit unit2 = new RangedUnit("name", 1);
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
    void remove() throws Exception {
        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        army.add(unit);
        army.remove(unit);
        assertFalse(army.hasUnits());
    }

    /**
     * test getAllUnits() using getAllUnits().size().
     */

    @Test
    void getAllUnits() throws Exception {
        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        Unit unit2 = new RangedUnit("name2", 2);
        army.add(unit);
        army.add(unit2);
        assertEquals(2,army.getAllUnits().size());
    }

    /**
     * tests getRandom, adding one unit to Army and checking if it returns it.
     */
    @Test
    void getRandom() throws Exception {
        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        army.add(unit);
        assertEquals(unit,army.getRandom());

    }

    /**
     * tests toString using previously know output.
     */

    @Test
    void testToString() throws Exception {

        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        army.add(unit);
        assertEquals("name\nRangedUnit,name,1\n",army.toString());
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
     * TODO: to be changed or removed as hashcode changes when class is changed even if hashCode() still works.
     * test if hashCode corresponds to previous HashCode.
     */
    @Test
    void testHashCode() throws Exception {
        Army army = new Army("name");
        Unit unit = new RangedUnit("name", 1);
        army.add(unit);
        assertEquals(1853462241,army.hashCode());
    }

    @Test
    void simulate() throws Exception {
        CommanderUnit unit1 = new CommanderUnit("name", 2);
        CommanderUnit unit2 = new CommanderUnit("name", 2);
        CavalryUnit unit3 = new CavalryUnit("name", 2);
        InfantryUnit unit4 = new InfantryUnit("name", 2);
        CommanderUnit unit5 = new CommanderUnit("name", 2);
        RangedUnit unit6 = new RangedUnit("name", 2);
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