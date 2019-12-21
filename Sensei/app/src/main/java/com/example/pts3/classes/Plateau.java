package com.example.pts3.classes;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.pts3.R;
import com.example.pts3.activity.Game_Activity;
import com.example.pts3.interfaces.IPerso;
import com.example.pts3.interfaces.IPiece;
import com.example.pts3.interfaces.ITuile;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private LinearLayout aff;
    private IPiece[][] plateau;
    private int sizeX;
    private int sizeY;

    private int size_per_tuile;

    private Activity activity;

    private List<ImageView> list_image_case;

    private RelativeLayout draw_plateau;

    public Plateau(int sizeX, int sizeY, Activity activity, LinearLayout aff) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.activity = activity;
        this.aff = aff;

        this.plateau = new IPiece[sizeX][sizeY];
        this.list_image_case = new ArrayList<>();
    }

    public void setCase(int x, int y, IPiece piece) {
        this.plateau[x][y] = piece;
    }

    public void setPlateau(Point size) {
        this.size_per_tuile = size.x / sizeX;

        draw_plateau = new RelativeLayout(activity);

        int sizeXLayout = sizeX * size_per_tuile;
        int sizeYLayout = sizeY * size_per_tuile;

        ViewGroup.LayoutParams paramsLayout = new ViewGroup.LayoutParams(sizeXLayout, sizeYLayout);
        draw_plateau.setLayoutParams(paramsLayout);

        draw_plateau.setBackgroundColor(Color.WHITE);

        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                ImageView img_plat = new ImageView(activity);

                if(plateau[i][j] instanceof Tuile || plateau[i][j] == null) {
                    double r = Math.random();
                    if (r < 0.4) img_plat.setBackgroundResource(R.drawable.tuile_1);
                    else if (r < 0.8) img_plat.setBackgroundResource(R.drawable.tuile_4);
                    else if (r < 0.9) img_plat.setBackgroundResource(R.drawable.tuile_2);
                    else img_plat.setBackgroundResource(R.drawable.tuile_3);
                }
                else if(plateau[i][j] instanceof Obstacle) {
                    img_plat.setBackgroundResource(R.drawable.obs);
                }

                int x = size_per_tuile * i;
                int y = size_per_tuile * j;

                img_plat.setX(x);
                img_plat.setY(y);

                ViewGroup.LayoutParams paramsImg = new ViewGroup.LayoutParams(size_per_tuile, size_per_tuile);
                img_plat.setLayoutParams(paramsImg);

                this.list_image_case.add(img_plat);
            }
        }

        aff.addView(draw_plateau, 1);
    }

    private void setPosTuile() {
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                if(plateau[i][j] instanceof Tuile) {

                    int x = size_per_tuile * i;
                    int y = size_per_tuile * j;

                    ((ITuile) plateau[i][j]).draw_image().setX(x);
                    ((ITuile) plateau[i][j]).draw_image().setY(y);

                    ViewGroup.LayoutParams paramsImg = new ViewGroup.LayoutParams(size_per_tuile, size_per_tuile);
                    ((ITuile) plateau[i][j]).draw_image().setLayoutParams(paramsImg);

                    if(((ITuile) plateau[i][j]).getPerso() != null) {
                        ((ITuile) plateau[i][j]).getPerso().getImage().setX(x);
                        ((ITuile) plateau[i][j]).getPerso().getImage().setY(y);

                        ((ITuile) plateau[i][j]).getPerso().getImage().setLayoutParams(paramsImg);
                    }
                }
            }
        }
    }

    public void drawPlateau(List<int[]> click_corect_coord, Tour tour) {
        draw_plateau.removeAllViews();

        for(ImageView img : this.list_image_case) {
            draw_plateau.addView(img);
        }

        setPosTuile();

        for(int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (plateau[i][j] instanceof Tuile) {
                    draw_plateau.addView(((ITuile) plateau[i][j]).draw_image());

                    if(((ITuile) plateau[i][j]).getPerso() != null) {
                        draw_plateau.addView(((ITuile) plateau[i][j]).getPerso().getImage());
                    }
                }
            }
        }

        if(click_corect_coord != null) {
            for(int[] coord : click_corect_coord) {
                ImageView img = new ImageView(activity);
                if(tour.isTurnRed()) img.setBackgroundResource(R.drawable.prev_tuile_rouge);
                else img.setBackgroundResource(R.drawable.prev_tuile_vert);

                ViewGroup.LayoutParams paramsLayout = new ViewGroup.LayoutParams(size_per_tuile, size_per_tuile);
                img.setLayoutParams(paramsLayout);

                int x = coord[0] * size_per_tuile;
                int y = coord[1] * size_per_tuile;

                img.setX(x);
                img.setY(y);

                draw_plateau.addView(img);
            }
        }
    }

    public IPiece[][] getPlateau() {
        return plateau;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean correctCoord(int[] coord, Tour tour) {
        boolean ret = true;

        if(!this.coordInPlateau(coord)) ret = false;
        else {

            if(plateau[coord[0]][coord[1]] == null) ret = false;

            else if(plateau[coord[0]][coord[1]] instanceof Obstacle) ret = false;

            else if(tour.isTurnRed()) {
                if(plateau[coord[0]][coord[1]] instanceof TuileVert) ret = false;
            }
            else {
                if(plateau[coord[0]][coord[1]] instanceof TuileRouge) ret = false;
            }
        }

        return ret;
    }

    public int moovCoordToCoord(int[] coord, int[] coordWantMoov, Tour tour) {
        int ret = 0;
        ITuile tuile = (ITuile) plateau[coord[0]][coord[1]];
        if(tuile.getPerso() == null) {
            plateau[coordWantMoov[0]][coordWantMoov[1]] = plateau[coord[0]][coord[1]];
            plateau[coord[0]][coord[1]] = null;

            tour.moinsDpTuile();
        }
        else {
            ((Tuile) plateau[coord[0]][coord[1]]).movePerso((Tuile) plateau[coordWantMoov[0]][coordWantMoov[1]]);
            tour.moinsDpPlayer();
        }
        return ret;
    }

    public List<int[]> getCoordMoov(int[] coord, Tour tour, int mode) {
        List<int[]> coordMoov = null;
        ITuile tuile = (ITuile) plateau[coord[0]][coord[1]];

        if(tuile.getPerso() == null && tour.getRestDpTuile() > 0) {
            coordMoov = this.getCoordMoovTuile(coord, mode);
        }
        else if(tuile.getPerso() != null && tour.getRestDpPlayer() > 0){
            coordMoov = this.getCoordMoovPlayer(coord, tour.isTurnRed());
        }
        return coordMoov;
    }

    private List<int[]> getCoordMoovPlayer(int[] coord, boolean turnRed) {
        List<int[]> res = new ArrayList<>();

        for(int j = -1; j < 2; j++) {
            for(int i = -1; i < 2; i++) {
                if(i != 0 || j != 0) {
                    int[] dir = new int[2];
                    dir[0] = i;
                    dir[1] = j;

                    int plusX = coord[0] + dir[0];
                    int plusY = coord[1] + dir[1];
                    IPiece piece = null;
                    if(plusX >= 0 && plusX < sizeX && plusY >= 0 && plusY < sizeY) {
                        if(plateau[plusX][plusY] instanceof ITuile) {
                            piece = (Tuile) plateau[plusX][plusY];
                        }
                    }

                    while((plusX >= 0 && plusX < sizeX && plusY >= 0 && plusY < sizeY) && (piece == null || (turnRed ? piece instanceof TuileRouge : piece instanceof TuileVert))) {

                        if(piece instanceof TuileRouge || piece instanceof TuileVert) {
                            int a[] = new int[2];
                            a[0] = plusX;
                            a[1] = plusY;
                            res.add(a);
                        }

                        plusX += dir[0];
                        plusY += dir[1];

                        if(plusX >= 0 && plusX < sizeX && plusY >= 0 && plusY < sizeY) {
                            if(plateau[plusX][plusY] instanceof ITuile) {
                                piece = (Tuile) plateau[plusX][plusY];
                            }
                            else {
                                piece = null;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    private List<int[]> getCoordMoovTuile(int[] coord, int mode) {
        List<int[]> res = new ArrayList<int[]>();

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {

                if(Math.abs(i) != Math.abs(j)) {
                    if(mode == 1) {
                        int[] rep = this.getCoordMoovTuileDirection(coord, i, j);
                        if(rep != null) res.add(rep);
                    }
                    else {
                        int nextI = i;
                        int nextJ = j;
                        int[] rep = this.getCoordMoovTuileDirection(coord, nextI, nextJ);
                        while(rep != null) {
                            res.add(rep);

                            nextI += i;
                            nextJ += j;

                            rep = this.getCoordMoovTuileDirection(coord, nextI, nextJ);
                        }
                    }
                }
            }
        }
        return res;
    }

    private int[] getCoordMoovTuileDirection(int[] coord, int i, int j) {
        int[] res = null;

        if(coord[0] + i >= 0) {
            if(coord[0] + i < sizeX) {
                if(coord[1] + j >= 0) {
                    if(coord[1] + j < sizeY) {
                        if(plateau[coord[0] + i][coord[1] + j] == null) {
                            int a[] = new int[2];
                            a[0] = coord[0] + i;
                            a[1] = coord[1] + j;
                            res = a;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean coordInPlateau(int[] coord) {
        boolean rep = true;

        if(coord[0] < 0 || coord[0] >= this.getSizeX()) rep = false;

        if(coord[1] < 0 || coord[1] >= this.getSizeY()) rep = false;

        return rep;
    }

    public IPerso getWinner() {
        IPerso winner = null;

        for(int i = 0; i < sizeX; i++) {
            if(plateau[i][0] instanceof TuileVert) {
                if(((ITuile) plateau[i][0]).getPerso() != null) {
                    winner = ((ITuile) plateau[i][0]).getPerso();
                }
            }
            if(plateau[i][sizeY - 1] instanceof TuileRouge) {
                if(((ITuile) plateau[i][sizeY - 1]).getPerso() != null) {
                    winner = ((ITuile) plateau[i][sizeY - 1]).getPerso();
                }
            }
        }
        return winner;
    }

    public ITuile getTuile(int[] coord) {
        return (ITuile) plateau[coord[0]][coord[1]];
    }

    public boolean[] getAntiJeu() {
        boolean[] antiJeu = new boolean[2];

        antiJeu[0] = this.antiJeuPerso(0, this.getSizeY() - 1, 0, true, 0);
        antiJeu[1] = this.antiJeuPerso(0, this.getSizeY() - 1, 0, false, 0);

        return antiJeu;
    }

    private boolean antiJeuPerso(int minY, int maxY, int colonne, boolean turnRed, int nbEnemie) {
        boolean haveWall = false;

        for(int i = minY; i <= maxY; i++) {
            IPiece piece = plateau[colonne][i];

            if((turnRed ? piece instanceof TuileRouge : piece instanceof TuileVert) || piece instanceof Obstacle) {
                if(i < this.getSizeY() - 1) {
                    IPiece pieceDown = plateau[colonne][i + 1];
                    if((turnRed ? pieceDown instanceof TuileVert : pieceDown instanceof TuileRouge)) nbEnemie ++;
                }
                if(i > 0) {
                    IPiece pieceUp = plateau[colonne][i - 1];
                    if((turnRed ? pieceUp instanceof TuileVert : pieceUp instanceof TuileRouge)) nbEnemie ++;
                }

                if(colonne < this.getSizeX() - 1) {
                    int parMinY = i - 1;
                    if(parMinY < 0) parMinY = 0;
                    int parMaxY = i + 1;
                    if(parMaxY >= this.getSizeY()) parMaxY = this.getSizeY() - 1;
                    haveWall = this.antiJeuPerso(parMinY, parMaxY, colonne + 1, turnRed, nbEnemie);
                }
                else {
                    if(nbEnemie >= 3) {
                        haveWall = true;
                    }
                }
            }
        }
        return haveWall;
    }

    public int[] getCoord() {
        int[] coord = new int[2];

        coord[0] = (int) draw_plateau.getX();
        coord[1] = (int) draw_plateau.getY();

        return coord;
    }

    public boolean click_in_plateau(MotionEvent event) {
        boolean in_plateau = true;

        if(event.getX() <= draw_plateau.getX()) in_plateau = false;
        if(event.getY() <= draw_plateau.getY()) in_plateau = false;
        if(event.getX() >= draw_plateau.getX() + draw_plateau.getLayoutParams().width) in_plateau = false;
        if(event.getY() >= draw_plateau.getY() + draw_plateau.getLayoutParams().height) in_plateau = false;

        return in_plateau;
    }

    public int[] get_coord_click(MotionEvent event) {
        int[] coord = new int[2];

        int[] pos_rel = new int[2];

        pos_rel[0] = (int) (event.getX() - draw_plateau.getX());
        pos_rel[1] = (int) (event.getY() - draw_plateau.getY());

        coord[0] = (int) Math.floor(pos_rel[0] / size_per_tuile);
        coord[1] = (int) Math.floor(pos_rel[1] / size_per_tuile);

        return coord;
    }
}
