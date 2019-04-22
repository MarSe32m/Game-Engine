package org.martin.scene;

public abstract class Scene3D extends Scene {

	public final void init() {
		getCoreEngine().setPerspective();
	}
}
