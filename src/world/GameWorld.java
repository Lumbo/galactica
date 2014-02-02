package world;

import java.util.ArrayList;
import java.util.List;








import org.lwjgl.util.vector.Vector3f;

import physics.Physics;
import controller.Camera;
import controller.Controller;
import entity.Quad;
import entity.Sphere;
import entity.Surface;
import entity.Triangle;
import graphics.Renderer;

/**
 * GameWorld is a class that really starts everything. The GameWorld class owns knowledge about everything
 * inside the world. Everything. It does not always have details.
 * 
 * Specifically this class has knowledge about objects that exists inside the GameWorld.
 * 
 * @author lumbo
 *
 */

public class GameWorld {
	
	private Controller controller;
	private Renderer renderer;
	private Surface surface;
	private Physics physics;
	
	private static GameWorld gameWorld;
	
	private float worldX = 100f;
	private float worldY = 100f;
	private float worldZ = 100f;
	
	
	private Player player;
	
	private List<Triangle> triangleList = new ArrayList<Triangle>();
	private List<Sphere> sphereList = new ArrayList<Sphere>();
	private List<Quad> quadList = new ArrayList<Quad>();
	
	private GameWorld(){
		
		// initiate everything that we need
		this.physics = new Physics();
		this.surface = new Surface();
		this.player = new Player("Lumbo");
		this.controller = new Controller(this); 
		this.renderer = new Renderer(this, controller);
		
		
		renderer.updateRenderer();
	}
	
	public static GameWorld getInstance(){
		if(gameWorld == null){
			gameWorld = new GameWorld();
		}
		return gameWorld;
	}
	
	public void populateRandomSquares(){
		for(int i=0; i<1;i++){
			double rand = Math.random()*4;
			quadList.add(new Quad((float) rand, (float) rand, (float) rand, 0, 2, 0));
		}
			
		renderer.addQuads(quadList);
		
	}
	
	public void populateRandomSpheres(){
		double rand = Math.random();
		for (int i=0; i<100; i++){
			sphereList.add(new Sphere((float)rand*100f, 20, 20));
		}
		
		renderer.addSpheres(sphereList);
	}
	
	/**************************************/
	/*/
	 * 		GameWorld specific shit
	 */
	/**************************************/
	
	
	public float getWorldX(){
		return worldX;
	}
	
	public float getWorldY(){
		return worldY;
	}
	
	public float getWorldZ(){
		return worldZ;
	}
	
	
	
	
	
	
	/**************************************/
	/*/
	 * 		Surface specific shit
	 */
	/**************************************/
	
	
	
	public Surface getSurface(){
		return surface;
	}
	
	public void showSurface(boolean show){
		surface.showSurface(show);
	}
	
	public boolean isSurfaceVisible(){
		return surface.isSurfaceVisible();
	}
	
	public Physics getPhysics(){
		return physics;
	}
	
	
	
	/**************************************/
	/*/
	 * 		Render specific shit
	 */
	/**************************************/
	
	public int getRenderDelta(){
		return renderer.getDelta();
	}
	
	public void addSurface(List<Quad> surfaceQuads){
		renderer.addSurface(surfaceQuads);
	}
	
	
	
	
	/**************************************/
	/*/
	 * 		Player specific shit
	 */
	/**************************************/
	
	public String getPlayerName(){
		return player.getName();
	}
	
	public Vector3f getPlayerPosition(){
		return player.getPlayerPosition();
	}
	
	public float getWalkingSpeed(){
		return player.getWalkingSpeed();
	}
	
	public double getPlayerVelocity(){
		return player.getVelocity();
	}
	
	public Vector3f getPlayerRotation(){
		return player.getRotation();
	}
	
	public Camera getPlayerCamera(){
		return player.getPlayerCamera();
	}
	
	
	
	
	
}
