package org.martin.math;

public class MathExtras {
	public static float sqrt(float y) {
		float x = y * 0.25f;
		float diff = y - x * x;
		while(Math.abs(diff) > 0.0000001) {
			x = x + diff / (2 * x);
			diff = y - x * x;
		}
		return x;
	}
	
	public static double sqrt(double y) {
		double x = y * 0.25f;
		double diff = y - x * x;
		int iterations = 0;
		while(Math.abs(diff) > 0.00000001 && iterations < 1000) {
			iterations++;
			x = x + diff / (2 * x);
			diff = y - x * x;
		}
		return x;
	}
}
