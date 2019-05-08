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
	
	private Matrix4f viewMatrix = Matrix4f.identity();
	
	public Camera() {
	}
	
	//TODO: Remove this after testing
	public void move() {
		Vector3f movementDirection = new Vector3f();
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)) {
			movementDirection.z -= 0.1f;
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
			movementDirection.z += 0.1f;
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
			movementDirection.x -= 0.1f;
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
			movementDirection.x += 0.1f;
		}
		if(movementDirection.lengthSquared() > 0)
			transform.move(Quaternion.rotate(movementDirection, transform.getQuaternion()));
	}
	
	
	
	public Matrix4f getViewMatrix() {
		if(transform.changed()) {
			transform.modificationsDone();
			viewMatrix = transform.getTransformationMatrix().inversed();
		}
		return viewMatrix;
	}
	
	public boolean isPointInside(Vector2f point) {
		float width = CoreEngine.getOriginalWidth() / 2.0f;
		float height = CoreEngine.getOriginalHeight() / 2.0f;
		return point.x > -width && point.x < width && point.y > -height && point.y < height;
	}
	
	public Vector2f[] getCorners() {
		float width = CoreEngine.getOriginalWidth();
		float height = CoreEngine.getOriginalHeight();
		Vector2f[] corners = new Vector2f[4];
		//Top right
		corners[0] = new Vector2f(width / 2.0f, height / 2.0f).rotated(transform.getQuaternion().toEuler().z);
		//Bottom right
		corners[1] = new Vector2f(width / 2.0f, -height / 2.0f).rotated(transform.getQuaternion().toEuler().z);
		//Bottom left
		corners[2] = new Vector2f(-width / 2.0f, -height / 2.0f).rotated(transform.getQuaternion().toEuler().z);
		//Top left
		corners[3] = new Vector2f(-width / 2.0f, height / 2.0f).rotated(transform.getQuaternion().toEuler().z);
		return corners;
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
		float width = CoreEngine.getOriginalWidth();
		float height = CoreEngine.getOriginalHeight();
		return Matrix4f.orthographic(-width / 2.0f, width / 2.0f, -height / 2.0f, height / 2.0f, -100000.0f, 100000.0f);
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
	
	public Transform getTransform() {
		return transform;
	}
	
}
