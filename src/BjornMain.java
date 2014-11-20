
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import graphics.Model;
import graphics.OBJLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
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
		
		initGL();
		
		float position = 0f;
		float rotationSpeed = 0.5f;
		float rotation = 0.0f;
		float lightRotation = 0.0f;
		Model m = null;
		try{
			m = OBJLoader.getModel("res/models/bunny/bunny.obj");
		}catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Could not open file");
		}catch (IOException e){
			e.printStackTrace();
		}
		
		int shaderProgram = createShader("res/shaders/shader.vert", "res/shaders/shader.frag");
		
		glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(new float[]{-10, 80, -10, 1}));
		
		while (!Display.isCloseRequested()){
			//glUseProgram(shaderProgram);
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

	private void initGL() {
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[] {0.05f, 0.05f, 0.05f, 1f}));
		glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(new float[] {1.55f, 1.5f, 1.55f, 1f}));
		glEnable(GL_CULL_FACE); 
		glCullFace(GL_BACK); //Don't draw the back side of triangles
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);
		
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective((float) 30, 640f/480f, 0.001f, 100);
		glMatrixMode(GL_MODELVIEW);
	}
	
	private int createShader(String vertexShaderPath, String fragmentShaderPath){
		int shaderProgram = glCreateProgram();
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fragmentShaderPath));
			String line;
			while ((line = reader.readLine()) != null){
				vertexShaderSource.append(line).append('\n');
			}
			System.out.println(vertexShaderSource.toString());
			reader.close();
		}catch (IOException e){
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader(vertexShaderPath));
			String line;
			while ((line = reader.readLine()) != null){
				fragmentShaderSource.append(line).append('\n');
			}
			System.out.println(fragmentShaderSource.toString());
			reader.close();
		}catch (IOException e){
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		
		glShaderSource(vertexShader, vertexShaderSource.toString());
		glCompileShader(vertexShader);
		if(glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Vertex shader did not compile");
		}
		glShaderSource(fragmentShader, fragmentShaderSource.toString());
		glCompileShader(fragmentShader);
		if(glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("fragment shader did not compile");
		}
		
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
		
		return shaderProgram;
	}
	
	public static void main(String[] args){
		new BjornMain();
	}

	
	private static FloatBuffer asFloatBuffer(float [] floats){
		FloatBuffer res = BufferUtils.createFloatBuffer(floats.length);
		res.put(floats);
		res.flip();
		return res;
	}
}
