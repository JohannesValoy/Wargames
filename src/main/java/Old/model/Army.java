package Old.model;

import java.io.Serializable;
import java.util.*;

/**
 * Keeps a collection  of units in ArrayList.
 * Does not check for invalid entries.
 */

public class Army implements Serializable {
    private String name;
    private List<Unit> units;
    private Random random = new Random();

    /**
     * unused constructor, using name and list<Unit>.
     * @param name String
     * @param units as Unit
     */

    public Army(String name, List<Unit> units) {

        this.name = name;
        this.units = new ArrayList<>(units);
    }

    /**
     * Initialises Army using name.
     * @param name as String
     */

    public Army(String name) {

        this.name = name;
        this.units = new ArrayList<>();
    }

    /**
     * Returns name as String.
     * @return name as String
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name as String.
     * @param name as String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds unit to list
     * @param unit as Unit
     */
    public void add(Unit unit) {

        this.units.add(unit);
    }

    /**
     * Sets list fo units as this.units.
     * @param units as List
     */

    public void addAll(List<Unit> units) {

        this.units = units;
    }

    /**
     * Removes unit from units
     * @param unit as unit
     */

    public void remove(Unit unit) {

        this.units.remove(unit);
    }

    /**
     * Returns amount of units as int.
     * @return amount of units as int
     */
    public boolean hasUnits(){
        return !units.isEmpty();
    }


    /**
     * Returns List this.units.
     * not as iterator.
     * @return units as list.
     */
    public List<Unit> getAllUnits() {
            return this.units;
    }

    public Iterator getIterator(){
        return units.iterator();
    }

    /**
     * Returns random unit.
     * @return random unit.
     */

    public Unit getRandom(){
        int randomNumber = this.random.nextInt(units.size());
        return units.get(randomNumber);
    }

    /**
     * Tests if param object is same as this object.
     * Override equals.
     * @param o as army
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    /**
     * Return unique hash code of object using name and units.
     * @return unique hash code of object using name and units.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }

    /**
     * Returns list of infantry units.
     * @return list of infantry units;
     */
    public List<Unit> getInfantryUnits() {
        ArrayList<Unit> unitList = new ArrayList<>();
        units
                .stream()
                .filter(unit -> unit instanceof InfantryUnit)
                .forEach(unitList::add);
        return unitList;
    }

    /**
     * Returns list of cavalry units.
     * @return list of cavalry units;
     */
    public List<Unit> getCavalryUnits() {
        ArrayList<Unit> unitList = new ArrayList<>();
        units
                .stream()
                .filter(unit -> unit instanceof CavalryUnit)
                .forEach(unitList::add);
        return unitList;
    }

    /**
     * Returns list of ranged units.
     * @return list of ranged units;
     */
    public List<Unit> getRangedUnits() {
        ArrayList<Unit> unitList = new ArrayList<>();
        units
                .stream()
                .filter(unit -> unit instanceof RangeUnit)
                .forEach(unitList::add);
        return unitList;
    }

    /**
     * Returns list of commander units.
     * @return list of commander units;
     */
    public List<Unit> getCommanderUnits() {
        ArrayList<Unit> unitList = new ArrayList<>();
        units
                .stream()
                .filter(unit -> unit instanceof CommanderUnit)
                .forEach(unitList::add);
        return unitList;
    }


    @Override
    public String toString() {
        String armyName = getName();
        String armyAsString = armyName + "\n";
        for(Unit unit: units){
            armyAsString = armyAsString + unit.toString() + "\n";
        }
        return  armyAsString;
    }
}
