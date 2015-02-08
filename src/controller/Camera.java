package controller;

import java.lang.Math;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import graphics.Renderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

import util.Quat4f;

public class Camera {
	public Matrix4f viewMatrix = new Matrix4f();

	//XXX: Replace below fields with a perspective matrix?
	private float fov;
	private float aspect;
	private float zNear;
	private float zFar;
	
	private boolean initiated = false;
	
	public Camera() {
		this(
			30.0f,
			(float) Display.getWidth() / (float) Display.getHeight(),
			0.001f,
			100);
	}

	public Camera(float fov, float aspect, float zNear, float zFar) {
		//Initialize the model matrix as a 4x4 identity matrix
		viewMatrix.setIdentity();

		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
	}
	
	// The below function is a huge reason why Java is shit compared to C.
	// All this stuff is needed simply to create a simple buffer of floats
	// which is needed to convert the matrix data stored in the Matrix4f
	// class. Compare this with C where this would all be replaced with
	// a single float[16].
	//
	// XXX: This should probably be placed as a helper function in some
	//      other package.
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

	
	public void initProjection() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fov, aspect, zNear, zFar);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	
		// Instead of just implementing a method to get a FloatBuffer
		// from the matrix LWJGL only implements a method for you to
		// extract the matrix data by passing it a buffer you created.
		// This of course results in four rows that should reasonably
		// be implemented by a single line.	
		FloatBuffer fb = createFloatBuffer(16);
		viewMatrix.store(fb);
		fb.position(0); // Annoying
		GL11.glLoadMatrix(fb);
		
		initiated = true;
	}
	
	public boolean isInitiated() {
		return initiated;
	}
	
	public void useView() {
		FloatBuffer fb = createFloatBuffer(16);
		viewMatrix.store(fb);
		fb.position(0); // Annoying
		GL11.glLoadMatrix(fb);
	}
	
	public Quat4f getQuaternion(){
		float w = (float)(Math.sqrt(1.0 + viewMatrix.m00 + viewMatrix.m11 + viewMatrix.m22) / 2.0);
		float w4 = (float) (4.0 * w);
		float x = (float) (viewMatrix.m21 - viewMatrix.m12) / w4;
		float y = (float) (viewMatrix.m02 - viewMatrix.m20) / w4;
		float z = (float) (viewMatrix.m10 - viewMatrix.m01) / w4;
		
		return new Quat4f(w, x, y, z);
	}
	
	public void moveX(float amt) { viewMatrix.m30 += amt; }

	//Y should be the up and down axis while moving along it, LIKE FLYING :D 
	public void moveY(float amt) { viewMatrix.m31 += amt; }

	//Z is here the "plane-axis", meaning that you orientate along the plane through the z-axis
	public void moveZ(float amt) { viewMatrix.m32 += amt; }
	
	/*
	 * Moves the camera relative to it's z-axis orientation
	 */
	public void moveRelativeZ(float amt) {
		viewMatrix.m30 += amt * viewMatrix.m20;
		viewMatrix.m32 += amt * viewMatrix.m22;
	}
	
	//X axis, rotate around this and you look up and down
	public void rotateX(float amt) {
		FloatBuffer fb = createFloatBuffer(16);
		viewMatrix.store(fb);
		fb.position(0); // Annoying
		
		float sinx = (float) Math.sin(amt);
		float cosx = (float) Math.cos(amt);

		float[] temp = new float[2];
		float[] data = new float[16];

		fb.get(data);

		temp[0] = viewMatrix.m01 * cosx - viewMatrix.m02 * sinx;
		temp[1] = viewMatrix.m01 * sinx + viewMatrix.m02 * cosx;

		viewMatrix.m01 = temp[0];
		viewMatrix.m02 = temp[1];

		temp[0] = viewMatrix.m11*cosx - viewMatrix.m12*sinx;
		temp[1] = viewMatrix.m11*sinx + viewMatrix.m12*cosx;

		viewMatrix.m11 = temp[0];
		viewMatrix.m12 = temp[1];

		temp[0] = viewMatrix.m21*cosx - viewMatrix.m22*sinx;
		temp[1] = viewMatrix.m21*sinx + viewMatrix.m22*cosx;

		viewMatrix.m21 = temp[0];
		viewMatrix.m22 = temp[1];

		temp[0] = viewMatrix.m31*cosx - viewMatrix.m32*sinx;
		temp[1] = viewMatrix.m31*sinx + viewMatrix.m32*cosx;

		viewMatrix.m31 = temp[0];
		viewMatrix.m32 = temp[1];
	}
	
	//Y is the horizontal axis, the axis up your ass, when you turn around this, you are turning
	public void rotateY(float amt) {
		viewMatrix.rotate(amt, new Vector3f(0.0f, 1.0f, 0.0f));
	}
	
	public void rotateZ(float amt) {
		viewMatrix.rotate(amt, new Vector3f(0.0f, 0.0f, 1.0f));
	}

	/*
	 * Don't ask how this method works because I don't know. 
	 *
	 */	
	public void rotateRelativeX(float amt) {
		FloatBuffer fb = createFloatBuffer(16);
		viewMatrix.store(fb);
		fb.position(0); // Annoying
		
		float sinx = (float) Math.sin(amt);
		float cosx = (float) Math.cos(amt);

		float[] data = new float[16];
		float[] pos = new float[3];
		float[] temp = new float[6];

		fb.get(data);

		pos[0] = data[12]*data[0] + data[13]*data[1] + data[14]*data[2];
		pos[1] = data[12]*data[4] + data[13]*data[5] + data[14]*data[6];
		pos[2] = data[12]*data[8] + data[13]*data[9] + data[14]*data[10];

		temp[0] = data[4] * cosx + data[8] * sinx;
		temp[1] = data[5] * cosx + data[9] * sinx;
		temp[2] = data[6] * cosx + data[10] * sinx;

		temp[3] = data[4]*(-sinx) + data[8]*cosx;
		temp[4] = data[5]*(-sinx) + data[9]*cosx;
		temp[5] = data[6]*(-sinx) + data[10]*cosx;

		viewMatrix.m10 = temp[0];
		viewMatrix.m11 = temp[1];
		viewMatrix.m12 = temp[2];

		viewMatrix.m20 = temp[3];
		viewMatrix.m21 = temp[4];
		viewMatrix.m22 = temp[5];

		viewMatrix.m30 = 
			pos[0] * data[0] +
			pos[1] * viewMatrix.m10 +
			pos[2] * viewMatrix.m20;
		viewMatrix.m31 =
			pos[0] * data[1] +
			pos[1] * viewMatrix.m11 +
			pos[2] * viewMatrix.m21;
		viewMatrix.m32 =
			pos[0] * data[2] +
			pos[1] * viewMatrix.m12 +
			pos[2] * viewMatrix.m22;	
	}
	
	public void rotateRelativeY(float amt) {
		FloatBuffer fb = createFloatBuffer(16);
		viewMatrix.store(fb);
		fb.position(0); // Annoying
		
		float sinx = (float) Math.sin(amt);
		float cosx = (float) Math.cos(amt);

		float[] data = new float[16];
		float[] pos = new float[3];
		float[] temp = new float[6];

		fb.get(data);

		pos[0] = data[12]*data[0] + data[13]*data[1] + data[14]*data[2];
		pos[1] = data[12]*data[4] + data[13]*data[5] + data[14]*data[6];
		pos[2] = data[12]*data[8] + data[13]*data[9] + data[14]*data[10];

		temp[0] = data[0] * cosx + data[8] * sinx;
		temp[1] = data[1] * cosx + data[9] * sinx;
		temp[2] = data[2] * cosx + data[10] * sinx;

		temp[3] = data[0]*(-sinx) + data[8]*cosx;
		temp[4] = data[1]*(-sinx) + data[9]*cosx;
		temp[5] = data[2]*(-sinx) + data[10]*cosx;

		viewMatrix.m00 = temp[0];
		viewMatrix.m01 = temp[1];
		viewMatrix.m02 = temp[2];

		viewMatrix.m20 = temp[3];
		viewMatrix.m21 = temp[4];
		viewMatrix.m22 = temp[5];

		viewMatrix.m30 = 
			pos[0] * viewMatrix.m00 +
			pos[1] * viewMatrix.m10 +
			pos[2] * viewMatrix.m20;
		viewMatrix.m31 =
			pos[0] * viewMatrix.m01 +
			pos[1] * viewMatrix.m11 +
			pos[2] * viewMatrix.m21;
		viewMatrix.m32 =
			pos[0] * viewMatrix.m02 +
			pos[1] * viewMatrix.m12 +
			pos[2] * viewMatrix.m22;	
	}

	public Vector3f getCameraPosition() {
		return new Vector3f(
			viewMatrix.m30,
			viewMatrix.m31,
			viewMatrix.m32);
	}
	
}

