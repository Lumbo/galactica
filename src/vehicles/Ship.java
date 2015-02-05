package vehicles;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import physics.Physics;
import entity.BaseEntity;
import entity.Quad;
import graphics.Model;
import vehicles.parts.Engine;

public class Ship extends BaseEntity {
	
	private List<Engine> engines = new ArrayList<Engine>();
	
	private String name;
	
	private boolean isHovering = true;
	private double shield;
	private double hullHitPoints;
	private double mass;
	private double energy;
	private float distanceToSurface = 0;
	private float yVelocity = 0;
	private float rotationSpeed = 0;
	private float tiltSpeed = 0;
	private float rx = 0;
	private float ry = 0;
	private float rz = 0;
	
	
	private Vector3f resultantForce = new Vector3f(getPosition());
	
	public Ship(Model m){
		super(m);
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void applyForce(Vector3f force){
		movementReducer();
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
		
	}
	
	public void applyTiltStabilizer(){
		
	}
	
	public void turnDegrees(float angle){
		
		rotationSpeed += angle / 10;
		rotate(rotationSpeed, 0, 1, 0);
		
		System.out.println("x " + getRotateX());
		System.out.println("y " + getRotateY());
		System.out.println("z " + getRotateZ());
	}
	
	public void tiltUpDown(float angle){
		rotationSpeed += angle;
		rotate((float)rotationSpeed, 0, 0, -1);
		System.out.println("x " + getRotateX());
		System.out.println("y " + getRotateY());
		System.out.println("z " + getRotateZ());
		
	}
	
	public void tiltLeftRight(float angle){
		
	}
	
	public void setHover(boolean hover){
		isHovering = hover;
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
	
	public void helpSystems(){	
		if(isHovering){
			float currentThrottle = engines.get(0).getThrottle();
			
			float resultThrottlePercentage = (float) (-getMass()*yVelocity/(engines.get(0).getMaxForce()*4));		
			setThrottle(resultThrottlePercentage);
		}
	}
	
	public void movementReducer(){
		float slowFactor = (float) (yVelocity/getMass()*1000);
		if(yVelocity < 0){
			yVelocity = yVelocity + slowFactor;
		}
		else {
			yVelocity = yVelocity - slowFactor;
		}
	}
	
	private void setThrottle(float throttle){
		for(Engine e : getEngines()){
			e.setThrottle(throttle);
		}
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
	
	public boolean isHovering(){
		return isHovering;
	}
	
	public void printDebugVectors(){
		printAxis();
		printResultantForceVectors();
	}
	
	public void printAxis(){
		glPushMatrix();
		glBegin(GL_LINES);
		int lineLength = 50;
		glColor3f(255, 0, 0);
		glVertex3f(getPositionX(), getPositionY(), getPositionZ());
		glVertex3f(getPositionX()+lineLength, getPositionY(), getPositionZ());
		glColor3f(0, 255, 0);
		glVertex3f(getPositionX(), getPositionY(), getPositionZ());
		glVertex3f(getPositionX(), getPositionY()+lineLength, getPositionZ());
		glColor3f(0, 0, 255);
		glVertex3f(getPositionX(), getPositionY(), getPositionZ());
		glVertex3f(getPositionX(), getPositionY(), getPositionZ()+lineLength);
		glEnd();
		glPopMatrix();
	}
	
	public void printResultantForceVectors(){
		glPushMatrix();
		glBegin(GL_LINES);
		glColor3f(255, 0, 0);
		glVertex3f(getPositionX(), getPositionY(), getPositionZ());
		glVertex3f(getPositionX(), getPositionY()+yVelocity*100, getPositionZ());
		glEnd();
		glPopMatrix();
	}
	
}
