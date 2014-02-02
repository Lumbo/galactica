package vehicles.ships;

import java.util.ArrayList;
import java.util.List;

import entity.Quad;
import vehicles.Ship;

public class FalconShip extends Ship {
	
	private double weight; 
	
	private List<Quad> quadList = new ArrayList<Quad>();
	
	public FalconShip(){
		
	}
	
	@Override
	public void setShipName(String s) {
		setName(s);
	}

	@Override
	public void setShipModel(String s) {
		setModel(s);
	}
	
	public void setWeight(double w) {
		this.weight = w;
	}
	
	public double getWeight() {
		return weight;
	}
	
	
	
	
	
}
