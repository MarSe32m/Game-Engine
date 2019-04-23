package org.martin.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import org.martin.core.*;
import org.martin.graphics.models.*;
import org.martin.math.*;
import org.martin.scene.*;
import org.martin.shaders.*;

public class MasterRenderer {

	StaticShader shader = new StaticShader();
	private EntityRenderer renderer = new EntityRenderer(shader);
	
	
	
	
	private Map<TexturedModel, List<GameObject>> batch = new HashMap<TexturedModel, List<GameObject>>();
	
	public MasterRenderer() {
		
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
	
	
	
}
