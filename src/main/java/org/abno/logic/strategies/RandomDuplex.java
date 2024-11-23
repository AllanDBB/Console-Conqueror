package org.abno.logic.strategies;

import java.util.Random;

public class RandomDuplex implements IStrategy {
    private Random random = new Random();

    @Override
    public int[] executeStrategy(int[][] fighters, int selectedFighter) {
        int[] attackArray = fighters[selectedFighter].clone();
        int randomIndex = random.nextInt(attackArray.length);
        attackArray[randomIndex] *= 2;
        return attackArray;
    }
}

