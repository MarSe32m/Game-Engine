package org.martin.scene;

import java.util.*;

import org.martin.math.*;

public class GameObject {
	private static long lastObjectID = 0;
	private long objectID;
	private ArrayList<GameObject> children = new ArrayList<GameObject>();
	private GameObject parent;
	private Transform transform = new Transform();
	
	public GameObject() {
		objectID = GameObject.lastObjectID++;
	}
	
	public void render() {
		Stack<Transform> renderStack = new Stack<Transform>();
		// TODO: Render itself and render its children relative to this (aka. Scene graph)
		// TODO: Start straight out with a batch renderer so we don't have to do that optimization later
		renderStack.push(transform);
		for(GameObject child : children)
			child.render(renderStack);
	}

	private void render(Stack<Transform> renderStack) {
		//TODO: Render the current object
		Vector3f posInScene = transform.getPosition();
		
		for(Transform transform : renderStack)
			posInScene.add(transform.getPosition());
		
		renderStack.push(transform);
		
		for(GameObject object : children)
			object.render(renderStack);
		
		renderStack.pop();
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
	}
	
	public boolean equals(GameObject obj) {
		return objectID == obj.objectID;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
}
