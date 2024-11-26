package org.abno.logic.player;

import org.abno.logic.cards.Card;

public class Player {
    private String name;
    private Card firstCard;
    private Card secondCard;
    private Card thirdCard;
    private Card fourthCard;

    /*
    int wins;
    int loses;
    int attacks;
    int success;
    int failed;
    int gaveup;  no se si esto es necesario*/


    Player (String name, Card firstCard, Card secondCard, Card thirdCard, Card fourthCard){
        this.name = name;
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.thirdCard = thirdCard;
        this.fourthCard = fourthCard;

    }

    public void attack(Player enemy, Card warrior, int weapon){
        if (warrior.isUsed(weapon)){
            return;
        }

        int damage = warrior.getWeapon(weapon)[warrior.getType().ordinal()];
        enemy.getCard(1).setLife(enemy.getCard(1).getLife() - damage);
        enemy.getCard(2).setLife(enemy.getCard(2).getLife() - damage);
        enemy.getCard(3).setLife(enemy.getCard(3).getLife() - damage);
        enemy.getCard(4).setLife(enemy.getCard(4).getLife() - damage);

        if (enemy.getCard(1).getLife() < 0){enemy.getCard(1).setLife(0);}
        if (enemy.getCard(2).getLife() < 0){enemy.getCard(2).setLife(0);}
        if (enemy.getCard(3).getLife() < 0){enemy.getCard(3).setLife(0);}
        if (enemy.getCard(4).getLife() < 0){enemy.getCard(4).setLife(0);}

        warrior.setUsed(weapon, true);
    }

    public Card getCard(int card){
        switch (card){
            case 1:
                return firstCard;

            case 2:
                return secondCard;

            case 3:
                return thirdCard;

            case 4:
                return fourthCard;
        }
        return null;
    }


}
