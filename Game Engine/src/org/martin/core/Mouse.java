package org.martin.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.martin.math.*;

public class Mouse extends GLFWMouseButtonCallback {

	public static final int MOUSE_LEFT = GLFW_MOUSE_BUTTON_1;
	public static final int MOUSE_RIGHT = GLFW_MOUSE_BUTTON_2;
	
	private static boolean[] mouseButtons = new boolean[64];
	private static boolean[] last_mouse_button_states = new boolean[64];

	private static boolean[] buttons_down = new boolean[64];
	private static ArrayList<Integer> button_down_codes = new ArrayList<Integer>();
	
	private static boolean[] buttons_up = new boolean[64];
	private static ArrayList<Integer> button_up_codes = new ArrayList<Integer>();
	
	private static long window = 0;
	
	public void invoke(long window, int button, int action, int mods) {
		Mouse.window = window;
		mouseButtons[button] = action != GLFW_RELEASE;
		
		if(last_mouse_button_states[button] != mouseButtons[button]) {
			if(mouseButtons[button]) {
				buttons_down[button] = true;
				button_down_codes.add(button);
			} else {
				buttons_up[button] = true;
				button_up_codes.add(button);
			}
		}
		last_mouse_button_states[button] = mouseButtons[button];
	}

	public void flush() {
		for(int i : button_down_codes) {
			buttons_down[i] = false;
		}
		for(int i : button_up_codes) {
			buttons_up[i] = false;
		}
		button_down_codes.clear();
		button_up_codes.clear();
	}
	
	public static boolean isMouseButtonDown(int button) {
		return mouseButtons[button];
	}
	
	// Returns true on the frame the mouse button is released
	public static boolean mousePressed(int button) {
		return buttons_down[button];
	}
	
	// Returns true on the frame the mouse button is released
	public static boolean mouseReleased(int button) {
		return buttons_up[button];
	}
	
	public static Vector2f getPosition() {
		if(window == 0)
			return new Vector2f();
		
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, xBuffer, yBuffer);
		double x = xBuffer.get(0) - CoreEngine.getWidth() / 2.0;
		double y = -yBuffer.get(0) + CoreEngine.getHeight() / 2.0;
		return new Vector2f((float) x, (float) y);
	}
	
}
