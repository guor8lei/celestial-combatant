package com.leigmail.guor.celestialcombatant;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    //GameView declaration
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay(); //Getting Display object

        Point size = new Point(); //Getting screen resolution as point object
        display.getSize(size);

        gameView = new GameView(this, size.x, size.y); //Initialize GameView
        setContentView(gameView); //add to contentview
    }

    //pausing game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //run game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}