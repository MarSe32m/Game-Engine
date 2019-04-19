package org.martin.core;

import static org.lwjgl.glfw.GLFW.*;

import java.util.*;

import org.lwjgl.glfw.*;

public class Keyboard extends GLFWKeyCallback {
	private static boolean[] keys = new boolean[65536];
	private static boolean[] last_key_presses = new boolean[65536];
	
	private static boolean[] keys_down = new boolean[65536];
	private static ArrayList<Integer> key_down_codes = new ArrayList<Integer>();
	
	private static boolean[] keys_up = new boolean[65536];
	private static ArrayList<Integer> key_up_codes = new ArrayList<Integer>();
	
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;		
		
		if(last_key_presses[key] != keys[key]) {
			if(keys[key]) {
				keys_down[key] = true;
				key_down_codes.add(key);
			} else {
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
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
	
	// Is the key pressed down during that frame, returns true on the frame the key was pressed down
	public static boolean keyPressed(int key) {
		return keys_down[key];
	}
	
	// Is the key released during that frame, returns true on the frame that key was released
	public static boolean keyReleased(int key) {
		return keys_up[key];
	}
	
	
}
