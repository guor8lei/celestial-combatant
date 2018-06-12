package com.leigmail.guor.celestialcombatant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by guorl on 5/17/2017.
 *
 * Player class
 */
public class Player {
    private Bitmap bitmap; //bitmap for display
    private int x; //x position
    private int y; //y position

    private int maxY; //max y position (bottom of screen)
    private int minY; //min y position (top of screen

    private Rect detectCollision; //for collision detection

    private int speed = 0; //initial speed is 0

    private boolean boosting; //is the ship boosting? (is the screen being touched?)

    private final int GRAVITY = -30; //gravitational constant
    private final int MIN_SPEED = 1; //minimum possible speed
    private final int MAX_SPEED = 60; //max speed

    //constructor
    public Player(Context context, int screenX, int screenY) {
        x = 75; //set x position
        y = 50; //set y position
        speed = 1; //intial speed

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship); //get image for display

        maxY = screenY - bitmap.getHeight(); //bottom of screen is maximum y position
        minY = 0; //top of screen is minimum y position
        boosting = false; //ship is not boosting

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight()); //initializing rect object
    }

    //set boosting to true
    public void startBoosting() {
        boosting = true;
    }

    //set boosting to false
    public void stopBoosting() {
        boosting = false;
    }

    //update player position
    public void update() {
        if (boosting) {
            speed += 8; //if boosting, go up
        } else {
            speed -= 8; //if boosting go down
        }

        if (speed > MAX_SPEED) { //do not allow spaceship to surpass maximum speed
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED) { //do not allow spaceship to surpass minimum speed
            speed = MIN_SPEED;
        }

        y -= speed + GRAVITY; //account for gravititational constant

        if (y < minY) { //do not let player leave top of screen
            y = minY;
        }

        if (y > maxY) { //do not let player leave bottom of screen
            y = maxY;
        }

        //for collision detection
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //get rect for collision detection
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //get bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    //get x position
    public int getX() {
        return x;
    }

    //get y position
    public int getY() {
        return y;
    }

    //get speed
    public int getSpeed() {
        return speed;
    }
}