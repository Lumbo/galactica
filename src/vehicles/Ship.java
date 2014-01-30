package vehicles;

import java.util.ArrayList;
import java.util.List;

import entity.Quad;
import entity.Triangle;
import vehicles.parts.Engine;

public abstract class Ship {
	
	private List<Quad> quads = new ArrayList<Quad>();
	private List<Triangle> triangles = new ArrayList<Triangle>();
	
	private List<Engine> engines = new ArrayList<Engine>();
	
	private String name;
	private String model;
	
	private double health;
	private double weight;
	
	
	public Ship(){
		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setModel(String model){
		this.model = model;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	
	public String getShipName(){
		return name;
	}
	
	public String getShipModel(){
		return model;
	}
	
	public void draw(){
		
	}
	
	
	public abstract void setShipName(String s);
	public abstract void setShipModel(String s);
	
}
