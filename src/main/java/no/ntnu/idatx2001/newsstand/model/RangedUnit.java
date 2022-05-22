package no.ntnu.idatx2001.newsstand.model;

/**
 * Represents a range unit based on unit.
 */

public class RangedUnit extends Unit{
    private int attacks;

    /**
     * Initiates Customized RangeUnit.
     * sets Attack = 0
     * @param name as string
     * @param health int
     * @param attack int
     * @param armor int
     */

    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
        this.attacks = 0;
    }

    /**
     * Initiates RangeUnit using default values.
     * sets Attack = 0
     * @param name String
     * @param health int
     */
    public RangedUnit(String name, int health) {
        super(name, health, 15, 8);
        this.attacks = 0;
    }

    /**
     * overrides Unit methode
     * returns attackBonus as int.
     * @return attackBonus as int
     */
    @Override
    public int getAttackBonus(boolean editable) {;
        return attackBonus + 3;
    }

    /**
     * overrides Unit methode
     * returns resistBonus as int.
     * @return resistBonus as int
     */
    @Override
    public int getResistBonus() {
        int resistBonus = 0;
        if(attacks == 0){
            resistBonus = this.resistBonus + 6;
        } else {
            resistBonus = this.resistBonus + 4;
        }
        return resistBonus;
    }

    /**
     * sets health using int. +1 attack everytime health is changed.
     * @param health as int.
     */
    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        attacks ++;
    }
}
