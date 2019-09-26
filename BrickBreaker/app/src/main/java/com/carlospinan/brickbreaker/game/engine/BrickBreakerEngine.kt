package com.carlospinan.brickbreaker.game.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.carlospinan.brickbreaker.game.*
import com.carlospinan.brickbreaker.game.models.sprites.Ball
import com.carlospinan.brickbreaker.game.models.sprites.Block
import com.carlospinan.brickbreaker.game.models.sprites.Paddle
import com.carlospinan.brickbreaker.game.view.BrickBreakerView


/**
 * @author Carlos Piñan
 */
private var TOTAL_BLOCKS = 6
private const val FONT_FAMILY = "Helvetica"

class BrickBreakerEngine(
    brickBreakerView: BrickBreakerView
) : SensorEventListener {

    private val bitmapEngine by lazy {
        BitmapEngine()
    }

    private val context = brickBreakerView.context
    private var score = 0
    private val scorePaint = Paint()
    private var bitmapPaddle: Bitmap
    private var bitmapBall: Bitmap
    private var bitmapBlock: Bitmap
    private val blockList: MutableList<Block> = mutableListOf()

    private var paddle: Paddle
    private var ball: Ball

    init {
        scorePaint.textSize = 30.0f
        scorePaint.setARGB(255, 255, 0, 0)
        scorePaint.textAlign = Paint.Align.CENTER
        scorePaint.typeface = Typeface.create(FONT_FAMILY, Typeface.BOLD)

        bitmapPaddle = bitmapEngine.loadBitmap(context, SPRITE_PADDLE)
        bitmapBall = bitmapEngine.loadBitmap(context, SPRITE_BALL)
        bitmapBlock = bitmapEngine.loadBitmap(context, SPRITE_BLOCK)

        paddle = Paddle(bitmapPaddle)
        paddle.updatePosition(
            x = SCREEN_WIDTH / 2.0f,
            y = SCREEN_HEIGHT - paddle.height / 2.0f
        )

        ball = Ball(bitmapBall)
        ball.reset()

        createBlocks()
    }

    private fun createBlocks() {
        val blockWidth = bitmapBlock.width
        val blockHeight = bitmapBlock.height
        var blockX = blockWidth * 1.3f
        var blockY = blockHeight * 1.2f

        for (index in 1..TOTAL_BLOCKS) {
            val block = Block(bitmapBlock)
            block.updatePosition(blockX, blockY)
            blockX += blockWidth * 1.1f
            if (index % 6 == 0) {
                blockX += blockWidth * 1.2f
                blockY = blockHeight * 1.3f
            }
        }

    }

    private fun input() {
        // TODO Get this based on accelerometer.
        paddle.direction = 1.0f
    }

    private fun collisions() {

    }

    private fun checkGameState() {
        if (blockList.isEmpty()) {
            TOTAL_BLOCKS += 6
            createBlocks()
            ball.reset()
        }
    }

    fun update(dt: Float) {
        input()
        collisions()
        checkGameState()

        ball.update(dt)
    }

    fun draw(canvas: Canvas) {
        paddle.draw(canvas)
        ball.draw(canvas)
        for (block in blockList) {
            block.draw(canvas)
        }
        canvas.drawText("Score: $score", SCREEN_WIDTH * 0.5f, 300.0f, scorePaint)
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        synchronized(this) {
            when (event?.sensor?.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    paddle.direction = event.values[1]
                }
            }
        }
    }

}