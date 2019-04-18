package org.martin.math;

import static java.lang.Math.*;

public class Matrix4f {
	public float matrix[] = new float[4 * 4];
	
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		
		for(int i = 0; i < 4 * 4; i++)
			result.matrix[i] = 0.0f;

		result.matrix[0 + 0 * 4] = 1.0f;
		result.matrix[1 + 1 * 4] = 1.0f;
		result.matrix[2 + 2 * 4] = 1.0f;
		result.matrix[3 + 3 * 4] = 1.0f;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix) {
			Matrix4f result = new Matrix4f();
			
			for(int y = 0; y < 4; y++)
				for(int x = 0; x < 4; x++) {
					float sum = 0.0f;
					for(int k = 0; k < 4; k++)
						sum += this.matrix[k + y * 4] * matrix.matrix[x + k * 4];
					result.matrix[x + y  *4] = sum;
				}
					
			
			return result;
	}
	
	public static Matrix4f translate(Vector3f translation) {
		Matrix4f matrix = identity();
		matrix.matrix[0 + 3 * 4] = translation.x;
		matrix.matrix[1 + 3 * 4] = translation.y;
		matrix.matrix[2 + 3 * 4] = translation.z;
		return matrix;
	}
	
	public static Matrix4f translate(Vector4f translation) {
		Matrix4f matrix = identity();
		matrix.matrix[0 + 3 * 4] = translation.x;
		matrix.matrix[1 + 3 * 4] = translation.y;
		matrix.matrix[2 + 3 * 4] = translation.z;
		return matrix;
	}
	
	public static Matrix4f scale(float xScale, float yScale) {
		Matrix4f matrix = identity();
		matrix.matrix[0 + 0 * 4] = xScale;
		matrix.matrix[1 + 1 * 4] = yScale;
		return matrix;
	}
	
	public static Matrix4f scale(float xScale, float yScale, float zScale) {
		Matrix4f matrix = identity();
		matrix.matrix[0 + 0 * 4] = xScale;
		matrix.matrix[1 + 1 * 4] = yScale;
		matrix.matrix[2 + 2 * 4] = zScale;
		return matrix;
	}
	
	public static Matrix4f scale(Vector3f scaling) {
		return scale(scaling.x, scaling.y, scaling.z);
	}
	
	public static Matrix4f rotate(float angle) {
		Matrix4f matrix = identity();
		float cos = (float) cos(angle);
		float sin = (float) sin(angle);
		matrix.matrix[0 + 0 * 4] = cos;
		matrix.matrix[0 + 1 * 4] = -sin;
		matrix.matrix[1 + 0 * 4] = sin;
		matrix.matrix[1 + 1 * 4] = cos;
		return matrix;
	}
	
	public static Matrix4f rotate(float angle, float x, float y, float z) {
		Matrix4f result = identity();
		float cos = (float) cos(angle);
		float sin = (float) sin(angle);
		float omc = 1.0f - cos;
		
		result.matrix[0 + 0 * 4] = x * omc + cos;
		result.matrix[1 + 0 * 4] = y * x * omc + z * sin;
		result.matrix[2 + 0 * 4] = x * z * omc - y * sin;
		
		result.matrix[0 + 1 * 4] = x * y * omc - z * sin;
		result.matrix[1 + 1 * 4] = y * omc + cos;
		result.matrix[2 + 1 * 4] = y * z * omc + x * sin;
		
		result.matrix[0 + 2 * 4] = x * z * omc + y * sin;
		result.matrix[1 + 2 * 4] = y * z * omc - x * sin;
		result.matrix[2 + 2 * 4] = z * omc + cos;
		
		return result;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = identity();
		result.matrix[0 + 0 * 4] = 2.0f / (right - left);
		result.matrix[1 + 1 * 4] = 2.0f / (top - bottom);
		result.matrix[2 + 2 * 4] = 2.0f / (near - far);
		result.matrix[0 + 3 * 4] = -(left + right) / (left - right);
		result.matrix[1 + 3 * 4] = -(bottom + top) / (bottom - top);
		result.matrix[2 + 3 * 4] = -(near + far) / (far - near);
		return result;
	}
	
	public static Matrix4f transformationMatrix(Matrix4f translation, Matrix4f scale, Matrix4f rotation) {
		return scale.multiply(rotation).multiply(translation);
		
	}
}
