package org.martin.applicationTesting;

import org.lwjgl.glfw.*;
import org.martin.core.*;
import org.martin.event.*;
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
	public void willAppear() {
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
		Input.hideMouse();
	}
	
	float lastX = 0.0f;
	float lastY = 0.0f;
	float cameraRot = 0.0f;
	
	@Override
	public void update() {
		
		getCoreEngine().getMainCamera().move();
		object.setRotation(Quaternion.rotation(Time.getElapsedTime() * 0.01f * (float)Math.PI * 2f, 1, 1, 0));
		Vector2f mousePos = Input.mousePos();
		Camera mainCamera = getCoreEngine().getMainCamera();
		float deltaX = mousePos.x;
		float deltaY = mousePos.y;
		if(deltaX != 0 || deltaY != 0)
			mainCamera.getTransform().rotateBy(deltaY * Time.getDeltaTime() * 0.05f, -deltaX * Time.getDeltaTime() * 0.05f, 0);
		Input.setMousePosition(new Vector2f());
	}
	
	@Override
	public void willDisappear() {
		Input.unhideMouse();
	}

	@Override
	public boolean onMouseEvent(MouseButtonEvent e) {
		return false;
	}

	
	@Override
	public boolean onMouseMove(MouseMovedEvent e) {
		return false;
	}
	
	@Override
	public boolean onKeyEvent(KeyEvent e) {
		if(e.getType() == Event.Type.KEY_PRESSED)
			if(e.getKeyCode() == GLFW.GLFW_KEY_ESCAPE)
				getCoreEngine().exit();
		return false;
	}
	
	
	
}
