package org.johakv.stud.ntnu.no;

import Old.controllers.FileController;
import Old.model.Army;
import Old.model.CavalryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class FileControllerTest {

    @Test
    void saveTournament() throws IOException {
        Army tournament = new Army("name");
        FileController fileController = new FileController();
        fileController.saveTournament(tournament);
        assertEquals(tournament, fileController.retriveTournament());
    }

    @Test
    void savecommanderUint() throws IOException {
        CavalryUnit tournament = new CavalryUnit("name",1);
        FileController fileController = new FileController();
        fileController.saveTournament(tournament);
        assertEquals(tournament, fileController.retriveTournament());
    }
}