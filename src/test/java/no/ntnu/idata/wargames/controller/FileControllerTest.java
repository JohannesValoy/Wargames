package no.ntnu.idata.wargames.controller;

import no.ntnu.idata.wargames.logic.controllers.FileController;
import no.ntnu.idata.wargames.logic.warsimulation.Army;
import no.ntnu.idata.wargames.logic.warsimulation.Battle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class FileControllerTest {

    /**
     * Positive and negative test for FileController.
     * <p>saveBattle.</p>
     *
     * @throws java.io.IOException if any.
     */
    @Test
    public void saveBattle() throws IOException {
        FileController fileController = new FileController();
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        Army army = new Army("armyOne");

        fileController.saveBattle(battle);

        assertEquals(battle.getClass(), fileController.retrieveBattle().getClass());
        //negative test.
        boolean wrongClassAccepted = true;
        try {
            fileController.saveBattle(army);
            assertEquals(army.getClass(), fileController.retrieveBattle().getClass());
        } catch (IllegalArgumentException e){
            wrongClassAccepted = false;
        }
        assertFalse(wrongClassAccepted);
    }
}
