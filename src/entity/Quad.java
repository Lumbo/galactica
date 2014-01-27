package entity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class Quad {
	private double height;
	private double width;
	private double depth;
	private boolean initiated = false;
	
	public Quad(){
	}
	
	public Quad(double height, double width, double depth){
		createQuad(height, width, depth);
	}
	
	public boolean isInitiated(){
		return initiated;
	}
	
	public void createQuad(double height, double width, double depth){
		this.height = height;
		this.width = width; 
		this.depth = depth;
		
        initiated = true;
	}
	
	public void draw(){
		// 1
        GL11.glColor3d(1.0f,1.0f,0.0f);
        GL11.glVertex3d( width, height,-depth);        
        GL11.glVertex3d(-width, height,-depth);        
        GL11.glVertex3d(-width, height, depth);
        GL11.glVertex3d( width, height, depth);
        
        // 2
        GL11.glColor3f(1.0f,0.5f,0.0f);            
        GL11.glVertex3d( width,-height, depth);
        GL11.glVertex3d(-width,-height, depth);
        GL11.glVertex3d(-width,-height,-depth);
        GL11.glVertex3d( width,-height,-depth);
        
        // 3
        GL11.glColor3f(1.0f,0.0f,0.0f);
        GL11.glVertex3d( width, height, depth);
        GL11.glVertex3d(-width, height, depth);
        GL11.glVertex3d(-width,-height, depth);
        GL11.glVertex3d( width,-height, depth);
        
        // 4
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3d( width,-height,-depth);
        GL11.glVertex3d(-width,-height,-depth);
        GL11.glVertex3d(-width, height,-depth);
        GL11.glVertex3d( width, height,-depth);
        
        // 5
        GL11.glColor3f(0.0f,0.0f,1.0f);
        GL11.glVertex3d(-width, height, depth);
        GL11.glVertex3d(-width, height,-depth);
        GL11.glVertex3d(-width,-height,-depth);
        GL11.glVertex3d(-width,-height, depth);
        
        // 6
        GL11.glColor3f(1.0f,0.0f,1.0f);
        GL11.glVertex3d( width, height,-depth);
        GL11.glVertex3d( width, height, depth);
        GL11.glVertex3d( width,-height, depth);
        GL11.glVertex3d( width,-height,-depth);
	}
	
	
	public void createQuad(){
		/*if (p1 == 0.0 && p2 == 0.0 && p3 == 0.0){
			p1 = Math.random()*100;
			p2 = Math.random()*100;
			p3 = Math.random()*100;
			setColor(Math.random()*255, Math.random()*255, Math.random()*255);
		}
		/*System.out.println("p1 = " + p1);
		System.out.println("p2 = " + p2);
		System.out.println("p3 = " + p3);
		
		/*
			Y|    / Z
			 |   /
			 |  /
			 | /
			 |/______ X
		
		//glVertex3d( X , Y , Z);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);          // Top Right Of The Quad (Top)
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);          // Top Left Of The Quad (Top)
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);          // Bottom Left Of The Quad (Top)
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);          // Bottom Right Of The Quad (Top)
		
		
		GL11.glVertex3d(p1, p1, p1);
		GL11.glVertex3d(p1+p2, p1, p1);
		GL11.glVertex3d(p1+p2, p1+p2, p1+p3);
		GL11.glVertex3d(p1, p1+p2, p1+p2);
		
		
		*/
		
		
		for (int i=0; i<10; i++){
			for (int j=0; j<10; j++){
				for (int k=0; k<10; k++){
					
				}
			}
		}
		
		/*for (int i=0; i<6; i++){
			GL11.glColor3f(1.0f,(float)i/6,0.0f);
			for (int j=0; j<4; j++){
			       GL11.glVertex3f((float)p1*10.0f, (float)p2*10.0f, (float)p3*-10.0f);        
			       GL11.glVertex3f((float)p1*-1.0f, (float)p2*1.0f, (float)p3*-1.0f);        
			       GL11.glVertex3f((float)p1*-1.0f, (float)p2*1.0f, (float)p3*1.0f);
			       GL11.glVertex3f((float)p1*1.0f, (float)p2*1.0f, (float)p3*1.0f);
			}
		}
			
		// Top right		
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);        
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);        
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
        
        // 2
        GL11.glColor3f(1.0f,0.5f,0.0f);            
        GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
        GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
        
        // 3
        GL11.glColor3f(1.0f,0.0f,0.0f);
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
        GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
        
        // 4
        GL11.glColor3f(1.0f,1.0f,0.0f);
        GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
        
        // 5
        GL11.glColor3f(0.0f,0.0f,1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
        GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
        GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
        
        // 6
        GL11.glColor3f(1.0f,0.0f,1.0f);
        GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
        GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
        GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
        GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
		*/

		
	}
	
	public void setColor(double r, double b, double g){
		GL11.glColor3d(r, g, b);
	}
}
