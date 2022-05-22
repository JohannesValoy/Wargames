package no.ntnu.idata2001.wargames.controller;

import no.ntnu.idata2001.wargames.controllers.FileController;
import no.ntnu.idata2001.wargames.model.Army;
import no.ntnu.idata2001.wargames.model.Battle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class FileControllerTest {

    /**
     * <p>saveBattle.</p>
     *
     * @throws java.io.IOException if any.
     */
    @Test
    public void saveBattle() throws IOException {
        Battle battle = new Battle(new Army("armyOne"), new Army("armyTwo"));
        FileController fileController = new FileController();
        fileController.saveBattle(battle);
        assertEquals(battle.getClass(), fileController.retrieveBattle().getClass());

        Army army = new Army("armyOne");
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
