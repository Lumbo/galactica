package vehicles.ships;

import graphics.Model;
import vehicles.Ship;

public class FalconShip extends Ship {
	
	private double weight; 
	
	
	public FalconShip(){
		
	}
	
	@Override
	public void setName(String s) {
		setName(s);
	}

	@Override
	public void setModel(Model model) {
		super.setModel(model);
	}
	
	public void setWeight(double w) {
		this.weight = w;
	}
	
	public double getWeight() {
		return weight;
	}

}
