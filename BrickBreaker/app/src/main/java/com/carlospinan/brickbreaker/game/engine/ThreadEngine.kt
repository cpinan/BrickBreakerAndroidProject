package com.carlospinan.brickbreaker.game.engine

import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * @author Carlos Piñan
 */
private const val FPS = 60L

class ThreadEngine(
    private val surfaceView: SurfaceView,
    private val surfaceHolder: SurfaceHolder
) : Thread() {

    var running: Boolean = false
    var pause: Boolean = false

    override fun run() {
        var canvas: Canvas? = null
        var lastTimeStamp = System.currentTimeMillis()
        while (running) {
            if (!pause) {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    // Added -1 to prevent ArithmeticException
                    val timeElapsed = System.currentTimeMillis() - (lastTimeStamp - 1)
                    val dt: Float = 1.0f / timeElapsed
                    // surfaceView.update(dt)
                    surfaceView.draw(canvas)
                    lastTimeStamp = System.currentTimeMillis()
                }
                canvas?.let {
                    surfaceHolder.unlockCanvasAndPost(it)
                }
            }
            sleep(1000L / FPS)
        }
    }

}