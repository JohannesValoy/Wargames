package no.ntnu.idatx2001.newsstand.ui.controllers;

import no.ntnu.idatx2001.newsstand.model.Army;
import no.ntnu.idatx2001.newsstand.model.Battle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * saves and retrieves a Tournament from a file.
 */

public class FileController {

    /**
     * Saves Tournament to file using stream
     */
    public FileController() throws IOException {
    }

    public void saveBattle(Object battle) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Battle.bin"))){
            objectOutputStream.writeObject(battle);
        } catch (IOException e){
            System.out.println("ObjectInputStream could not write the fileData");
        }
    }

    public Battle newBattle() throws IOException {
        Battle battle = new Battle(new Army("ArmyOne", new ArrayList<>()), new Army("ArmyTwo", new ArrayList<>()));
        saveBattle(battle);
        return battle;

    }
    public Battle retrieveBattle() throws IOException {
        Battle battle = null;

        try(FileInputStream fileInputStream = new FileInputStream("Battle.bin")){

            try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Battle.bin"))){

                Object object= objectInputStream.readObject();
                if(object.getClass().getSimpleName().equals("Battle")){
                battle = (Battle) object;

                } else {
                    throw new IllegalArgumentException("File is not Readable - " + object.getClass().getSimpleName());
                }

            } catch (IOException | ClassNotFoundException e){
                battle = newBattle();
            }

        } catch (IOException e) {
            battle = newBattle();
        }
        return battle;
    }
}
