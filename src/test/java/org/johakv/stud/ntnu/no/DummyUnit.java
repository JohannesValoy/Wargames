package org.johakv.stud.ntnu.no;

import Old.model.Unit;

public class DummyUnit extends Unit {
    /**
     * Initiates Unit using name, health, attack and armor.
     *
     * @param name   as string
     * @param health int
     * @param attack int
     * @param armor  int
     */
    protected DummyUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    @Override
    public int getAttackBonus() {
        return 0;
    }

    @Override
    public int getResistBonus() {
        return 0;
    }

}
