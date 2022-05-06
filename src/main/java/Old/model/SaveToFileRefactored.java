package Old.model;

import java.io.*;
import java.util.Iterator;

/**
 * Responsible for saving and retrieving army in a .csv file. Throws IOException.
 */
public class SaveToFileRefactored {

    /**
     * Empty constructor.
     */
    public SaveToFileRefactored(){
        // Empty because all the data needed are requested in the methode parameters.
    }

    /**
     * Saves army in .csv file using army object and number 0 or 1 for armyOne and armyTwo in a battle. throws IOException if printing does not work. 
     * @param army as Army.
     * @param armyNumber as int. 0 or 1, the two competing armies.
     */
    public void saveArmy(Army army, int armyNumber){
        try {
            if(armyNumber < 2 && armyNumber>= 0) {
                String filename = "army" + armyNumber + ".csv";
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
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
     * @param armyNumber as int.
     * @return army As Army. 
     * @throws IOException if it can not find file or file content is corrupted.
     */

    public Army retrieveArmy(int armyNumber) throws IOException{
        String line;
        Army army = null;
        UnitFactory unitFactory = new UnitFactory();

        if(armyNumber < 2 && armyNumber >= 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader("army" + armyNumber + ".csv")))
            {
                String armyName = reader.readLine();
                army = new Army(armyName);

                while ((line = reader.readLine()) != null) {
                    String[] text = line.split(",");
                    String unitType = text[0];
                    String name = text[1];
                    int health = Integer.parseInt(text[2].replace(" ", ""));
                    unitFactory.addUnit(health, name, unitType, 1);
                }
            }
            catch (IOException e){e.printStackTrace();}
        }
        for (Iterator<Unit> it = unitFactory.retrieveUnits().iterator(); it.hasNext(); ) {
            Unit unit = it.next();
            army.add(unit);
        }
        return army;
    }
}

