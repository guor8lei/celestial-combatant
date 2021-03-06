package com.leigmail.guor.celestialcombatant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.Random;

/**
 * Created by guorl on 5/21/2017.
 *
 * Orb Class
 */
public class Orb {
    private Bitmap bitmap; //bitmap for display
    private int x; //x position
    private int y; //y position

    private int maxX; //max x position (right of screen)
    private int minX; //min x position (left of screen)

    private int maxY; //max y position (bottom of screen)
    private int minY; //min y position (top of screen

    private Rect detectCollision; //for collision detection

    //constructor
    public Orb(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.redorb); //get image
        maxX = screenX; //right of screen is max x position
        maxY = screenY-200; //bottom of screen is max y position
        minX = 0; //left of screen is min x position
        minY = 0; //top of screen is min y position

        Random generator = new Random();
        x = screenX; //start at right of screen
        y = generator.nextInt(maxY) + minY; //random y position in screen range

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight()); //initializing rect object
    }

    //update orb position
    public void update(int playerSpeed) {
        x -= playerSpeed; //move left
        //if orb has gone off screen, reposition on right of screen
        if (x < minX - bitmap.getWidth()) {
            Random generator = new Random();
            x = maxX;
            y = generator.nextInt(maxY) + minY;
        }

        //for collision detection
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //set x position
    public void setX(int x){
        this.x = x;
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
}