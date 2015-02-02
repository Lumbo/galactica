package util;

import org.lwjgl.util.vector.Vector3f;

public class Quat4f {
	
	private float x;
	private float y;
	private float z;
	private float w;	// amount of rotation
	private float magnitude;
	
	private float rotationMatrix[][] = new float[4][4];
	
	
	// The quaternion is defined as q = w + xi + yj + zk
	// Could probably discard the imaginary part
	public Quat4f(){
		this(1f, 0f, 0f, 0f); // No rotation
	}
	
	public Quat4f(float w, float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * To the unit quaternion - a fucking hyper-sphere! 
	 */
	public void normalize(){
		magnitude = getMagnitude();
		w = w/magnitude;
		x = x/magnitude;
		y = y/magnitude;
		z = z/magnitude;
	}
	
	
	/**
	 * Unit quat, mag should be 1
	 * Can be used to check if (w2 + x2 + y2 + z2)
	 * differs too much from 1
	 * @return magnitude of quaternion
	 */
	public float getMagnitude(){ 
		return (float) Math.sqrt(w*w + x*x + y*y + z*z);
	}
	
	
	public Quat4f getMultiplication(Quat4f q1, Quat4f q2){
		Quat4f resultQuat = new Quat4f();
		resultQuat.setW(q1.getW()*q2.getW() - q1.getX()*q2.getX() - q1.getY()*q2.getY() - q1.getZ()*q2.getZ());
		resultQuat.setX(q1.getW()*q2.getX() + q1.getX()*q2.getW() + q1.getY()*q2.getZ() - q1.getZ()*q2.getY());
		resultQuat.setY(q1.getW()*q2.getY() - q1.getX()*q2.getZ() + q1.getY()*q2.getW() + q1.getZ()*q2.getX());
		resultQuat.setZ(q1.getW()*q2.getZ() + q1.getX()*q2.getY() - q1.getY()*q2.getX() + q1.getZ()*q2.getW());
		
		return resultQuat;
	}
	
	public Quat4f generateTempRotation(Quat4f quat, float angle, Vector3f axis){
		Quat4f totalRotation = new Quat4f();
		Quat4f localRotation = this;	// This is just to make shit more clear in my head
		
		localRotation.setW((float) Math.cos(angle/2));
		localRotation.setX((float) (axis.getX()*Math.sin(angle/2)));
		localRotation.setY((float) (axis.getY()*Math.sin(angle/2)));
		localRotation.setZ((float) (axis.getZ()*Math.sin(angle/2)));
		totalRotation = getMultiplication(localRotation, quat);
		
		return totalRotation;
	}
	
	public void generateRotationMatrix(){
		rotationMatrix[0][0] = 1 - 2*y*y - 2*z*z;
		rotationMatrix[1][0] = 2*x*y - 2*w*z;
		rotationMatrix[2][0] = 2*x*z + 2*w*y;
		rotationMatrix[3][0] = 0;
		
		rotationMatrix[0][1] = 2*x*y + 2*w*z;
		rotationMatrix[1][1] = 1 - 2*x*x - 2*z*z;
		rotationMatrix[2][1] = 2*y*z + 2*w*x;
		rotationMatrix[3][1] = 0;

		rotationMatrix[0][2] = 2*x*z - 2*w*y;
		rotationMatrix[1][2] = 2*y*z - 2*w*x;
		rotationMatrix[2][2] = 1 - 2*x*x -2*y*y;
		rotationMatrix[3][2] = 0;
		
		rotationMatrix[0][3] = 0;
		rotationMatrix[1][3] = 0;
		rotationMatrix[2][3] = 0;
		rotationMatrix[3][3] = 1;
		
	}
	
	
	public void setX(float x){
		this.x = x;
	}
	
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setZ(float z){
		this.z = z;
	}
	
	public void setW(float w){
		this.w = w;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getZ(){
		return z;
	}
	
	public float getW(){
		return w;
	}
	
	
}
