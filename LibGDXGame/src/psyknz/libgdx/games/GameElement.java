package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameElement {
	
	// Core game logic for the GameElement.
	public void update(float delta);
	
	// Draws all parts of the GameElement to the screen using that screens SptiteBatch.
	public void draw(SpriteBatch batch);
}
