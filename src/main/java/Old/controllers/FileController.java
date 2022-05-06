package Old.controllers;

import java.io.*;

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

    public void saveTournament(Object tournament) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Tournament.bin"))){
            objectOutputStream.writeObject(tournament);
        } catch (IOException e){e.printStackTrace();}
    }

    public Object retriveTournament(){
        Object tournament = null;
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Tournament.bin"))){
            tournament = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){e.printStackTrace();}
        return tournament;
    }
}
