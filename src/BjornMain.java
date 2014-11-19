
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import graphics.Model;
import graphics.OBJLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class BjornMain {
	public BjornMain(){
		try{
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Bjorn");
			Display.create();
			System.out.println("Opengl version is " + GL11.glGetString(GL11.GL_VERSION));
		} catch (LWJGLException e){
			e.printStackTrace();
		}
		//init ogl
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective((float) 30, 640f/480f, 0.001f, 100);
		glMatrixMode(GL_MODELVIEW);
		
		float position = 0f;
		float rotationSpeed = 0.5f;
		float rotation = 0.0f;
		Model m = null;
		try{
			//m = OBJLoader.loadModel(new File("res/models/monkey/monkey.obj"));
			m = OBJLoader.loadModel(new File("res/models/bunny/bunny.obj"));
		}catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Could not open file");
		}catch (IOException e){
			e.printStackTrace();
		}
		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			
			glTranslatef(0, 0, position);
			glRotatef(rotation, 0, 1, 0);
			
			rotation += rotationSpeed;
			m.draw();
			
			glLoadIdentity();
			glTranslatef(-3, 0, position);
			glRotatef(rotation, 0, 0, 1);
			m.draw();
			
			glLoadIdentity();
			glTranslatef(3, 0, position);
			glRotatef(rotation, 0, 1, 1);
			m.draw();
			
			glLoadIdentity();
			glTranslatef(0, -3, position);
			glRotatef(rotation, 1, 1, 0);
			m.draw();
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				Display.destroy();
				System.exit(0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				position += 0.1f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
				position -= 0.1f;
			}
			
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	
	public static void main(String[] args){
		new BjornMain();
	}
}
