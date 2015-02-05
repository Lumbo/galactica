package entity;

import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_POSITION;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL44;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.GLUtils;

import util.Util;
import graphics.Model;

public class Light extends BaseEntity {

	private int pName = GL11.GL_LIGHT_MODEL_AMBIENT;
	private FloatBuffer lightModelFloatBuffer = Util.asFloatBuffer(new float[] {0.05f, 0.5f, 10, 0.5f});
	private FloatBuffer lightFloatBuffer = Util.asFloatBuffer(new float[]{0.5f, 0.5f, 0.5f, 1.0f});
	private FloatBuffer spotDirection = Util.asFloatBuffer(new float[] {0.0f, 1,0f, 0.0f});
	private float lightRotation = 0.0f;
	private float lightRotationSpeed = 1.5f;
	public Light(Model m) {
		super(m);
	}

	
	// TODO Should probably be one method, with an argument that is a ENUM 
	// that decides what type of light that should be rendered
	public void drawSpotLight(){
		GL11.glPushMatrix();
		lightRotation += lightRotationSpeed;
		moveTo((float)-Math.sin(lightRotation/100)*50, 20, ((float)Math.cos(lightRotation/100)*50)-100);
		FloatBuffer light1 = Util.asFloatBuffer(new float[]{1.0f, 0.2f, 0.2f, 1.0f});
		FloatBuffer lightPos1 = Util.asFloatBuffer(new float[]{-1f, 0.5f, 0.5f, 1.0f});

		GL11.glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		GL11.glRotatef(getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, light1);
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos1);
		GL11.glEnable(GL11.GL_COLOR);
		GL11.glColor3f(1.0f,  1.0f,  0.0f);
		
		
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		getModel().draw();
		GL11.glPopMatrix();
	}
	
	
	
	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		GL11.glRotatef(super.getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		GL11.glLightModel(GL_LIGHT_MODEL_AMBIENT, lightModelFloatBuffer);
		GL11.glLight(GL11.GL_LIGHT0, GL_POSITION, lightFloatBuffer);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		getModel().draw();
		GL11.glPopMatrix();
	}

	
	public void setSpotLightDirection(float[] f){
		spotDirection = Util.asFloatBuffer(f);
	}
	
}
