package org.martin.applicationTesting;

import org.martin.event.types.*;
import org.martin.graphics.models.primitives.*;
import org.martin.input.*;
import org.martin.math.*;
import org.martin.scene.*;
import org.martin.util.*;

public class GameScene extends Scene3D {
	
	GameObject object = new GameObject();
	GameObject object2 = new GameObject();

	Cube cube;
	
	@Override
	public void didAppear() {
		cube = new Cube();
		object2.setModel(cube);
		object2.setScale(1f, 0.5f, 0.5f);
		object2.setPosition(new Vector3f(2.0f, 2.0f, -2.0f));
		object.addChild(object2);
		
		object.setModel(cube);
		addChild(object);
		
		for(int i = 1; i < 1000; i++) {
			GameObject obj = new GameObject();
			obj.setPosition(new Vector3f((float) Math.random() * 15f - 7.5f, (float) Math.random() * 15f - 7.5f, -(float) Math.random() * 15f - 20f));
			obj.setRotation(Quaternion.rotation((float) (Math.random() * Math.PI), -1, -1, 0));
			obj.setModel(cube);
			object.addChild(obj);
		}

		object.setPosition(new Vector3f(0.0f, 0.0f, -10f));
	}
	
	@Override
	public void update() {
		getCoreEngine().getMainCamera().move();
		object.setRotation(Quaternion.rotation(Time.getElapsedTime() * 0.1f, -1, -1, 0));
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
