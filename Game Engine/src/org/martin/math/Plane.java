package org.martin.math;

public class Plane {

	public Vector3f normal = new Vector3f();
	public float d = 0;
	
	public Plane() {
	}

	public void normalize() {
		float mag = 1.0f / normal.length();
		normal.x *= mag;
		normal.y *= mag;
		normal.z *= mag;
		d *= mag;
	}
	
}
