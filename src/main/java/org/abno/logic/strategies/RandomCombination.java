package org.abno.logic.strategies;

import java.util.Random;

public class RandomCombination implements IStrategy {
    private Random random = new Random();

    @Override
    public int[] executeStrategy(int[][] fighters, int selectedFighter) {
        int[] attackArray = fighters[selectedFighter].clone();
        int randomFighter;
        do {
            randomFighter = random.nextInt(fighters.length);
        } while (randomFighter == selectedFighter);

        for (int i = 0; i < attackArray.length; i++) {
            attackArray[i] = Math.max(attackArray[i], fighters[randomFighter][i]);
        }
        return attackArray;
    }
}
