package entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class Surface {
	
	private boolean showSurface = true;
	private List<Quad> quadList = new ArrayList<Quad>();
	
	public Surface(){
		
	}
	
	public void showSurface(boolean surface){
		showSurface = surface;
	}
	
	public boolean isSurfaceVisible(){
		return showSurface;
	}
	
	public void generateSurface(int size){
		quadList.clear();
		if(showSurface){
			float width = 2, height = 2, depth = 2;
			
			for(int i=0; i<size;i++){
				width = (float) (i*1.1);
				height = (float) 1;
				depth = (float) (i*1.1);
				Quad q = new Quad(1, width, depth);
				
				quadList.add(q);
				//q.setColor(r, b, g);
			}		
		}
	}
	
	public void drawSurface(){
		for(Quad q : quadList){
			q.draw();
		}
	}
	
	public List<Quad> getQuads(){
		return quadList;
	}
	
	
	public void drawOne(double width, double height, double depth){

		
		
		// 1
		GL11.glColor3d(0.5f,0.5f,0.0f);
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
