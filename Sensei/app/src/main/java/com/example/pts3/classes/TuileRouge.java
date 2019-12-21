package com.example.pts3.classes;

import android.app.Activity;
import android.widget.ImageView;

import com.example.pts3.R;
import com.example.pts3.activity.Game_Activity;
import com.example.pts3.interfaces.IPerso;

public class TuileRouge extends Tuile {

    private PersoRouge persoRouge;

    private ImageView image;
    private Activity activity;

    public TuileRouge(Activity activity) {
        super();
        this.persoRouge = null;
        this.activity = activity;

        image = new ImageView(activity);
        image.setBackgroundResource(R.drawable.tuile_rouge);
    }

    @Override
    public void generatePerso() {
        this.persoRouge = new PersoRouge(activity);
    }

    @Override
    public IPerso getPerso() {
        return persoRouge;
    }

    @Override
    public char draw() {
        if(this.persoRouge == null) return 'r';
        else return this.persoRouge.getDrawable();
    }

    @Override
    public ImageView draw_image() {
        return image;
    }

    @Override
    protected void deletePerso() {
        this.persoRouge= null;
    }
}
