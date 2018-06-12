package com.leigmail.guor.celestialcombatant;

import java.util.Random;

/**
 * Created by guorl on 5/17/2017.
 *
 * Star Class
 */
public class Star {
    private int x; //x position
    private int y; //y position
    private int speed; //each star has its own speed

    private int maxX; //max x position (right of screen)
    private int minX; //min x position (left of screen)

    private int maxY; //max y position (bottom of screen)
    private int minY; //min y position (top of screen

    //constructor
    public Star(int screenX, int screenY) {
        maxX = screenX; //right of screen is max x position
        maxY = screenY; //bottom of screen is max y position
        minX = 0; //left of screen is min x position
        minY = 0; //top of screen is min y position

        Random generator = new Random();
        speed = generator.nextInt(5); //generate random speed

        x = generator.nextInt(maxX); //random x position
        y = generator.nextInt(maxY); //random y position
    }

    //update star position
    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed; //move left
        //if star has gone off screen, reposition on right of screen
        if (x < 0) {
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(5);
        }
    }

    //get star wideth
    public float getStarWidth() {
        float minX = 3.0f; //minimum possible star width
        float maxX = 7.0f; //maximum possible star width
        return new Random().nextFloat() * (maxX - minX) + minX; //create random star width
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