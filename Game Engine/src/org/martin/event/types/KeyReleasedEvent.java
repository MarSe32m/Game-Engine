package org.martin.event.types;

import org.martin.event.*;

public class KeyReleasedEvent extends KeyEvent {

	public KeyReleasedEvent(int key) {
		super(key, Event.Type.KEY_RELEASED);
	}

}
