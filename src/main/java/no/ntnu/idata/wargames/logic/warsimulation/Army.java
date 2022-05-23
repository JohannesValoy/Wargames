package no.ntnu.idata.wargames.logic.warsimulation;

import no.ntnu.idata.wargames.model.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Keeps a collection  of units in ArrayList.
 * Does not check for invalid entries.
 *
 * @author johan
 * @version $Id: $Id
 */
public class Army implements Serializable {
    private String name;
    private List<Unit> units;
    private Random random = new Random();

    /**
     * unused constructor, using name and list<Unit>.
     *
     * @param name String
     * @param units as Unit
     */
    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = new ArrayList<>(units);
    }


    /**
     * Initialises Army using name.
     *
     * @param name as String
     */
    public Army(String name) {
        this.name = name;
        this.units = new ArrayList<>();
    }

    /**
     * Returns name as String.
     *
     * @return name as String
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name as String.
     *
     * @param name as String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds unit to list
     *
     * @param unit as Unit
     */
    public void add(Unit unit) {

        this.units.add(unit);
    }

    /**
     * Sets list fo units as this.units.
     *
     * @param units as List
     */
    public void addAll(List<Unit> units) {
        this.units = units;
    }

    /**
     * Removes unit from units
     *
     * @param unit as unit
     */
    public void remove(Unit unit) {
        if(this.units.contains(unit)){
        this.units.remove(unit);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Returns amount of units as int.
     *
     * @return amount of units as int
     */
    public boolean hasUnits(){
        return !units.isEmpty();
    }


    /**
     * Returns List this.units.
     * not as iterator.
     *
     * @return units as list.
     */
    public List<Unit> getAllUnits() {
            return this.units;
    }

    /**
     * Returns random unit.
     *
     * @return random unit.
     */
    public Unit getRandom(){
        int randomNumber = this.random.nextInt(units.size());
        return units.get(randomNumber);
    }

    /**
     * {@inheritDoc}
     *
     * Tests if param object is same as this object.
     * Override equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    /**
     * {@inheritDoc}
     *
     * Return unique hash code of object using name and units.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }

    /**
     * Returns list of infantry units.
     *
     * @return list of infantry units;
     */
    public List<Unit> getInfantryUnits() {
        return units.stream()
                .filter(InfantryUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of cavalry units.
     *
     * @return list of cavalry units;
     */
    public List<Unit> getCavalryUnits() {
        return units.stream()
                .filter(CavalryUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of ranged units.
     *
     * @return list of ranged units;
     */
    public List<Unit> getRangedUnits() {
        return units.stream()
                .filter(RangedUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of commander units.
     *
     * @return list of commander units;
     */
    public List<Unit> getCommanderUnits() {
        return units.stream()
                .filter(CommanderUnit.class::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String toString() {
        String armyName = getName();
        StringBuilder armyAsString = new StringBuilder(armyName + "\n");
        for(Unit unit: units){
            armyAsString.append(unit.toString()).append("\n");
        }
        return armyAsString.toString();
    }

    /**
     * <p>newRandom.</p>
     */
    public void newRandom(){
        this.random = new Random();
    }
}
