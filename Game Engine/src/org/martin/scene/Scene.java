package org.martin.scene;

import org.martin.core.*;
import org.martin.event.*;
import org.martin.event.types.*;

public abstract class Scene implements EventListener {
	private CoreEngine coreEngine;
	protected final GameObject rootObject;
	
	public Scene() {
		rootObject = new GameObject();
	}
	
	public abstract void init();
	
	public abstract void didAppear();
	
	public abstract void willDisappear();
	
	public abstract void update();
	
	public final void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMouseEvent((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMove((MouseMovedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseEvent((MouseReleasedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyEvent((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyEvent((KeyReleasedEvent) e));
	}
	
	public abstract boolean onMouseEvent(MouseButtonEvent e);
	public abstract boolean onMouseMove(MouseMovedEvent e);
	public abstract boolean onKeyEvent(KeyEvent e);
	
	public final CoreEngine getCoreEngine() {
		return coreEngine;
	}
	
	public final void setCoreEngine(CoreEngine engine) {
		this.coreEngine = engine;
	}
	
	public final GameObject getRootObject() {
		return rootObject;
	}
	
	public final void addChild(GameObject object) {
		rootObject.addChild(object);
	}
	
}
