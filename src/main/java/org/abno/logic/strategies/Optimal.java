package org.abno.logic.strategies;

public class Optimal implements IStrategy {
    @Override
    public int[] executeStrategy(int[][] fighters, int selectedFighter) {
        int numTypes = fighters[0].length; //en teoría es 10 siempre
        int[] attackArray = new int[numTypes];

        for (int i = 0; i < numTypes; i++) {
            for (int[] fighter : fighters) {
                attackArray[i] = Math.max(attackArray[i], fighter[i]);
            }
        }
        return attackArray;
    }
}
