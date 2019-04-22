package org.martin.event.types;

import org.martin.event.*;

public class MouseMovedEvent extends Event {

	private float x, y;
	private boolean dragged;
	
	public MouseMovedEvent(float x, float y, boolean dragged) {
		super(Type.MOUSE_MOVED);
		this.x = x;
		this.y = y;
		this.dragged = dragged;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isDragged() {
		return dragged;
	}

}
