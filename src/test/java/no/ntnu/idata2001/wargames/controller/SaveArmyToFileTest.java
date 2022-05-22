package no.ntnu.idata2001.wargames.controller;

import no.ntnu.idata2001.wargames.controllers.CSVController;
import no.ntnu.idata2001.wargames.model.Army;
import no.ntnu.idata2001.wargames.model.CavalryUnit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveArmyToFileTest {

    /**
     * <p>makeFile.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @Test
    public void makeFile() throws Exception {
        CSVController savingArmy = new CSVController();

        Army armyOne = new Army("Army1");
        CavalryUnit unit = new CavalryUnit("CavalryUnitArmyOne", 1);
        armyOne.add(unit);
        savingArmy.saveArmy(armyOne,0, "ArmyOne");
        Army armyTwo = new Army("Army2");
        CavalryUnit unit2 = new CavalryUnit("CavalryUnitArmyTwo", 2);
        armyTwo.add(unit2);
        savingArmy.saveArmy(armyTwo,1, "ArmyTwo");
        ArrayList armies = new ArrayList();

        Army retrievedArmyOne = savingArmy.retrieveArmy("ArmyOne");
        assertEquals(armyOne.getRandom().getAttack(), retrievedArmyOne.getRandom().getAttack());
        assertEquals(armyOne.getRandom().getHealth(), retrievedArmyOne.getRandom().getHealth());
        Army retrievedArmyTwo = savingArmy.retrieveArmy("ArmyTwo");
        assertEquals(armyTwo.getRandom().getAttack(), retrievedArmyTwo.getRandom().getAttack());
        assertEquals(armyTwo.getRandom().getHealth(), retrievedArmyTwo.getRandom().getHealth());
    }

}

