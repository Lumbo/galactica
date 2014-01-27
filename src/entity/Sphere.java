package entity;

public class Sphere extends org.lwjgl.util.glu.Sphere{
	
	private float radius;
	private int slices;
	private int stacks;
	
	public Sphere(){
		super();
	}
	
	public Sphere(float radius, int slices, int stacks){
		this.radius = radius;
		this.slices = slices;
		this.stacks = stacks;
	}
	
	public void draw(){
		draw(radius, slices, stacks);
	}
	
	
}
