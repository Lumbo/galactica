package graphics;


import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
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
import org.lwjgl.opengl.GL41;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.GLUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import physics.Physics;
import vehicles.Ship;
import vehicles.parts.Engine;
import vehicles.ships.FalconShip;
import world.GameWorld;
import world.Player;
import controller.Camera;
import controller.Controller;
import entity.BaseEntity;
import entity.Light;
import entity.Quad;
import entity.Sphere;

public class Renderer {
	
	private Controller controller;
	private GameWorld gameWorld;
	
	private static double angle = 0;
	
	private int gridSize = 10;
	private float tileSize = 0.02f;
	private int ceilingHeight = 10;
	
	
	private static float zNear = 0.001f;
	private static float zFar = 20000f;

	private List<Quad> quadList = new ArrayList<Quad>();
	private List<Sphere> sphereList = new ArrayList<Sphere>();

    private static long lastFPS;
	private static int fps;
    private static long lastFrame;
    private static boolean vsync = true; 
	
    private boolean printFPS = true;
    
	private double x = 0;
	private double y = 0;
	private double z = 0;

	private Texture earth; 
	
	private Camera cam;
	
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
	
	public void draw(){
		try{
			Display.setDisplayMode(new DisplayMode(1024, 768));
			Display.setTitle("Bjorn");
			Display.create();
			System.out.println("Opengl version is " + GL11.glGetString(GL11.GL_VERSION));
		} catch (LWJGLException e){
			e.printStackTrace();
		}
		
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL11.GL_LIGHT1);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[] {0.2f, 0.2f, 0.2f, 1.0f}));
		glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(new float[] {1.55f, 1.5f, 1.55f, 1f}));
		//glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK); //Don't draw the back side of triangles
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);
		
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective((float) 70, Display.getWidth()/Display.getHeight(), 0.001f, 10000);
		glMatrixMode(GL_MODELVIEW);
		
		
		float position = -100f;
		float rotationSpeed = 0.5f;
		float rotation = 0.0f;
		float lightRotation = 0.0f;
		float lightRotationSpeed = 1.5f;
		
		Model surface = null;
		FalconShip ship = null;
		Light light = null;
		try{
			ship = new FalconShip(OBJLoader.getModel("res/models/ships/falcon/falcon6.obj"));
			surface = OBJLoader.getModel("res/models/surface/flat.obj");
			light = new Light(OBJLoader.getModel("res/models/light/lightbulb.obj"));
			
			
			// TODO: REFACTOR THE NEXT TWO LINES TO THE PROPER PLACE!
			ship.setName("Falcon");
			gameWorld.getPlayer().addShipToPlayer(ship);
			gameWorld.getPlayer().setActiveShip(ship);
			
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
		
		glLight(GL11.GL_LIGHT0, GL_POSITION, asFloatBuffer(new float[]{0.1f, 0.1f, 0.1f, 1.0f}));
		
		// Move away from the 0,0,0 position
		cam.moveY(-10);
		cam.moveX(1);
		cam.moveZ(-10, 1);
		
		
		while (!Display.isCloseRequested()){
			//glUseProgram(shaderProgram);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			// Make it possible to see the lines by pressing L
			if(gameWorld.isWorldRepresentedAsLines()){
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			}
			else{
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			}
			
			glLoadIdentity();
			GL11.glPushMatrix();
			
			// Pull controller input
			pullController();

			// Camera setting thingy
			cam.useView();
			
			rotation += rotationSpeed;
			lightRotation += lightRotationSpeed;
			
			for(int i=0; i<1; i++){
				GL11.glPushMatrix();
				glTranslatef(1, -10, position);
				glRotatef(0, 1, 1, 0);
				GL11.glScalef(20, 20, 20);
				surface.draw();
				GL11.glPopMatrix();
			}
			
			
			ship.draw();

			
			
			// Spin the light around the ship
			light.moveTo((float)-Math.sin(lightRotation/100)*50, 20, ((float)Math.cos(lightRotation/100)*50)-100);
			light.drawSpotLight();
			
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				Display.destroy();
				System.exit(0);
			}
			
			GL11.glEnd();
			GL11.glPopMatrix();
			
			Display.update();
			Display.sync(60);
			
			if(printFPS){
				updateFPS();
			}
		}
		Display.destroy();
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
	
	
	public void rotateCube(double x, double y, double acceleration){

		if(controller.getMouseButton() == 1){
			getAngle(1);
			GL11.glTranslated(x, y, 0);
			GL11.glRotated(angle, controller.getMouseDx(), 0, acceleration);
		}
		else if(controller.getMouseButton() == 2){
			getAngle(-1);
			GL11.glTranslated(x, y, 0);
			GL11.glRotated(angle, 0, controller.getMouseDy(), acceleration);
		}
		else if(controller.getMouseButton() == -1){
			GL11.glTranslated(x, y, 0);
			GL11.glRotated(angle, 0, 0, 1.0f);
		}
		
		if(controller.getMouseScroll()!=0){
			GL11.glTranslated(0, 0, controller.getMouseScroll());
			GL11.glRotated(angle, 0, 0, controller.getMouseScroll());
		}
		
		System.out.println("Angle: " + angle);
		
		//GL11.glTranslated(-x, -y, 0);

		
	}
	
	public static double getAngle(int i){
		if (i>0){
			return angle += 0.15 * fps;	
		}
		else{
			return angle -= 0.15 * fps;
		}
			
		
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
	
	
	
	public void renderSphere(float x, float y, float z){
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		
		Sphere sphere = new Sphere(50.0f, 100, 1000);
		sphere.draw(50.0f, 100, 1000);
		
		GL11.glPopMatrix();
		
	}
	
	
	public void addQuad(Quad quad){
		quadList.add(quad);
	}
	
	public void addQuads(List<Quad> quad){
		quadList.addAll(quad);
	}
	
	public List<Quad> getQuads(){
		return quadList; 
	}
	
	
	public void addSphere(Sphere sphere){
		
	}
	
	public void addSpheres(List<Sphere> spheres){
		sphereList.addAll(spheres);
	}
	
	private static FloatBuffer asFloatBuffer(float [] floats){
		FloatBuffer res = BufferUtils.createFloatBuffer(floats.length);
		res.put(floats);
		res.flip();
		return res;
	}
	
}
