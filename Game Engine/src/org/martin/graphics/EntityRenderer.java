package org.martin.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.*;

import org.martin.graphics.models.*;
import org.martin.scene.*;
import org.martin.shaders.*;

public class EntityRenderer {

	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader) {
		this.shader = shader;
	}
	
	public void render(Map<TexturedModel, List<GameObject>> batch) {
		for(TexturedModel model : batch.keySet()) {
			prepareTexturedModel(model);
			List<GameObject> modelBatch = batch.get(model);
			for(GameObject object : modelBatch) {
				shader.loadTranformationMatrix(object.getWorldSpaceMatrix());
				glDrawElements(GL_TRIANGLES, model.getRawModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawModel = model.getRawModel();
		glBindVertexArray(rawModel.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		//glEnableVertexAttribArray(2); // Normal vectors?
		glActiveTexture(GL_TEXTURE0);
		//shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		glBindTexture(GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		//glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}	
	
}
