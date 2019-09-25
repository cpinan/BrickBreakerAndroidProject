package com.carlospinan.brickbreaker.game.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.view.SurfaceView
import com.carlospinan.brickbreaker.game.view.BrickBreakerView

/**
 * @author Carlos Piñan
 */
private const val TOTAL_BLOCK = 6
private const val FONT_FAMILY = "Helvetica"

class BrickBreakerEngine(
    private val brickBreakerView: BrickBreakerView
) {

    private var score = 0
    private val scorePaint = Paint()
    private lateinit var bitmapPlayer: Bitmap
    private lateinit var bitmapBall: Bitmap
    private lateinit var bitmapBlock: Bitmap

    init {
        scorePaint.textSize= 30.0f
        scorePaint.setARGB(255, 255,0,0)
        scorePaint.textAlign = Paint.Align.CENTER
        scorePaint.typeface = Typeface.create(FONT_FAMILY, Typeface.BOLD)
    }
}