package org.martin.applicationTesting;

import org.martin.event.types.*;
import org.martin.input.*;
import org.martin.math.*;
import org.martin.rendering.models.primitives.*;
import org.martin.scene.*;
import org.martin.util.*;

public class GameScene extends Scene3D {
	
	GameObject object = new GameObject();
	GameObject object2 = new GameObject();

	Cube cube;;
	
	@Override
	public void didAppear() {
		cube = new Cube();
		object2.setModel(cube);
		object2.getTransform().setScale(new Vector3f(1f, 1f, 1f));
		object2.getTransform().setPosition(new Vector3f(2.0f, 2.0f, -2.0f));
		object.addChild(object2);
		object.setModel(cube);
		object.getTransform().setPosition(new Vector3f(0.0f, 0.0f, -10f));
		addChild(object);
		
		for(int i = 0; i < 10000; i++) {
			GameObject obj = new GameObject();
			obj.setPosition(new Vector3f((float)Math.random() * 10.0f - 5f, (float)Math.random() * 10.0f  - 5f, (float)Math.random() * -5f - 10f));
			obj.setRotation(Quaternion.rotation((float) (Math.random() * Math.PI), -1, -1, 0));
			obj.setModel(cube);
			object.addChild(obj);
		}
		
	}
	
	@Override
	public void update() {
		getCoreEngine().getMainCamera().move();
		object.setRotation(Quaternion.rotation(Time.getElapsedTime(), -1, -1, 0));
	}
	
	@Override
	public void willDisappear() {
		
	}

	@Override
	public boolean onMouseEvent(MouseButtonEvent e) {
		return false;
	}

	@Override
	public boolean onMouseMove(MouseMovedEvent e) {
		object.move(new Vector3f(Input.mouseDelta().x * 0.01f, Input.mouseDelta().y * 0.01f, 0));
		return false;
	}
	
	@Override
	public boolean onKeyEvent(KeyEvent e) {
		return false;
	}
	
	
	
}
