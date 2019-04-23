package org.martin.math;

public class Transform {
	
	private Vector4f translation = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
	private Vector3f rotation = new Vector3f();
	private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
	private Quaternion rotationQ = new Quaternion(1.0f, 0, 0, 0);
	
	private boolean hasChanged = false;
	
	public Matrix4f getTransformationMatrix() {
		Matrix4f transformation = Matrix4f.identity();
		Matrix4f translationMatrix = Matrix4f.translate(translation);
		Matrix4f rotationMatrix = rotationQ.rotationMatrix();
		Matrix4f scaleMatrix = Matrix4f.scale(scale);
		transformation = Matrix4f.transformationMatrix(translationMatrix, scaleMatrix, rotationMatrix);
		return transformation;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public Quaternion getQuaternion() {
		return rotationQ;
	}
	
	public void setRotation(Vector3f euler) {
		hasChanged = true;
		rotation = euler;
		rotationQ = Quaternion.fromEuler(euler);
	}

	public void setRotation(Quaternion rotation) {
		hasChanged = true;
		this.rotation = rotation.toEuler();
		this.rotationQ = rotation;
		
	}
	
	public void setScale(Vector3f scale) {
		hasChanged = true;
		this.scale = scale;
	}
	
	public void setXScale(float scale) {
		hasChanged = true;
		this.scale.x = scale;
	}
	
	public void setYScale(float scale) {
		hasChanged = true;
		this.scale.y = scale;
	}
	
	public void setZScale(float scale) {
		hasChanged = true;
		this.scale.z = scale;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public float getXScale() {
		return scale.x;
	}
	
	public float getYScale() {
		return scale.y;
	}
	
	public float getZScale() {
		return scale.z;
	}
	
	public void setPosition(Vector3f position) {
		hasChanged = true;
		translation = new Vector4f(position);
	}
	
	public void move(Vector3f direction) {
		hasChanged = true;
		translation.add(direction);
	}
	
	public Vector3f getPosition() {
		return translation.xyz();
	}
	
	public Vector4f getTranslation() {
		return translation;
	}
	
	public boolean changed() {
		return hasChanged;
	}
}
