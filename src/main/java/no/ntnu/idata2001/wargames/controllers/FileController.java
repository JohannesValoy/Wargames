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
            throw new IOException("could not write to file");
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
                    if(battle.getArmyOne() == null){
                        throw new IOException("ArmyOne file is corrupted");
                    }
                    if(battle.getArmyTwo() == null){
                        throw new IOException("ArmyTwo file is corrupted");
                    }

                } else {
                    throw new ClassNotFoundException("File is not Readable - the class in file is wrong - " + object.getClass().getSimpleName());
                }

            } catch (IOException | ClassNotFoundException e){
                throw new IOException("File Corrupted");
            }

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return battle;
    }
}
