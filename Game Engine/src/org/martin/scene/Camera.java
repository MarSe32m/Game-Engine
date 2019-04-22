package org.martin.scene;

import org.lwjgl.glfw.*;
import org.martin.input.*;
import org.martin.math.*;

public class Camera {

	private Transform transform = new Transform();
	
	public Camera() {
		transform.setRotation(new Vector3f(0.0f, 0.0f, 0.0f));
	}
	
	//TODO: Remove this after testing
	public void move() {
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)) {
			transform.move(new Vector3f(0.0f, 0.0f, -0.01f));
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
			transform.move(new Vector3f(0.0f, 0.0f, 0.01f));
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
			transform.move(new Vector3f(-0.01f, 0.0f, 0.0f));
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
			transform.move(new Vector3f(0.01f, 0.0f, 0.0f));
		}
	}
	
	public Matrix4f getViewMatrix() {
		Vector3f translation = transform.getPosition();
		translation.multiply(-1);
		Vector3f scale = transform.getScale();
		Matrix4f translationMatrix = Matrix4f.translate(translation);
		Matrix4f scaleMatrix = Matrix4f.scale(1.0f / scale.x, 1.0f / scale.y, 1.0f / scale.z);
		Matrix4f rotation = transform.getQuaternion().rotationMatrix();
		Matrix4f viewMatrix = Matrix4f.transformationMatrix(translationMatrix, scaleMatrix, rotation);
		return viewMatrix;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
}
