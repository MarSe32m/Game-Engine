package org.martin.event.types;

import org.martin.event.*;

public class MousePressedEvent extends MouseButtonEvent {

	public MousePressedEvent(int button, float x, float y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}

}
