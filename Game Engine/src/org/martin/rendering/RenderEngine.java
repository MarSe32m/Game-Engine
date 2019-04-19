package org.martin.rendering;

import java.util.*;

import org.martin.core.*;
import org.martin.math.*;
import org.martin.scene.*;

public class RenderEngine {

	private MasterRenderer renderer = new MasterRenderer();
	
	public Camera camera;
	
	public void render(GameObject rootObject) {
		Stack<Transform> stack = new Stack<Transform>();
		stack.push(rootObject.getTransform());
		renderer.batch(rootObject, rootObject.getTransform().getTransformationMatrix());
		
		for(GameObject object : rootObject.getChildren())
			_render(object, stack);
		renderer.render(camera);
	}

	private void _render(GameObject object, Stack<Transform> stack) {
		stack.push(object.getTransform());
		Matrix4f transformationMatrix = Matrix4f.identity();
		
		for(Transform transform : stack)
			transformationMatrix.multiply(transform.getTransformationMatrix());
		
		renderer.batch(object, transformationMatrix);
		
		for(GameObject obj : object.getChildren())
			_render(obj, stack);
		
		stack.pop();
	}
	
}
