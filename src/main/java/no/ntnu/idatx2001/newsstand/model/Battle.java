package no.ntnu.idatx2001.newsstand.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Responsible for simulating a battle between armyOne and ArmyTwo.
 * @author Johannes Valøy
 */

public class Battle implements Serializable{
    private Army armyOne;
    private Army armyTwo;
    private Random isArmyOneAttacker;

    /**
     * Creates instance of Battle
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
            attacker.getRandom().attack(defendingUnit);

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
     * returns details of army as string. Overrides toString
     * @return details of army as string
     */

    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }

    public Army getArmyOne() {
        return armyOne;
    }

    public Army getArmyTwo() {
        return armyTwo;
    }
}
