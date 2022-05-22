package no.ntnu.idata2001.wargames.factory;

import no.ntnu.idata2001.wargames.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnitFactoryTest {

    @Test
    void importUnits() {
        UnitFactory unitFactory = new UnitFactory();
        CavalryUnit cavalryUnitOne = new CavalryUnit("CavalryUnitOne", 1);
        InfantryUnit infantryUnitOne = new InfantryUnit("InfantryUnitOne", 1);
        RangedUnit rangedUnitOne = new RangedUnit("RangedUnitOne", 1);
        List<Unit> units= new ArrayList<>();
        units.add(cavalryUnitOne);
        units.add(rangedUnitOne);
        units.add(infantryUnitOne);
        unitFactory.importUnits(units);
        assertEquals(unitFactory.retrieveAllunits(), units);
    }

    @Test
    void addUnit() throws IOException {
        UnitFactory unitFactory = new UnitFactory();
        unitFactory.addUnit(1, "cavalryUnitOne", "CavalryUnit", 2);
        unitFactory.addUnit(1, "infatryUnitOne", "InfantryUnit", 2);
        assertEquals(4, unitFactory.retrieveAllunits().size());
    }

    @Test
    void retrieveSpesificUnits() throws IOException {
        UnitFactory unitFactory = new UnitFactory();
        unitFactory.addUnit(1, "CavalryUnitOne", "CavalryUnit", 1);
        assertEquals(1,unitFactory.retrieveSpecificUnits("CavalryUnit", "CavalryUnitOne", 1).get(0).getHealth());
        assertEquals(CavalryUnit.class,unitFactory.retrieveSpecificUnits("CavalryUnit", "CavalryUnitOne", 1).get(0).getClass());
        assertEquals("CavalryUnitOne",unitFactory.retrieveSpecificUnits("CavalryUnit", "CavalryUnitOne", 1).get(0).getName());
    }

    @Test
    void retrieveCavalryUnits() {
        UnitFactory unitFactory = new UnitFactory();
        CavalryUnit cavalryUnitOne = new CavalryUnit("CavalryUnitOne", 1);
        InfantryUnit infantryUnitOne = new InfantryUnit("InfantryUnitOne", 1);
        RangedUnit rangedUnitOne = new RangedUnit("RangedUnitOne", 1);
        List<Unit> units= new ArrayList<>();
        units.add(cavalryUnitOne);
        units.add(rangedUnitOne);
        units.add(infantryUnitOne);
        unitFactory.importUnits(units);
        assertEquals(units.get(0), unitFactory.retrieveCavalryUnits().get(0));

    }

    @Test
    void retrieveCommanderUnits() {

        UnitFactory unitFactory = new UnitFactory();
        CavalryUnit cavalryUnitOne = new CavalryUnit("CavalryUnitOne", 1);
        InfantryUnit infantryUnitOne = new InfantryUnit("InfantryUnitOne", 1);
        RangedUnit rangedUnitOne = new RangedUnit("RangedUnitOne", 1);
        CommanderUnit commanderUnitOne = new CommanderUnit("CommanderUnitOne", 1);
        List<Unit> units= new ArrayList<>();
        units.add(cavalryUnitOne);
        units.add(rangedUnitOne);
        units.add(infantryUnitOne);
        units.add(commanderUnitOne);
        unitFactory.importUnits(units);
        assertEquals(units.get(3), unitFactory.retrieveCommanderUnits().get(0));
    }

    @Test
    void retrieveInfantryUnits() {
        UnitFactory unitFactory = new UnitFactory();
        CavalryUnit cavalryUnitOne = new CavalryUnit("CavalryUnitOne", 1);
        InfantryUnit infantryUnitOne = new InfantryUnit("InfantryUnitOne", 1);
        RangedUnit rangedUnitOne = new RangedUnit("RangedUnitOne", 1);
        CommanderUnit commanderUnitOne = new CommanderUnit("CommanderUnitOne", 1);
        List<Unit> units= new ArrayList<>();
        units.add(cavalryUnitOne);
        units.add(rangedUnitOne);
        units.add(infantryUnitOne);
        units.add(commanderUnitOne);
        unitFactory.importUnits(units);
        assertEquals(units.get(2), unitFactory.retrieveInfantryUnits().get(0));
    }

    @Test
    void retrieveRangedUnits() {
        UnitFactory unitFactory = new UnitFactory();
        CavalryUnit cavalryUnitOne = new CavalryUnit("CavalryUnitOne", 1);
        InfantryUnit infantryUnitOne = new InfantryUnit("InfantryUnitOne", 1);
        RangedUnit rangedUnitOne = new RangedUnit("RangedUnitOne", 1);
        CommanderUnit commanderUnitOne = new CommanderUnit("CommanderUnitOne", 1);
        List<Unit> units= new ArrayList<>();
        units.add(cavalryUnitOne);
        units.add(rangedUnitOne);
        units.add(infantryUnitOne);
        units.add(commanderUnitOne);
        unitFactory.importUnits(units);
        assertEquals(units.get(1), unitFactory.retrieveRangedUnits().get(0));
    }
}
