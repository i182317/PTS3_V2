package com.example.pts3.classes;

import android.app.Activity;

import com.example.pts3.interfaces.IPerso;

import java.util.List;

public class Game {

    private final Activity activity;
    private Plateau plateau;
    private Tour tour;
    private boolean regleAntiJeu;
    private int mode;
    private IPerso winner = null;
    private IPerso antiJeu = null;

    public Game(Plateau plateau, boolean regleAntiJeu, int mode, Activity activity) {
        this.plateau = plateau;
        this.regleAntiJeu = regleAntiJeu;
        this.mode = mode;
        this.tour = new Tour(true);
        this.activity = activity;
    }

    public IPerso getWinner() {
        return winner;
    }

    public boolean isRegleAntiJeu() {
        return regleAntiJeu;
    }

    public int isModeArashi() {
        return mode;
    }

    public Tour getTour() {
        return tour;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void haveWinner() {
        winner = plateau.getWinner();
    }

    public void drawPlateau(List<int[]> click_corect_coord) {
        this.plateau.drawPlateau(click_corect_coord, tour);
    }

    public boolean playerCanPlay() {
        return tour.canPlay();
    }

    public boolean isTurnRed() {
        return tour.isTurnRed();
    }

    public boolean correctCoord(int[] coord) {
        return plateau.correctCoord(coord, tour);
    }

    public List<int[]> getCoordMoov(int[] coord) {
        return plateau.getCoordMoov(coord, tour, mode);
    }

    public void nextTurn() {
        tour = new Tour(!tour.isTurnRed());
    }

    public void moov(int[] coord, int[] coordWantMoov) {
        plateau.moovCoordToCoord(coord, coordWantMoov, tour);
    }

    public void antiJeu() {
        if(this.isRegleAntiJeu()) {
            boolean[] antiJeuBool = plateau.getAntiJeu();
            if(antiJeuBool[0]) {
                antiJeu = new PersoRouge(activity);
            }
            else if(antiJeuBool[1]) {
                antiJeu = new PersoVert(activity);
            }
        }
    }

    public boolean canSkipTour() {
        return tour.canSkipTurn();
    }

    public IPerso getAntiJeu() {
        return antiJeu ;
    }

    public int[] getCoordPlateau() {
        return plateau.getCoord();
    }
}
