package entity;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import util.Quat4f;
import graphics.Model;

public class BaseEntity {
	private float position_x, position_y, position_z;
	private float rotation_x, rotation_y, rotation_z;
	private float rx = 1, ry = 1, rz = 1;
	private float scale = 1;
	private float rotationAngle = 1;
	private Model model;
	private Quat4f quat4f = new Quat4f();
	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f previousMatrix4f = new Matrix4f();
	
	
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
		
		quat4f = new Quat4f(angle, x, y, z);
		float[][] rotMat = quat4f.getRotationMatrix(angle, new Vector3f(x, y, z));
		
		viewMatrix.setIdentity();
		
		viewMatrix.m00 = rotMat[0][0];
		viewMatrix.m01 = rotMat[0][1];
		viewMatrix.m02 = rotMat[0][2];
		
		viewMatrix.m10 = rotMat[1][0];
		viewMatrix.m11 = rotMat[1][1];
		viewMatrix.m12 = rotMat[1][2];
		
		viewMatrix.m20 = rotMat[2][0];
		viewMatrix.m21 = rotMat[2][1];
		viewMatrix.m22 = rotMat[2][2];
		
		System.out.println("Angle: " + quat4f.getW());
		System.out.println("[" + rotMat[0][0] + ", " + rotMat[0][1] + ", " + rotMat[0][2] + ", " + viewMatrix.m03 + "]");
		System.out.println("[" + rotMat[1][0] + ", " + rotMat[1][1] + ", " + rotMat[1][2] + ", " + viewMatrix.m13 + "]");
		System.out.println("[" + rotMat[2][0] + ", " + rotMat[2][1] + ", " + rotMat[2][2] + ", " + viewMatrix.m23 + "]");
		System.out.println("[" + viewMatrix.m30 + ", " + viewMatrix.m31 + ", " + viewMatrix.m32 + ", " + viewMatrix.m33 + "]\n");
	}
	
	public Quat4f getQuaternion(){
		return quat4f;
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
	
	public void setRotationAngleX(float angle){
		this.rx += angle;
	}
	
	public void setRotationAngleY(float angle){
		this.ry += angle;
	}
	
	public void setRotationAngleZ(float angle){
		this.rz += angle;
	}
	
	public void resetXZY(){
		this.rotation_x = 0;
		this.rotation_y = 0;
		this.rotation_z = 0;
	}
	
	public void draw(){
		glPushMatrix();
		
		org.lwjgl.opengl.GL11.glTranslatef(position_x, position_y, position_z);
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
	
	public Vector3f getAngleVector(){
		return new Vector3f(rx, ry, rz);
	}
	
	public Vector3f getPosition(){
		return new Vector3f(position_x, position_y, position_z);
	}
	
	public Matrix4f getViewMatrix(){
		return viewMatrix;
	}
	
	private FloatBuffer createFloatBuffer(int size) {
		// Java has not sizeof operator so you are
		// forced to hardcode 4 as the size of a float
		ByteBuffer bb = ByteBuffer.allocateDirect(size * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();

		return fb;
	}

	private FloatBuffer createFloatBuffer(float[] arr) {
		FloatBuffer fb = createFloatBuffer(arr.length);
		fillFloatBuffer(fb, arr);

		return fb;
	}

	private FloatBuffer fillFloatBuffer(FloatBuffer fb, float[] arr) {
		// This is so stupid and unintuitive but you have to reset the
		// buffer internal "pointer" after filling it with data.
		fb.put(arr);
		fb.position(0);

		return fb;
	}
	
	public void useView() {
		FloatBuffer fb = createFloatBuffer(16);
		viewMatrix.store(fb);
		fb.position(0); // Annoying
		GL11.glLoadMatrix(fb);
	}
	
}
