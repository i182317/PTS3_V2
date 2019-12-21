package com.example.pts3.classes;

import android.app.Activity;
import android.widget.ImageView;

import com.example.pts3.R;
import com.example.pts3.activity.Game_Activity;
import com.example.pts3.interfaces.IPerso;

public class TuileVert extends Tuile {

    private Activity activity;
    private PersoVert persoVert = null;

    private ImageView image;

    public TuileVert(Activity activity) {
        super();
        this.persoVert = null;
        this.activity = activity;

        image = new ImageView(activity);
        image.setBackgroundResource(R.drawable.tuile_vert);
    }

    @Override
    protected void deletePerso() {
        this.persoVert = null;
    }

    @Override
    public void generatePerso() {
        this.persoVert = new PersoVert(activity);
    }

    @Override
    public IPerso getPerso() {
        return persoVert;
    }

    @Override
    public char draw() {
        if(persoVert == null) return 'v';
        else return persoVert.getDrawable();
    }

    @Override
    public ImageView draw_image() {
        return image;
    }
}
