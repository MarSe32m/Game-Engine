package org.martin.rendering;

import org.martin.core.*;
import org.martin.math.*;
import org.martin.scene.*;

public class MasterRenderer {

	// Hash map of <TexturedModel, List<GameObject>>()
	
	private Renderer renderer = new Renderer();
	
	void render(/*Light defaultLight*/ Camera camera) {
		
	}
	
	void batch(GameObject object, Matrix4f tranformation) {
		//TODO: Create the batch or add the object to a current batch (hash map)
	}
	
}
