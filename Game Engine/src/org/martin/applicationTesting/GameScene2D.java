package org.martin.applicationTesting;

import org.lwjgl.glfw.*;
import org.martin.event.*;
import org.martin.event.types.*;
import org.martin.graphics.models.primitives.*;
import org.martin.input.*;
import org.martin.math.*;
import org.martin.scene.*;
import org.martin.util.*;

public class GameScene2D extends Scene2D {

	Sprite sprite;
	Sprite sprite2;

	GameObject object = new GameObject();
	GameObject object2 = new GameObject();
	
	GameObject objects[] = new GameObject[1000];
	
	@Override
	public void willAppear() {
		sprite = new Sprite("texture");
		sprite2 = new Sprite("default_texture");
		object.setModel(sprite);
		object2.setModel(sprite2);
		addChild(object);
		object.addChild(object2);
		object2.setPosition(100f, 100f, 0.1f);
		
		for(int i = 0; i < 1000; i++) {
			GameObject obj = new GameObject();
			if(i % 2 == 0)
				obj.setModel(sprite);
			else
				obj.setModel(sprite2);
			if(i % 3 == 0)
				obj.isHidden = true;
			obj.setScale(0.5f, 0.5f, 1.0f);
			obj.setPosition(i, i, i);
			addChild(obj);
			objects[i] = obj;
		}
		
	}
	
	@Override
	public void update() {
		//getCoreEngine().getMainCamera().move();
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)) {
			object.move(0, 250f * Time.getDeltaTime(), 0);
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
			object.move(-250f * Time.getDeltaTime(), 0, 0);
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
			object.move(0, -250f * Time.getDeltaTime(), 0);
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
			object.move(250f * Time.getDeltaTime(), 0, 0);
		}
		object.setRotation(Quaternion.rotation(Time.getElapsedTime(), 0, 0.1f, -0.9f));
		Vector3f desiredPos = new Vector3f(Input.mousePos().x, Input.mousePos().y, 0);
		for(GameObject obj : objects) {
			desiredPos.z = obj.getTransform().getPosition().z;
			obj.setPosition(MathExtras.lerp(obj.getTransform().getPosition(), desiredPos, Time.getDeltaTime()));
		}
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
