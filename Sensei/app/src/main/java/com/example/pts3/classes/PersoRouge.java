package com.example.pts3.classes;

import android.app.Activity;
import android.widget.ImageView;

import com.example.pts3.R;
import com.example.pts3.activity.Game_Activity;
import com.example.pts3.interfaces.IPerso;
import com.example.pts3.interfaces.IPiece;

public class PersoRouge implements IPerso {

    private ImageView img;
    private char drawable = 'R';

    public PersoRouge(Activity activity) {
        super();

        img = new ImageView(activity);
        img.setBackgroundResource(R.drawable.wukong);
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
