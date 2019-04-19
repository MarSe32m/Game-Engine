package org.martin.math;

public class Vector4f {
	public float x, y, z, w;

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector3f vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = 1.0f;
	}
	
	public Vector4f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 0.0f;
	}

	public void add(Vector3f other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}
	
	public void add(Vector4f other) {
		x += other.x;
		y += other.y;
		z += other.z;
		w += other.w;
	}
	
	public Vector3f xyz() {
		return new Vector3f(x, y, z);
	}
	
	
	
}
