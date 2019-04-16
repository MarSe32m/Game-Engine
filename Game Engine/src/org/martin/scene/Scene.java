package org.martin.scene;

import org.martin.core.*;

public abstract class Scene {
	private CoreEngine coreEngine;
	protected GameObject rootObject;
	
	public Scene() {
		rootObject = new GameObject();
	}
	
	public void didAppear() {
		
	}
	
	public void willDisappear() {
		
	}
	
	public void update() {
		
	}
	
	public final void render() {
		rootObject.render();
	}
	
	public final CoreEngine getCoreEngine() {
		return coreEngine;
	}
	
	public final void setCoreEngine(CoreEngine engine) {
		this.coreEngine = engine;
	}
	
	public final GameObject getRootObject() {
		return rootObject;
	}
	
	public final void addChild(GameObject object) {
		rootObject.addChild(object);
	}
	
}
