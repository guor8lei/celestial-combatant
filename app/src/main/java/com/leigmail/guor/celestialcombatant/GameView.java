package com.leigmail.guor.celestialcombatant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

/**
 * Created by guorl on 5/17/2017.
 *
 * GameView class
 */
public class GameView extends SurfaceView implements Runnable {

    //defining field variables
    final int projectileSpeed = 30; //how fast the orbs, stars, and lasers move
    volatile boolean currentlyPlaying; //is the app in game right now?
    private Thread mainThread = null; //game thread

    private Paint paint; //to draw on screen
    private Canvas canvas; //screen canvas
    private SurfaceHolder surfaceHolder;

    int screenX; //x coordinate of screen

    int countMisses; //number of orbs missed
    boolean orbPresent; //check if orbs enter the screen
    private boolean isGameOver; //check if game is over

    //main objects
    private Laser laser; //laser Object
    private Orb orbs; //orbs Object
    private Player player; //player Object
    private ArrayList<Star> stars = new //array list of stars
            ArrayList<Star>();

    int currentScore; //current score

    //music
    static MediaPlayer backgroundMusic; //media player for background music
    final MediaPlayer orbObtainedSound; //sound when orb is obtained
    final MediaPlayer gameOverSound; //sound when game over

    Context context; //determines the transfer from GameActivity to MainActivity

    //constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        player = new Player(context, screenX, screenY); //initialize player

        surfaceHolder = getHolder();
        paint = new Paint();

        int numStars = 100; //number of stars on screen

        //create star array
        for (int i = 0; i < numStars; i++) {
            Star currentStar = new Star(screenX, screenY);
            stars.add(currentStar);
        }

        orbs = new Orb(context, screenX, screenY); //create orb
        laser = new Laser(context, screenX, screenY); //create laser

        this.screenX = screenX; //establish x coordinate of screen
        countMisses = 0; //set missed orbs to 0
        isGameOver = false; //the game is not over
        currentScore = 0; //setting the current score to 0

        //initializing the media players for the game sounds
        backgroundMusic = MediaPlayer.create(context,R.raw.silverscrapes);
        orbObtainedSound = MediaPlayer.create(context,R.raw.obtainorb);
        gameOverSound = MediaPlayer.create(context,R.raw.gameover);

        //starting the game music when the game starts
        backgroundMusic.start();

        //initializing context
        this.context = context;
    }

    //repeats whenever game is playing
    @Override
    public void run() {
        while (currentlyPlaying) {
            update();
            draw();
            control();
        }
    }

    //updates position of each object
    private void update() {

        currentScore++; //current score increases as time passes

        player.update(); //player position updates based on input

        //stars move towards player
        for (Star s : stars) {
            s.update(projectileSpeed);
        }

        //when orb is present, orbPresent is true
        if(orbs.getX()==screenX){
            orbPresent = true;
        }

        orbs.update(projectileSpeed); //update orb position

        //calculates for collision detection between orb and player
        if (Rect.intersects(player.getDetectCollision(), orbs.getDetectCollision())) {
            orbObtainedSound.start(); //play sound for orb obtained
            orbs.setX(-1000); //make orb disappear
        }
        else{
            if(orbPresent){ //if orb is still on screen
                if(orbs.getDetectCollision().exactCenterX()<10){ //check if orb has passed the player
                    countMisses++; //missed orbs counter increases by 1
                    orbPresent = false; //orb is no longer on screen

                    if(countMisses==3){ //if 3 orbs have been missed, game over
                        currentlyPlaying = false; //no longer in game
                        isGameOver = true; //gameOver is true

                        backgroundMusic.stop(); //background music stops
                        gameOverSound.start(); //play the game over sound
                    }
                }
            }
        }

        laser.update(projectileSpeed); //updating the laser coordinates

        //checking for collision detection between player and laser
        if(Rect.intersects(player.getDetectCollision(),laser.getDetectCollision())){

            currentlyPlaying = false; //game is over
            isGameOver = true;

            backgroundMusic.stop(); //background music stops
            gameOverSound.start(); //play the game over sound
        }
    }

    //draws objects on screen
    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK); //make background color black

            //make stars white
            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            //draw stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            //display current score
            paint.setTextSize(60);
            canvas.drawText("Score: "+ currentScore,100,50,paint);

            //display orbs missed
            paint.setTextSize(60);
            canvas.drawText("Orbs Missed: "+countMisses,500,50,paint);

            //draw player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //draw orbs
            canvas.drawBitmap(
                    orbs.getBitmap(),
                    orbs.getX(),
                    orbs.getY(),
                    paint
            );

            //draw laser
            canvas.drawBitmap(
                    laser.getBitmap(),
                    laser.getX(),
                    laser.getY(),
                    paint
            );

            //draw game over
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int)((canvas.getHeight()/2)-((paint.descent()+paint.ascent())/2));
                canvas.drawText("GAME OVER",canvas.getWidth()/2,yPos,paint);
                paint.setTextSize(75);
                canvas.drawText("Tap to Return to Main Menu",canvas.getWidth()/2,yPos+100,paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); //display on screen
        }
    }

    private void control() {
        try {
            mainThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //pause playing
    public void pause() {
        currentlyPlaying = false;
        try {
            mainThread.join();
        } catch (InterruptedException e) {
        }
    }

    //resume plaing
    public void resume() {
        currentlyPlaying = true;
        mainThread = new Thread(this);
        mainThread.start();
    }

    //whenever user touches screen
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting(); //if not touching, player does not boost
                break;
            case MotionEvent.ACTION_DOWN:
                player.startBoosting(); //if touching boost
                break;

        }
        //if game over, tapping again brings back to main menu
        if(isGameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }
        return true;
    }

    //stop the music when app exits
    public static void stopMusic(){
        backgroundMusic.stop();
    }
}