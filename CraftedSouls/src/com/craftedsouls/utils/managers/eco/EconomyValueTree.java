package com.craftedsouls.utils.managers.eco;

public class EconomyValueTree {
    public int copper;
    public int silver;
    public int gold;

    public EconomyValueTree(int copper, int silver, int gold) {
        this.copper = copper;
        this.silver = silver;
        this.gold = gold;
    }

    public EconomyValueTree(int copper) {
        this.copper = copper;
    }

    //Done
    public void SimplifyValues() {
        copper = copper + silver * 100 + gold * 10000;

        int knownCopper = copper;
        int knownSilver = copper / 100;
        int knownGold = copper / 10000;

        copper = knownCopper - (knownSilver * 100);
        silver = knownSilver - (knownGold * 100);
        gold = knownGold;
    }

}
