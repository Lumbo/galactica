package vehicles.parts;

import org.lwjgl.util.vector.Vector3f;

import vehicles.Ship;
import entity.BaseEntity;
import graphics.Model;

public class Engine extends BaseEntity{
	
	private boolean isActive;
	
	private final float maxThrottle = 110.0f;
	private final float minThrottle = 0.0f;
	private float currentHeat;
	private float heatLimit;
	private float maxForce;
	private float throttle;
	
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
	
	public void setThrottle(float percentage){
		if(percentage>maxThrottle){
			percentage = maxThrottle;
		}
		else if(percentage<minThrottle){
			percentage = minThrottle;
		}
		else{
			this.throttle = percentage;	
		}
		
		float effectiveForce = (float) ((throttle/100)*maxForce);

		forceDirection = new Vector3f(
				(float)(0), 
				(float)(effectiveForce), 
				(float)(0));
	}
	
	public void setHeat(float heat){
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
	
	public float getThrottle(){
		return throttle;
	}
	
	public void setShip(Ship s){
		this.ship = s;
	}
}
