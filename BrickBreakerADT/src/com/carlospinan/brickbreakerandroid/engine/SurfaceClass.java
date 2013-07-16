package com.carlospinan.brickbreakerandroid.engine;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.carlospinan.brickbreakerandroid.interfaces.IBaseGame;

/**
 * Created by mac on 14/07/13.
 */
public abstract class SurfaceClass extends SurfaceView implements SurfaceHolder.Callback, IBaseGame {

    private float accelx, accely, accelz;

    public SurfaceClass(Context context) {
        super(context);
        accelx = 0;
        accely = 0;
        accelz = 0;
    }

    public void setAccelerometer(float accelx, float accely, float accelz) {
        this.accelx = accelx;
        this.accely = accely;
        this.accelz = accelz;
    }

    public float[] getAccelerometer() {
        float values[] = {accelx, accely, accelz};
        return values;
    }

    public void onPause() {
        // Implementada por el hijo
    }

    public void onResume() {
        // Implementada por el hijo
    }

    public void onDestroy() {
        // Implementada por el hijo
    }
}
