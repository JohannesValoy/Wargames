package no.ntnu.idatx2001.newsstand.model;

import java.io.IOException;
import java.util.ArrayList;

public class UnitFactory {
    private ArrayList<Unit> units = new ArrayList<Unit>();


    public void addUnit(int health, String name, String unitType, int n) throws IOException {
        int i = 0;
        String newName = name;
        while(i<n){
            if(n>1){
                newName = name + "(" + (i+1) + ")";
            }
            switch (unitType) {
                case "CavalryUnit" -> {
                    CavalryUnit cavalryUnit = new CavalryUnit(newName, health, 20, 12);
                    units.add(cavalryUnit);
                }
                case "CommanderUnit" -> {
                    CommanderUnit commanderUnit = new CommanderUnit(newName, health, 25, 15);
                    units.add(commanderUnit);
                }
                case "InfantryUnit" -> {
                    InfantryUnit infantryUnit = new InfantryUnit(newName, health, 15, 10);
                    units.add(infantryUnit);
                }
                case "RangedUnit" -> {
                    RangedUnit rangedUnit = new RangedUnit(newName  , health, 15, 8);
                    units.add(rangedUnit);
                }
                default -> throw new IOException();
            }
            i++;
        }
    }

    public ArrayList<Unit> retrieveUnits(){
        return this.units;
    }
}
