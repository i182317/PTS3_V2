package com.example.pts3.classes;

import android.widget.ImageView;

import com.example.pts3.interfaces.IPiece;
import com.example.pts3.interfaces.ITuile;

public abstract class Tuile implements ITuile, IPiece {
    public void movePerso(ITuile tuile) {
        this.deletePerso();
        tuile.generatePerso();
    }

    protected abstract void deletePerso();
}
