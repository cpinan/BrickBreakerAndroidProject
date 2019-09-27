package com.carlospinan.brickbreaker.game.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.carlospinan.brickbreaker.game.*
import com.carlospinan.brickbreaker.game.models.sprites.Ball
import com.carlospinan.brickbreaker.game.models.sprites.Block
import com.carlospinan.brickbreaker.game.models.sprites.BlockState
import com.carlospinan.brickbreaker.game.models.sprites.Paddle
import com.carlospinan.brickbreaker.game.view.BrickBreakerView


/**
 * @author Carlos Piñan
 */
private var TOTAL_BLOCKS = 6
private const val FONT_FAMILY = "Helvetica"
private const val MILLISECONDS = 100L
private const val AMPLITUDE = 100

class BrickBreakerEngine(
    brickBreakerView: BrickBreakerView
) : SensorEventListener {

    private val context = brickBreakerView.context
    private var accelerometerValues: FloatArray = floatArrayOf()
    private var score = 0
    private val scorePaint = Paint()
    private var bitmapPaddle: Bitmap
    private var bitmapBall: Bitmap
    private var bitmapBlock: Bitmap
    private val blockList: MutableList<Block> = mutableListOf()

    private var paddle: Paddle
    private var ball: Ball

    private val bitmapEngine by lazy {
        BitmapEngine()
    }

    private val vibrator by lazy {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

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

        SoundEngine.preloadSFX(context, SFX_COIN)
        SoundEngine.preloadSFX(context, SFX_HIT)

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
            blockList.add(block)
            blockX += blockWidth * 1.1f
            if (index % 6 == 0) {
                blockX = blockWidth * 1.3f
                blockY += blockHeight * 1.2f
            }
        }

    }

    private fun input() {
        if (accelerometerValues.size > 2) {
            paddle.direction = accelerometerValues[1]
        }
    }

    private fun collisions() {
        // Check paddle collision with ball
        if (paddle.collides(ball)) {
            vibrate(100)
            SoundEngine.playSFX(SFX_HIT)
            ball.hitWithPaddle(paddle)
        }
        // Check block collision with ball
        val destroyedBlocks = mutableListOf<Block>()
        for (block in blockList) {
            if (block.blockState == BlockState.ALIVE &&
                ball.collides(block)
            ) {
                score++
                SoundEngine.playSFX(SFX_COIN)
                ball.invertSpeedY()
                block.destroy()
                block.visible = false
                destroyedBlocks.add(block)
            }
        }
        for (block in destroyedBlocks) {
            blockList.remove(block)
        }
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
        paddle.update(dt)
    }

    fun draw(canvas: Canvas) {
        paddle.draw(canvas)
        ball.draw(canvas)
        for (block in blockList) {
            block.draw(canvas)
        }
        canvas.drawText("Score: $score", SCREEN_WIDTH * 0.5f, 300.0f, scorePaint)
    }

    private fun vibrate(millis: Long = MILLISECONDS) {
        vibrator.cancel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(millis, AMPLITUDE))
        } else {
            vibrator.vibrate(millis)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        synchronized(this) {
            when (event?.sensor?.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    accelerometerValues = event.values
                }
            }
        }
    }

}