package graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import world.GameWorld;

public class MainWindow {
	public MainWindow() {
		setupDisplay();
		startRendering();
	}
	
	public void setupDisplay(){

	}
	
	public void startRendering(){
		GameWorld.getInstance();
	}
}
