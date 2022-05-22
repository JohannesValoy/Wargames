package no.ntnu.idatx2001.newsstand.model;

import no.ntnu.idatx2001.newsstand.ui.controllers.FileController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class FileControllerTest {

    @Test
    void saveTournament() throws IOException {
        Army army = new Army("name");
        FileController fileController = new FileController();
        fileController.saveBattle(army);
        assertEquals(army.getClass(), fileController.retrieveBattle().getClass());
    }

    @Test
    void saveCommanderUnit() throws IOException {
        CavalryUnit tournament = new CavalryUnit("Name",1);
        FileController fileController = new FileController();
        fileController.saveBattle(tournament);
        assertEquals(tournament.getClass(), fileController.retrieveBattle().getClass());
    }
}