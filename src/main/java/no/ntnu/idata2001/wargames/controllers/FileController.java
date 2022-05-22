package no.ntnu.idata2001.wargames.controllers;

import no.ntnu.idata2001.wargames.model.Army;
import no.ntnu.idata2001.wargames.model.Battle;

import java.io.*;
import java.util.ArrayList;

/**
 * saves and retrieves a Tournament from a file.
 *
 * @author johan
 * @version $Id: $Id
 */
public class FileController {

    /**
     * Saves Tournament to file using stream
     *
     * @throws java.io.IOException if any.
     */
    public FileController() throws IOException {
    }

    /**
     * <p>saveBattle.</p>
     *
     * @param battle a {@link java.lang.Object} object
     * @throws java.io.IOException if any.
     */
    public void saveBattle(Object battle) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Battle.bin"))){
            objectOutputStream.writeObject(battle);
        } catch (IOException e){
            System.out.println("ObjectInputStream could not write the fileData");
        }
    }

    /**
     * <p>newBattle.</p>
     *
     * @return a {@link no.ntnu.idata2001.wargames.model.Battle} object
     * @throws java.io.IOException if any.
     */
    public Battle newBattle() throws IOException {
        Battle battle = new Battle(new Army("ArmyOne", new ArrayList<>()), new Army("ArmyTwo", new ArrayList<>()));
        saveBattle(battle);
        return battle;

    }
    /**
     * <p>retrieveBattle.</p>
     *
     * @return a {@link no.ntnu.idata2001.wargames.model.Battle} object
     * @throws java.io.IOException if any.
     */
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
