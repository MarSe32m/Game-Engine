package org.martin.graphics;

import org.martin.graphics.textures.*;

public class TexturedModel {

	protected RawModel rawModel;
	protected ModelTexture texture;
	
	protected float frustumRadius = 0.5f;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		if(model != null)
			frustumRadius = model.frustumRadius;
		this.rawModel = model;
		this.texture = texture;
	}
	
	public RawModel getRawModel() {
		return rawModel;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
	
	public float getFrustumRadius() {
		return frustumRadius;
	}
	
}
