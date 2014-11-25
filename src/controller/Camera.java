package controller;

import graphics.Renderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

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
	
	private boolean initiated = false;
	
	public Camera(){
		this((float)30,(float)Display.getWidth()/(float)Display.getHeight(),0.001f,100);
	}
	
	public Camera(float fov, float aspect, float zNear, float zFar){
		x=0;
		y=0;
		z=0;
		
		rx=0;
		ry=0;
		rz=0;
		
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
	}
	
	public void initProjection(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fov, aspect, zNear, zFar);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		initiated = true;
	}
	
	public boolean isInitiated(){
		return initiated;
	}
	
	public void useView(){
		GL11.glRotatef(rx, 1, 0, 0);
		GL11.glRotatef(ry, 0, 1, 0);
		GL11.glRotatef(rz, 0, 0, 1);
		GL11.glTranslatef(x, y, z);
	}
	
	public void moveX(float x){
		this.x = x;
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
	
	public Vector3f getCameraPosition(){
		return new Vector3f(x, y, z);
	}
	
}












