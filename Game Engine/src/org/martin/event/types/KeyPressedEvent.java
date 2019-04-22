package org.martin.event.types;

import org.martin.event.*;

public class KeyPressedEvent extends KeyEvent {

	public KeyPressedEvent(int key) {
		super(key, Event.Type.KEY_PRESSED);
	}

}
