package org.abno.logic.strategies;

public class BestCombination implements IStrategy {
    @Override
    public int[] executeStrategy(int[][] fighters, int selectedFighter) {
        int[] attackArray = fighters[selectedFighter].clone();

        for (int i = 0; i < attackArray.length; i++) {
            for (int[] fighter : fighters) {
                attackArray[i] = Math.max(attackArray[i], fighter[i]);
            }
        }
        return attackArray;
    }
}
