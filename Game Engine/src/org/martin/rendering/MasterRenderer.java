package org.martin.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import org.martin.core.*;
import org.martin.math.*;
import org.martin.rendering.models.*;
import org.martin.scene.*;
import org.martin.shaders.*;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer = new EntityRenderer(shader);
	
	private ProjectionType type = ProjectionType.PERSPECTIVE;
	private Matrix4f projectionMatrix;
	
	private Camera camera;
	
	private Map<TexturedModel, List<GameObject>> batch = new HashMap<TexturedModel, List<GameObject>>();
	
	public MasterRenderer() {
		updateProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	void render(Light defaultLight, Camera camera) {
		prepare();
		shader.start();
		shader.loadLight(defaultLight);
		shader.loadViewMatrix(camera);
		renderer.render(batch);
		shader.stop();
		batch.clear();
	}
	
	void batch(GameObject object) {
		TexturedModel model = object.getModel();
		List<GameObject> modelBatch = batch.get(model);
		if(modelBatch != null) {
			modelBatch.add(object);
		} else {
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(object);
			batch.put(model, newBatch);
		}
	}
	
	public void prepare() {
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	public void updateProjectionMatrix() {
		_updateProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	private void _updateProjectionMatrix() {
		switch(type) {
		case PERSPECTIVE:
			float aspectRatio = (float)CoreEngine.getWidth() / (float)CoreEngine.getHeight();
			projectionMatrix = Matrix4f.perspectiveProjection(70, aspectRatio, 1000.0f, 0.1f);
			break;
		case ORTHOGRAPHIC:
			projectionMatrix = Matrix4f.orthographic(-(float) CoreEngine.getWidth() / 2.0f, (float) CoreEngine.getWidth() / 2.0f, 
					-(float) CoreEngine.getHeight() / 2.0f, (float) CoreEngine.getHeight() / 2.0f, -1.0f, 1.0f);
			break;
		}
	}
	
	public void setProjectionType(ProjectionType type) {
		this.type = type;
		updateProjectionMatrix();
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
