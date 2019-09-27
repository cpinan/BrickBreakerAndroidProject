package com.carlospinan.brickbreaker.game.models.sprites

import android.graphics.Bitmap
import com.carlospinan.brickbreaker.game.SCREEN_WIDTH
import com.carlospinan.brickbreaker.game.models.Sprite
import kotlin.math.max
import kotlin.math.min

/**
 * @author Carlos Piñan
 */
private const val SPEED = 25.0f

class Paddle(bitmap: Bitmap) : Sprite(bitmap) {

    var direction: Float = 0.0f

    override fun update(dt: Float) {
        var deltaX = x + direction * SPEED * dt
        deltaX = max(deltaX, width / 2.0f)
        deltaX = min(deltaX, SCREEN_WIDTH - width / 2.0f)
        x = deltaX
    }

}