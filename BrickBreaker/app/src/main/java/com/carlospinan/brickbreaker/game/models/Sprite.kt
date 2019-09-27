package com.carlospinan.brickbreaker.game.models

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.carlospinan.brickbreaker.BuildConfig

/**
 * @author Carlos Piñan
 */
abstract class Sprite(private val bitmap: Bitmap) {

    var x: Float = 0f
    var y: Float = 0f
    var visible: Boolean = true
    val width = bitmap.width
    val height = bitmap.height
    private val anchorX: Float = width * 0.5f
    private val anchorY: Float = height * 0.5f

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
                postTranslate(x - anchorX, y - anchorY)
                canvas.drawBitmap(bitmap, matrix, null)
                if (BuildConfig.DEBUG_COLLISION) {
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

    fun collides(sprite: Sprite): Boolean {
        return bounds().intersect(sprite.bounds())
    }

    private fun bounds(): Rectangle {
        val left = x - anchorX
        val top = y - anchorY
        val right = left + width
        val bottom = top + height
        return Rectangle.make(left, top, right, bottom)
    }

}