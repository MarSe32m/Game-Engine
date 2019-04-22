package org.martin.rendering;

import org.martin.math.*;

public class Light {

	private Transform transform = new Transform();
	private Vector3f colour;
	
	public Light(Vector3f colour) {
		this.colour = colour;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
}
