package com.carlospinan.brickbreaker.game.view

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.carlospinan.brickbreaker.game.SCREEN_HEIGHT
import com.carlospinan.brickbreaker.game.SCREEN_WIDTH
import com.carlospinan.brickbreaker.game.engine.BrickBreakerEngine
import com.carlospinan.brickbreaker.game.engine.ThreadEngine

/**
 * @author Carlos Piñan
 */
class BrickBreakerView(
    context: Context
) : SurfaceView(context), SurfaceHolder.Callback {

    val game = BrickBreakerEngine(this)
    private val thread = ThreadEngine(this, this.holder)

    init {
        holder.addCallback(this)
    }

    fun update(dt: Float) {
        game.update(dt)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.let {
            it.save()
            it.drawColor(0xFF000000.toInt())
            val scaleX = canvas.width / SCREEN_WIDTH
            val scaleY = canvas.height / SCREEN_HEIGHT
            it.scale(scaleX, scaleY)
            game.draw(it)
            it.restore()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.pause = true
        thread.running = false
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread.pause = false
        thread.running = true
        thread.start()
    }

}