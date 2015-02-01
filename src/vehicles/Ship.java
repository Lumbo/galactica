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
	private double mass;
	private double energy;
	private float distanceToSurface = 0;
	private float yVelocity = 0;
	private float rotationSpeed = 0;
	
	private Vector3f resultantForce = new Vector3f(getPosition());
	
	public Ship(Model m){
		super(m);
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void applyForce(Vector3f force){
		yVelocity += force.getY();
		resultantForce.set(
				resultantForce.getX()+force.getX(), 
				resultantForce.getY()+yVelocity, 
				resultantForce.getZ()+force.getZ());
		moveTo(resultantForce);
	}
	
	public void applyRotationReducer(){
		float rotationFactor = (float)((rotationSpeed)/(Math.log(mass)*7));
		rotationSpeed -= rotationFactor;
		rotate((float)(rotationSpeed), 0, 1, 0);
//		System.out.println("Angle: " + getRotationAngle()
//				+ " x: " + getRotateX()
//				+ " y: " + getRotateY()
//				+ " z: " + getRotateZ());
//		
//		System.out.println("Angle: " + getRotationAngle() 
//				+ " anglex: " + getAngleVector().getX()
//				+ " angley: " + getAngleVector().getY()
//				+ " anglez: " + getAngleVector().getZ()
//				+ " x: " + getRotateX()
//				+ " y: " + getRotateY()
//				+ " z: " + getRotateZ());
		
	}
	
	public void applyTiltStabilizer(){
		
	}
	
	public void turnDegrees(float angle){		
		rotationSpeed += angle;
		rotate(1, 0, rotationSpeed, 0);
	}
	
	public void tiltLeftRight(float angle){
		
	}
	
	public void tiltUpDown(float angle){
		rotate(1, 0, getRotateY(), angle);
		
	}
	
	public void setShieldHitPoints(int shield){
		this.shield = shield;
	}
	
	public void setHullHitPoints(double hullHitPoints){
		this.hullHitPoints = hullHitPoints;
	}
	
	public void setMass(double mass){
		this.mass = mass;
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
	
	public double getMass(){
		return mass;
	}
	
	public double getEnergy(){
		return energy;
	}
	
	public List<Engine> getEngines(){
		return engines;
	}
	
	
}
