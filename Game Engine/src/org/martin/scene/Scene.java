package org.martin.scene;

import org.martin.core.*;

public class Scene {
	private CoreEngine coreEngine;
	private GameObject rootObject;
	
	public void didAppear() {
		
	}
	
	public void willDisappear() {
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		rootObject.render();
	}
	
	public CoreEngine getCoreEngine() {
		return coreEngine;
	}
	
	public void setCoreEngine(CoreEngine engine) {
		this.coreEngine = engine;
	}
	
}
