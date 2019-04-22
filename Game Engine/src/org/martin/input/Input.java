package org.martin.input;

import static org.lwjgl.glfw.GLFW.*;

import org.martin.event.*;
import org.martin.math.*;
import org.martin.scene.*;

public class Input {
	private static Input s_Instance = new Input();

	private Mouse mouse = new Mouse();
	private Keyboard keyboard = new Keyboard();
	
	public static final int MOUSE_LEFT = GLFW_MOUSE_BUTTON_1;
	public static final int MOUSE_RIGHT = GLFW_MOUSE_BUTTON_2;
	
	Scene currentScene;
	
	private Input() {}

	public static Keyboard getKeyboard() {
		return s_Instance.keyboard;
	}
	
	public static boolean isKeyPressed(int key) {
		return s_Instance.keyboard.keyPressed(key);
	}

	public static boolean isKeyReleased(int key) {
		return s_Instance.keyboard.keyReleased(key);
	}
	
	public static boolean isKeyDown(int key) {
		return s_Instance.keyboard.isKeyDown(key);
	}
	
	public static Mouse getMouse() {
		return s_Instance.mouse;
	}
	
	public static boolean isMouseButtonDown(int button) {
		return s_Instance.mouse.isMouseButtonDown(button);
	}
	
	public static boolean isMouseButtonPressed(int button) {
		return s_Instance.mouse.mousePressed(button);
	}
	
	public static boolean isMouseButtonReleased(int button) {
		return s_Instance.mouse.mouseReleased(button);
	}
	
	public static Vector2f mousePos() {
		return s_Instance.mouse.getPosition();
	}
	
	public static Vector2f mouseDelta() {
		return s_Instance.mouse.getMouseDelta();
	}
	
	public static void setScene(Scene scene) {
		s_Instance.currentScene = scene;
	}
	
	static void onEvent(Event event) {
		s_Instance._onEvent(event);
	}
	
	private void _onEvent(Event event) {
		s_Instance.currentScene.onEvent(event);
	}
	
	public static void update() {
		s_Instance.mouse.updateMouseDelta();
	}
	
	public static void flush() {
		s_Instance.mouse.flush();
		s_Instance.keyboard.flush();
	}
	
}
