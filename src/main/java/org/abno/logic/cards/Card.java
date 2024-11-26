package org.abno.logic.cards;

import java.util.Objects;
import java.util.Random;

public class Card {

    private TypeOfCard type;
    private String name;
    private String image;
    private Weapon[] weapons;



    private int life;


    public Card(TypeOfCard type, String name, String image, Weapon[] weapons){
        this.type = type;
        this.name = name;
        this.image = image;
        this.life = 100;
        this.weapons = weapons;
    }

    public TypeOfCard getType() {
        return type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public Weapon getSpecificWeapon(String name){
        for (Weapon w : this.weapons){
            if (Objects.equals(w.getName(), name)){
                return w;
            }
        }
        return null;
    }
}
