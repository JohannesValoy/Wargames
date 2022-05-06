package Old.model;

import java.io.IOException;
import java.util.ArrayList;

public class UnitFactory {
    private ArrayList<Unit> units = new ArrayList<Unit>();


    public void addUnit(int health, String name, String unitType, int n) throws IOException {
        int i = 0;
        while(i<=n){
            switch (unitType) {
                case "CavalryUnit" -> {
                    CavalryUnit cavalryUnit = new CavalryUnit(name, health, 20, 12);
                    units.add(cavalryUnit);
                }
                case "CommanderUnit" -> {
                    CommanderUnit commanderUnit = new CommanderUnit(name, health, 25, 15);
                    units.add(commanderUnit);
                }
                case "InfantryUnit" -> {
                    InfantryUnit infantryUnit = new InfantryUnit(name, health, 15, 10);
                    units.add(infantryUnit);
                }
                case "RangeUnit" -> {
                    RangeUnit rangeUnit = new RangeUnit(name, health, 15, 8);
                    units.add(rangeUnit);
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
