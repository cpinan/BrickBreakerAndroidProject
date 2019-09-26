package com.carlospinan.brickbreaker.game.engine

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.carlospinan.brickbreaker.game.view.BrickBreakerView

/**
 * @author Carlos Piñan
 */
private const val FPS = 60L

class ThreadEngine(
    private val view: BrickBreakerView,
    private val surfaceHolder: SurfaceHolder
) : Thread() {

    var running: Boolean = false
    var pause: Boolean = false

    override fun run() {
        var canvas: Canvas?
        var lastTimeStamp = System.currentTimeMillis()
        while (running) {
            if (!pause) {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    // Added -1 to prevent ArithmeticException
                    val timeElapsed = System.currentTimeMillis() - (lastTimeStamp - 1)
                    val dt: Float = 1.0f / timeElapsed
                    view.update(dt)
                    view.draw(canvas)
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