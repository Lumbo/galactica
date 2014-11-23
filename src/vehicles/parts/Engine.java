package vehicles.parts;

import org.lwjgl.util.vector.Vector3f;

public class Engine {
	
	private double maxForce;
	private double throttle;
	private double currentHeat;
	private double heatLimit;
	private double damage;
	private boolean isActive;
	
	private Vector3f forceDirection;
	
	public Engine(){
		forceDirection = new Vector3f(0, 1, 0);
	}
	
	public Engine(double maxForce){
		this.maxForce = maxForce;
	}
	
	public void setMaxForce(double force){
		this.maxForce = force;
	}
	
	
	public void setThrottle(double percentage){
		this.throttle = percentage;
		
		double effectiveForce = (throttle/100)*maxForce;

		forceDirection = new Vector3f(
				(float)(forceDirection.getX()*effectiveForce), 
				(float)(forceDirection.getX()*effectiveForce), 
				(float)(forceDirection.getX()*effectiveForce));
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
		setThrottle(throttle += 0.02);
	}
	
	public void decreaseThrottle(){
		setThrottle(throttle -= 0.02);
	}
	
	public double getMaxForce(){
		return this.maxForce;
	}
	
}
