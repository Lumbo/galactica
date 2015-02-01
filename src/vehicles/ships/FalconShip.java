package vehicles.ships;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import physics.Physics;
import graphics.Model;
import graphics.OBJLoader;
import vehicles.Ship;
import vehicles.parts.Engine;

public class FalconShip extends Ship {
	
	private double mass = 2000000; // in kilograms
	private double fuel = 1000;
	private float rx = 0, ry = 0, rz = 0;
	
	public FalconShip(Model m) throws FileNotFoundException, IOException{
		super(m);
		List<Engine> engineList = new ArrayList<Engine>();
		for(int i=0; i<4; i++){
			Engine engine = new Engine(OBJLoader.getModel("res/models/engine/engine1.obj"));
			engine.setMaxForce(10000);
			if(i == 1 || i == 3){
				engine.rotate(180, 0, 1, 0);
			}
			//engine.setThrottle(82f);
			engineList.add(engine);
		}
		addEngines(engineList);
		setMass(mass);
		
	}
	
	public void setFuel(double fuel){
		this.fuel = fuel;
	}
	
	public double getFuel(){
		return fuel;
	}
	
	public Vector3f getPosition(){
		return new Vector3f(getPositionX(), getPositionY(), getPositionZ());
	}
	
	@Override
	public void draw() {
		extraDraw();
		//applyForce(new Vector3f(0, Physics.getGravity(), 0));
		for(int i=0; i<getEngines().size(); i++){
			if(i==0){
				getEngines().get(i).moveTo(getPositionX()+13, getPositionY()+5, getPositionZ()-4.6f);
			}
			else if(i==1){
				getEngines().get(i).moveTo(getPositionX()+13, getPositionY()+5, getPositionZ()+3.0f);
			}
			else if(i==2){
				getEngines().get(i).moveTo(getPositionX()-8, getPositionY()+4, getPositionZ()-6.6f);
			}
			else if(i==3){
				getEngines().get(i).moveTo(getPositionX()-8, getPositionY()+4, getPositionZ()+5f);
			}
			getEngines().get(i).draw();
		}
		glPushMatrix();
		glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		glRotatef(getRotationAngle(), getRotateX(), getRotateY(), getRotateZ());
		glScalef(2, 2, 2);

		Vector3f forceVector;
		for(Engine e : getEngines()){
			forceVector = new Vector3f(
					e.getForceVector().getX(),
					(float)(e.getForceVector().getY()/getMass()),
					e.getForceVector().getZ());
			applyForce(forceVector);
		}
		
		helpSystems();
		
		getModel().draw();
		glPopMatrix();
	}
	
	public void extraDraw(){
		glPushMatrix();
		GL11.glRotatef(getRotateX(), 1, 0, 0);
		GL11.glRotatef(getRotateY(), 0, 1, 0);
		GL11.glRotatef(getRotateZ(), 0, 0, 1);
		GL11.glTranslatef(getPositionX(), getPositionY(), getPositionZ());
		glPopMatrix();
	}
}
