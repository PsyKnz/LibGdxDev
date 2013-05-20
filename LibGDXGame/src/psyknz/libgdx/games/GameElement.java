package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameElement {
	
	/* Identifier for the GameElement. Allows for intelligent grouping of GameElements
	 * using string concatenation and contains functions. */
	public String id;
	
	/* Numeric representation of when the GameElement should be rendered relative to other
	 * GameElements. The greater the depth the later it should be rendered. */
	public float depth;
	
	// Core game logic for the GameElement.
	public void update(float delta);
	
	// Draws all parts of the GameElement to the screen using that screens SptiteBatch.
	public void draw(SpriteBatch batch);
}
