package com.carlospinan.brickbreakerandroid.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.carlospinan.brickbreakerandroid.configs.GameConfig;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 14/07/13.
 */
public class Utils {

    public static void console(String msg) {
        if (GameConfig.LOG_DEBUG)
            Log.d(GameConfig.TAG, msg);
    }

    public static Bitmap load(Context context, String str_name) {
        AssetManager asset_manager = context.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = asset_manager.open(str_name);
            bitmap = BitmapFactory.decodeStream(istr, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
