package org.martin.applicationTesting;

import java.util.*;

import org.lwjgl.glfw.*;
import org.martin.event.*;
import org.martin.event.types.*;
import org.martin.graphics.models.primitives.*;
import org.martin.input.*;
import org.martin.math.*;
import org.martin.scene.*;
import org.martin.util.*;

public class GameScene extends Scene3D {
	
	GameObject object = new GameObject();
	GameObject player = new GameObject();
	GameObject object2 = new GameObject();

	Cube cube;
	
	public LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	
	boolean zoomed = false;
	
	@Override
	public void willAppear() {
		cube = new Cube();
		object2.setModel(cube);
		object2.setScale(1f, 0.5f, 0.5f);
		object2.setPosition(new Vector3f(2.0f, 2.0f, -2.0f));
		object.addChild(object2);
		
		object.setModel(cube);
		addChild(object);
		
		player.setModel(cube);
		addChild(player);
		
		for(int i = 1; i < 1000; i++) {
			GameObject obj = new GameObject();
			obj.setPosition(new Vector3f((float) Math.random() * 30f, (float) Math.random() * 30f, -(float) Math.random() * 30f));
			obj.setRotation(Quaternion.rotation((float) (Math.random() * Math.PI), -1, -1, 0));
			obj.setModel(cube);
			object.addChild(obj);
		}

		object.setPosition(new Vector3f(0.0f, 0.0f, -10f));
		Input.hideMouse();
		Input.setMousePosition(new Vector2f());
		Input.flush();
		getCoreEngine().getMainCamera().isArcball = true;
		getCoreEngine().getMainCamera().arcDistance = 10.0f;
		
	}
	
	float rotX = 0.0f;
	float rotY = 0.0f;
	@Override
	public void update() {
		if(getCoreEngine().getMainCamera().isArcball) {
			move();			
		} else {
			getCoreEngine().getMainCamera().move();		
		}
		
		Vector2f mousePos = Input.mousePos();
		Camera mainCamera = getCoreEngine().getMainCamera();
		mainCamera.targetPos = player.getTransform().getPosition();
		if(mousePos.x != 0 || mousePos.y != 0) {
			rotX = Math.min(Math.max(rotX + mousePos.y * Time.getDeltaTime() * 0.05f * (zoomed ? 0.5f : 1f), -(float)Math.PI / 2.0f), (float)Math.PI / 2.0f);
			rotY -= mousePos.x * Time.getDeltaTime() * 0.05f * (zoomed ? 0.5f : 1f);
			mainCamera.getTransform().setRotation(new Vector3f(rotX, rotY, 0));
		}
		for(int i = 0; i < bullets.size();) {
			Bullet bullet = bullets.get(i);
			if(!bullet.shouldRemove) {
				bullet.update();
				i++;
				continue;
			} else {
				bullets.remove(i);
			}
		}
		
		Input.setMousePosition(new Vector2f());
	}
	
	public void move() {
		Vector3f movementDirection = new Vector3f();
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)) {
			movementDirection.z -= 0.1f;
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
			movementDirection.z += 0.1f;
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
			movementDirection.x -= 0.1f;
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
			movementDirection.x += 0.1f;
		}
		if(movementDirection.lengthSquared() > 0) {
			player.getTransform().move(Quaternion.rotate(movementDirection, player.getTransform().getQuaternion()));
			player.getTransform().setRotation(Quaternion.interpolate(player.getTransform().getQuaternion(), 
																		getCoreEngine().getMainCamera().getTransform().getQuaternion(), 
																		0.1f));
		}
	}
	
	@Override
	public void willDisappear() {
		Input.unhideMouse();
	}

	@Override
	public boolean onMouseEvent(MouseButtonEvent e) {
		if(e.getType() == Event.Type.MOUSE_PRESSED) {
			if(e.getButton() == Input.MOUSE_LEFT) {
				shoot();
			} else if(e.getButton() == Input.MOUSE_RIGHT) {
				zoomed = true;
				getCoreEngine().getMainCamera().getTransform().setScale(new Vector3f(0.5f, 0.5f, 1.0f));
			}
		} else if(e.getType() == Event.Type.MOUSE_RELEASED) {
			if(e.getButton() == Input.MOUSE_RIGHT) {
				zoomed = false;
				getCoreEngine().getMainCamera().getTransform().setScale(new Vector3f(1.0f, 1.0f, 1.0f));	
			}
		}
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
	
	private void shoot() {
		Vector3f direction = Quaternion.rotate(new Vector3f(0f, 0.01f, -1f), player.getTransform().getQuaternion());
		Bullet bullet = new Bullet(direction);
		Vector3f startPos = player.getTransform().getPosition();
		startPos.add(direction.multiplied(1.2f));
		bullet.setPosition(startPos);
		bullet.setRotation(player.getTransform().getQuaternion());
		bullets.add(bullet);
		addChild(bullet);
	}
	
}
