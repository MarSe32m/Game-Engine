package org.martin.core;

import org.martin.math.*;

public class Camera {

	private Transform transform = new Transform();
	
	public Camera() {
		transform.setRotation(new Vector3f(0.0f, (float)Math.PI, 0.0f));
		transform.setPosition(new Vector3f(0.0f, 0.0f, 1.0f));
	}
	
	public Matrix4f getViewMatrix() {
		Vector3f translation = transform.getPosition();
		translation.multiply(-1);
		Vector3f scale = transform.getScale();
		scale.x = 1.0f / scale.x;
		scale.y = 1.0f / scale.y;
		scale.z = 1.0f / scale.z;		
		Matrix4f translationMatrix = Matrix4f.translate(translation);
		Matrix4f scaleMatrix = Matrix4f.scale(scale);
		Matrix4f rotation = transform.getQuaternion().rotationMatrix();
		Matrix4f viewMatrix = Matrix4f.transformationMatrix(translationMatrix, scaleMatrix, rotation);
		return viewMatrix;
	}
	
}
