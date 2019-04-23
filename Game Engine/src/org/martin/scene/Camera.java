package org.martin.scene;

import org.lwjgl.glfw.*;
import org.martin.core.*;
import org.martin.input.*;
import org.martin.math.*;

public class Camera {

	private Transform transform = new Transform();
	
	private float fov = 70f;
	private float near = 0.1f;
	private float far = 1000f;
	
	private Plane[] frustumClippingPlanes = {new Plane(), new Plane(), new Plane(), new Plane(), new Plane(), new Plane()};
	
	private boolean hasMoved = true;
	
	public Camera() {
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
	
	public Plane[] frustumPlanes() {
		hasMoved = transform.changed();
		if(!hasMoved) {
			return frustumClippingPlanes;
		}
		Matrix4f combinedMatrix = getPerspectiveProjection().multiplyRight(getViewMatrix());

		// Left clipping plane
		frustumClippingPlanes[0].normal.x = combinedMatrix.matrix[3 + 0 * 4] + combinedMatrix.matrix[0 + 0 * 4];
		frustumClippingPlanes[0].normal.y = combinedMatrix.matrix[3 + 1 * 4] + combinedMatrix.matrix[0 + 1 * 4];
		frustumClippingPlanes[0].normal.z = combinedMatrix.matrix[3 + 2 * 4] + combinedMatrix.matrix[0 + 2 * 4];
		frustumClippingPlanes[0].d = 		combinedMatrix.matrix[3 + 3 * 4] + combinedMatrix.matrix[0 + 3 * 4];
		
		// Right clipping plane
		frustumClippingPlanes[1].normal.x = combinedMatrix.matrix[3 + 0 * 4] - combinedMatrix.matrix[0 + 0 * 4];
		frustumClippingPlanes[1].normal.y = combinedMatrix.matrix[3 + 1 * 4] - combinedMatrix.matrix[0 + 1 * 4];
		frustumClippingPlanes[1].normal.z = combinedMatrix.matrix[3 + 2 * 4] - combinedMatrix.matrix[0 + 2 * 4];
		frustumClippingPlanes[1].d = 		combinedMatrix.matrix[3 + 3 * 4] - combinedMatrix.matrix[0 + 3 * 4];
		
		// Top clipping plane
		frustumClippingPlanes[2].normal.x = combinedMatrix.matrix[3 + 0 * 4] - combinedMatrix.matrix[1 + 0 * 4];
		frustumClippingPlanes[2].normal.y = combinedMatrix.matrix[3 + 1 * 4] - combinedMatrix.matrix[1 + 1 * 4];
		frustumClippingPlanes[2].normal.z = combinedMatrix.matrix[3 + 2 * 4] - combinedMatrix.matrix[1 + 2 * 4];
		frustumClippingPlanes[2].d = 		combinedMatrix.matrix[3 + 3 * 4] - combinedMatrix.matrix[1 + 3 * 4];
		
		// Bottom clipping plane
		frustumClippingPlanes[3].normal.x = combinedMatrix.matrix[3 + 0 * 4] + combinedMatrix.matrix[1 + 0 * 4];
		frustumClippingPlanes[3].normal.y = combinedMatrix.matrix[3 + 1 * 4] + combinedMatrix.matrix[1 + 1 * 4];
		frustumClippingPlanes[3].normal.z = combinedMatrix.matrix[3 + 2 * 4] + combinedMatrix.matrix[1 + 2 * 4];
		frustumClippingPlanes[3].d = 		combinedMatrix.matrix[3 + 3 * 4] + combinedMatrix.matrix[1 + 3 * 4];
		
		// Near clipping plane
		frustumClippingPlanes[4].normal.x = combinedMatrix.matrix[3 + 0 * 4] + combinedMatrix.matrix[2 + 0 * 4];
		frustumClippingPlanes[4].normal.y = combinedMatrix.matrix[3 + 1 * 4] + combinedMatrix.matrix[2 + 1 * 4];
		frustumClippingPlanes[4].normal.z = combinedMatrix.matrix[3 + 2 * 4] + combinedMatrix.matrix[2 + 2 * 4];
		frustumClippingPlanes[4].d = 		combinedMatrix.matrix[3 + 3 * 4] + combinedMatrix.matrix[2 + 3 * 4];
		
		// Far clipping plane
		frustumClippingPlanes[5].normal.x = combinedMatrix.matrix[3 + 0 * 4] - combinedMatrix.matrix[2 + 0 * 4];
		frustumClippingPlanes[5].normal.y = combinedMatrix.matrix[3 + 1 * 4] - combinedMatrix.matrix[2 + 1 * 4];
		frustumClippingPlanes[5].normal.z = combinedMatrix.matrix[3 + 2 * 4] - combinedMatrix.matrix[2 + 2 * 4];
		frustumClippingPlanes[5].d = 		combinedMatrix.matrix[3 + 3 * 4] - combinedMatrix.matrix[2 + 3 * 4];
		
		for(int i = 0; i < 6; i++)
			frustumClippingPlanes[i].normalize();
		
		return frustumClippingPlanes;
	}
	
	public Matrix4f getViewMatrix() {
		Vector3f translation = transform.getPosition();
		translation.multiply(-1);
		Matrix4f translationMatrix = Matrix4f.translate(translation);
		Vector3f scale = transform.getScale();
		Matrix4f scaleMatrix = Matrix4f.scale(1.0f / scale.x, 1.0f / scale.y, 1.0f / scale.z);
		Matrix4f rotation = transform.getQuaternion().rotationMatrix();
		Matrix4f viewMatrix = Matrix4f.transformationMatrix(translationMatrix, scaleMatrix, rotation);
		return viewMatrix;
	}
	
	public void setProjection(float fov, float near, float far) {
		this.fov = fov;
		this.near = near;
		this.far = far;
	}
	
	public Matrix4f getPerspectiveProjection() {
		float aspectRatio = (float)CoreEngine.getWidth() / (float)CoreEngine.getHeight();
		return Matrix4f.perspectiveProjection(aspectRatio, fov, far, near);
	}
	
	public Matrix4f getOrthogonalProjection() {
		float height = CoreEngine.getHeight();
		float width = CoreEngine.getWidth();
		return Matrix4f.orthographic(-width / 2.0f, width / 2.0f, -height / 2.0f, height / 2.0f, 1.0f, -1.0f);
	}
	
	public Transform getTransform() {
		return transform;
	}
	
}
