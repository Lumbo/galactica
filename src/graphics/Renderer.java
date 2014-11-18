package graphics;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;






import world.GameWorld;
import world.Player;
import controller.Camera;
import controller.Controller;
import entity.Quad;
import entity.Sphere;
import entity.Triangle;

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
	private List<Triangle> triangleList = new ArrayList<Triangle>();
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
		
		initOpenGL(); //Initiate opengl
		
	}
	

	public void updateRenderer(){
		while(!Display.isCloseRequested()){
			Display.update();
			if(vsync){
				Display.sync(60);	//60 fps	
			}
			controllerInput();
			draw();
		}
		
		Display.destroy();
	}

	
	public void initOpenGL(){
		/*GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GL11.glOrtho(-160, 160, -120, 120, zNear, zFar);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE);
		*/
		
		cam = gameWorld.getPlayerCamera();
		
		getDelta();
		lastFPS = getTime();
		
		
	}
	
	public void draw(){
		//Clear screen buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//load identify matrix
		GL11.glLoadIdentity();
		
		//Use camera view
		cam.useView();
		
		// set quad color
		GL11.glColor3f(0.5f, 0.5f, 1.0f);
		
		// draw quad
		GL11.glPushMatrix();
		//x = x+controller.getMouseDx();
		//y = y+controller.getMouseDy();
		//rotateCube(controller.getMouseDx(), controller.getMouseDy(), 1);
		
		// draw sphere (earth or w/e)
        //renderSphere(-2f, -0.5f, -1f);
        
        /*
        GL11.glLineWidth(1000.0f);
        
        GL11.glBegin(GL11.GL_LINES);
        
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glVertex3f(50.0f, 50.0f, 10.0f);
        GL11.glEnd();
		*/
        
        
		GL11.glTranslated(x, y, z);
		GL11.glRotated(angle, x, y, 1);

		
		// generate grid
		int ceilingDisplayList = GL11.glGenLists(1);
		GL11.glNewList(ceilingDisplayList, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		
		GL11.glVertex3d(-gridSize, ceilingHeight, -gridSize);
		GL11.glTexCoord2d(gridSize*10*tileSize, 0);
		
		GL11.glVertex3d(gridSize, ceilingHeight, -gridSize);
		GL11.glTexCoord2d(gridSize*10*tileSize, gridSize*10*tileSize);
		
		GL11.glVertex3d(gridSize, ceilingHeight, gridSize);
		GL11.glTexCoord2d(0, gridSize*10*tileSize);
		
		GL11.glVertex3d(-gridSize, ceilingHeight, gridSize);
		
		GL11.glEnd();
		GL11.glEndList();

		GL11.glBegin(GL11.GL_QUADS);
		
		//Draw all the quads
		for(Quad q : quadList){
			q.draw();
		}
		
		
		//Draw all spheres
		for(Sphere s : sphereList){
			s.draw();
		}
		
		
		GL11.glEnd();
		
		
		//Draw all the triangles
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(Triangle t : triangleList){
			t.draw();
		}
		
		
		GL11.glEnd();
		GL11.glPopMatrix();
		
		
		
		
		if(printFPS){
			updateFPS();
		}
	}
	
	
	public int getDelta(){
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	
	private void updateFPS(){
		if(getTime() - lastFPS > 1000){
			if(printFPS){
				System.out.println("FPS: " + fps);
			}
			updateTitle();
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateTitle(){
		Display.setTitle("Player " + gameWorld.getPlayerName() + " fps: " + fps);
	}
	
	
	
	public void controllerInput(){
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
	
	
}
