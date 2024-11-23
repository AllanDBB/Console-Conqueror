package org.abno.logic;

import java.util.ArrayList;
import java.util.Random;

public class Card {

    private TypeOfCard type;
    private String name;
    private String image;

    private int life;

    private int[] weapon1= new int[10];
    private int[] weapon2= new int[10];
    private int[] weapon3= new int[10];
    private int[] weapon4= new int[10];
    private int[] weapon5= new int[10];

    private boolean used1 = false;
    private boolean used2 = false;
    private boolean used3 = false;
    private boolean used4 = false;
    private boolean used5 = false;

    Card(TypeOfCard type, String name, String image){
        this.type = type;
        this.name = name;
        this.image = image;
        this.life = 100;

        fillWeapons();
    }

    private void fillWeapons(){
        Random r = new Random();

        for (int i: weapon1){i = r.nextInt(20,100);}
        for (int i: weapon2){i = r.nextInt(20,100);}
        for (int i: weapon3){i = r.nextInt(20,100);}
        for (int i: weapon4){i = r.nextInt(20,100);}
        for (int i: weapon5){i = r.nextInt(20,100);}

    }


    public boolean isUsed(int weapon){
        switch (weapon){
            case 1:
                return used1;

            case 2:
                return used2;

            case 3:
                return used3;

            case 4:
                return used4;

            case 5:
                return used5;

            default:
                return false;
        }
    }

    public void setUsed(int weapon, boolean bool){
        switch (weapon){
            case 1:
                this.used1 = bool;
                break;

            case 2:
                this.used2 = bool;
                break;

            case 3:
                this.used3 = bool;
                break;

            case 4:
                this.used4 = bool;
                break;

            case 5:
                this.used5 = bool;
                break;
        }
    }

    public int[] getWeapon(int weapon) {
        switch (weapon) {
            case 1:
                return weapon1;

            case 2:
                return weapon2;

            case 3:
                return weapon3;

            case 4:
                return weapon4;

            case 5:
                return weapon5;

            default:
                return null;
        }
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
}
