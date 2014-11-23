package vehicles;

import java.util.ArrayList;
import java.util.List;

import entity.Quad;
import graphics.Model;
import vehicles.parts.Engine;

public class Ship {
	
	private List<Engine> engines = new ArrayList<Engine>();
	
	private String name;
	private Model model;
	
	private double shield;
	private double hullHitPoints;
	private double weight;
	private double energy;
	
	public Ship(){
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setModel(Model model){
		this.model = model;
	}
	
	public void setShieldHitPoints(int shield){
		this.shield = shield;
	}
	
	public void setHullHitPoints(double hullHitPoints){
		this.hullHitPoints = hullHitPoints;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	public void setEnergy(double energy){
		this.energy = energy;
	}
	
	public void addEngines(List<Engine> engines){
		this.engines = engines; 
	}
	
	public String getName(){
		return name;
	}
	
	public Model getModel(){
		return model;
	}
	
	public double getShieldHitPoints(){
		return shield;
	}
	
	public double getHullHitPoints(){
		return hullHitPoints;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public double getEnergy(){
		return energy;
	}
}
