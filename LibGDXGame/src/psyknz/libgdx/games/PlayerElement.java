package psyknz.libgdx.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerElement extends GameElement{
	// Reference to the SnakeElement which the player can control and its head.
	private SnakeElement player;
	private Rectangle head;
	
	// Whether or not the players SnakeElement head has been touched.
	private boolean touched = false;
	
	public PlayerElement(Sprite outline, Sprite inside) {
		player = new SnakeElement(outline, inside, LibGDXGame.width / 2, LibGDXGame.height / 2, 5, 32, 40, new Color(0, 0, 1, 1));
		head = player.getBounds(0);
	}
	
	// Tests for user input and moves the players SnakeElement appropriately. Needs a better implementation.
	public void update(float delta) {
		// If the screen is touched checks to see if the players finger is above the snakes head, If it is the snake is set to touched.
		if(Gdx.input.isTouched()) {
			if(head.contains(Gdx.input.getX() * LibGDXGame.visibleWidth / Gdx.graphics.getWidth(), 
			                 LibGDXGame.visibleHeight - Gdx.input.getY() * LibGDXGame.visibleHeight / Gdx.graphics.getHeight())) {
				touched = true;
			}
			
			// If the player is set to touched then the head of the SnakeElement follows the finger around the screen.
			if(touched){
				player.move(Gdx.input.getX() * LibGDXGame.visibleWidth / Gdx.graphics.getWidth(), 
				            LibGDXGame.visibleHeight - Gdx.input.getY() * LibGDXGame.visibleHeight / Gdx.graphics.getHeight());
			}
		}
		else {
			// If the player releases the snake head ensures that the snake isn't set to touched.
			touched = false;
		}
		
		// Allows the players SnakeBody parts to follow along behind the head.
		player.update(delta);
	}
	
	public void draw(SpriteBatch batch) {
		// Draws the players SnakeElement.
		player.draw(batch);
	}

}
