package entity;

import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_POSITION;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import util.Util;
import graphics.Model;

public class Light extends BaseEntity {

	private int pName = GL11.GL_LIGHT_MODEL_AMBIENT;
	private FloatBuffer lightModelFloatBuffer1 = Util.asFloatBuffer(new float[] {0.05f, 0.5f, 10, 0.5f});
	private FloatBuffer lightFloatBuffer = Util.asFloatBuffer(new float[]{100, 800, -10, 0.1f});
	
	public Light(Model m) {
		super(m);
	}
	
	
	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glRotatef(super.getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		GL11.glLightModel(GL_LIGHT_MODEL_AMBIENT, lightModelFloatBuffer1);
		GL11.glLight(GL11.GL_LIGHT1, GL_POSITION, lightFloatBuffer);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		getModel().draw();
		GL11.glPopMatrix();
	}

	
}
