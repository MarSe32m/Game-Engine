package org.martin.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.*;

import org.lwjgl.glfw.*;
import org.martin.event.types.*;

public class Keyboard extends GLFWKeyCallback {
	private boolean[] keys = new boolean[65536];
	private boolean[] last_key_presses = new boolean[65536];
	
	private boolean[] keys_down = new boolean[65536];
	private ArrayList<Integer> key_down_codes = new ArrayList<Integer>();
	
	private boolean[] keys_up = new boolean[65536];
	private ArrayList<Integer> key_up_codes = new ArrayList<Integer>();
	
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if(key == GLFW_KEY_UNKNOWN)
			return;
		
		keys[key] = action != GLFW_RELEASE;		
		
		if(last_key_presses[key] != keys[key]) {
			if(keys[key]) {
				KeyPressedEvent event = new KeyPressedEvent(key);
				Input.onEvent(event);
				keys_down[key] = true;
				key_down_codes.add(key);
			} else {
				KeyReleasedEvent event = new KeyReleasedEvent(key);
				Input.onEvent(event);
				keys_up[key] = true;
				key_up_codes.add(key);
			}
		}
		last_key_presses[key] = keys[key];
	}

	public void flush() {
		for(int i : key_down_codes) {
			keys_down[i] = false;
		}
		for(int i : key_up_codes) {
			keys_up[i] = false;
		}
		key_down_codes.clear();
		key_up_codes.clear();
	}
	
	// Is it currently down, returns true every frame its held down
	public boolean isKeyDown(int key) {
		return keys[key];
	}
	
	// Is the key pressed down during that frame, returns true on the frame the key was pressed down
	public boolean keyPressed(int key) {
		return keys_down[key];
	}
	
	// Is the key released during that frame, returns true on the frame that key was released
	public boolean keyReleased(int key) {
		return keys_up[key];
	}
	
	
}
