package com.leigmail.guor.celestialcombatant;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //play button
    private ImageButton buttonPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //set the orientation to landscape

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay); //get the play button
        buttonPlay.setOnClickListener(this); //click listener
    }

    //when play button is pressed
    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, GameActivity.class)); //starting GameActivity
    }
}