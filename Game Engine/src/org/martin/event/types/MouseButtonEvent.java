package org.martin.event.types;

import org.martin.event.*;

public class MouseButtonEvent extends Event {

	protected int button;
	protected float x, y;
	
	public MouseButtonEvent(int button, float x, float y, Type type) {
		super(type);
		this.button = button;
		this.x = x;
		this.y = y;
	}

	public int getButton() {
		return button;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
}
