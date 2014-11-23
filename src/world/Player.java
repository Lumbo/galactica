package world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import controller.Camera;
import vehicles.Ship;

public class Player {
	
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f rotation = new Vector3f(0,0,0);
	
	private List<Ship> shipList = new ArrayList<Ship>();
	
	private String name;
	private Ship currentShip;
	
	private double velocity;
	private float walkingSpeed = 10;
	
	private Camera camera = new Camera();
	
	public Player(String name){
		this.name = name;
		if (currentShip == null){
			velocity = 1;
		}
	}
	
	public String getName(){
		return name;
	}
	
	
	public void addShipToPlayer(Ship ship){
		shipList.add(ship);
	}
	
	public List<Ship> getShips(){
		return shipList;
	}
	
	public Ship getCurrentShip(){
		return currentShip;
	}
	
	public Vector3f getPlayerPosition(){
		return position;
	}
	
	/**
	 * Should always be called to change/choose ship
	 * @param ship
	 */
	public void setActiveShip(Ship ship){
		for(Ship s : shipList){
			if ((s.getModel()).equals(ship.getModel())){
				currentShip = ship;
			}
		}
	}
	
	public void setWalkingSpeed(float speed){
		this.walkingSpeed = speed;
	}
	
	public float getWalkingSpeed(){
		return walkingSpeed;
	}
	
	public void setVelocity(double velocity){
		this.velocity = velocity;
	}
	
	public double getVelocity(){
		return velocity;
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
	public Camera getPlayerCamera(){
		return camera;
	}
	
}
