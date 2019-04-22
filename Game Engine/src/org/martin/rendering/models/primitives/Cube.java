package org.martin.rendering.models.primitives;

import org.martin.rendering.*;
import org.martin.rendering.models.*;

public class Cube extends TexturedModel {

	private float size = 0.5f;
	
	private float[] vertices = {-size,size,-size,-size,-size,-size,size,-size,-size,size,size,-size,-size,size,size,-size,-size,size,size,-size,size,size,size,size,size,size,-size,size,-size,-size,size,-size,size,size,size,size,-size,size,-size,-size,-size,-size,-size,-size,size,-size,size,size,-size,size,size,-size,size,-size,size,size,-size,size,size,size,-size,-size,size,-size,-size,-size,size,-size,-size,size,-size,size};
	
	private float[] textureCoords = {0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0};
	
	private int[] indices = {0,1,3,3,1,2,4,5,7,7,5,6,8,9,11,11,9,10,12,13,15,15,13,14,16,17,19,19,17,18,20,21,23,23,21,22};
	
	public Cube() {
		super(null, null);
		this.rawModel = Loader.getInstance().loadToVAO(vertices, textureCoords, indices);
		this.texture = Loader.getInstance().loadModelTexture("texture");
	}

	

}
