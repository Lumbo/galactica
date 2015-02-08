package graphics;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.util.glu.GLU.gluPerspective;

//import BjornMain;







import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.GLUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import physics.Physics;
import util.Quat4f;
import vehicles.ships.FalconShip;
import world.GameWorld;
import controller.Camera;
import controller.Controller;
import entity.Light;
import entity.Quad;
import entity.Sphere;
import entity.Surface;

public class Renderer {
	
	private Controller controller;
	private GameWorld gameWorld;
	private Camera cam;
	
	private static int fps;
	private static double angle = 0;
	
	private static float zNear = 0.001f;
	private static float zFar = 20000f;
	
    private static long lastFPS;
    private static long lastFrame;
	
    private boolean printFPS = true;
	
    
	public Renderer(GameWorld gameWorld, Controller controller) {
		this.gameWorld = gameWorld;
		this.controller = controller;
	}

	
	public void initRenderer(){
		cam = gameWorld.getPlayerCamera();
		getFpsDelta();
		lastFPS = getTime();
		draw();
	}
	
	
	// The below function is a huge reason why Java is shit compared to C.
	// All this stuff is needed simply to create a simple buffer of floats
	// which is needed to convert the matrix data stored in the Matrix4f
	// class. Compare this with C where this would all be replaced with
	// a single float[16].
	//
	// XXX: This should probably be placed as a helper function in some
	//      other package.
	private FloatBuffer createFloatBuffer(int size) {
		// Java has not sizeof operator so you are
		// forced to hardcode 4 as the size of a float
		ByteBuffer bb = ByteBuffer.allocateDirect(size * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();

		return fb;
	}

	private FloatBuffer createFloatBuffer(float[] arr) {
		FloatBuffer fb = createFloatBuffer(arr.length);
		fillFloatBuffer(fb, arr);

		return fb;
	}

	private FloatBuffer fillFloatBuffer(FloatBuffer fb, float[] arr) {
		// This is so stupid and unintuitive but you have to reset the
		// buffer internal "pointer" after filling it with data.
		fb.put(arr);
		fb.position(0);

		return fb;
	}
	
	public void useView(Matrix4f mat) {
		FloatBuffer fb = createFloatBuffer(16);
		mat.store(fb);
		fb.position(0); // Annoying
		GL11.glLoadMatrix(fb);
	}
	

	
	public void draw(){
		try{
			Display.setDisplayMode(new DisplayMode(1024, 768));
			Display.setTitle("Galactica");
			Display.create();
			
			System.out.println("Opengl version is " + glGetString(GL_VERSION));
		} catch (LWJGLException e){
			e.printStackTrace();
		}
		
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_LIGHT1);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[] {0.2f, 0.2f, 0.2f, 1.0f}));
		glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(new float[] {1.55f, 1.5f, 1.55f, 1f}));
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK); //Don't draw the back side of triangles
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective((float) 70, Display.getWidth()/Display.getHeight(), zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
		
		Surface surface = null;
		FalconShip ship = null;
		Light light = null;
		try{
			ship = new FalconShip(OBJLoader.getModel("res/models/ships/falcon/falcon8.obj"));
			surface = new Surface(OBJLoader.getModel("res/models/surface/flat.obj"));
			light = new Light(OBJLoader.getModel("res/models/light/lightbulb.obj"));
			
			
			// TODO: REFACTOR THE NEXT TWO LINES TO THE PROPER PLACE!
			ship.setName("Falcon");
			gameWorld.getPlayer().addShipToPlayer(ship);
			gameWorld.getPlayer().setActiveShip(ship);
			ship.moveTo(1, 10, -50.0f);
			
		}catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Could not open file");
		}catch (IOException e){
			e.printStackTrace();
		}
		
		int shaderProgram = glCreateProgram();
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader("res/shaders/shader.vert"));
			String line;
			while ((line = reader.readLine()) != null){
				vertexShaderSource.append(line).append('\n');
			}
			System.out.println(vertexShaderSource.toString());
			reader.close();
		}catch (IOException e){
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader("res/shaders/shader.frag"));
			String line;
			while ((line = reader.readLine()) != null){
				fragmentShaderSource.append(line).append('\n');
			}
			System.out.println(fragmentShaderSource.toString());
			reader.close();
		}catch (IOException e){
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		
		glShaderSource(vertexShader, vertexShaderSource.toString());
		glCompileShader(vertexShader);
		if(glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Vertex shader did not compile");
		}
		glShaderSource(fragmentShader, fragmentShaderSource.toString());
		glCompileShader(fragmentShader);
		if(glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Vertex shader did not compile");
		}
		
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
		
		glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(new float[]{0.1f, 0.1f, 0.1f, 1.0f}));
		
		// Move away from the 0,0,0 position
		cam.moveY(-10);
		cam.moveX(1);
		cam.moveZ(-10);
		
		
		while (!Display.isCloseRequested()){
			//glUseProgram(shaderProgram);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			// Make it possible to see the lines by pressing L
			if(gameWorld.isWorldRepresentedAsLines()){
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			}
			else{
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
			
			glLoadIdentity();
			glPushMatrix();
			
			// Pull controller input
			pullController();
			
			
			Matrix4f res = new Matrix4f();
			Quat4f quat = new Quat4f();

	
			// Surface update
			//Matrix4f.mul(cam.viewMatrix, surface.getViewMatrix(), res);
			quat = Quat4f.getMultiplication(cam.getQuaternion(), surface.getQuaternion());
			useView(res);
			surface.draw();
			
			// Light update
			Matrix4f.mul(cam.viewMatrix, light.getViewMatrix(), res);
			light.drawSpotLight();
			
			// Ship update
			Matrix4f.mul(cam.viewMatrix, ship.getViewMatrix(), res);
			useView(res);
			// Print the plane-axis for the ship
			ship.printDebugVectors();
			ship.draw();
			
			
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				Display.destroy();
				System.exit(0);
			}
			
			glEnd();
			glPopMatrix();
			
			Display.update();
			Display.sync(60);
			
			if(printFPS){
				updateFPS();
			}
		}
		Display.destroy();
		System.exit(0);
	}

	
	/**
	 * This method just figures out a delta by comparing drawn frames and adds
	 * the FPS in the title every second or so. Uses {@link #updateFPS()} to do some updates.
	 * 
	 * @return
	 */
	public int getFpsDelta(){
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	
	private void updateFPS(){
		if(getTime() - lastFPS > 1000){
			Display.setTitle("Player " + gameWorld.getPlayerName() + " fps: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void pullController(){
		controller.keyboardPoll();
		controller.mousePoll();
	}
	
	private Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("JPG", new FileInputStream(new File("res/" + key + ".jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static FloatBuffer asFloatBuffer(float [] floats){
		FloatBuffer res = BufferUtils.createFloatBuffer(floats.length);
		res.put(floats);
		res.flip();
		return res;
	}
	
}
