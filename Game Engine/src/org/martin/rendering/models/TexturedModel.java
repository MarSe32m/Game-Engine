package org.martin.rendering.models;

import org.martin.rendering.textures.*;

public class TexturedModel {

	protected RawModel rawModel;
	protected ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
	}
	
	public RawModel getRawModel() {
		return rawModel;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
	
}
