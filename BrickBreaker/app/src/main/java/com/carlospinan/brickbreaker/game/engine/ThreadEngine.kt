package com.carlospinan.brickbreaker.game.engine

import android.graphics.Canvas
import android.view.SurfaceHolder

/**
 * @author Carlos Piñan
 */
private const val FPS = 60L

class ThreadEngine(
    private val surfaceEngine: SurfaceEngine,
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
                    lastTimeStamp = System.currentTimeMillis()
                }
            }
            sleep(1000L / FPS)
        }
    }

}