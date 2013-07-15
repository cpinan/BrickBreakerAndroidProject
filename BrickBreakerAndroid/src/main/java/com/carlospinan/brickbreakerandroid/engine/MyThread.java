package com.carlospinan.brickbreakerandroid.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by mac on 14/07/13.
 */
public class MyThread extends Thread {

    private static final long FPS = 60L;

    private SurfaceClass sf_class;
    private SurfaceHolder holder;
    private boolean running;
    private boolean pause;

    public MyThread(SurfaceClass sf_class, SurfaceHolder holder) {
        this.sf_class = sf_class;
        this.holder = holder;
        running = false;
        pause = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean getRunning() {
        return running;
    }

    public boolean getPause() {
        return pause;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        long start = System.currentTimeMillis();

        while (running) {
            if (!pause) {
                try {
                    canvas = holder.lockCanvas();
                    synchronized (holder) {
                        float dt = 1.0f / (System.currentTimeMillis() - (start - 1));
                        start = System.currentTimeMillis();
                        sf_class.update(dt);
                        sf_class.draw(canvas);
                    }
                } finally {
                    if (canvas != null)
                        holder.unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException ie) {

                }
            }
        }
    }
}
