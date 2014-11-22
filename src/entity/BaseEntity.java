package entity;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

import graphics.Model;

public class BaseEntity {
	private float position_x, position_y, position_z;
	private float rotation_x, rotation_y, rotation_z;
	private float scale = 1;
	private Model model;
	private Vector3f test;
	
	
	public BaseEntity(Model m){
		this.model = m;
	}
	
	public void moveTo(float x, float y, float z){
		this.position_x = x;
		this.position_y = y;
		this.position_z = z;
	}
	
	public void scale(float s){
		this.scale = s;
	}
	
	public void rotateX(float r){
		this.rotation_x += r;
	}
	
	public void rotateY(float r){
		this.rotation_y += r;
	}
	
	public void rotateZ(float r){
		this.rotation_z += r;
	}
	public void draw(){
		glPushMatrix();
		org.lwjgl.opengl.GL11.
		glTranslatef(position_x, position_y, position_z);
		glScalef(scale, scale, scale);
		this.model.draw();
		glPopMatrix();
	}

}
