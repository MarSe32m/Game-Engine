package org.martin.graphics;

import org.martin.math.*;
import org.martin.scene.*;

public class RenderEngine {

	private MasterRenderer renderer;
	
	public Camera camera = new Camera();
	
	private ProjectionType type = ProjectionType.PERSPECTIVE;
	private Matrix4f projectionMatrix;
	
	private Light defaultLight;
	
	public RenderEngine() {
		defaultLight = new Light(new Vector3f(1.0f, 0.5f, 0.5f));
		defaultLight.getTransform().setPosition(new Vector3f(2000f, 3000f, 2000f));
		renderer = new MasterRenderer();
		camera.setProjection(70f, 0.1f, 1000f);
		updateProjectionMatrix();
	}
	
	public void render(GameObject rootObject) {
		rootObject.updateWorldSpaceMatrix(true);
		for(GameObject object : rootObject.getChildren())
			_render(object);
		
		renderer.render(defaultLight, camera);
	}

	private void _render(GameObject object) {
		if(frustumCull(object, object.getCullingRadius()))
			renderer.batch(object);
		
		for(GameObject obj : object.getChildren())
			_render(obj);
		
	}
	
	private boolean frustumCull(GameObject object, float radius) {
		Plane[] clippingPlanes = camera.frustumPlanes();
		Matrix4f worldSpaceMatrix = object.getParent().getWorldSpaceMatrix();
		Vector3f worldSpacePos = worldSpaceMatrix.multiplyRight(object.getTransform().getTranslation()).xyz();
		for(int i = 0; i < 6; i++) {
			if(worldSpacePos.dot(clippingPlanes[i].normal) + clippingPlanes[i].d + radius <= 0)
				return false;
		}
		return true;
	}
	
	public void setProjectionType(ProjectionType type) {
		this.type = type;
		updateProjectionMatrix();
	}
	
	public void updateProjectionMatrix() {
		_updateProjectionMatrix();
		renderer.shader.start();
		renderer.shader.loadProjectionMatrix(projectionMatrix);
		renderer.shader.stop();
	}
	
	private void _updateProjectionMatrix() {
		switch(type) {
		case PERSPECTIVE:
			projectionMatrix = camera.getPerspectiveProjection();
			break;
		case ORTHOGRAPHIC:
			projectionMatrix = camera.getOrthogonalProjection();
			break;
		}
	}
	
	
	
}
