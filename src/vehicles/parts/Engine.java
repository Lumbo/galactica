package vehicles.parts;

public class Engine {
	
	private double maxForce;
	private double throttle;
	private double currentHeat;
	private double heatLimit;
	private double damage;
	
	
	public Engine(){
		
	}
	
	public Engine(double maxForce){
		this.maxForce = maxForce;
	}
	
	public void setForce(double force){
		this.maxForce = force;
	}
	
	public double getForce(){
		return this.maxForce;
	}
	
	public void setThrottle(double percentage){
		this.throttle = percentage;
	}
	
	public void setHeat(double heat){
		this.currentHeat = heat;
		if (currentHeat>=heatLimit){
			overHeatWarning();
		}
	}
	
	
	public void overHeatWarning(){
		
	}
	
}
