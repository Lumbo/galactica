package entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class Surface {
	private List<Quad> quadList = new ArrayList<Quad>();
	
	
	public void draw(){
		/*GL11.glColor3d(1.0f,1.0f,0.0f);
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
        GL11.glVertex3d( width,-height,-depth);*/
	}
	
}
