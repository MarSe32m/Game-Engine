package org.martin.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.martin.core.*;
import org.martin.event.types.*;
import org.martin.math.*;

public class Mouse extends GLFWMouseButtonCallback {
	
	private boolean[] mouseButtons = new boolean[64];
	private boolean[] last_mouse_button_states = new boolean[64];

	private boolean[] buttons_down = new boolean[64];
	private ArrayList<Integer> button_down_codes = new ArrayList<Integer>();
	
	private boolean[] buttons_up = new boolean[64];
	private ArrayList<Integer> button_up_codes = new ArrayList<Integer>();
	
	private long window;
	
	public void invoke(long window, int button, int action, int mods) {
		this.window = window;
		
		if(button == GLFW_KEY_UNKNOWN)
			return;
		mouseButtons[button] = action != GLFW_RELEASE;
		
		if(last_mouse_button_states[button] != mouseButtons[button]) {
			if(mouseButtons[button]) {
				MousePressedEvent event = new MousePressedEvent(button, currentPos.x, currentPos.y);
				Input.onEvent(event);
				buttons_down[button] = true;
				button_down_codes.add(button);
			} else {
				MouseReleasedEvent event = new MouseReleasedEvent(button, currentPos.x, currentPos.y);
				Input.onEvent(event);
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
	
	public boolean isMouseButtonDown(int button) {
		return mouseButtons[button];
	}
	
	public boolean mousePressed(int button) {
		return buttons_down[button];
	}
	
	public boolean mouseReleased(int button) {
		return buttons_up[button];
	}
	
	private Vector2f lastPos = new Vector2f();
	private Vector2f currentPos = new Vector2f();
	private Vector2f deltaPos = new Vector2f();
	
	void updateMouseDelta() {
		_getPosition();
		deltaPos = Vector2f.subtract(currentPos, lastPos);
		if(deltaPos.lengthSquared() > 0) {
			MouseMovedEvent event = new MouseMovedEvent(currentPos.x, currentPos.y, isMouseButtonDown(Input.MOUSE_LEFT));
			Input.onEvent(event);
		}
		lastPos = currentPos;
	}
	
	Vector2f getMouseDelta() {
		return deltaPos;
	}
	
	Vector2f getPosition() {
		return currentPos;
	}
	
	private void _getPosition() {
		if(window == 0)
			return;
		
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, xBuffer, yBuffer);
		double x = xBuffer.get(0) - CoreEngine.getWidth() / 2.0;
		double y = -yBuffer.get(0) + CoreEngine.getHeight() / 2.0;
		currentPos = new Vector2f((float) x, (float) y);
	}
	
}
