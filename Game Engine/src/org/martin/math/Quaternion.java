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
	
	public Quaternion(Vector3f vector) {
		this.w = 0f;
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
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
	
	public void normalize() {
		float normInv = 1.0f/norm();
		w *= normInv;
		x *= normInv;
		y *= normInv;
		z *= normInv;
	}
	
	public Quaternion normalized() {
		float normInv = 1.0f/norm();
		return new Quaternion(w * normInv, x * normInv, y * normInv, z * normInv);
	}
	
	public float norm() {
		return (float) Math.sqrt(w * w + x * x + y * y + z * z);
	}
	
	public void multiply(float scalar) {
		w *= scalar;
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	
	public Vector3f xyz() {
		return new Vector3f(x, y, z);
	}
	
	//TODO: Optimize
	public static Vector3f rotate(Vector3f vector, Quaternion quaternion) {
		Vector3f u = quaternion.xyz();
		float s = quaternion.w;
		Vector3f result = u.multiplied(2.0f * u.dot(vector));
		result.add(vector.multiplied(s * s - u.lengthSquared()));
		result.add(Vector3f.cross(u, vector).multiplied(2.0f * s));
		return result;
		///Quaternion p = new Quaternion(vector);
		//Quaternion q = quaternion;
		//Quaternion qPrime = quaternion.conjugate();
		//Quaternion res = Quaternion.hamiltonProduct(Quaternion.hamiltonProduct(q, p), qPrime);
		//Quaternion newQuat = Quaternion.hamiltonProduct(quaternion, Quaternion.hamiltonProduct(new Quaternion(vector), quaternion.inverse()));
		//return new Vector3f(res.x, res.y, res.z);
	}
	
	public static Quaternion hamiltonProduct(Quaternion left, Quaternion right) {
		return new Quaternion(left.w * right.w - left.x * right.x - left.y * right.y - left.z * right.z,
							  left.w * right.x + left.x * right.w + left.y * right.z - left.z * right.y,
							  left.w * right.y - left.x * right.z + left.y * right.w + left.z * right.x,
							  left.w * right.z + left.x * right.y - left.y * right.x + left.z * right.w);
	}
	
	public static Quaternion rotation(float angle, float x, float y, float z) {
		final float a = angle / 2.0f;
		final float sin = (float) sin(a);
		final float cos = (float) cos(a);
		Quaternion result = new Quaternion(cos, sin * x, sin * y, sin * z);
		result.normalize();
		return result;
	}
	
	public static Quaternion fromEuler(Vector3f euler) {
		float cy = (float)cos(euler.z * 0.5);
		float sy = (float)sin(euler.z * 0.5);
	    float cp = (float)cos(euler.y * 0.5);
	    float sp = (float)sin(euler.y * 0.5);
	    float cr = (float)cos(euler.x * 0.5);
	    float sr = (float)sin(euler.x * 0.5);
	    return new Quaternion(cy * cp * cr + sy * sp * sr, 
	    					  cy * cp * sr - sy * sp * cr, 
	    					  sy * cp * sr + cy * sp * cr, 
	    					  sy * cp * cr - cy * sp * sr);
	    
	}
	
	public static Vector3f toEuler(Quaternion quaternion) {
		Quaternion q = quaternion.normalized();
		Vector3f result = new Vector3f();
		float sinr_cosp = 2.0f * (q.w * q.x + q.y * q.z);
		float cosr_cosp = 1.0f - 2.0f * (q.x * q.x + q.y * q.y);
		result.x = (float)atan2(sinr_cosp, cosr_cosp);
		
		float sinp = 2.0f * (q.w * q.y - q.z * q.x);
		if(abs(sinp) >= 1)
			result.y = signum(sinp) * (float)PI / 2.0f;
		else
			result.y = (float)asin(sinp);
		
		float siny_cosp = 2.0f * (q.w * q.z + q.x * q.y);
		float cosy_cosp = 1.0f - 2.0f * (q.y * q.y + q.z * q.z);  
		result.z = (float)atan2(siny_cosp, cosy_cosp);
		
		return result;
	}
	
	public Vector3f toEuler() {
		Vector3f result = new Vector3f();
		Quaternion q = normalized();
		float sinr_cosp = 2.0f * (q.w * q.x + q.y * q.z);
		float cosr_cosp = 1.0f - 2.0f * (q.x * q.x + q.y * q.y);
		result.x = (float)atan2(sinr_cosp, cosr_cosp);
		
		float sinp = 2.0f * (q.w * q.y - q.z * q.x);
		if(abs(sinp) >= 1)
			result.y = signum(sinp) * (float)PI / 2.0f;
		else
			result.y = (float)asin(sinp);
		
		float siny_cosp = 2.0f * (q.w * q.z + q.x * q.y);
		float cosy_cosp = 1.0f - 2.0f * (q.y * q.y + q.z * q.z);  
		result.z = (float)atan2(siny_cosp, cosy_cosp);
		return result;
	}
	
	public Matrix4f rotationMatrix() {
		Matrix4f matrix = new Matrix4f();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float x2 = x * x;
		final float y2 = y * y;
		final float z2 = z * z;
		matrix.matrix[0 + 0 * 4] = 1 - 2 * (y2 + z2);
		matrix.matrix[1 + 0 * 4] = 2 * (xy + zw);
		matrix.matrix[2 + 0 * 4] = 2 * (xz - yw);
		matrix.matrix[0 + 1 * 4] = 2 * (xy - zw);
		matrix.matrix[1 + 1 * 4] = 1 - 2 * (x2 + z2);
		matrix.matrix[2 + 1 * 4] = 2 * (yz + xw);
		matrix.matrix[0 + 2 * 4] = 2 * (xz + yw);
		matrix.matrix[1 + 2 * 4] = 2 * (yz - xw);
		matrix.matrix[2 + 2 * 4] = 1 - 2 * (x2 + y2);
		matrix.matrix[3 + 3 * 4] = 1;
		return matrix;
	}
	
	
	public static Quaternion interpolate(Quaternion a, Quaternion b, float t) {
		Quaternion result = new Quaternion(1, 0, 0, 0);
		float dot = a.dot(b);
		float blend = 1f - t;
		if(dot < 0) {
			result.w = blend * a.w - t * b.w;
			result.x = blend * a.x - t * b.x;
			result.y = blend * a.y - t * b.y;
			result.z = blend * a.z - t * b.z;
		} else {
			result.w = blend * a.w + t * b.w;
			result.x = blend * a.x + t * b.x;
			result.y = blend * a.y + t * b.y;
			result.z = blend * a.z + t * b.z;
		}
		return result;
	}
	
	public Quaternion inverse() {
		if(norm() == 0)
			return new Quaternion();
		float normInv = 1.0f/norm();
		return new Quaternion(w, -x * normInv, -y * normInv, -z * normInv);
	}
	
}
