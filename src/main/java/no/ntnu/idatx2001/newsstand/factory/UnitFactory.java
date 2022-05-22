package no.ntnu.idatx2001.newsstand.factory;

import no.ntnu.idatx2001.newsstand.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnitFactory {
    private List<Unit> units = new ArrayList<>();

    public void importUnits(List<Unit> units){
        this.units = units;
    }

    /**
     * Makes list of units using health as int, name as string, UnitType as string  and amount as n.
     * Trows IllegalArgumentException if UnitType is not Valid.
     * @param health
     * @param name
     * @param unitType
     * @param n
     * @throws IOException
     */
    public void addUnit(int health, String name, String unitType, int n) throws IOException {
        int i = 0;
        String newName = name;
        if(n >= 0) {

            while (i < n) {

                if (n > 1) {
                    newName = name + "(" + (i + 1) + ")";
                }

                switch (unitType) {
                    case "CavalryUnit" -> units.add(new CavalryUnit(newName, health));

                    case "CommanderUnit" -> units.add(new CommanderUnit(newName, health));

                    case "InfantryUnit" -> units.add(new InfantryUnit(newName, health));

                    case "RangedUnit" -> units.add(new RangedUnit(newName, health));

                    default -> throw new IllegalArgumentException("UnitType has to be 'CavalryUnit', 'CommanderUnit', 'InfantryUnit' or 'RangedUnit'");
                }
                i++;
            }
        } else {
            throw new IllegalArgumentException("amount of units has to be a positive integer");
        }

    }

    public List<Unit> retrieveAllunits(){
        return this.units;
    }

    public List<Unit> retrieveSpesificUnits(String unittype, String name, int health) {

        return units.stream()
                .filter(unit -> unit.getClass().getSimpleName().equals(unittype))
                .filter(unit -> unit.getName().equals(name))
                .filter(unit -> unit.getHealth() == health)
                .collect(Collectors.toList());

    }


    public List<Unit> retrieveSpesificUnitTypeUnits(String unitType) {

        return units.stream()
                .filter(unit -> unit.getClass().getSimpleName().equals(unitType))
                .collect(Collectors.toList());
    }

    public List<Unit> retrieveCavalryUnits() {

        return units.stream()
                .filter(unit -> unit.getClass().isInstance(new CavalryUnit("",1)))
                .collect(Collectors.toList());
    }

    public List<Unit> retrieveCommanderUnits() {
        return units.stream()
                .filter(unit -> unit.getClass().isInstance(new CommanderUnit("",1)))
                .collect(Collectors.toList());
    }

    public List<Unit> retrieveInfantryUnits() {
        return units.stream()
                .filter(unit -> unit.getClass().isInstance(new InfantryUnit("",1)))
                .collect(Collectors.toList());
    }

    public List<Unit> retrieveRangedUnits() {
        return units.stream()
                .filter(unit -> unit.getClass().isInstance(new RangedUnit("",1)))
                .collect(Collectors.toList());
    }
}
