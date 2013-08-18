package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CircleElement extends BoundedElement {
	
	// Reference to the sprite used to draw the orb.
	public Sprite sprite;
	
	// The color used to tint the Sprite.
	public Color color;
	
	public CircleElement(Sprite sprite, Color color, float size) {
		super(0, 0, size, size);
		this.sprite = sprite;
		this.color = color;
		
		// Sets the sprites origin to its center.
		xOrig = size / 2;
		yOrig = size / 2;
	}
	
	// Updates the OrbElement's game logic.
	@Override
	public void update(float delta) {}
	
	// Draws the OrbElement to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		sprite.setColor(color);
		sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		sprite.draw(batch);
	}

}