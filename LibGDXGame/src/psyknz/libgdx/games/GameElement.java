package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameElement {
	
	// Identifier for the GameElement. Allows for intelligent grouping of GameElements using string concatenation and contains functions.
	public String tag;
	
	/* Numeric representation of when the GameElement should be rendered relative to other GameElements. The greater the depth the later
	 * it should be rendered. For example backgrounds should be placed at a depth near 0 while UI components should have a depth near
	 * to 1. */
	public float depth;
	
	// Core game logic for the GameElement.
	public abstract void update(float delta);
	
	// Draws all parts of the GameElement to the screen using that screens SptiteBatch.
	public abstract void draw(SpriteBatch batch);
}
