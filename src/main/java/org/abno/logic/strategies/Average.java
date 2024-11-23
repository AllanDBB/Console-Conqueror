package org.abno.logic.strategies;

public class Average implements IStrategy {
    @Override
    public int[] executeStrategy(int[][] fighters, int selectedFighter) {
        int numFighters = fighters.length;
        int numTypes = fighters[0].length;
        int[] attackArray = new int[numTypes];

        for (int i = 0; i < numTypes; i++) {
            int sum = 0;
            for (int[] fighter : fighters) {
                sum += fighter[i];
            }
            attackArray[i] = sum / numFighters;
        }
        return attackArray;
    }
}
