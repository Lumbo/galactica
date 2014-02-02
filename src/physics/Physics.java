package physics;

import org.lwjgl.util.vector.Vector3f;

import util.Constant.PhysicsConstant;
import entity.Quad;

public class Physics {
	
	public float gravityForce = PhysicsConstant.toMeter(-9.82f);
	
	
	public Physics(){
		
	}
	
	public float getGravity(){
		return gravityForce;
	}
	
	public Vector3f getNormalForce(float x, float y, float z){
		return getNormalForce(new Vector3f(x,y,z));
	}
	
	public Vector3f getNormalForce(Quad q){
		return getNormalForce(new Vector3f(
				q.getDirection().getX(), 
				q.getDirection().getY(), 
				q.getDirection().getZ()));
	}
	
	public Vector3f getNormalForce(Vector3f v){
		return new Vector3f(v.getX(), -v.getY(), v.getZ());
	}
	
}
