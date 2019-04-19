package org.martin.core;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.martin.rendering.*;
import org.martin.scene.*;

public class CoreEngine {
	private long window;
	
	private static int width;
	private static int height;
	private String title;
	
	private RenderEngine renderEngine;
	
	private Scene currentScene;
	
	private Camera camera = new Camera();
	
	
	
	private double tickRate;
	
	private Keyboard keyboard = new Keyboard();
	private Mouse mouse = new Mouse();
	
	public CoreEngine(int width, int height, String title, int fps) {
		CoreEngine.width = width;
		CoreEngine.height = height;
		this.title = title;
		if(fps <= 300)
			this.tickRate = 1.0 / (double) fps;
		else
			this.tickRate = 1.0 / 300.0;
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
		
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

		glfwSetKeyCallback(window, keyboard);
		glfwSetMouseButtonCallback(window, mouse);
		
		GL.createCapabilities();
		glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, width, height);
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		
		renderEngine  = new RenderEngine();
		
		engineLoop();
	}
	
	private void engineLoop() {
		// TODO: Set up size callbacks, projection matrices etc.
		setUpSizeCallBack();
		double timePassed = -1;
		double lastUpdateTime = Time.getTimeMillis();
		int updates = 0;
		int renders = 0;
		double lastDynamicLoopTime = Time.getTimeMillis();
		double dynamicDelta = 0;
		double lastTickRateCheck = Time.getTimeMillis();
		
		//TODO: Remove when render framework is more generic and robust
		currentScene.didAppear();
		
		while(!glfwWindowShouldClose(window)) {
			
			
			double currentTime = Time.getTimeMillis();
			
			while(timePassed >= tickRate) {
				glfwPollEvents();
				updates++;
				double updateTime = Time.getTimeMillis();
				Time.setDelta((float)(currentTime - lastUpdateTime) / 1000.0f);
				lastUpdateTime = currentTime;
				
				currentScene.update();
				//evaluateActions(dt); -> Actions Engine -> currentScene.didevaluateActions
				//simulatePhysics(dt, currentScene); -> Physics Engine -> currentScene.didSimulatePhysics
				//applyConstraints(dt); -> Constraints Engine -> currentScene.didApplyConstraints
				keyboard.flush();
				mouse.flush();
				
				updateTime = Time.getTimeMillis() - updateTime;
				timePassed -= tickRate;
				try {
					long timeToSleep = (long) (tickRate * 1000 - updateTime);
					if (timeToSleep >= 5)
						Thread.sleep(timeToSleep - 4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			renders++;
			renderEngine.render(currentScene.getRootObject());
			// Call RenderEngine methods
			//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			// Have ^ line inside it
			// AFTER CLEAR, RENDER THE SCENE
			
			int error = glGetError();
			if(error != GL_NO_ERROR)
				System.err.println(error);
			
			glfwSwapBuffers(window);
			
			dynamicDelta = (currentTime - lastDynamicLoopTime) / 1000.0;
			lastDynamicLoopTime = currentTime;
			timePassed += dynamicDelta;
			
			if(currentTime - lastTickRateCheck >= 1000) {
				glfwSetWindowTitle(window, title + ", UPS: " + updates + ", FPS: " + renders + ", " + "delta time: " + Time.getDeltaTime());

				lastTickRateCheck = currentTime;
				updates = 0;
				renders = 0;
			}
		}
		
		
		stop();
	}
	
	private void setUpSizeCallBack() {
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke(long window, int width, int height) {
				CoreEngine.width = width;
				CoreEngine.height = height;
				glViewport(0, 0, width, height);
				
			}
		});
	}
	
	public void presentScene(Scene scene) {
		setCurrentScene(scene);
	}
	
	private void setCurrentScene(Scene scene) {
		currentScene.willDisappear();
		currentScene = scene;
		currentScene.setCoreEngine(this);
		currentScene.didAppear();
	}
	
	private void stop() {
		// Clean up
		// TODO: Consider removing this line
		currentScene.willDisappear();
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public void setPreferredFPS(int fps) {
		tickRate = fps > 300 ? 1.0 / (double) 300 : 1.0 / (double) fps;	
	}
	
}
