package com.example.pts3.classes;

public class Tour {

    private boolean turnRed;
    private int restDpTuile = 2;
    private int restDpPlayer = 1;

    public Tour(boolean turnRed) {
        this.turnRed = turnRed;
    }

    public boolean isTurnRed() {
        return turnRed;
    }

    public boolean canPlay() {
        return (this.restDpPlayer > 0 || this.restDpTuile > 0);
    }

    public int getRestDpTuile() {
        return restDpTuile;
    }

    public int getRestDpPlayer() {
        return restDpPlayer;
    }

    public void moinsDpTuile() {
        this.restDpTuile --;
    }

    public void moinsDpPlayer() {
        this.restDpPlayer --;
    }

    public boolean canSkipTurn() {
        return restDpTuile + restDpPlayer < 3;
    }
}
