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
	
	public static float minimalRadius(float[] points) {
		float radius = 0.0f;
		float[] midPoint = {0f, 0f, 0f};
		for(int i = 0; i < points.length; i+=3) {
			midPoint[0] += points[i];
			midPoint[1] += points[i + 1];
			midPoint[2] += points[i + 2];
		}
		midPoint[0] /= (float)points.length / 3;
		midPoint[1] /= (float)points.length / 3;
		midPoint[2] /= (float)points.length / 3;
		for(int i = 0; i < points.length; i+=3) {
			float dist = distance(new float[]{points[i], points[i + 1], points[i + 2]}, midPoint);
			if(dist > radius) {
				radius = dist;
			}
		}
		return radius;
	}
	
	private static float distance(float[] points, float[] toPoints) {
		float x = points[0] - toPoints[0];
		float y = points[1] - toPoints[1];
		float z = points[2] - toPoints[2];
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public static Vector3f lerp(Vector3f start, Vector3f end, float t) {
		return new Vector3f(lerp(start.x, end.x, t), 
							lerp(start.y, end.y, t), 
							lerp(start.z, end.z, t));
	}
	
	public static float lerp(float start, float end, float t) {
		return start + (end - start) * t;
	}
	
}
