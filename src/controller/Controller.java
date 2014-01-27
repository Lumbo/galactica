package controller;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import world.GameWorld;

public class Controller {
	private GameWorld gameWorld;
	
	public Controller(GameWorld gameWorld) {
		 this.gameWorld = gameWorld; 
	}
	
	public void keyboardPoll() {
		while(Keyboard.next()){
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
		    	gameWorld.populateRandomSquares();
		    }
		    if (Keyboard.isKeyDown(Keyboard.KEY_P)){
		    	gameWorld.populateRandomSpheres();
		    }
		}
	    if(Keyboard.isKeyDown(Keyboard.KEY_W)){
	    	gameWorld.getPlayerCamera().moveZ(0.2f, 1);
	    }
	    else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
	        gameWorld.getPlayerCamera().moveZ(-0.2f, 1);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_A)){
	        //gameWorld.getPlayerCamera().rotateY(-0.2f);
	    	gameWorld.getPlayerCamera().moveZ(0.2f, 0);
	    }
	    else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
	        //gameWorld.getPlayerCamera().rotateY(0.2f);
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
		//Look over the horizontal line, x-axis
		gameWorld.getPlayerCamera().rotateY((float)getMouseDx());
		
		//Look up and down, y-axis
		gameWorld.getPlayerCamera().rotateX((float)-getMouseDy());
		
		if (getMouseDy()!=0){
			System.out.println("Moving Y-wise: " + getMouseDy());
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
