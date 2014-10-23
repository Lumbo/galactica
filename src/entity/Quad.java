package entity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Quad implements EntityInterface {
	private float height;
	private float width;
	private float depth;
	
	private double mass;
	private Color topColor;
	
	private Vector3f position;
	private Vector3f direction = new Vector3f(0,0,0);
	private Vector3f force = new Vector3f(0,0,0);
	
	private boolean initiated = false;
	private boolean isMoveable = true;
	
	public Quad(){
	}
	
	public Quad(float height, float width, float depth){
		this(height, width, depth, 0,0,0);
	}
	
	
	public Quad(float height, float width, float depth, float posX, float posY, float posZ){
		createQuad(height, width, depth, posX, posY, posZ);
		setPosition((float) posX+width/2, (float) posY+height/2,(float) posZ+depth/2);
	}
	
	public void setTopColor(Color c){
		this.topColor = c;
	}
	
	public boolean isInitiated(){
		return initiated;
	}
	
	public void createQuad(float height, float width, float depth, 
			float posX, float posY, float posZ){
		this.height = height;
		this.width = width; 
		this.depth = depth;
		
        initiated = true;
	}
	
	
	
	/**
	 * Checks whether the argument Quad, q, collide with this Quad
	 * @param Quad q
	 * @return true if the two quads collide
	 */
	public boolean isColliding(Quad q){
		if(isCollidingY(q)){
			System.out.println("Q1 Coord x: " + q.getPosition().getX() + 
					", y: " + q.getPosition().getY() + 
					", z: " + q.getPosition().getZ());
			System.out.println("Q2 Coord x: " + q.getPosition().getX() + 
					", y: " + q.getPosition().getY() + 
					", z: " + q.getPosition().getZ());
			return true;
		}
		
		if(isCollidingY(q)){
			return true;
		}
		
		System.out.println("X colliding: " + isCollidingX(q));
		System.out.println("Y colliding: " + isCollidingY(q));
		System.out.println("Z colliding: " + isCollidingZ(q));
		
		return false;
	}
	
	private boolean isCollidingX(Quad q){
		//If "this" is to the left of q, the only possible collision
		//should be "this"'s right side and q's left
		if(this.getPosition().getX() < q.getPosition().getX()){
			if(this.getPosition().getX()+this.getWidth()/2 >= q.getPosition().getX()-q.getWidth()/2){
				return true;
			}	
		}
		else{
			if(this.getPosition().getX()-this.getWidth()/2 <= q.getPosition().getX()+q.getWidth()/2){
				return true;
			}
		}
		return false;
	}
	
	private boolean isCollidingY(Quad q){
		//Check the Y-axis
		if(this.getPosition().getY() > q.getPosition().getY()){
			if(this.getPosition().getY()+this.getHeight()/2 >= q.getPosition().getY()-q.getHeight()/2){
				System.out.println("Colliding coords: y1 - " + this.getPosition().getY() + ">=" + q.getPosition().getY());
				System.out.println("y1 height: " + this.getHeight()/2 + ", y2 height: " + q.getHeight()/2);
				return true;
			}
		}
//		else{
//			if(this.getPosition().getY()-this.getHeight()/2 <= q.getPosition().getY()+q.getHeight()/2){
//				System.out.println("Colliding coords: y1 - " + this.getPosition().getY() + "<=" + q.getPosition().getY());
//				System.out.println("y1 height: " + this.getHeight()/2 + ", y2 height: " + q.getHeight()/2);
//				return true;
//			}
//		}
		return false;
	}
	
	private boolean isCollidingZ(Quad q){
		//Check the Z-axis
		if(this.getPosition().getZ() > q.getPosition().getZ()){
			if(this.getPosition().getZ()-this.getDepth()/2 >= q.getPosition().getZ()+q.getDepth()/2){
				return true;
			}
		}
		else{
			if(this.getPosition().getZ()+this.getDepth()/2 <= q.getPosition().getZ()-q.getDepth()/2){
				return true;
			}
		}
		return false;
	}
	
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getDepth(){
		return depth;
	}
	
	public boolean isMoveable(){
		return isMoveable;
	}
	
	public void setIsMoveable(boolean b){
		isMoveable = b;
	}
	
	
	public void draw(){
		// 1 - Front
		//GL11.glNormal3f(0, 0, 1);
        GL11.glColor3d(1.0f,1.0f,0.0f);
        //GL11.glColor3d(topColor.getRed(), topColor.getBlue(), topColor.getGreen());
        GL11.glVertex3d( width+position.getX(), height+position.getY(),-depth+position.getZ());        
        GL11.glVertex3d(-width+position.getX(), height+position.getY(),-depth+position.getZ());        
        GL11.glVertex3d(-width+position.getX(), height+position.getY(), depth+position.getZ());
        GL11.glVertex3d( width+position.getX(), height+position.getY(), depth+position.getZ());
        
        // 2 - Back
        //GL11.glNormal3f(0, 0, -1);
        GL11.glColor3f(1.0f,0.5f,0.0f);
        GL11.glVertex3d( width+position.getX(),-height+position.getY(), depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(),-height+position.getY(), depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(),-height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d( width+position.getX(),-height+position.getY(),-depth+position.getZ());
        
        // 3 - Right
        //GL11.glNormal3f(-1, 0, 0);
        GL11.glColor3f(1.0f,0.0f,0.0f);
        GL11.glVertex3d( width+position.getX(), height+position.getY(), depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(), height+position.getY(), depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(),-height+position.getY(), depth+position.getZ());
        GL11.glVertex3d( width+position.getX(),-height+position.getY(), depth+position.getZ());
        
        // 4 - Left
        //GL11.glNormal3f(1, 0, 0);
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3d( width+position.getX(),-height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(),-height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(), height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d( width+position.getX(), height+position.getY(),-depth+position.getZ());
        
        // 5 - Top
        GL11.glNormal3f(0, 1, 0);
        GL11.glColor3f(0.0f,0.0f,1.0f);
        GL11.glVertex3d(-width+position.getX(), height+position.getY(), depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(), height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(),-height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d(-width+position.getX(),-height+position.getY(), depth+position.getZ());
        
        // 6 - Bottom
        //GL11.glNormal3f(0, -1, 0);
        GL11.glColor3f(1.0f,0.0f,1.0f);
        GL11.glVertex3d( width+position.getX(), height+position.getY(),-depth+position.getZ());
        GL11.glVertex3d( width+position.getX(), height+position.getY(), depth+position.getZ());
        GL11.glVertex3d( width+position.getX(),-height+position.getY(), depth+position.getZ());
        GL11.glVertex3d( width+position.getX(),-height+position.getY(),-depth+position.getZ());
	}

	@Override
	public void applyForce(Vector3f force) {
		this.force = new Vector3f(
				this.force.getX()+force.getX(), 
				this.force.getY()+force.getY(), 
				this.force.getZ()+force.getZ());
		position.set(
				position.getX()+this.force.getX(), 
				position.getY()+this.force.getY(), 
				position.getZ()+this.force.getZ());
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
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
	
	public Vector3f getForce(){
		return this.force;
	}
	

	@Override
	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
}
