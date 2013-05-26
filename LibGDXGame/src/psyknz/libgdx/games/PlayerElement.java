package psyknz.libgdx.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerElement extends GameElement{
	
	// Reference to the SnakeElement which the player can control.
	private SnakeElement player;
	
	private boolean touched = false;
	
	public PlayerElement(Sprite sprite) {
		player = new SnakeElement(sprite, LibGDXGame.width / 2, LibGDXGame.height / 2, 3, 32, 40, new Color(0, 0, 1, 1));
	}
	
	// Tests for user input and moves the players SnakeElement appropriately. Needs a better implementation.
	public void update(float delta) {
		if(Gdx.input.isTouched()) {
			if(player.getBounds(0).contains(Gdx.input.getX(), LibGDXGame.height - Gdx.input.getY())) {
				touched = true;
			}
			else if(touched){
				player.move(Gdx.input.getX(), LibGDXGame.height - Gdx.input.getY());
			}
		}
		else {
			touched = false;
		}
		
		player.update(delta);
	}
	
	public void draw(SpriteBatch batch) {
		player.draw(batch);
	}

}
