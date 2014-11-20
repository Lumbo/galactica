import graphics.MainWindow;

import java.awt.Dimension;
import java.awt.Frame;

import world.GameWorld;


public class Main {

	public static void main(String[] args) {
		
		GameWorld.getInstance(); // Starts the GameWorld, which also triggers the Renderer
	}

}
