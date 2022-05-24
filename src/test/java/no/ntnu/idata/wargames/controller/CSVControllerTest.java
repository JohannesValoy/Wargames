package no.ntnu.idata.wargames.controller;

import no.ntnu.idata.wargames.logic.controllers.CSVController;
import no.ntnu.idata.wargames.logic.warsimulation.Army;
import no.ntnu.idata.wargames.model.CavalryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVControllerTest {

    /**
     * Positive test for CSVController
     * <p>makeFile.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    void makeFile() throws Exception {
        CSVController savingArmy = new CSVController();
        Army armyOne = new Army("Army1");
        Army armyTwo = new Army("Army2");
        CavalryUnit unit = new CavalryUnit("CavalryUnitArmyOne", 1);
        CavalryUnit unit2 = new CavalryUnit("CavalryUnitArmyTwo", 2);
        armyOne.add(unit);
        armyTwo.add(unit2);

        savingArmy.saveArmy(armyOne,0, "ArmyOne");
        savingArmy.saveArmy(armyTwo,1, "ArmyTwo");

        Army retrievedArmyOne = savingArmy.retrieveArmy("ArmyOne");
        Army retrievedArmyTwo = savingArmy.retrieveArmy("ArmyTwo");


        assertEquals(armyOne.getRandom().getAttack(), retrievedArmyOne.getRandom().getAttack());
        assertEquals(armyOne.getRandom().getHealth(), retrievedArmyOne.getRandom().getHealth());
        assertEquals(armyTwo.getRandom().getAttack(), retrievedArmyTwo.getRandom().getAttack());
        assertEquals(armyTwo.getRandom().getHealth(), retrievedArmyTwo.getRandom().getHealth());
    }

}

