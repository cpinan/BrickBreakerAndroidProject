package com.carlospinan.brickbreaker.game

import android.util.Log
import com.carlospinan.brickbreaker.BuildConfig

/**
 * @author Carlos Piñan
 */
object GameConfig {

    const val TAG = "BRICK BREAKER"
    const val DEBUG_RECTANGLE = true
    const val SCREEN_WIDTH = 800.0f
    const val SCREEN_HEIGHT = 480.0f

    fun log(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

}