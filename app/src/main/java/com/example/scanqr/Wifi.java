package com.example.scanqr;

import java.util.ArrayList;

public class Wifi {
    String nom;
    private String password;

    public Wifi(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    static ArrayList<Wifi> listWifi = new ArrayList<>();
}
