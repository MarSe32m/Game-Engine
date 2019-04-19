package org.martin.engineTesting;

import org.martin.core.*;
import org.martin.math.*;
import org.martin.rendering.*;
import org.martin.scene.*;
import org.martin.shaders.*;

public class GameScene extends Scene2D {

	Loader loader;
	Renderer renderer;
	RawModel model;
	StaticShader shader;
	
	public GameScene() {
		super();
	}
	
	@Override
	public void didAppear() {
		super.didAppear();
		
		float[] vertices = {
			    -0.5f,  0.5f, 0f,
			    -0.5f, -0.5f, 0f,
			     0.5f, -0.5f, 0f,
			     0.5f,  0.5f, 0f
			  };
		
		int[] indices = {
			0,1,3,
			3,1,2
		};
		
		loader = new Loader();
		renderer = new Renderer();
		shader = new StaticShader();
		model = loader.loadToVAO(vertices, indices);
		
	}
	
	@Override
	public void willDisappear() {
		super.willDisappear();
		loader.cleanUp();
		shader.cleanUp();
	}
	
	public void update() {
		renderer.prepare();
		shader.start();
		renderer.render(model);
		shader.stop();
		
	}
	
}
