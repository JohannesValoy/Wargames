package no.ntnu.idatx2001.newsstand.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleTest {

    @Test
    void getName() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne,armyTwo);
        assertEquals(armyOne, battle.getArmyOne());
        assertEquals(armyTwo, battle.getArmyTwo());
    }

    @Test
    void getInfantryUnits() throws IOException {
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        UnitFactory unitFactory = new UnitFactory();

        unitFactory.addUnit(1, "name", "InfantryUnit",5);
        ArrayList<Unit> infantryUnitList = new ArrayList<>(unitFactory.retrieveAllunits());
        battle.getArmyOne().addAll(infantryUnitList);
        assertEquals(infantryUnitList, battle.getInfantryUnits());
    }

    @Test
    void getCavalryUnits() throws IOException {
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        UnitFactory unitFactory = new UnitFactory();

        unitFactory.addUnit(1, "name", "CavalryUnit",5);
        ArrayList<Unit> cavalryUnitList = new ArrayList<>(unitFactory.retrieveAllunits());
        battle.getArmyOne().addAll(cavalryUnitList);
        assertEquals(cavalryUnitList, battle.getCavalryUnits());
    }

    @Test
    void getRangedUnits() throws IOException {
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        UnitFactory unitFactory = new UnitFactory();

        unitFactory.addUnit(1, "name", "RangedUnit",5);
        ArrayList<Unit> rangedUnitList = new ArrayList<>(unitFactory.retrieveAllunits());
        battle.getArmyOne().addAll(rangedUnitList);
        assertEquals(rangedUnitList, battle.getRangeUnits());
    }
}