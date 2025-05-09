package com.elams.demo_morpion.model;

public record Player(String nom, String signe) {
    public Player {
        if (nom.trim().isEmpty() || signe.trim().isEmpty()) throw new IllegalArgumentException("Les arguments doivent être non vides");
    }
}
