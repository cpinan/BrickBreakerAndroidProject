package com.carlospinan.brickbreakerandroid.utils;

/**
 * Created by mac on 14/07/13.
 */
public class Rectangle {

	public float left, top, right, bottom;

	public Rectangle(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public boolean intersect(Rectangle r) {
		return left <= r.right && right >= r.left && top <= r.bottom
				&& bottom >= r.top;
	}

}
