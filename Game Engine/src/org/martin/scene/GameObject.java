package org.martin.scene;

import java.util.*;

import org.martin.math.*;
import org.martin.rendering.models.*;

public class GameObject {
	private static long lastObjectID = 0;
	private long objectID;
	
	private GameObject parent;
	private ArrayList<GameObject> children = new ArrayList<GameObject>();
	
	private Transform transform = new Transform();
	
	private Matrix4f worldSpaceMatrix = transform.getTransformationMatrix();
	private boolean wSM_isDirty = true;
	
	private TexturedModel texturedModel;
	
	public GameObject() {
		objectID = GameObject.lastObjectID++;
	}
	
	public ArrayList<GameObject> getChildren() {
		return children;
	}
	
	public void addChild(GameObject object) {
		children.add(object);
		object.parent = this;
	}
	
	private void removeChild(GameObject object) {
		children.remove(object);
		
	}
	
	public void removeFromParent() {
		if(parent != null)
			parent.removeChild(this);
		parent = null;
	}
	
	public boolean equals(GameObject obj) {
		return objectID == obj.objectID;
	}
	
	public Transform getTransform() {
		wSM_isDirty = true;
		return transform;
	}
	
	public void setPosition(float x, float y, float z) {
		transform.setPosition(new Vector3f(x, y, z));
		wSM_isDirty = true;
	}
	
	public void setPosition(Vector3f position) {
		transform.setPosition(position);
		wSM_isDirty = true;
	}
	
	public void move(Vector3f direction) {
		transform.move(direction);
		wSM_isDirty = true;
	}
	
	public void move(float x, float y, float z) {
		transform.move(new Vector3f(x, y, z));
		wSM_isDirty = true;
	}
	
	public void setRotation(Quaternion rotation) {
		transform.setRotation(rotation);
		wSM_isDirty = true;
	}
	
	public void setRotation(Vector3f euler) {
		transform.setRotation(euler);
		wSM_isDirty = true;
	}
	
	public void setScale(float x, float y, float z) {
		transform.setScale(new Vector3f(x, y, z));
		wSM_isDirty = true;
	}
	
	public void setXScale(float x) {
		transform.setXScale(x);
		wSM_isDirty = true;
	}
	
	public void setYScale(float y) {
		transform.setYScale(y);;
		wSM_isDirty = true;
	}
	
	public void setZScale(float z) {
		transform.setZScale(z);
		wSM_isDirty = true;
	}
	
	
	public Matrix4f getWorldSpaceMatrix() {
		return worldSpaceMatrix;
	}
	
	public void setWorldSpaceMatrix(Matrix4f matrix) {
		worldSpaceMatrix = matrix;
	}
	
	public TexturedModel getModel() {
		return texturedModel;
	}
	
	public void setModel(TexturedModel model) {
		this.texturedModel = model;
	}
	
	public void updateWorldSpaceMatrix(boolean fromRoot) {
		if (fromRoot) {
			worldSpaceMatrix = transform.getTransformationMatrix();
			if(wSM_isDirty) {
				wSM_isDirty = false;
				updateChildrenWorldSpaceMatrix();
			} else {
				for(GameObject child : children) {
					if(child.wSM_isDirty) {
						child.worldSpaceMatrix = worldSpaceMatrix.multiplyRight(child.getTransform().getTransformationMatrix());
					}
					child.updateWorldSpaceMatrix(false);
				}
			}
		} else {
			if(wSM_isDirty) {
				wSM_isDirty = false;
				updateChildrenWorldSpaceMatrix();
			} else {
				for(GameObject child : children)
					child.updateWorldSpaceMatrix(false);
			}
		}
	}
	
	private void updateChildrenWorldSpaceMatrix() {
		wSM_isDirty = false;
		for(GameObject child : children) {
			child.worldSpaceMatrix = worldSpaceMatrix.multiplyRight(child.transform.getTransformationMatrix());
			child.updateChildrenWorldSpaceMatrix();
		}
	}
	
}
