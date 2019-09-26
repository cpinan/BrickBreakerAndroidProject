package com.carlospinan.brickbreaker.game.models.sprites

import android.graphics.Bitmap
import com.carlospinan.brickbreaker.game.SCREEN_HEIGHT
import com.carlospinan.brickbreaker.game.SCREEN_WIDTH
import com.carlospinan.brickbreaker.game.models.Sprite

/**
 * @author Carlos Piñan
 */
private const val MAX_SPEED_X = 5.0f
private const val MAX_SPEED_Y = 5.0f

class Ball(bitmap: Bitmap) : Sprite(bitmap) {

    private var speedX = 0.0f
    private var speedY = 0.0f

    override fun update(dt: Float) {
        val deltaX = x + speedX
        val deltaY = y + speedY
        if (deltaX < width || deltaX > SCREEN_WIDTH - width / 2.0f) {
            // TODO Add sfx
            speedX *= -1
        }
        if (deltaY < height || deltaY > SCREEN_HEIGHT - height / 2.0f) {
            // TODO Add sfx
            speedY *= -1
        }
        x += speedX
        y += speedY
    }

    fun reset() {
        x = width.toFloat()
        y = SCREEN_HEIGHT - height.toFloat()
        speedX = MAX_SPEED_X
        speedY = -MAX_SPEED_Y
    }

}