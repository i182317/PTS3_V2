package com.example.pts3.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pts3.R;


public class EndGame_activity extends AppCompatActivity {
    public TextView textJ1,textJ2;
    private ImageView imageViewReplay,imageViewMenu;
    private String Win;
    private int screenWidth;
    private int screenHeight;
    private ConstraintLayout screenReplay;
    private EndGame_activity endGame_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_activity);
        textJ1 = (TextView) findViewById(R.id.textJ1);
        textJ2 = (TextView) findViewById(R.id.textJ2);
        screenReplay=(ConstraintLayout) findViewById(R.id.screenReplay);
        endGame_activity=this;
        Intent intent = getIntent();
        Win=intent.getStringExtra("Win");
        if(Win.equals("Vert")){
            textJ1.setText("Vert WIN");
            textJ2.setText("Rouge LOOSE");
        }else if(Win.equals("Rouge")){
            textJ2.setText("Rouge WIN");
            textJ1.setText("Vert LOOSE");
        }else if(Win.equals("VertAntiJeu")){
            textJ1.setText("Vert WIN par Anti Jeu");
            textJ2.setText("Rouge LOOSE par Anti Jeu");
        }else if(Win.equals("RougetAntiJeu")){
            textJ2.setText("Rouge WIN par Anti Jeu");
            textJ1.setText("Vert LOOSE par Anti Jeu");
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        imageViewReplay = new ImageView(this);
        imageViewReplay.setX(screenWidth/2-(screenWidth/4)/2);
        imageViewReplay.setY(screenHeight/2-(screenHeight/8));
        imageViewReplay.setBackgroundResource(R.drawable.test);
        ViewGroup.LayoutParams replay = new ViewGroup.LayoutParams(screenWidth/4,screenHeight/8);
        imageViewReplay.setLayoutParams(replay);
        screenReplay.addView(imageViewReplay);

        imageViewMenu = new ImageView(this);
        imageViewMenu.setX(screenWidth/2-(screenWidth/4)/2);
        imageViewMenu.setY(screenHeight/2+(screenHeight/8));
        imageViewMenu.setBackgroundResource(R.drawable.test);
        ViewGroup.LayoutParams menu = new ViewGroup.LayoutParams(screenWidth/4,screenHeight/8);
        imageViewMenu.setLayoutParams(menu);
        screenReplay.addView(imageViewMenu);

        testTouchListener();

    }

    private void testTouchListener() {
        imageViewReplay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentreplay = new Intent(endGame_activity,Game_Activity.class);
                        endGame_activity.startActivity(intentreplay);
                        finish();
                    }
                });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();

        if(hasFocus) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
}