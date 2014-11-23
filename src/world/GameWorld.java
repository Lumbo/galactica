package world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import controller.Camera;
import controller.Controller;
import entity.Quad;
import entity.Sphere;
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
	private boolean isRenderingLines;
	
	private Player player;
	
	private List<Sphere> sphereList = new ArrayList<Sphere>();
	private List<Quad> quadList = new ArrayList<Quad>();
	
	private GameWorld(){
		
		// initiate everything that we need
		this.controller = new Controller(this);
		this.player = new Player("Lumbo");
		this.renderer = new Renderer(this, controller);
		
		
		renderer.initRenderer();
		getPlayerCamera().initProjection();
		controller.initKeyboard();
		
	}
	
	public static GameWorld getInstance(){
		if(gameWorld == null){
			gameWorld = new GameWorld();
		}
		return gameWorld;
	}
	
	
	/**************************************/
	/*/
	 * 		Render specific shit
	 */
	/**************************************/
	
	public int getRenderDelta(){
		return renderer.getFpsDelta();
	}
	
	public void isWorldRepresentedAsLines(boolean bool){
		isRenderingLines = bool;
	}
	
	public boolean isWorldRepresentedAsLines(){
		return isRenderingLines;
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
