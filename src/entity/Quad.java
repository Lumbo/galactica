package entity;

import java.awt.Color;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Quad implements EntityInterface {
	private double height;
	private double width;
	private double depth;
	
	private double posX;
	private double posY;
	private double posZ;
	
	private double mass;
	
	private Vector3f position;
	private Vector3f direction = new Vector3f(0,0,0);
	
	private boolean initiated = false;
	
	public Quad(){
	}
	
	public Quad(double height, double width, double depth){
		double randWidth = Math.random()*200;
		double randHeight = Math.random()*100;
		double randDepth = Math.random()*200;
		
		if(randWidth>100){
			randWidth = -randWidth+100;
		}
		
		if(randDepth>100){
			randDepth = -randDepth+100;
		}
		
		createQuad(height, width, depth, Math.random()*100, Math.random()*100+height, Math.random()*100);
		setPosition((float) width/2, (float) height/2,(float) depth/2);
	}
	
	public boolean isInitiated(){
		return initiated;
	}
	
	public void createQuad(double height, double width, double depth, double posX, double posY, double posZ){
		this.height = height;
		this.width = width; 
		this.depth = depth;
		
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		
        initiated = true;
	}
	
	public void draw(){
		// 1
        GL11.glColor3d(1.0f,1.0f,0.0f);
        GL11.glVertex3d( width+posX, height+posY,-depth+posZ);        
        GL11.glVertex3d(-width+posX, height+posY,-depth+posZ);        
        GL11.glVertex3d(-width+posX, height+posY, depth+posZ);
        GL11.glVertex3d( width+posX, height+posY, depth+posZ);
        
        // 2
        GL11.glColor3f(1.0f,0.5f,0.0f);            
        GL11.glVertex3d( width+posX,-height+posY, depth+posZ);
        GL11.glVertex3d(-width+posX,-height+posY, depth+posZ);
        GL11.glVertex3d(-width+posX,-height+posY,-depth+posZ);
        GL11.glVertex3d( width+posX,-height+posY,-depth+posZ);
        
        // 3
        GL11.glColor3f(1.0f,0.0f,0.0f);
        GL11.glVertex3d( width+posX, height+posY, depth+posZ);
        GL11.glVertex3d(-width+posX, height+posY, depth+posZ);
        GL11.glVertex3d(-width+posX,-height+posY, depth+posZ);
        GL11.glVertex3d( width+posX,-height+posY, depth+posZ);
        
        // 4
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3d( width+posX,-height+posY,-depth+posZ);
        GL11.glVertex3d(-width+posX,-height+posY,-depth+posZ);
        GL11.glVertex3d(-width+posX, height+posY,-depth+posZ);
        GL11.glVertex3d( width+posX, height+posY,-depth+posZ);
        
        // 5
        GL11.glColor3f(0.0f,0.0f,1.0f);
        GL11.glVertex3d(-width+posX, height+posY, depth+posZ);
        GL11.glVertex3d(-width+posX, height+posY,-depth+posZ);
        GL11.glVertex3d(-width+posX,-height+posY,-depth+posZ);
        GL11.glVertex3d(-width+posX,-height+posY, depth+posZ);
        
        // 6
        GL11.glColor3f(1.0f,0.0f,1.0f);
        GL11.glVertex3d( width+posX, height+posY,-depth+posZ);
        GL11.glVertex3d( width+posX, height+posY, depth+posZ);
        GL11.glVertex3d( width+posX,-height+posY, depth+posZ);
        GL11.glVertex3d( width+posX,-height+posY,-depth+posZ);
	}
	
	
	public void setColor(double r, double b, double g){
		GL11.glColor3d(r, g, b);
	}

	@Override
	public void applyForce(Vector3f force) {
		direction.set(direction.getX()+force.getX(), 
				direction.getY()+force.getY(), 
				direction.getZ()+force.getZ());
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
		posX = posX + position.getX();
		posY = posY + position.getY();
		posZ = posZ + position.getZ();
	}

	@Override
	public void setPosition(float x, float y, float z) {
		setPosition(new Vector3f(x,y,z));
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public void setMass(double mass) {
		this.mass = mass;		
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public Vector3f getDirection() {
		return direction;
	}
}
