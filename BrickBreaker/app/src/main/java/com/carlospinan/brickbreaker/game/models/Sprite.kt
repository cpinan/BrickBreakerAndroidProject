package com.carlospinan.brickbreaker.game.models

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.carlospinan.brickbreaker.game.DEBUG_RECTANGLE

/**
 * @author Carlos Piñan
 */
abstract class Sprite(private val bitmap: Bitmap) {

    var x: Float = 0f
    var y: Float = 0f
    private var visible: Boolean = true
    val width = bitmap.width
    val height = bitmap.height
    private val anchor_x: Float = width * 0.5f
    private val anchor_y: Float = height * 0.5f

    private val matrix = Matrix()
    private val debugPaintArea = Paint()

    init {
        debugPaintArea.setARGB(128, 255, 0, 0)
    }

    abstract fun update(dt: Float)

    fun draw(canvas: Canvas) {
        if (visible) {
            matrix.apply {
                reset()
                postTranslate(x - anchor_x, y - anchor_y)
                canvas.drawBitmap(bitmap, matrix, null)
                if (DEBUG_RECTANGLE) {
                    val rectangle = bounds()
                    canvas.drawRect(
                        rectangle.left,
                        rectangle.top,
                        rectangle.right,
                        rectangle.bottom,
                        debugPaintArea
                    )
                }
            }
        }
    }

    fun updatePosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun bounds(): Rectangle {
        val left = x - anchor_x
        val top = y - anchor_y
        val right = left + width
        val bottom = top + height
        return Rectangle.make(left, top, right, bottom)
    }

}