package org.martin.graphics.models.primitives;

import org.martin.graphics.models.*;
import org.martin.graphics.textures.*;
import org.martin.graphics.*;

public final class Sprite extends TexturedModel {

	private static float[] vertices;
	private static float[] textureCoords = {
			0,0,
			0,1,
			1,1,
			1,0
		};
	private static int[] indices = {
			0,1,3,
			3,1,2
		};
	
	public Sprite(RawModel model, ModelTexture texture) {
		super(model, texture);
	}

	public Sprite(String textureName, float width, float height) {
		super(null, null);
		setUpVertices(width, height);
		this.rawModel = Loader.getInstance().loadToVAO(vertices, textureCoords, indices);
		this.texture = Loader.getInstance().loadModelTexture(textureName);
	}
	
	public Sprite(String textureName) {
		super(null, null);
		this.texture = Loader.getInstance().loadModelTexture(textureName);
		setUpVertices(this.texture.getWidth(), this.texture.getHeight());
		this.rawModel = Loader.getInstance().loadToVAO(vertices, textureCoords, indices);
	}

	private void setUpVertices(float width, float height) {
		vertices = new float[] {
			    -width,  height, 0f,
			    -width, -height, 0f,
			     width, -height, 0f,
			     width,  height, 0f
			  };
	}
	
}
