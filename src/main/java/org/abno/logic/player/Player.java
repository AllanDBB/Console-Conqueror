package org.abno.logic.player;

import org.abno.logic.cards.Card;
import org.abno.logic.cards.Weapon;

public class Player {
    private String name;
    private Card[] cards;

    /*
    int wins;
    int loses;
    int attacks;
    int success;
    int failed;
    int gaveup;  no se si esto es necesario*/


    Player (String name, Card[] cards){
        this.name = name;
        this.cards = cards;
    }

    public void attack(Player enemy, Card warrior, String weapon){
        if (warrior.getSpecificWeapon(weapon).isUsed()){
            return;
        }

        int damage = warrior.getSpecificWeapon(weapon).getArray()[warrior.getType().ordinal()];

        for (Card c: enemy.getCards()){
            c.setLife(c.getLife() - damage);
            if (c.getLife() < 0){
                c.setLife(0);
            }
        }

        warrior.getSpecificWeapon(weapon).setUsed(true);
    }

    public String getName() {
        return name;
    }

    public Card[] getCards() {
        return cards;
    }

    public Card getSpecificCard(String name){
        for (Card c: this.cards){
            if (c.getName() == name){
                return c;
            }
        }
        return null;
    }
}
