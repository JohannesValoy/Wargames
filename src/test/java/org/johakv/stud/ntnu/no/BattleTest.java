package org.johakv.stud.ntnu.no;

import Old.model.Army;
import Old.model.Battle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void getName() {
        Army army = new Army("ArmyOne");
        Army army2 = new Army("ArmyTwo");
        Battle battle = new Battle(army,army2);
        assertEquals(army, battle.getArmyOne());
        assertEquals(army2, battle.getArmyTwo());
    }

    /**
     * tests simulate using 7 different type of units. Tests when armyOne wins and looses.
     */

   /* @Test
    void simulate() {
        CommanderUnit unit = new CommanderUnit("name", 2);
        CommanderUnit unit2 = new CommanderUnit("name", 2);
        CavalryUnit unit3 = new CavalryUnit("name", 2);
        InfantryUnit unit4 = new InfantryUnit("name", 2);
        CommanderUnit unit5 = new CommanderUnit("name", 2);
        RangeUnit unit6 = new RangeUnit("name", 2);
        CommanderUnit unit7 = new CommanderUnit("name", 1);


        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");

        armyOne.add(unit);
        armyTwo.add(unit2);
        armyTwo.add(unit3);
        armyTwo.add(unit4);
        armyTwo.add(unit5);
        armyTwo.add(unit6);
        armyTwo.add(unit7);

        Battle simulation = new Battle(armyOne, armyTwo);
        assertEquals(armyTwo, simulation.simulate());
        Army armyThree = new Army("ArmyThree");

        armyOne.add(unit);
        armyOne.add(unit2);
        armyOne.add(unit3);
        armyOne.add(unit4);
        armyThree.add(unit7);

        Battle OppositeSimulation = new Battle(armyOne, armyThree);
        assertEquals(armyOne, OppositeSimulation.simulate());

    }*/




}