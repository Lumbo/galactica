package entity;

import org.lwjgl.util.vector.Vector3f;

public interface EntityInterface {
	
	public void applyForce(Vector3f force);
	public void setPosition(Vector3f position);
	public void setPosition(float x, float y, float z);
	public void setDirection(Vector3f direction);
	public Vector3f getDirection();
	public Vector3f getPosition();
	public void setMass(double mass);
	public double getMass();
	
}
