package no.ntnu.idata2001.wargames.controllers;

import no.ntnu.idata2001.wargames.factory.UnitFactory;
import no.ntnu.idata2001.wargames.model.Army;
import no.ntnu.idata2001.wargames.model.Unit;

import java.io.*;
import java.nio.file.Files;

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
    public void saveArmy(Army army, int armyNumber, String filename){
        try {
            if(armyNumber < 2 && armyNumber>= 0) {
                File file = new File(filename);
                if(!file.createNewFile()){
                    Files.delete(file.toPath());
                    file = new File(filename);
                }
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                printWriter.write(army.toString());
                printWriter.flush();
                printWriter.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves army from .csv file using number of army 0 or 1. Throws IOException.
     * Limited to 999 units to stop it from overloading some computers.
     *
     * @param filename as int.
     * @return army As Army.
     */
    public Army retrieveArmy(String filename) {
        String line;
        Army army = null;
        int i = 1;
        UnitFactory unitFactory = new UnitFactory();
            try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
            {
                String armyName = reader.readLine();
                army = new Army(armyName);

                while ((line = reader.readLine()) != null && i < 1000) {
                    String[] text = line.split(",");
                    String unitType = text[0];
                    String name = text[1];
                    int health = Integer.parseInt(text[2].replace(" ", ""));
                    unitFactory.addUnit(health, name, unitType, 1);
                    i++;
                }
            }
            catch (IOException e){army = new Army("ArmyOne");}
        for (Unit unit : unitFactory.retrieveAllunits()) {
            assert army != null;
            army.add(unit);
        }
        return army;
    }
}

