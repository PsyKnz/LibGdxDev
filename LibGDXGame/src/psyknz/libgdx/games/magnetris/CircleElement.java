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
	
	public CircleElement(Sprite sprite, Color color, float diameter) {
		super(0, 0, diameter, diameter);
		this.sprite = sprite;
		this.color = color;
		
		// Sets the sprites origin to its center (essentially xOrig and yOrig are both equal to the radius).
		xOrig = yOrig = diameter / 2;
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
	
	// Returns whether the given x, y co-ordinate is inside of the CircleElement.
	public boolean contains(float x, float y) {
		if(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) < Math.pow(xOrig, 2)) return true; 
		return false;
	}
	
	// Returns whether the given CircleElement is fully contained within this CircleElement.
	public boolean contains(CircleElement circle) {
		if(Math.pow(circle.getX() - getX(), 2) + Math.pow(circle.getY() - getY(), 2) < Math.pow(xOrig, 2) - Math.pow(circle.xOrig, 2)) return true;
		return false;
	}
	
	// Returns whether the two CircleElements overlap.
	public boolean overlaps(CircleElement circle) {
		if(Math.pow(circle.getX() - getX(), 2) + Math.pow(circle.getY() - getY(), 2) < Math.pow(xOrig, 2) + Math.pow(circle.xOrig, 2)) return true;
		return false;
	}
}