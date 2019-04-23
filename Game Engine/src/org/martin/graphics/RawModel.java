package org.martin.graphics;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	
	float frustumRadius = 0.5f;
	
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
}
