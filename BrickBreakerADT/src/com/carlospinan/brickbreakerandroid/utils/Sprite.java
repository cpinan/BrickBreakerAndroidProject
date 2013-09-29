package com.carlospinan.brickbreakerandroid.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.carlospinan.brickbreakerandroid.configs.GameConfig;
import com.carlospinan.brickbreakerandroid.interfaces.IBaseGame;

/**
 * Created by mac on 14/07/13.
 */
public class Sprite implements IBaseGame {

	private Bitmap bitmap;
	private float x, y;
	private float anchor_x, anchor_y;
	private boolean visible;
	private Matrix matrix;
	private Paint p_area;

	public Sprite(Bitmap bitmap) {
		this.bitmap = bitmap;
		this.x = 0;
		this.y = 0;
		this.anchor_x = bitmap.getWidth() * 0.5f;
		this.anchor_y = bitmap.getHeight() * 0.5f;
		this.visible = true;
		matrix = new Matrix();

		p_area = new Paint();
		p_area.setARGB(128, 255, 0, 0);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	@Override
	public void update(float dt) {
		// Los hijos implementan el update
	}

	@Override
	public void draw(Canvas canvas) {
		if (visible) {
			matrix.reset();
			matrix.postTranslate(x - anchor_x, y - anchor_y);
			canvas.drawBitmap(bitmap, matrix, null);
			if (GameConfig.DEBUG_AREA) {
				Rectangle r = getBounds();
				canvas.drawRect(r.left, r.top, r.right, r.bottom, p_area);
			}
		}
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getWidth() {
		return bitmap.getWidth();
	}

	public float getHeight() {
		return bitmap.getHeight();
	}

	public Rectangle getBounds() {
		float left = x - anchor_x;
		float right = left + getWidth();
		float top = y - anchor_y;
		float bottom = top + getHeight();
		return new Rectangle(left, top, right, bottom);
	}

}
