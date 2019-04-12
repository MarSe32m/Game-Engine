package org.martin.core;

public class Time {
	public static long SECOND_FROM_MILLIS = 1000000L;
	
	private static float deltaTime;
	
	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}
	
	public static long getTimeNanos() {
		return System.nanoTime();
	}
	
	public static float getDeltaTime() {
		return deltaTime;
	}
	
	public static void setDelta(float delta) {
		Time.deltaTime = delta;
	}
	
}
