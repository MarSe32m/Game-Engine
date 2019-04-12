package org.martin.scene;

import org.martin.core.*;

public class Scene {
	private CoreEngine coreEngine;
	private GameObject rootObject;
	
	public void didPresent() {
		
	}
	
	public void willDisappear() {
		
	}
	
	public void update(float deltaTime) {
		
	}
	
	public void render() {
		rootObject.render();
	}
	
}
