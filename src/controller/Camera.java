package controller;

import graphics.Renderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Camera {
	private float x;
	private float y;
	private float z;
	
	private float rx;
	private float ry;
	private float rz;
	
	private float fov;
	private float aspect;
	private float zNear;
	private float zFar;
	
	public Camera(){
		this(70,(float)Display.getWidth()/(float)Display.getHeight(),0.03f,1000f);
	}
	
	public Camera(float fov, float aspect, float zNear, float zFar){
		
		x=34;
		y=-54;
		z=33;
		
		rx=22;
		ry=134;
		rz=0;
		
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
		initProjection();
	}
	
	public void initProjection(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fov, aspect, zNear, zFar);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glEnable(GL11.GL_DEPTH);
		
		GL11.glEnable(GL11.GL_BLEND | GL11.GL_ALPHA | GL11.GL_PERSPECTIVE_CORRECTION_HINT);
		
		/*GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_FOG);*/
		
		
	}
	
	public void useView(){
		GL11.glRotatef(rx, 1, 0, 0);
		GL11.glRotatef(ry, 0, 1, 0);
		GL11.glRotatef(rz, 0, 0, 1);
		GL11.glTranslatef(x, y, z);
	}
	
	//Z is here the "plane-axis", meaning that you orientate along the plane through the z-axis
	public void moveZ(float amt, float dir){
		z += amt * Math.sin(Math.toRadians(ry + 90 * dir));
		x += amt * Math.cos(Math.toRadians(ry + 90 * dir));
	}
	
	
	//Y should be the up and down axis while moving along it, LIKE FLYING :D 
	public void moveY(float amt){
		y += amt;
	}
	
	//Y is the horizontal axis, the axis up your ass, when you turn around this, you are turning
	public void rotateY(float amt){
		ry += amt;
	}
	
	//X axis, rotate around this and you look up and down
	public void rotateX(float amt){
		if (rx + amt < 90.0f && rx + amt > -90.0f){
			rx += amt;
		}
	}
	
	public void printCameraLocation(){
		System.out.println("Camera coords\n"
				+ " x: " + getX() + "\n"
				+ " y: " + getY() + "\n"
				+ " z: " + getZ());
	}
	
	public void printCameraRotation(){
		System.out.println("Rotation coords\n"
				 + " rx: " + getRX() + "\n"
				 + " ry: " + getRY() + "\n"
				 + " rz: " + getRZ());
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
	
	public float getRX(){
		return rx;
	}
	
	public float getRY(){
		return ry;
	}
	
	public float getRZ(){
		return rz;
	}
	
	
	public void setFov(float fov){
		this.fov = fov;
	}
	
}












