package org.abno.logic.cards;

import java.util.Random;

public class Weapon {

    private int[] array= new int[10];
    private boolean used = false;
    private String name;

    public Weapon(String name){
        this.name = name;
        fillWeapon();
    }

    private void fillWeapon(){
        Random r = new Random();
        for (int i: array){
            i = r.nextInt(20,101);
        }
    }

    public boolean isUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public int[] getArray() {
        return array;
    }
}
