package com.carlospinan.brickbreaker.game.models

/**
 * @author Carlos Piñan
 */
class Rectangle(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
) {

    companion object {
        fun make(left: Float, top: Float, right: Float, bottom: Float): Rectangle {
            return make(left, top, right, bottom)
        }
    }

    fun intersect(left: Float, top: Float, right: Float, bottom: Float): Boolean {
        return intersect(make(left, top, right, bottom))
    }

    fun intersect(rectangle: Rectangle): Boolean {
        return left <= rectangle.right &&
                right >= rectangle.left &&
                top <= rectangle.bottom &&
                bottom >= rectangle.top
    }

}