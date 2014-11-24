package vehicles.ships;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import physics.Physics;
import graphics.Model;
import graphics.OBJLoader;
import vehicles.Ship;
import vehicles.parts.Engine;

public class FalconShip extends Ship {
	
	private double weight = 2000000; // in kilograms
	private double fuel = 1000;
	
	public FalconShip(Model m) throws FileNotFoundException, IOException{
		super(m);
		List<Engine> engineList = new ArrayList<Engine>();
		for(int i=0; i<4; i++){
			Engine engine = new Engine(OBJLoader.getModel("res/models/engine/engine1.obj"));
			engine.setMaxForce(1);
			if(i == 1 || i == 3){
				engine.rotate(180, 0, 1, 0);
			}
			engineList.add(engine);
		}
		addEngines(engineList);
	}
	
	public void setWeight(double w) {
		this.weight = w;
	}
	
	public void setFuel(double fuel){
		this.fuel = fuel;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getFuel(){
		return fuel;
	}
	
	public Vector3f getPosition(){
		return new Vector3f(getPositionX(), getPositionY(), getPositionZ());
	}
	
	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(1, -1, -100.0f);
		GL11.glRotatef(0, 1, 1, 0);
		GL11.glScalef(2, 2, 2);
		for(Engine e : getEngines()){
			applyForce(e.getForceVector());
		}
		for(int i=0; i<getEngines().size(); i++){
			if(i==0){
				getEngines().get(i).moveTo(getPositionX()+7, getPositionY()+2, getPositionZ()-3f);
			}
			else if(i==1){
				getEngines().get(i).moveTo(getPositionX()+7, getPositionY()+2, getPositionZ()+2.2f);
			}
			else if(i==2){
				getEngines().get(i).moveTo(getPositionX()-8, getPositionY()+4, getPositionZ()-5f);
			}
			else if(i==3){
				getEngines().get(i).moveTo(getPositionX()-8, getPositionY()+4, getPositionZ()+4.2f);
			}
			
			getEngines().get(i).scale(0.5f);
			getEngines().get(i).draw();
		}
		getModel().draw();
		


		GL11.glPopMatrix();
	}

}
