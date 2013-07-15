package com.carlospinan.brickbreakerandroid.game.sprites;

import android.graphics.Bitmap;

import com.carlospinan.brickbreakerandroid.utils.Sprite;

/**
 * Created by mac on 14/07/13.
 */
public class Block extends Sprite {

    public static final int STATE_ALIVE = 1;
    public static final int STATE_DESTROYED = 2;

    private int state;

    public Block(Bitmap bitmap) {
        super(bitmap);
        state = STATE_ALIVE;
    }

    public void destroy() {
        state = STATE_DESTROYED;
    }

    public int getState() {
        return state;
    }
}
