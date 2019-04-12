package org.martin.core.math;

import static java.lang.Math.*;

public class Vector2f {
	public float x = 0.0f;
	public float y = 0.0f;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void normalize() {
		x /= length();
		y /= length();
	}
	
	public float length() {
		return (float)sqrt(x *x  + y * y);
	}
	
	public float lengthSquared() {
		return x * x + y * y;
	}
	
	public Vector2f normalized() {
		return new Vector2f(x / length(), y / length());
	}
	
	public void rotate(float radians) {
		x = x * (float)cos(radians) - y * (float)sin(radians);
		y = x * (float)sin(radians) + y * (float)cos(radians);
	}
	
	public Vector2f rotated(float radians) {
		float newX = x * (float)cos(radians) - y * (float)sin(radians);
		float newY = x * (float)sin(radians) + y * (float)cos(radians);
		return new Vector2f(newX, newY);
	}
	
	public float dot(Vector2f other) {
		return x * other.x + y * other.y;
	}
	
	public void multiply(float scalar) {
		x *= scalar;
		y *= scalar;
	}
	
}
