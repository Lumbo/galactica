package controller;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import world.GameWorld;

public class Controller {
	private GameWorld gameWorld;
	
	private float mouseSensitivity = 0.3f;
	
	public Controller(GameWorld gameWorld) {
		 this.gameWorld = gameWorld; 
	}
	
	public void keyboardPoll() {
		while(Keyboard.next()){
			//Spawn shit
			//Spawn squares
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
		    	gameWorld.populateRandomSquares();
		    }
		    if (Keyboard.isKeyDown(Keyboard.KEY_P)){
		    	gameWorld.populateRandomSpheres();
		    }
		    if (Keyboard.isKeyDown(Keyboard.KEY_INSERT)){
		    	gameWorld.showSurface(!gameWorld.isSurfaceVisible());;
		    }
		}
	    if(Keyboard.isKeyDown(Keyboard.KEY_W)){
	    	gameWorld.getPlayerCamera().moveZ(0.2f, 1);
	    }
	    else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
	        gameWorld.getPlayerCamera().moveZ(-0.2f, 1);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_A)){
	    	gameWorld.getPlayerCamera().moveZ(0.2f, 0);
	    }
	    else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
	    	gameWorld.getPlayerCamera().moveZ(-0.2f, 0);
	    }

	    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
	    	gameWorld.getPlayerCamera().moveY(-0.3f);
	    }
	    else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
	    	gameWorld.getPlayerCamera().moveY(0.1f);
	    }

		// Arrow keys
		else if (Keyboard.getEventKey() == Keyboard.KEY_UP){
			
		}
		else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
			
		}
		else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN){
			
		}
		else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
			
		}
	}
	
	
	
	public void mousePoll(){
		if(Mouse.isButtonDown(0)){
			//Look over the horizontal line, x-axis
			gameWorld.getPlayerCamera().rotateY((float)getMouseDx()*mouseSensitivity);
			
			//Look up and down, y-axis
			gameWorld.getPlayerCamera().rotateX((float)-getMouseDy()*mouseSensitivity);			
		}
	}
	
	
	
	public double getMouseDx(){
		return Mouse.getDX();
	}
	
	public double getMouseDy(){
		return Mouse.getDY();
	}
	
	public double getMouseScroll(){
		return Mouse.getDWheel();
	}
	
	
	/**
	 * 
	 * @return if -1 is returned no button is currently pressed
	 */
	public int getMouseButton(){
		//Leftclick
		if(Mouse.isButtonDown(0)){
			return 1;
		}
		//Rightclick
		else if(Mouse.isButtonDown(1)){
			return 2;
		}
		//No buttons where pressed
		else{
			return -1;
		}
	}
	
	
}
