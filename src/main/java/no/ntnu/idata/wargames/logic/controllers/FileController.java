package no.ntnu.idata.wargames.logic.controllers;

import no.ntnu.idata.wargames.logic.warsimulation.Battle;

import java.io.*;

/**
 * saves and retrieves a Tournament from a file using object-stream.
 * Object put in will exact same as put inn - even random-generators.
 *
 * @author Johannes Kolvik Valøy
 * @version 1.0
 */
public class FileController {

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
     * <p>retrieveBattle.</p>
     *
     * @return a {@link Battle} object
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
