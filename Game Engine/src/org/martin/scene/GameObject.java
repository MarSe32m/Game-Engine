package org.martin.scene;

import java.util.*;

public class GameObject {
	private static long lastObjectID = 0;
	private long objectID;
	private ArrayList<GameObject> children = new ArrayList<GameObject>();
	
	private GameObject parent;
	
	public GameObject() {
		objectID = GameObject.lastObjectID++;
	}
	
	public void render() {
		// TODO: Render itself and render its children relative to this (aka. Scene graph)
		for(GameObject child : children)
			child.render();
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
	
}
