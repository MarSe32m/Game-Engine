package org.martin.graphics.textures;

public class ModelTexture {

	private int textureID;
	
	private int width = 0;
	private int height = 0;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getID() {
		return textureID;
	}
	
}
