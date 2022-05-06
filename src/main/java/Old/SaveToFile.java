package Old;

import Old.model.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class SaveToFile {

    public SaveToFile() {

    }

    public void makeFile(String text, String filename){
        try {
                int i = 0;
                boolean searching = true;
                while(searching) {
                    File myObj = new File(filename + ".csv");
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                        printToFile(text, filename);
                        searching = false;
                    } else {
                        System.out.println(filename);
                        System.out.println("File already exists.");
                        filename = filename + i;
                        i++;
                    }
                }

            } catch(IOException e){
                e.printStackTrace();
        }
    }

    public void saveArmy(ArrayList<Army> armyList) throws IOException {

        String armyAsString = "";
        int n = 0;
        String armyName = "";
        for (Army army : armyList) {
            armyName = armyList.get(n).getName();
            armyAsString =  armyName + "\n";
            Iterator it = armyList.get(n).getIterator();
            while (it.hasNext()) {
                armyAsString = armyAsString + it.next().toString() + "\n";
            }
        }
        makeFile(armyAsString, armyName);

    }
    public void printToFile(String text, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName + ".csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(text + "\n");
        writer.close();
    }

    public Army readFile(int armyNumber) throws IOException {
        String line;

        //try {
            BufferedReader br = new BufferedReader(new FileReader("army" + armyNumber + "txt"));
            Army army = new Army(br.readLine());

            while( (line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] string = line.split(",");
                String unitType = string[0];
                String name = string[1];
                int health = Integer.parseInt(string[2].replaceAll(" ", ""));

                switch(unitType) {
                    case "CavalryUnit":
                        CavalryUnit cavalryUnit = new CavalryUnit(name, health);
                        army.add(cavalryUnit);

                    case "CommanderUnit":
                        CommanderUnit commanderUnit = new CommanderUnit(name, health);
                        army.add(commanderUnit);

                    case "InfantryUnit":
                        InfantryUnit infantryUnit = new InfantryUnit(name, health);
                        army.add(infantryUnit);

                    case "RangeUnit":
                        RangeUnit rangeUnit = new RangeUnit(name, health);
                        army.add(rangeUnit);
                }
            }
        br.close();
        //} catch (IOException e){e.printStackTrace();}
        return army;
    }
}



