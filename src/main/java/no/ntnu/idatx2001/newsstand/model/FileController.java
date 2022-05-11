package no.ntnu.idatx2001.newsstand.model;

import java.io.*;
import java.util.ArrayList;

/**
 * saves and retrieves a Tournament from a file.
 */

public class FileController {

    /**
     * Saves Tournament to file using stream
     * @throws FileNotFoundException
     */
    public FileController() throws IOException {

    }

    public void saveBattle(Object battle) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Battle.bin"))){
            objectOutputStream.writeObject(battle);
        } catch (IOException e){e.printStackTrace();}
    }

    public Battle retriveBattle() throws IOException {
        Battle battle = null;
        try(FileInputStream fileInputStream = new FileInputStream("Battle.bin")){

            try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Battle.bin"))){
                battle = (Battle) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e){e.printStackTrace();}

        } catch (IOException e) {
            saveBattle(new Battle(new Army("ArmyOne", new ArrayList<>()), new Army("ArmyTwo", new ArrayList<>())));
        }

        return battle;
    }
}
