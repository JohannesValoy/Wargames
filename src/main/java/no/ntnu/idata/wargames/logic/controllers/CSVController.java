package no.ntnu.idata.wargames.logic.controllers;

import no.ntnu.idata.wargames.logic.factory.UnitFactory;
import no.ntnu.idata.wargames.logic.warsimulation.Army;
import no.ntnu.idata.wargames.model.Unit;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * Responsible for saving and retrieving army in a .csv file. Throws IOException.
 *
 * @author johan
 * @version $Id: $Id
 */
public class CSVController {

    /**
     * Empty constructor.
     */
    public CSVController(){
        // Empty because all the data needed are requested in the methode parameters.
    }

    /**
     * Saves army in .csv file using army object and number 0 or 1 for armyOne and armyTwo in a battle. throws IOException if printing does not work.
     *
     * @param army as Army.
     * @param armyNumber as int. 0 or 1, the two competing armies.
     * @param filename a {@link java.lang.String} object
     */
    public void saveArmy(Army army, int armyNumber, String filename) throws IOException {
        try {
            if(armyNumber < 2 && armyNumber>= 0) {
                File file = new File(filename);
                if(!file.createNewFile()){
                    throw new FileAlreadyExistsException("File Already Exists");
                }
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                printWriter.write(army.toString());
                printWriter.flush();
                printWriter.close();
            }
        } catch(IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Retrieves army from .csv file using number of army 0 or 1. Throws IOException.
     * Limited to 999 units to stop it from overloading some computers.
     *
     * @param filename as int.
     * @return army As Army.
     */
    public Army retrieveArmy(String filename) throws IOException {
        String line;
        Army army = null;
        int i = 0;
        UnitFactory unitFactory = new UnitFactory();
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
                String firstLine = reader.readLine();
                if(!firstLine.isEmpty()) {
                String armyName = firstLine;
                    army = new Army(armyName);
                } else {
                    throw new IOException("Empty army-name error");
                }

                    while ((line = reader.readLine()) != null) {
                        String[] text = line.split(",");
                        String unitType = text[0];
                        String name = text[1];
                        int health = 0;
                        try {
                            health = Integer.parseInt(text[2].replace(" ", ""));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new IOException("Health invalid");
                        }
                        if (text[0].isEmpty() || text[1].isEmpty() || text[2] == null) {
                            throw new IOException("File is Corrupted");
                        } else {
                            try{ unitFactory.addUnit(health, name, unitType, 1);
                            } catch (IllegalArgumentException e){
                                throw new IOException(e.getMessage());
                            }

                            i++;
                        }
                        //
                        if(i > 999){
                            throw new IOException("File has More than 999 units");
                        }

                    }
                } catch (IOException e){
                throw new IOException(e.getMessage());
            }
        for (Unit unit : unitFactory.retrieveAllunits()) {
            army.add(unit);
        }
        return army;
    }
}

