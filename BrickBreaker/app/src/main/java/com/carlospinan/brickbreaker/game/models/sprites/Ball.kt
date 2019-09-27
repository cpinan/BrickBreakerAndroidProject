package com.carlospinan.brickbreaker.game.models.sprites

import android.graphics.Bitmap
import com.carlospinan.brickbreaker.game.SCREEN_HEIGHT
import com.carlospinan.brickbreaker.game.SCREEN_WIDTH
import com.carlospinan.brickbreaker.game.SFX_HIT
import com.carlospinan.brickbreaker.game.engine.SoundEngine
import com.carlospinan.brickbreaker.game.models.Sprite


/**
 * @author Carlos Piñan
 */
private const val MAX_SPEED_X = 7.0f
private const val MAX_SPEED_Y = 7.0f

class Ball(bitmap: Bitmap) : Sprite(bitmap) {

    private var speedX = 0.0f
    private var speedY = 0.0f

    override fun update(dt: Float) {
        val deltaX = x + speedX
        val deltaY = y + speedY
        if (deltaX < width || deltaX > SCREEN_WIDTH - width / 2.0f) {
            SoundEngine.playSFX(SFX_HIT)
            speedX *= -1
        }
        if (deltaY < height || deltaY > SCREEN_HEIGHT - height / 2.0f) {
            SoundEngine.playSFX(SFX_HIT)
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

    fun hitWithPaddle(paddle: Paddle) {
        val ballPosition = x - paddle.x
        val hit = ballPosition / (paddle.width - width)

        speedX = hit * MAX_SPEED_X
        invertSpeedY()
        y = paddle.y - paddle.height
    }

    fun invertSpeedY() {
        speedY *= -1
    }

}