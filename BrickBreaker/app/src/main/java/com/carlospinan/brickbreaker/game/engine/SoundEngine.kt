package com.carlospinan.brickbreaker.game.engine

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool


/**
 * @author Carlos Piñan
 */
object SoundEngine {

    private val soundsHashMap = HashMap<String, HashMap<Int, SoundPool>>()

    fun preloadSFX(context: Context, resource: String) {
        if (resource.isNotEmpty()) {
            var soundId: Int
            val soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
            val assetManager = context.assets
            val descriptor = assetManager.openFd(resource)
            soundId = soundPool.load(descriptor, 1)
            if (soundId != -1) {
                if (soundsHashMap[resource] == null) {
                    val hashMap = HashMap<Int, SoundPool>()
                    hashMap[soundId] = soundPool
                    soundsHashMap[resource] = hashMap
                }
            }
        }
    }

    fun playSFX(resource: String) {
        if (soundsHashMap[resource] != null) {
            val hash = soundsHashMap[resource]
            hash?.entries?.let {
                for (soundEntry in it) {
                    val soundId = soundEntry.key
                    val soundPool = soundEntry.value
                    soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
                }
            }
        }
    }

    fun pause() {
        for (soundEntry in soundsHashMap?.entries) {
            val hash = soundEntry.value
            for (soundPoolEntry in hash?.entries) {
                soundPoolEntry.value.stop(soundPoolEntry.key)
            }
        }
    }

    fun releaseSounds() {
        for (soundEntry in soundsHashMap?.entries) {
            val hash = soundEntry.value
            for (soundPoolEntry in hash?.entries) {
                soundPoolEntry.value.release()
            }
        }
        soundsHashMap.clear()
    }

}