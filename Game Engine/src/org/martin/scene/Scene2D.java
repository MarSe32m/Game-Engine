package org.martin.scene;

public abstract class Scene2D extends Scene {
	
	public final void init() {
		getCoreEngine().setOrthographic();
	}
	
	@Override
	public abstract void update();

	@Override
	public abstract void willDisappear();
	
}
