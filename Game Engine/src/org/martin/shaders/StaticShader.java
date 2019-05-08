package org.martin.shaders;

import org.martin.graphics.*;
import org.martin.math.*;
import org.martin.scene.*;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "res/vertex.txt";
	private static final String FRAGMENT_FILE = "res/fragment.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	private int location_alpha;
	
	private int location_lightPosition;
	private int location_lightColour;
	
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_alpha = super.getUniformLocation("alpha");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour");
	}
	
	public void loadAplha(float alpha) {
		super.loadFloat(location_alpha, alpha);
	}
	
	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getTransform().getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = camera.getViewMatrix();
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
}
