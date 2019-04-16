package org.martin.math;

import static java.lang.Math.*;

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
		float length = length();
		x /= length;
		y /= length;
		z /= length;
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
	
	// TODO: Implement
	public void rotate(Vector3f euler) {
		
	}
	
	// TODO: Implement rotated
	public Vector3f rotated(Vector3f euler) {
		return null;
	}
	
	public float dot(Vector3f other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	public void multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
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
	
	// TODO: Implement
	public Vector3f cross(Vector3f other) {
		return null;
	}
	
}
