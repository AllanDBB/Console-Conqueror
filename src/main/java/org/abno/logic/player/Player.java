package org.abno.logic.player;

import org.abno.logic.cards.Card;
import org.abno.logic.cards.Weapon;

import java.net.Socket;

public class Player {
    private Socket socket;
    private String username;
    private String userId;
    private Card[] cards;

    /*
    int wins;
    int loses;
    int attacks;
    int success;
    int failed;
    int gaveup;  no se si esto es necesario*/


    public Player(Socket socket, String username, String userId){
        this.socket = socket;
        this.username = username;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
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

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}
