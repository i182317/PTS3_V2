package com.example.pts3.classes;

import android.app.Activity;
import android.widget.ImageView;

import com.example.pts3.R;
import com.example.pts3.activity.Game_Activity;
import com.example.pts3.interfaces.IPerso;

public class PersoVert implements IPerso {

    private ImageView img;
    private char drawable = 'V';

    public PersoVert(Activity activity) {
        super();

        img = new ImageView(activity);
        img.setBackgroundResource(R.drawable.zilean);
    }

    @Override
    public char getDrawable() {
        return drawable;
    }

    @Override
    public ImageView getImage() {
        return img;
    }
}
