package physics;

import org.lwjgl.util.vector.Vector3f;

public class Force {
	
	private Vector3f force;
	
	public Force(){
		
	}
	
	public Force(Vector3f force){
		this.force = force;
	}
	
	public Vector3f getForce(){
		return force;
	}
	
}
