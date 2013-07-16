package com.carlospinan.brickbreakerandroid.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 14/07/13.
 */
public class SoundManager {

    public static HashMap<String, HashMap<Integer, SoundPool>> hash_sounds = new HashMap<String, HashMap<Integer, SoundPool>>();

    public static void preloadSFX(Context context, String assetSound) {
        if (assetSound.length() > 0) {
            int sound_id = -1;
            SoundPool sound_pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            try {
                AssetManager asset_manager = context.getAssets();
                AssetFileDescriptor descriptor = asset_manager
                        .openFd(assetSound);
                sound_id = sound_pool.load(descriptor, 1);

            } catch (IOException e) {
            }
            if (sound_id != -1) {
                if (hash_sounds.get(assetSound) == null) {
                    HashMap<Integer, SoundPool> hash_map = new HashMap<Integer, SoundPool>();
                    hash_map.put(sound_id, sound_pool);
                    hash_sounds.put(assetSound, hash_map);
                }
            }
        }
    }

    public static void playSFX(String asset_sound) {
        if (hash_sounds.get(asset_sound) != null) {
            HashMap<Integer, SoundPool> hash = hash_sounds.get(asset_sound);
            for (Map.Entry<Integer, SoundPool> entry : hash.entrySet()) {
                int sound_id = entry.getKey();
                SoundPool sp = entry.getValue();
                sp.play(sound_id, 1, 1, 1, 0, 1);
            }
        }
    }

    public static void pause() {
        for (Map.Entry<String, HashMap<Integer, SoundPool>> entry : hash_sounds
                .entrySet()) {
            HashMap<Integer, SoundPool> hash = entry.getValue();
            for (Map.Entry<Integer, SoundPool> e : hash.entrySet()) {
                SoundPool sp = e.getValue();
                if (sp != null) {
                    sp.stop(e.getKey());
                }
            }
        }
    }

    public static void releaseSounds() {

        for (Map.Entry<String, HashMap<Integer, SoundPool>> entry : hash_sounds
                .entrySet()) {
            HashMap<Integer, SoundPool> hash = entry.getValue();
            for (Map.Entry<Integer, SoundPool> e : hash.entrySet()) {
                SoundPool sp = e.getValue();
                if (sp != null) {
                    sp.release();
                }
            }
        }

        hash_sounds.clear();
    }

}
