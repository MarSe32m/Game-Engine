package org.martin.scene;

import java.util.*;

import org.martin.graphics.*;
import org.martin.math.*;

public class GameObject {
	private static long lastObjectID = 0;
	private long objectID;
	
	private GameObject parent;
	private ArrayList<GameObject> children = new ArrayList<GameObject>();
	
	private Transform transform = new Transform();
	
	private Matrix4f worldSpaceMatrix = transform.getTransformationMatrix();
	
	private TexturedModel texturedModel;
	
	private float frustumRadius = 0.5f;
	private float originalFrustumRadius = 0.5f;
	
	boolean is2D = false;
	
	public boolean isHidden = false;
	
	public float alpha = 1.0f;
	
	private float multipliedAlpha = 1.0f;
	
	public GameObject() {
		objectID = GameObject.lastObjectID++;
	}
	
	public final ArrayList<GameObject> getChildren() {
		return children;
	}
	
	public final void addChild(GameObject object) {
		children.add(object);
		object.parent = this;
	}
	
	private final void removeChild(GameObject object) {
		children.remove(object);
		
	}
	
	public final void removeFromParent() {
		if(parent != null)
			parent.removeChild(this);
		parent = null;
	}
	
	public boolean equals(GameObject obj) {
		return objectID == obj.objectID;
	}
	
	public final GameObject getParent() {
		return parent;
	}
	
	public final Transform getTransform() {
		return transform;
	}
	
	public final void setPosition(float x, float y, float z) {
		transform.setPosition(new Vector3f(x, y, z));
	}
	
	public final void setPosition(Vector3f position) {
		transform.setPosition(position);
	}
	
	public final void move(Vector3f direction) {
		transform.move(direction);
	}
	
	public final void move(float x, float y, float z) {
		transform.move(new Vector3f(x, y, z));
	}
	
	public final void setRotation(Quaternion rotation) {
		transform.setRotation(rotation);
	}
	
	public final void setRotation(Vector3f euler) {
		transform.setRotation(euler);
	}
	
	public final void setScale(float x, float y, float z) {
		transform.setXScale(x);
		transform.setYScale(y);
		transform.setZScale(z);
	}
	
	public final void setXScale(float x) {
		transform.setXScale(x);
		if(x > transform.getYScale() && x > transform.getZScale())
			frustumRadius = originalFrustumRadius * x;
	}
	
	public final void setYScale(float y) {
		transform.setYScale(y);
		if(y > transform.getXScale() && y > transform.getZScale())
			frustumRadius = originalFrustumRadius * y;
	}
	
	public final void setZScale(float z) {
		transform.setZScale(z);
		if(z > transform.getXScale() && z > transform.getYScale())
			frustumRadius = originalFrustumRadius * z;
	}
	
	
	public final Matrix4f getWorldSpaceMatrix() {
		return worldSpaceMatrix;
	}
	
	public final void setWorldSpaceMatrix(Matrix4f matrix) {
		worldSpaceMatrix = matrix;
	}
	
	public final TexturedModel getModel() {
		return texturedModel;
	}
	
	public final void setModel(TexturedModel model) {
		this.originalFrustumRadius = this.frustumRadius;
		this.frustumRadius = model.getFrustumRadius();
		this.texturedModel = model;
	}
	
	public final float getCullingRadius() {
		return frustumRadius;
	}
	
	public final void updateWorldSpaceMatrix(boolean fromRoot) {
		boolean wSM_isDirty = transform.changed();
		transform.modificationsDone();
		if (fromRoot) {
			worldSpaceMatrix = transform.getTransformationMatrix();
			multipliedAlpha = alpha;
			if(wSM_isDirty) {
				updateChildrenWorldSpaceMatrix();
			} else {
				for(GameObject child : children) {
					if(child.transform.changed()) {
						child.worldSpaceMatrix = worldSpaceMatrix.multiplyRight(child.getTransform().getTransformationMatrix());
					}
					child.multipliedAlpha = child.alpha * multipliedAlpha;
					child.updateWorldSpaceMatrix(false);
				}
			}
		} else {
			if(wSM_isDirty) {
				wSM_isDirty = false;
				updateChildrenWorldSpaceMatrix();
			} else {
				for(GameObject child : children) {
					child.updateWorldSpaceMatrix(false);
				}
			}
		}
	}
	
	private final void updateChildrenWorldSpaceMatrix() {
		for(GameObject child : children) {
			child.multipliedAlpha = child.alpha * multipliedAlpha;
			child.worldSpaceMatrix = worldSpaceMatrix.multiplyRight(child.transform.getTransformationMatrix());
			child.updateChildrenWorldSpaceMatrix();
		}
	}

	public float getMultipliedAlpha() {
		return multipliedAlpha;
	}
	
}
