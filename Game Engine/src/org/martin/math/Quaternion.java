package org.martin.math;

import static java.lang.Math.*;

public class Quaternion {
	public float w, x, y, z;
	
	public Quaternion() {
		w = 0.0f;
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float dot(Quaternion other) {
		return w * other.w + x * other.x + y * other.y + z * other.z;
	}
	
	public Quaternion cross(Quaternion other) {
		return new Quaternion(0.0f, y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}
	
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}
	
	public float norm() {
		return (float)sqrt(w * w + x * x + y * y + z * z);
	}
	
	public void multiply(float scalar) {
		w *= scalar;
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	
	public Quaternion inverse() {
		if(norm() == 0)
			return new Quaternion();
		float normInv = 1.0f/norm();
		return new Quaternion(w, -x * normInv, -y * normInv, -z * normInv);
	}
	
}
