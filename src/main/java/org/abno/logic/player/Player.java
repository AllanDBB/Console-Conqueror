package org.abno.logic.player;

import org.abno.logic.cards.Card;
import org.abno.logic.cards.Weapon;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private Socket socket;
    private String username;
    private String userId;
    private List<Card> cards = new ArrayList<>();
    private int[] stats= new int[6];

    /*
    int wins;
    int loses;
    int attacks;            este es el orden del array
    int success;
    int failed;
    int gaveup;  no se si esto es necesario*/


    public Player(Socket socket, String username, String userId){
        this.socket = socket;
        this.username = username;
        this.userId = userId;
    }

    public int[] getStats() {
        return stats;
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


    public List<Card> getCards() {
        return cards;
    }

    public Card getSpecificCard(String name){
        for (Card c: this.cards){
            System.out.println(c.getName());
            if (Objects.equals(c.getName(), name)){
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


    public void setCards(List<Card> playerCards) {
        this.cards = playerCards;
    }
}
