package org.martin.rendering;

import org.martin.core.*;
import org.martin.math.*;
import org.martin.scene.*;

public class RenderEngine {

	private MasterRenderer renderer;
	
	public Camera camera = new Camera();
	
	private Light defaultLight;
	
	public RenderEngine() {
		defaultLight = new Light(new Vector3f(1.0f, 0.5f, 0.5f));
		defaultLight.getTransform().setPosition(new Vector3f(2000f, 3000f, 2000f));
		
		renderer = new MasterRenderer();
	}
	
	public void render(GameObject rootObject) {
		rootObject.updateWorldSpaceMatrix(true);
		for(GameObject object : rootObject.getChildren())
			_render(object);
		
		renderer.render(defaultLight, camera);
	}

	private void _render(GameObject object) {
		renderer.batch(object);
		for(GameObject obj : object.getChildren())
			_render(obj);
		
	}
	
	public void updateProjectionType(ProjectionType type) {
		renderer.setProjectionType(type);
	}
	
	public void updateProjectionMatrix() {
		renderer.updateProjectionMatrix();
	}
	
}
