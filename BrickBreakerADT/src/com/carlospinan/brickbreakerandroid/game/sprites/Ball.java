package com.carlospinan.brickbreakerandroid.game.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.carlospinan.brickbreakerandroid.configs.GameConfig;
import com.carlospinan.brickbreakerandroid.game.Assets;
import com.carlospinan.brickbreakerandroid.utils.SoundManager;
import com.carlospinan.brickbreakerandroid.utils.Sprite;

/**
 * Created by mac on 14/07/13.
 */
public class Ball extends Sprite {

    private static final float MAX_SPEED_X = 5.0f;
    private static final float MAX_SPEED_Y = 5.0f;

    private float speed_x, speed_y;

    public Ball(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (getX() + speed_x < getWidth() * 0.5f || getX() + speed_x > GameConfig.SCREEN_WIDTH - getWidth() * 0.5f) {
            SoundManager.playSFX(Assets.SFX_HIT);
            speed_x *= -1;
        }


        if (getY() + speed_y < getHeight() * 0.5f || getY() + speed_y > GameConfig.SCREEN_HEIGHT - getHeight() * 0.5f) {
            SoundManager.playSFX(Assets.SFX_HIT);
            speed_y *= -1;
        }

        float new_x = getX() + speed_x;
        float new_y = getY() + speed_y;

        setPosition(new_x, new_y);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void move(Sprite obj) {
        float ball_pos = getX() - obj.getX();
        float hit = (ball_pos / (obj.getWidth() - getWidth()));

        speed_x = hit * MAX_SPEED_X;
        invertSpeed();

    }

    public void invertSpeed() {
        speed_y *= -1;
    }

    public void reset() {
        setPosition(getWidth(), GameConfig.SCREEN_HEIGHT - getHeight());
        speed_x = MAX_SPEED_X;
        speed_y = -MAX_SPEED_Y;
    }
}
