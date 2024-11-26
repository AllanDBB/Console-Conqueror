package org.abno.logic.cards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Weapon {

    private int[] array = new int[10];
    private boolean used = false;
    private String name;
    private int minValue;
    private int maxValue;

    public Weapon(String name) {
        this.name = name;
        loadConfig(); // Carga la configuración desde el TXT
        fillWeapon(); // Llena el arreglo con valores aleatorios basados en el TXT
    }

    private void loadConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    if (parts[0].trim().equalsIgnoreCase("minValue")) {
                        minValue = Integer.parseInt(parts[1].trim());
                    } else if (parts[0].trim().equalsIgnoreCase("maxValue")) {
                        maxValue = Integer.parseInt(parts[1].trim());
                    }
                }
            }

            if (minValue == 0 || maxValue == 0) {
                throw new RuntimeException("El archivo config.txt no contiene valores válidos para minValue y maxValue.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }

    private void fillWeapon() {
        Random r = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(minValue, maxValue + 1);
        }
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public int[] getArray() {
        return array;
    }
}
