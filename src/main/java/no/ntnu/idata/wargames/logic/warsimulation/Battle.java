package no.ntnu.idata.wargames.logic.warsimulation;

import no.ntnu.idata.wargames.model.Unit;

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
    private Random isArmyOneAttacker;

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
                defender = armyTwo;
            }
            else {
                attacker = armyTwo;
                defender = armyOne;
            }

            Unit defendingUnit = defender.getRandom();
            Unit attackingUnit = attacker.getRandom();
            attackingUnit.attack(defendingUnit);

            if (defendingUnit.getHealth() < 1){
                defender.remove((defendingUnit));
            }


        }

        //Sets the winner when one army is out of units.
        if (armyOne.hasUnits()){
            winner = armyOne;
        } else {
            winner = armyTwo;
        }
        return winner;

    }

    public void newRandom(){
        this.isArmyOneAttacker = new Random();
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
     * @return ArmyOne as Army.
     */
    public Army getArmyOne() {
        return armyOne;
    }

    /**
     * returns ArmyTwo as Army.
     *
     * @return ArmyTwo as Army.
     */
    public Army getArmyTwo() {
        return armyTwo;
    }

    /**
     * Returns RangedUnits.
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
     * Returns list of InfantryUnits.
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
     * Returns list of CavalryUnits.
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
