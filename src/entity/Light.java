package entity;

import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_POSITION;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL44;

import util.Util;
import graphics.Model;

public class Light extends BaseEntity {

	private int pName = GL11.GL_LIGHT_MODEL_AMBIENT;
	private FloatBuffer lightModelFloatBuffer = Util.asFloatBuffer(new float[] {0.05f, 0.5f, 10, 0.5f});
	private FloatBuffer lightFloatBuffer = Util.asFloatBuffer(new float[]{100, 800, -10, 0.1f});
	
	public Light(Model m) {
		super(m);
	}

	
	// TODO Should probably be one method, with an argument that is a ENUM 
	// that decides what type of light that should be rendered
	public void drawSpotLight(){
		GL11.glPushMatrix();
		GL11.glColor3f(1f, 0, 0);
		GL11.glRotatef(super.getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		GL11.glLightModel(GL_LIGHT_MODEL_AMBIENT, lightModelFloatBuffer);
		GL11.glLight(GL11.GL_LIGHT0, GL_POSITION, lightFloatBuffer);
		
		//GL11.glLightf(GL11.GL_LIGHT0, GL_POSITION, -5);
		GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION, 0.1f);
		
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		getModel().draw();
		GL11.glPopMatrix();
	}
	
	
	
	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glRotatef(super.getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		GL11.glLightModel(GL_LIGHT_MODEL_AMBIENT, lightModelFloatBuffer);
		GL11.glLight(GL11.GL_LIGHT0, GL_POSITION, lightFloatBuffer);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		getModel().draw();
		GL11.glPopMatrix();
	}

	
}
