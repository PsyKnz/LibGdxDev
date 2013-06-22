package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class PlayerElement implements GameElement{
    
	private PlayScreen screen;
	
	// Reference to the SnakeElement which the player can control and its heads bounding box.
	private SnakeElement player;
	
	// Whether or not the players SnakeElement head has been touched.
	private boolean touched = false;
	
	// Temporary variable to process collisions.
	private Array<ShapeElement> collidedElements;
	
	public PlayerElement(PlayScreen screen, Sprite circle, int x, int y) {
		this.screen = screen;
		
		player = new SnakeElement(screen, circle, Color.BLUE, x, y, 24, 0);
	}
	
	// Tests for user input and moves the players SnakeElement appropriately. Needs a better implementation.
	public void update(float delta) {
		// If the screen is touched checks to see if the players finger is above the snakes head, If it is the snake is set to touched.
		if(screen.isTouched()) {
			if(player.getBounds(0).contains(screen.touchX(), screen.touchY())) {
				touched = true;
			}
			
			// If the player is set to touched then the head of the SnakeElement follows the finger around the screen.
			if(touched){
				player.move(screen.touchX(), screen.touchY());
			}
		}
		else {
			// If the player releases the snake head ensures that the snake isn't set to touched.
			touched = false;
		}
		
		// If the player passes outside of the arena boundaries then the background changes colour.
		// EVENTUALLY THE PLAYER WILL EXPLODE INSTEAD.
		if(!screen.arena.bounds.contains(player.getBounds(0))) {
			screen.background.set(screen.arena.color.r / 3, screen.arena.color.g / 3, screen.arena.color.b / 2, screen.background.a);
		}
		else screen.background.set(Color.BLACK);
		
		// Tests to see if the player element is colliding with one of the on screen enemies.
		collidedElements = screen.enemies.testCollision(player.getBounds(0));
		if(collidedElements != null) {
			for(int i = 0; i < collidedElements.size; i++) {
				player.grow();
				screen.enemies.destroyElement(collidedElements.get(i));
			}
		}
		
		// Allows the players SnakeBody parts to follow along behind the head.
		player.update(delta);
	}
	
	public void draw(SpriteBatch batch) {
		// Draws the players SnakeElement.
		player.draw(batch);
	}

}
