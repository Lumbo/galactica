package entity;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

import graphics.Model;

public class BaseEntity {
	private float position_x, position_y, position_z;
	private float rotation_x, rotation_y, rotation_z;
	private float scale = 1;
	private float rotationAngle = 1;
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
	
	public void moveTo(Vector3f pos){
		moveTo(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public void scale(float s){
		this.scale = s;
	}
	
	public void rotate(float angle, float x, float y, float z){
		setRotationAngle(angle);
		rotateX(x);
		rotateY(y);
		rotateZ(z);
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
	
	public void setRotationAngle(float angle){
		this.rotationAngle += angle;
	}
	
	public void rotateStatic(float angle, float x, float y, float z){
		setStaticRotationAngle(angle);
		rotateStaticX(x);
		rotateStaticY(y);
		rotateStaticZ(z);
	}
	
	public void setStaticRotationAngle(float angle){
		this.rotationAngle = angle;
	}
	
	public void rotateStaticX(float r){
		this.rotation_x = r;
	}
	
	public void rotateStaticY(float r){
		this.rotation_y = r;
	}
	
	public void rotateStaticZ(float r){
		this.rotation_z = r;
	}
	
	public void draw(){
		glPushMatrix();
		org.lwjgl.opengl.GL11.
		glTranslatef(position_x, position_y, position_z);
		glRotatef(rotationAngle, rotation_x, rotation_y, rotation_z);
		glScalef(scale, scale, scale);
		this.model.draw();
		glPopMatrix();
	}
	
	public Model getModel(){
		return model;
	}

	public float getRotationAngle(){
		return rotationAngle;
	}
	
	public float getRotateX(){
		return rotation_x;
	}
	
	public float getRotateY(){
		return rotation_y;
	}
	
	public float getRotateZ(){
		return rotation_z;
	}
	
	public float getPositionX(){
		return position_x;
	}
	
	public float getPositionY(){
		return position_y;
	}
	
	public float getPositionZ(){
		return position_z;
	}
	
	public Vector3f getPosition(){
		return new Vector3f(position_x, position_y, position_z);
	}
}
