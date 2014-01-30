package entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class Surface {
	
	private boolean showSurface = true;
	
	
	public Surface(){
		
	}
	
	public void showSurface(boolean surface){
		showSurface = surface;
	}
	
	public boolean isSurfaceVisible(){
		return showSurface;
	}
	
	public void drawSurface(int size){
		if(showSurface){
			
		
			//float[x][y][z] 
			/*float[][][] surfaceGrid = new float[10][10][10];
			for(int x=0; x<10; x++){
				surfaceGrid[x][0][0] = 2f*x;
				for(int y=0; y<10; y++){
					surfaceGrid[x][y][0] = 2f*x;
					for(int z=0; z<10; z++){
						surfaceGrid[x][y][z] = 2f*x;		
					}
				}
			}*/
			double width = 2, height = 2, depth = 2;
			
			for(int i=0; i<size;i++){
				width = i*1.1;
				height = i;
				depth = i*1.1;
				
				drawOne(width, -1, depth);
				
			}
		
		}
	}
	
	
	public void drawOne(double width, double height, double depth){

		
		
		// 1
		GL11.glColor3d(1.0f,0.5f,0.0f);
        GL11.glVertex3d( width, height,-depth);        
        GL11.glVertex3d(-width, height,-depth);        
        GL11.glVertex3d(-width, height, depth);
        GL11.glVertex3d( width, height, depth);
        /*
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
        */
	}
	
}
