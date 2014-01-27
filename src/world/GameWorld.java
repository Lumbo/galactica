package world;

import java.util.ArrayList;
import java.util.List;






import org.lwjgl.util.vector.Vector3f;

import controller.Camera;
import controller.Controller;
import entity.Quad;
import entity.Sphere;
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
	private static GameWorld gameWorld;
	private Renderer renderer;
	
	private Player player;
	
	private List<Triangle> triangleList = new ArrayList<Triangle>();
	private List<Sphere> sphereList = new ArrayList<Sphere>();
	private List<Quad> quadList = new ArrayList<Quad>();
	
	private GameWorld(){
		
		// initiate everything that we need
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
		/*for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				for(int k=0; k<10; k++){
					double rand = Math.random()*10;
					quadList.add(new Quad(rand, rand, rand));
				}
			}
		}*/
		
		for (int i=0; i<10;i++){
			quadList.add(new Quad(10+i, 10+i, 10+i));
		}
		
		double rand = Math.random()*2;
		quadList.add(new Quad(rand, rand, rand));
		
		renderer.addQuads(quadList);
		
	}
	
	public void populateRandomSpheres(){
		double rand = Math.random();
		for (int i=0; i<100; i++){
			sphereList.add(new Sphere((float)rand*100f, 20, 20));
		}
		
		renderer.addSpheres(sphereList);
		
	}
	
	
	public void generateRandomSurface(int size){
		for(int i=0; i<size; i++){
			
		}
	}
	
	
	
	/**************************************/
	/*/
	 * 		Render specific shit
	 */
	/**************************************/
	
	public int getRenderDelta(){
		return renderer.getDelta();
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
