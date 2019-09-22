package com.carlospinan.brickbreaker.game.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

/**
 * @author Carlos Piñan
 */
class BitmapEngine {

    fun loadBitmap(context: Context, resource: String): Bitmap {
        val assetManager = context.assets
        try {
            return BitmapFactory.decodeStream(
                assetManager.open(resource)
            )
        } catch (e: IOException) {
            throw Exception("There was an error ${e.localizedMessage}")
        }
    }

}