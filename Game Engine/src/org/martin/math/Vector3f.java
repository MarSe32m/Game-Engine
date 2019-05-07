package org.martin.math;

import static java.lang.Math.*;

import org.joml.*;

public class Vector3f {
	public float x;
	public float y;
	public float z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f() {
		x = 0.0f;
		y = 0.0f; 
		z = 0.0f;
	}
	
	public void normalize() {
		float length = 1.0f / length();
		x *= length;
		y *= length;
		z *= length;
	}
	
	public float length() {
		return (float)sqrt(x *x  + y * y + z * z);
	}
	
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public Vector3f normalized() {
		float length = length();
		return new Vector3f(x / length, y / length, z / length);
	}

	private void rotateX(float r) {
		y =(float)(y * cos(r) - z * sin(r));
		z = (float)(y * sin(r) + z * cos(r));
	}
	
	private void rotateY(float r) {
		x = (float)(x * cos(r) + z * sin(r));
		z = (float)(z * cos(r) - x * sin(r));
	}
	
	private void rotateZ(float r) {
		x = (float)(x * cos(r) - y * sin(r));
		y = (float)(x * sin(r) + y * cos(r));
	}
	
	
	
	// TODO: Implement
	public void rotate(Vector3f euler) {
		rotateX(euler.x);
		rotateY(euler.y);
		rotateZ(euler.z);
	}
	
	// TODO: Implement rotated
	public Vector3f rotated(Vector3f euler) {
		return null;
	}
	
	public float dot(Vector3f other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	public static float dot(Vector3f a, Vector3f b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public void multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	
	public Vector3f multiplied(float scalar) {
		return new Vector3f(x * scalar, y * scalar, z * scalar);
	}
	
	public void divide(float divident) {
		multiply(1.0f/divident);
	}
	
	public void add(Vector3f other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}
	
	public void subtract(Vector3f other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}
	
	public Vector2f xy() {
		return new Vector2f(x, y);
	}
	
	public Vector3f cross(Vector3f other) {
		return new Vector3f(y * other.z - z * other.y,
							z * other.x - x * other.z,
							x * other.y - y * other.x);
	}
	
	public static Vector3f cross(Vector3f a, Vector3f b) {
		return new Vector3f(a.y * b.z - a.z * b.y,
							a.z * b.x - a.x * b.z,
							a.x * b.y - a.y * b.x);
	}
	
}
