package org.martin.event.types;

import org.martin.event.*;

public class MouseReleasedEvent extends MouseButtonEvent {

	public MouseReleasedEvent(int button, float x, float y) {
		super(button, x, y, Event.Type.MOUSE_RELEASED);
	}

}
