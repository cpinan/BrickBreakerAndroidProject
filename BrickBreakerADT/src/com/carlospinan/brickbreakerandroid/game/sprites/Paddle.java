package com.carlospinan.brickbreakerandroid.game.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.carlospinan.brickbreakerandroid.configs.GameConfig;
import com.carlospinan.brickbreakerandroid.utils.Sprite;

/**
 * Created by mac on 14/07/13.
 */
public class Paddle extends Sprite {

    private static final float SPEED = 3.0f;

    public Paddle(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void move(float values[]) {
        float dx = values[0];
        setPosition(getX() + dx * SPEED, getY());

        if (getX() < getWidth() * 0.5f)
            setPosition(getWidth() * 0.5f, getY());

        if (getX() + getWidth() * 0.5f > GameConfig.SCREEN_WIDTH)
            setPosition(GameConfig.SCREEN_WIDTH - getWidth() * 0.5f, getY());
    }
}
