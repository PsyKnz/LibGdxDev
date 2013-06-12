package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class TeleportingElement extends GameElement {
    // The elements sprites.
	private Sprite outline;
	private Sprite inside;
	
	// The elements colors, used when drawing the sprites.
	public Color outlineColor;
	public Color insideColor;
	
	// The size of the element in in-game co-ordinates.
	private final int SIZE = 32;
	
	// The bounding box for the element.
	private Rectangle bounds;
	
	private float timer;
	private float angle;
    
	public TeleportingElement(Sprite outline, Sprite inside, Color color, int x, int y) {
		// Sets the elements sprites and their color.
		this.outline = outline;
		this.inside = inside;
		
		setColor(color);
		
		// Generate the elements bounding box.
		this.bounds = new Rectangle(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
		
		timer = MathUtils.random() * 5 + 5;
	}
	
	public void update(float delta) {
		timer -= delta;
		
		if(timer <= 0) {
			// teleport the element
			timer = MathUtils.random() * 5 + 5;
		}
		else if(timer <= 2) {
			// spin the element. the closer the timer is to 0 the faster it should spin.
		}
	}
	
	public void draw(SpriteBatch batch) {
		// Draws the outline of the element.
		outline.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		outline.setColor(outlineColor);
		outline.draw(batch);
		
		// Draws the inside of the element.
		inside.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		inside.setColor(insideColor);
		inside.draw(batch);
	}
	
	// Returns the bounding box for the element.
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Sets the color of the elements outline and blends that with the background to produce the inside color.
	public void setColor(Color color) {
		outlineColor = color;
		insideColor = new Color(color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1);
	}
}
