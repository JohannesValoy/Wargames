package no.ntnu.idata2001.wargames.model;

import no.ntnu.idata2001.wargames.factory.UnitFactory;
import no.ntnu.idata2001.wargames.units.InfantryUnit;
import no.ntnu.idata2001.wargames.units.Unit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleTest {

    /**
     * <p>getName.</p>
     */
    @Test
    void getArmyOneAndTwo() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne,armyTwo);

        assertEquals(armyOne, battle.getArmyOne());
        assertEquals(armyTwo, battle.getArmyTwo());
    }

    /**
     * <p>getInfantryUnits.</p>
     *
     * @throws java.io.IOException if any.
     */
    @Test
    void getInfantryUnits(){
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        UnitFactory unitFactory = new UnitFactory();
        List<Unit> infantryUnitList;

        try {
            unitFactory.addUnit(1, "name", "InfantryUnit", 5);
        } catch (Exception e){
            e.printStackTrace();
        }

        infantryUnitList = unitFactory.retrieveAllunits();
        battle.getArmyOne().addAll(infantryUnitList);

        assertEquals(infantryUnitList, battle.getInfantryUnits());

        //Negative test
        for(Unit infantryUnit: battle.getArmyOne().getInfantryUnits()){
            battle.getArmyOne().remove(infantryUnit);
        }
        assertEquals(infantryUnitList, battle.getInfantryUnits());

    }

    /**
     * <p>getCavalryUnits.</p>
     *
     * @throws java.io.IOException if any.
     */
    @Test
    void getCavalryUnits() throws IOException {
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        UnitFactory unitFactory = new UnitFactory();
        List<Unit> cavalryUnitList;

        unitFactory.addUnit(1, "name", "CavalryUnit",5);
        cavalryUnitList = unitFactory.retrieveAllunits();
        battle.getArmyOne().addAll(cavalryUnitList);

        assertEquals(cavalryUnitList, battle.getCavalryUnits());

        //Negative test
        for(Unit cavalryUnit: battle.getArmyOne().getCavalryUnits()){
            battle.getArmyOne().remove(cavalryUnit);
        }
        assertEquals(cavalryUnitList, battle.getInfantryUnits());

    }

    /**
     * <p>getRangedUnits.</p>
     *
     * @throws java.io.IOException if any.
     */
    @Test
    void getRangedUnits() throws IOException {
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        UnitFactory unitFactory = new UnitFactory();
        List<Unit> rangedUnitList;

        unitFactory.addUnit(1, "name", "RangedUnit",5);
        rangedUnitList = unitFactory.retrieveAllunits();
        battle.getArmyOne().addAll(rangedUnitList);

        assertEquals(rangedUnitList, battle.getRangeUnits());

        //Negative test
        for(Unit rangedUnit: battle.getArmyOne().getRangedUnits()){
            battle.getArmyOne().remove(rangedUnit);
        }
        assertEquals(rangedUnitList, battle.getInfantryUnits());

    }
}
