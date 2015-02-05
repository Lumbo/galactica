package entity;

import graphics.Model;
import org.lwjgl.opengl.GL11;

public class Surface extends BaseEntity {
	public Surface(Model m) {
		super(m);
	}
	
	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		GL11.glRotatef(super.getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		GL11.glScalef(20f, 10f, 20f);
		getModel().draw();
		GL11.glPopMatrix();
	}
	
}
