package no.ntnu.idata2001.wargames.controller;

import no.ntnu.idata2001.wargames.controllers.CSVController;
import no.ntnu.idata2001.wargames.model.Army;
import no.ntnu.idata2001.wargames.model.CavalryUnit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVControllerTest {

    /**
     * <p>makeFile.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    public void makeFile() throws Exception {
        CSVController savingArmy = new CSVController();
        Army armyOne = new Army("Army1");
        Army armyTwo = new Army("Army2");
        CavalryUnit unit = new CavalryUnit("CavalryUnitArmyOne", 1);
        CavalryUnit unit2 = new CavalryUnit("CavalryUnitArmyTwo", 2);
        armyOne.add(unit);
        armyTwo.add(unit2);

        Army retrievedArmyOne = savingArmy.retrieveArmy("ArmyOne");
        Army retrievedArmyTwo = savingArmy.retrieveArmy("ArmyTwo");

        savingArmy.saveArmy(armyOne,0, "ArmyOne");
        savingArmy.saveArmy(armyTwo,1, "ArmyTwo");

        assertEquals(armyOne.getRandom().getAttack(), retrievedArmyOne.getRandom().getAttack());
        assertEquals(armyOne.getRandom().getHealth(), retrievedArmyOne.getRandom().getHealth());
        assertEquals(armyTwo.getRandom().getAttack(), retrievedArmyTwo.getRandom().getAttack());
        assertEquals(armyTwo.getRandom().getHealth(), retrievedArmyTwo.getRandom().getHealth());
    }

}

