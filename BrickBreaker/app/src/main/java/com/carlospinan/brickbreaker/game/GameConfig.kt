package com.carlospinan.brickbreaker.game

import android.util.Log
import com.carlospinan.brickbreaker.BuildConfig

/**
 * @author Carlos Piñan
 */

const val TAG = "BRICK BREAKER"
const val SCREEN_WIDTH = 800.0f
const val SCREEN_HEIGHT = 480.0f

fun log(message: String) {
    if (BuildConfig.LOGS) {
        Log.d(TAG, message)
    }
}