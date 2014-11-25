package vehicles.parts;

import org.lwjgl.util.vector.Vector3f;

import vehicles.Ship;
import entity.BaseEntity;
import graphics.Model;

public class Engine extends BaseEntity{
	
	private boolean isActive;
	
	private double throttle;
	private double currentHeat;
	private double heatLimit;
	private double damage;
	
	private final float maxThrottle = 110.0f;
	private final float minThrottle = 0.0f;
	private float maxForce;
	
	private Vector3f forceDirection;
	private Ship ship;
	
	public Engine(Model m){
		super(m);
		forceDirection = new Vector3f(0.0f, 0.0f, 0.0f);
		System.out.println(forceDirection);
		scale(0.5f);
		setThrottle(42);
	}
	
	public void setMaxForce(float force){
		this.maxForce = force;
	}
	
	public void setThrottle(double percentage){
		this.throttle = percentage;
		
		float effectiveForce = (float) ((throttle/100)*maxForce);

		forceDirection = new Vector3f(
				(float)(0), 
				(float)(effectiveForce), 
				(float)(0));
		System.out.println(forceDirection);
	}
	
	public void setHeat(double heat){
		this.currentHeat = heat;
		if (currentHeat>=heatLimit){
			overHeatWarning();
		}
	}
	
	
	public void overHeatWarning(){
		
	}
	
	public void increaseThrottle(){
		if((throttle+0.5) < maxThrottle){
			throttle += 0.5;	
		}
		else{
			throttle = maxThrottle;
		}
		setThrottle(throttle);
	}
	
	public void decreaseThrottle(){
		if((throttle-0.5) > minThrottle){
			throttle -= 0.5;	
		}
		else{
			throttle = minThrottle;
		}
		
		setThrottle(throttle);
	}
	
	public double getMaxForce(){
		return this.maxForce;
	}
	
	public Vector3f getForceVector(){
		return forceDirection;
	}
	
	public double getThrottle(){
		return throttle;
	}
	
	public void setShip(Ship s){
		this.ship = s;
	}
}
