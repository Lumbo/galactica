package graphics;


import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;

import vehicles.Ship;
import world.GameWorld;
import controller.Camera;
import controller.Controller;
import entity.Quad;
import entity.Sphere;
import entity.Surface;
import entity.Triangle;

public class Renderer {
	
	private Controller controller;
	private GameWorld gameWorld;
	private Surface surface;
	
	private static double angle = 0;
	
	private int gridSize = 10;
	private float tileSize = 0.02f;
	private int ceilingHeight = 10;
	
	//for the text
	private static UnicodeFont font;
	private static DecimalFormat formatter = new DecimalFormat("#.##");
	
	private static FloatBuffer perspectiveProjectionMatrix = BufferUtils.createFloatBuffer(16);
	private static FloatBuffer orthographicProjectionMatrix = BufferUtils.createFloatBuffer(16); 
	
	private List<Quad> quadList = new ArrayList<Quad>();
	private List<Quad> surfaceQuads = new ArrayList<Quad>();
	private List<Triangle> triangleList = new ArrayList<Triangle>();
	private List<Sphere> sphereList = new ArrayList<Sphere>();
	private List<Ship> shipList = new ArrayList<Ship>();

    private static long lastFPS;
	private static int fps;
    private static long lastFrame;
    private static boolean vsync = true; 
	
    private boolean printFPS = true;
    
	private double x = 0;
	private double y = 0;
	private double z = 0;

	private Texture earthTexture; 
	
	private Camera cam;
	
	public Renderer(GameWorld gameWorld, Controller controller) {
		this.gameWorld = gameWorld;
		this.controller = controller;
		this.surface = gameWorld.getSurface(); 
		
		initOpenGL(); //Initiate opengl
		
	}
	
	public void updateRenderer(){
		while(!Display.isCloseRequested()){
			Display.setInitialBackground(0, 0, 1.0f);
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
		setUpCamera();
		setUpLighting();
		setUpFonts();
		
		//Set up surface
		surface.generateSurface(100);
		surfaceQuads.addAll(surface.getQuads());
		
		getDelta();
		lastFPS = getTime();
	}
	
	
	public void setUpCamera(){
		cam = gameWorld.getPlayerCamera();
		
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, perspectiveProjectionMatrix);
		//GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, orthographicProjectionMatrix);
		GL11.glLoadMatrix(perspectiveProjectionMatrix);
		GL11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);
		
		
	}
	
	private void setUpLighting(){
		//GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glLightModelf(GL11.GL_LIGHT0, GL11.GL_AMBIENT);
		
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	
	@SuppressWarnings("unchecked")
	private static void setUpFonts(){
		java.awt.Font awtFont = new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addAsciiGlyphs();
		try{
			font.loadGlyphs();
		} catch (SlickException e){
			e.printStackTrace();
			System.exit(1);
		}
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
		
		//print debugging text
		/*
		//GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadMatrix(orthographicProjectionMatrix);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_LIGHTING);
		font.drawString(10, 10, "Camera: [x=" + formatter.format(cam.getX()) + 
				",y=" + formatter.format(cam.getY()) + 
				",z=" + formatter.format(cam.getZ()) + "]");
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		
		//GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadMatrix(perspectiveProjectionMatrix);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		*/
		
		//Draw Surface
		GL11.glBegin(GL11.GL_QUADS);
		surface.drawSurface();
		
		for(Quad q1 : quadList){
			for(Quad q2 : surfaceQuads){
				if(q1 != q2){
					if(q1.isColliding(q2)){
						System.out.println("Quad1: " + q1 + " collided with Quad 2 " + q2);
						q1.applyForce(new Vector3f(0,0,0));
						q1.setDirection(new Vector3f(0,0,0));
						q1.setIsMoveable(false);
					}
				}
			}
		}
		
		
		
		//Draw all the quads
		for(Quad q : quadList){
			System.out.println("Q1 Coord x: " + q.getPosition().getX() + 
					", y: " + q.getPosition().getY() + 
					", z: " + q.getPosition().getZ());
			System.out.println("Q2 Coord x: " + q.getPosition().getX() + 
					", y: " + q.getPosition().getY() + 
					", z: " + q.getPosition().getZ());
			if(q.isMoveable()){
				q.draw();
				q.applyForce(new Vector3f(0, gameWorld.getPhysics().getGravity(), 0));
				q.setPosition(new Vector3f(
						q.getPosition().getX()+q.getDirection().getX(), 
						q.getPosition().getY()+q.getDirection().getY(),
						q.getPosition().getZ()+q.getDirection().getZ()));
				GL11.glTranslatef(
						q.getPosition().getX(), 
						q.getPosition().getY(), 
						q.getPosition().getZ());
			}
			else{
				q.draw();
				System.out.println("DAFUUQ");
			}					
		}
		
		//Draw all spheres
		for(Sphere s : sphereList){
			s.draw();
			s.applyForce(new Vector3f(0, gameWorld.getPhysics().getGravity(), 0));
		}		
		
		//Draw the players ship
		for (Ship s : shipList){
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
		
		//Print cam coords
		cam.printCameraLocation();
		cam.printCameraRotation();
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
	
	
	public static double getAngle(int i){
		if (i>0){
			return angle += 0.15 * fps;	
		}
		else{
			return angle -= 0.15 * fps;
		}
	}

	
	public void addQuad(Quad quad){
		quadList.add(quad);
	}
	
	public void addQuads(List<Quad> quad){
		quadList.addAll(quad);
	}
	
	public void addSurface(List<Quad> surface){
		surfaceQuads.addAll(surface);
	}
	
	public List<Quad> getQuads(){
		return quadList; 
	}
	
	
	public void addSphere(Sphere sphere){
		sphereList.add(sphere);
	}
	
	public void addSpheres(List<Sphere> spheres){
		sphereList.addAll(spheres);
	}
	
	
	
}
