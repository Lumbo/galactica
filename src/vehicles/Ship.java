package vehicles;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import physics.Physics;
import entity.BaseEntity;
import entity.Quad;
import graphics.Model;
import vehicles.parts.Engine;

public class Ship extends BaseEntity {
	
	private List<Engine> engines = new ArrayList<Engine>();
	
	private String name;
	
	private double shield;
	private double hullHitPoints;
	private double weight;
	private double energy;
	private float yVelocity = 0;
	private float distanceFromSurface = 0;
	private Vector3f resultantForce = new Vector3f(getPosition());
	
	public Ship(Model m){
		super(m);
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void applyForce(Vector3f force){
		yVelocity += Physics.getGravity();
		resultantForce.set(
				resultantForce.getX()+force.getX(), 
				resultantForce.getY()+yVelocity+force.getY(), 
				resultantForce.getZ()+force.getZ());
		moveTo(resultantForce);
		yVelocity += force.getY();
		System.out.println("Falling speed: " + yVelocity);
		System.out.println("YPos: " + resultantForce.getY());
	}
	
	public void setShieldHitPoints(int shield){
		this.shield = shield;
	}
	
	public void setHullHitPoints(double hullHitPoints){
		this.hullHitPoints = hullHitPoints;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	public void setEnergy(double energy){
		this.energy = energy;
	}
	
	public void addEngines(List<Engine> engines){
		this.engines = engines; 
	}
	
	public void increaseThrottle(){
		String throttle = "";
		for(Engine e : getEngines()){
			e.increaseThrottle();
			throttle = throttle + e.getThrottle() + " ";
		}
		System.out.println(throttle);
	}
	
	public void decreaseThrottle(){
		String throttle = "";
		for(Engine e : getEngines()){
			e.decreaseThrottle();
			throttle = throttle + e.getThrottle() + " ";
		}
		System.out.println(throttle);
	}
	
	public String getName(){
		return name;
	}
	
	public double getShieldHitPoints(){
		return shield;
	}
	
	public double getHullHitPoints(){
		return hullHitPoints;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public double getEnergy(){
		return energy;
	}
	
	public List<Engine> getEngines(){
		return engines;
	}
	
	
}
