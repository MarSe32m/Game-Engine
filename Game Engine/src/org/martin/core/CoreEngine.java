package org.martin.core;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import org.martin.scene.*;

public class CoreEngine {
	private long window;
	
	private int width;
	private int height;
	private String title;
	
	private Scene currentScene;
	
	private double tickRate;
	
	public CoreEngine(int width, int height, String title, int fps) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.tickRate = 1.0 / (double) fps;
	}
	
	public void start(Scene scene) {
		this.currentScene = scene;
		this.currentScene.setCoreEngine(this);
		new Thread(() -> initEngine(), "Engine Main Thread").start();
	}
	
	private void initEngine() {
		System.out.println("Game Engine Version 0.0.1 Alpha, LWJGL version: " + Version.getVersion());
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		// Antialiasing
		glfwWindowHint(GLFW_STENCIL_BITS, 4);
		glfwWindowHint(GLFW_SAMPLES, 4);
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);
			width = pWidth.get();
			height = pHeight.get();
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		//Input callback
		//glfwSetKeyCallback(window, keyInput);
		//glfwSetMouseButtonCallback(window, mouseInput);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		engineLoop();
	}
	
	private void engineLoop() {
		// Set up size callbacks, projection matrices etc.
		
		double timePassed = 0;
		double lastUpdateTime = Time.getTimeMillis();
		
		int updates = 0;
		int renders = 0;
		
		double lastTickRateCheck = Time.getTimeMillis();
		
		while(!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			double currentTime = System.currentTimeMillis();
			
			while(timePassed >= tickRate) {
				
				updates++;
				double updateTime = Time.getTimeMillis();
				Time.setDelta((float)(updateTime - lastUpdateTime) / 1000.0f);
				lastUpdateTime = updateTime;
				
				currentScene.update();
				//evaluateActions(dt); -> Actions Engine -> currentScene.didevaluateActions
				//simulatePhysics(dt, currentScene); -> Physics Engine -> currentScene.didSimulatePhysics
				//applyConstraints(dt); -> Constraints Engine -> currentScene.didApplyConstraints
				
				updateTime = Time.getTimeMillis() - updateTime;
				timePassed -= tickRate;
				try {
					long timeToSleep = (long) (tickRate * 1000 - timePassed);
					if (timeToSleep >= 5)
						Thread.sleep(timeToSleep - 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			
			renders++;
			// Call RenderEngine methods
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			// Have ^ line inside it
			
			int error = glGetError();
			if(error != GL_NO_ERROR)
				System.err.println(error);
			
			glfwSwapBuffers(window);
			
			if(currentTime - lastTickRateCheck >= 1000) {
				glfwSetWindowTitle(window, title + ", UPS: " + updates + ", FPS: " + renders + ", " + "delta time: " + Time.getDeltaTime());

				lastTickRateCheck = currentTime;
				updates = 0;
				renders = 0;
			}
		}
		stop();
	}
	
	public void presentScene(Scene scene) {
		currentScene.willDisappear();
		currentScene = scene;
		currentScene.setCoreEngine(this);
		currentScene.didAppear();
	}
	
	private void stop() {
		// Clean up
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public void setPreferredFPS(int fps) {
		tickRate = fps > 300 ? 1.0 / (double) 300 : 1.0 / (double) fps;	
	}
	
}
