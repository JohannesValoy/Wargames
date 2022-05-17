package no.ntnu.idatx2001.newsstand.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveArmyToFileTest {

    @Test
    void makeFile() throws Exception {
        SaveToFileRefactored savingArmy = new SaveToFileRefactored();
        Army army = new Army("Army");
        CavalryUnit unit = new CavalryUnit("Name", 1);
        army.add(unit);
        savingArmy.saveArmy(army,0, "ArmyOne");
        Army army2 = new Army("Army2");
        CavalryUnit unit2 = new CavalryUnit("Name", 2);
        army2.add(unit2);
        savingArmy.saveArmy(army2,1, "ArmyTwo");
        ArrayList armies = new ArrayList();
        Army retrievedArmy = savingArmy.retrieveArmy("ArmyTwo");
        assertEquals(army.getRandom().getAttack(), retrievedArmy.getRandom().getAttack());
        assertEquals(retrievedArmy.getRandom().getHealth(), army.getRandom().getHealth());
    }

}

