package org.johakv.stud.ntnu.no;

import Old.model.Army;
import Old.model.CavalryUnit;
import Old.model.SaveToFileRefactored;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveArmyToFileTest {

    @Test
    void makeFile() throws IOException {
        SaveToFileRefactored savingArmy = new SaveToFileRefactored();
        Army army = new Army("Army");
        CavalryUnit unit = new CavalryUnit("Name", 1);
        army.add(unit);
        savingArmy.saveArmy(army,0);
        Army army2 = new Army("Army2");
        CavalryUnit unit2 = new CavalryUnit("Name", 2);
        army2.add(unit2);
        savingArmy.saveArmy(army2,1);
        ArrayList armies = new ArrayList();
        Army retrievedArmy = savingArmy.retrieveArmy(0);
        assertEquals(army.getRandom().getAttack(), retrievedArmy.getRandom().getAttack());
        assertEquals(retrievedArmy.getRandom().getHealth(), army.getRandom().getHealth());
    }

}

