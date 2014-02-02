package entity;

import org.lwjgl.util.vector.Vector3f;

public class Sphere extends org.lwjgl.util.glu.Sphere implements EntityInterface {
	
	private float radius;
	private int slices;
	private int stacks;
	
	public Sphere(){
		super();
	}
	
	public Sphere(float radius, int slices, int stacks){
		this.radius = radius;
		this.slices = slices;
		this.stacks = stacks;
	}
	
	public void draw(){
		draw(radius, slices, stacks);
	}

	@Override
	public void applyForce(Vector3f force) {
		// TODO Auto-generated method stub
		
	}
	
	public float getRadius(){
		return radius;
	}

	@Override
	public void setPosition(Vector3f position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMass(double mass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getMass() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector3f getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDirection(Vector3f direction) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
