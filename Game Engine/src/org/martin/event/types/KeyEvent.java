package org.martin.event.types;

import org.martin.event.*;

public class KeyEvent extends Event {

	private int key;
	
	public KeyEvent(int key, Type type) {
		super(type);
		this.key = key;
	}

	public int getKeyCode() {
		return key;
	}
	
}
