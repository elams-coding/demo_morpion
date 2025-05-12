package com.elams.demo_morpion;

public class Player {
    private final String name;
    private final String sign;
    private int score;

    public Player(String name, String sign) {
        super();
        this.name = name;
        this.sign = sign;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void incrScore() {
        score++;
    }

    @Override
    public String toString() {
        return name;
    }
}
