package org.martin.applicationTesting;

import org.martin.graphics.models.primitives.*;
import org.martin.math.*;
import org.martin.scene.*;
import org.martin.util.*;

public class Bullet extends GameObject {
	
	private Vector3f velocity;
	
	private final float lifeTime = 10.0f;
	private float speed = 10.0f;
	
	public boolean shouldRemove = false;
	
	private float currentTime = 0.0f;
	
	
	public Bullet(Vector3f direction) {
		super();
		this.velocity = direction.multiplied(speed);
		
		Cube cube = new Cube();
		setModel(cube);
		setScale(0.05f, 0.05f, 0.2f);
	}

	public void update() {
		move(velocity.multiplied(Time.getDeltaTime()));
		currentTime += Time.getDeltaTime();
		if(currentTime >= lifeTime) {
			removeFromParent();
			shouldRemove = true;
		}
	}
	
}
