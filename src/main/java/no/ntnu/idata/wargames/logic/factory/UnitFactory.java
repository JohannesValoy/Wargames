package no.ntnu.idata.wargames.logic.factory;

import no.ntnu.idata.wargames.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>UnitFactory class.</p>
 *
 * @author Johannes Kolvik Valøy
 * @version 1.0
 */
public class UnitFactory {
    private List<Unit> units = new ArrayList<>();

    /**
     * Imports units as list and saves them as list;
     * <p>importUnits.</p>
     *
     * @param units a {@link java.util.List} object
     */
    public void importUnits(List<Unit> units){
        this.units = units;
    }

    /**
     * Makes list of units using health as int, name as string, UnitType as string  and amount as n.
     * Trows IllegalArgumentException if UnitType is not Valid.
     * UniType has to be: 'CavalryUnit', 'CommanderUnit', 'InfantryUnit' or 'RangedUnit';
     *
     * @param health a int
     * @param name a {@link java.lang.String} object
     * @param unitType a {@link java.lang.String} object
     * @param n as int.
     * @throws java.io.IOException
     */
    public void addUnit(int health, String name, String unitType, int n) throws IOException {
        int i = 0;
        String newName = name;
        // checks if n is positive integer.
        if(n >= 0) {

            while (i < n) {

                //adds (1), (2), (3).... to the unit-names if there is added more than one unit at the time.
                if (n > 1) {
                    newName = name + "(" + (i + 1) + ")";
                }

                //Chooses unit-type to add from string unitType.
                switch (unitType) {
                    case "CavalryUnit" -> units.add(new CavalryUnit(newName, health));

                    case "CommanderUnit" -> units.add(new CommanderUnit(newName, health));

                    case "InfantryUnit" -> units.add(new InfantryUnit(newName, health));

                    case "RangedUnit" -> units.add(new RangedUnit(newName, health));

                    //Throws illegalArgumentException if the unit-type is invalid.
                    default -> throw new IllegalArgumentException("UnitType has to be 'CavalryUnit', 'CommanderUnit', 'InfantryUnit' or 'RangedUnit'");
                }
                i++;
            }
        } else {
            //Throws IllegalArgumentException if n is not more than 0
            throw new IllegalArgumentException("Amount of units has to be a positive integer");
        }

    }

    /**
     * <p>retrieveAllunits.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Unit> retrieveAllunits(){
        return this.units;
    }

    /**
     * <p>retrieveSpecificUnits.</p>
     * Retrieves specific units based on unit-type, name and health.
     * Unit-type has to be 'CavalryUnit', 'CommanderUnit', 'InfantryUnit' or 'RangedUnit'.
     * If multiple units are added to the register at once, they get (1),(2)... after the name. These will not show up in this search.
     *
     * @param unittype a {@link java.lang.String} object
     * @param name a {@link java.lang.String} object
     * @param health a int
     * @return a {@link java.util.List} object
     */
    public List<Unit> retrieveSpecificUnits(String unittype, String name, int health) {
        return units.stream()
                .filter(unit -> unit.getClass().getSimpleName().equals(unittype))
                .filter(unit -> unit.getName().equals(name))
                .filter(unit -> unit.getHealth() == health)
                .collect(Collectors.toList());

    }
    /**
     * Returns list of infantry units.
     *
     * @return list of infantry units;
     */
    public List<Unit> retrieveInfantryUnits() {
        return units.stream()
                .filter(InfantryUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of cavalry units.
     *
     * @return list of cavalry units;
     */
    public List<Unit> retrieveCavalryUnits() {
        return units.stream()
                .filter(CavalryUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of ranged units.
     *
     * @return list of ranged units;
     */
    public List<Unit> retrieveRangedUnits() {
        return units.stream()
                .filter(RangedUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of commander units.
     *
     * @return list of commander units;
     */
    public List<Unit> retrieveCommanderUnits() {
        return units.stream()
                .filter(CommanderUnit.class::isInstance)
                .collect(Collectors.toList());
    }

}
