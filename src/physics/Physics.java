package physics;

public class Physics {
	private final static float gravity = -9.82f/60000.0f;	// Dividing 60 because 60 fps
	
	public static float getGravity(){
		return gravity;
	}
	
}
