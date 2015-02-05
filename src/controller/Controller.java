package controller;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import vehicles.Ship;
import world.GameWorld;

public class Controller {
	private GameWorld gameWorld;
	private double sensitivity = 5;
	
	public Controller(GameWorld gameWorld) {
		 this.gameWorld = gameWorld; 
	}
	
	public void initKeyboard(){
		 try {
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void keyboardPoll() {
		if(Keyboard.isCreated()){
			while(Keyboard.next()){
			    if(Keyboard.isKeyDown(Keyboard.KEY_L)){
			    	gameWorld.isWorldRepresentedAsLines(!gameWorld.isWorldRepresentedAsLines());
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_H)){
			    	Ship ship = gameWorld.getPlayer().getCurrentShip();
			    	ship.setHover(!ship.isHovering());
			    	if(ship.isHovering()){
			    		System.out.println("Hovering system activated");	
			    	}
			    	else{
			    		System.out.println("Hovering system deactivated");
			    	}
			    }
			}
		    if(Keyboard.isKeyDown(Keyboard.KEY_W)){
		    	gameWorld.getPlayerCamera().moveZ(0.2f);
		    }
		    else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
		        gameWorld.getPlayerCamera().moveZ(-0.2f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_A)){
		    	gameWorld.getPlayerCamera().moveX(0.2f);
		    }
		    else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
		    	gameWorld.getPlayerCamera().moveX(-0.2f);
		    }
	
		    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
		    	gameWorld.getPlayerCamera().moveY(-0.3f);
		    }
		    else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
		    	gameWorld.getPlayerCamera().moveY(0.1f);
		    }
		    
		    if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){
		    	gameWorld.getPlayer().getCurrentShip().increaseThrottle();
		    }
		    else if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){
		    	gameWorld.getPlayer().getCurrentShip().decreaseThrottle();
		    }
		    
		    
			// Arrow keys // Steer ship
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
				gameWorld.getPlayer().getCurrentShip().tiltUpDown(1f);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				gameWorld.getPlayer().getCurrentShip().tiltUpDown(-1f);
			}
			
		    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				gameWorld.getPlayer().getCurrentShip().turnDegrees(-1f);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				gameWorld.getPlayer().getCurrentShip().turnDegrees(1f);
			}
		}
	}
	
	
	
	public void mousePoll(){
		//Look over the horizontal line, x-axis
		if(Mouse.isButtonDown(0)){
			//Look up and down, y-axis
			//gameWorld.getPlayerCamera().rotateX((float)-((getMouseDy()/500)*sensitivity));
			gameWorld.getPlayerCamera().moveZ((float)((getMouseDy()/20)*sensitivity));
			gameWorld.getPlayerCamera().rotateRelativeY((float)(-(getMouseDx()/1000)*sensitivity));
			
			
			if (getMouseDy()!=0){
				System.out.println("Moving Y-wise: " + getMouseDy());
			}	
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
