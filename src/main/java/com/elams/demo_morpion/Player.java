package com.elams.demo_morpion;

public class Player {
    private final String nom;
    private final String signe;
    private int score;

    public Player(String nom, String signe) {
        super();
        this.nom = nom;
        this.signe = signe;
        this.score = 0;
    }

    public String getNom() {
        return nom;
    }

    public String getSigne() {
        return signe;
    }

    public int getScore() {
        return score;
    }

    public void incrScore() {
        score++;
    }
}
