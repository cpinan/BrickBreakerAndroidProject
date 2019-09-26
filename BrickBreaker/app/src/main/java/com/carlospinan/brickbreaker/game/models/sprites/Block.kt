package com.carlospinan.brickbreaker.game.models.sprites

import android.graphics.Bitmap
import com.carlospinan.brickbreaker.game.models.Sprite

/**
 * @author Carlos Piñan
 */
enum class BlockState {
    ALIVE,
    DESTROYED
}

class Block(bitmap: Bitmap) : Sprite(bitmap) {

    private var _blockState = BlockState.ALIVE
    val blockState: BlockState
        get() = _blockState

    override fun update(dt: Float) {}

    fun destroy() {
        _blockState = BlockState.DESTROYED
    }

}