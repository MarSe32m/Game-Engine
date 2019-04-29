package org.martin.math;

import static java.lang.Math.*;

public class Vector2f {
	public float x = 0.0f;
	public float y = 0.0f;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f() {
		x = 0.0f;
		y = 0.0f;
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
		if(length() == 0)
			return new Vector2f();
		return new Vector2f(x / length(), y / length());
	}
	
	public void rotate(float radians) {
		x = (float)(x * cos(radians) - y * sin(radians));
		y = (float)(x * sin(radians) + y * cos(radians));
	}
	
	public Vector2f rotated(float radians) {
		float newX = (float)(x * cos(radians) - y * sin(radians));
		float newY = (float)(x * sin(radians) + y * cos(radians));
		return new Vector2f(newX, newY);
	}
	
	public float dot(Vector2f other) {
		return x * other.x + y * other.y;
	}
	
	public void multiply(float scalar) {
		x *= scalar;
		y *= scalar;
	}
	
	public void add(Vector2f other) {
		x += other.x;
		y += other.y;
	}
	
	public Vector2f added(Vector2f other) {
		return new Vector2f(x + other.x, y + other.y);
	}
	
	public float distanceSquared(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y).lengthSquared();
	}
	
	public float distance(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y).length();
	}
	
	public void subtract(Vector2f other) {
		x -= other.x;
		y -= other.y;
	}
	
	public static Vector2f subtract(Vector2f from, Vector2f with) {
		return new Vector2f(from.x - with.x, from.y - with.y);
	}
	
	public float pseudoCrossProduct(Vector2f other) {
		return x * other.y - y * other.x;
	}
	
	
	
}
