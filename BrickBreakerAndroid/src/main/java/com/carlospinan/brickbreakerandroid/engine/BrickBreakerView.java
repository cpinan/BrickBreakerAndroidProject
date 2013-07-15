package com.carlospinan.brickbreakerandroid.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.carlospinan.brickbreakerandroid.configs.GameConfig;
import com.carlospinan.brickbreakerandroid.game.BrickBreakerGame;

/**
 * Created by mac on 14/07/13.
 */
public class BrickBreakerView extends SurfaceClass {

    private MyThread thread;
    private BrickBreakerGame game;

    public BrickBreakerView(Context context) {
        super(context);
        game = new BrickBreakerGame(this, context);
        thread = new MyThread(this, getHolder());
        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (thread.getState() == Thread.State.TERMINATED)
            thread = new MyThread(this, getHolder());
        thread.setRunning(true);
        thread.setPause(false);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setPause(true);
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException ie) {

            }
        }
    }

    @Override
    public void update(float dt) {
        game.update(dt);
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(0xFF000000);

        float scale_x = canvas.getWidth() / GameConfig.SCREEN_WIDTH;
        float scale_y = canvas.getHeight() / GameConfig.SCREEN_HEIGHT;

        canvas.scale(scale_x, scale_y);

        game.draw(canvas);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (thread != null)
            thread.setPause(true);

        if (game != null)
            game.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (thread != null)
            thread.setPause(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (game != null)
            game.onDestroy();
    }


}
