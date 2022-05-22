package no.ntnu.idata2001.wargames.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Responsible for simulating a battle between armyOne and ArmyTwo.
 *
 * @author Johannes Valøy
 * @version $Id: $Id
 */
public class Battle implements Serializable{
    private Army armyOne;
    private Army armyTwo;
    private final Random isArmyOneAttacker;

    /**
     * Creates instance of Battle
     *
     * @param armyOne as army
     * @param armyTwo as army
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.isArmyOneAttacker = new Random();

    }

    /**
     * Simulates battle between two armies,
     * using random units from each army until one army is out of units.
     * Then it returns victory army.
     * Nothing is random, if same units, then same outcome every time.
     *
     * @return army
     */
    public Army simulate() {
        Army winner;
        Army attacker = null;
        Army defender = null;
        while (armyOne.hasUnits() && armyTwo.hasUnits())
        {
            if (isArmyOneAttacker.nextBoolean()) {
                attacker = armyOne;
                System.out.println("attacker is armyOne");
                defender = armyTwo;
            }
            else {
                attacker = armyTwo;
                System.out.println("attacker is armyTwo");
                defender = armyOne;
            }

            Unit defendingUnit = defender.getRandom();
            System.out.println("defender = " + defendingUnit.getName());
            Unit attackingUnit = attacker.getRandom();
            attackingUnit.attack(defendingUnit);
            System.out.println("attacker = " + attackingUnit.getName());

            if (defendingUnit.getHealth() < 1){
                defender.remove((defendingUnit));
            }


        }

        if (armyOne.hasUnits()){
            winner = armyOne;
        } else {
            winner = armyTwo;
        }
        return winner;

    }

    /**
     * {@inheritDoc}
     *
     * returns details of army as string. Overrides toString
     */

    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }

    /**
     * returns ArmyOne as Army.
     *
     * @return ArmyOne as Army
     */
    public Army getArmyOne() {
        return armyOne;
    }

    /**
     * returns ArmyTwo as Army.
     *
     * @return ArmyTwo as Army
     */
    public Army getArmyTwo() {
        return armyTwo;
    }

    /**
     * <p>getAllUnits.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Unit> getAllUnits(){
        List<Unit> units = new ArrayList<>(getArmyOne().getAllUnits());
        units.addAll(getArmyTwo().getAllUnits());
        return units;
    }

    /**
     * <p>getRangeUnits.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Unit> getRangeUnits(){
        List<Unit> rangedUnits = new ArrayList<>(getArmyOne().getRangedUnits());
        rangedUnits.addAll(getArmyTwo().getRangedUnits());
        return rangedUnits;
    }

    /**
     * <p>getInfantryUnits.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Unit> getInfantryUnits(){
        List<Unit> infantryUnits = new ArrayList<>(getArmyOne().getInfantryUnits());
        infantryUnits.addAll(getArmyTwo().getInfantryUnits());
        return infantryUnits;
    }

    /**
     * <p>getCavalryUnits.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Unit> getCavalryUnits(){
        List<Unit> rangedUnits = new ArrayList<>(getArmyOne().getCavalryUnits());
        rangedUnits.addAll(getArmyTwo().getCavalryUnits());
        return rangedUnits;
    }

}
