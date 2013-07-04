package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SpriteElement implements GameElement {
    
	// The sprite used to draw the SpriteElement.
	protected Sprite sprite;
	
	// The bounding box to be used when drawing the sprite;
	protected Rectangle bounds;
	
	// The point on the sprite which the image should be drawn about.
	protected float xOrigin, yOrigin;
	
	// The color used to tint the SpriteElement.
	public Color color = new Color(0, 0, 0, 1);
	
	// Whether or not the Sprite Element should be drawn.
	public boolean visible = true;
	
	// Creates the SpriteElement at the desired location maintaining the sprites dimensions and with its origin at the center of the sprite.
	public SpriteElement(Sprite sprite, float x, float y) {
		this(sprite, x, y, sprite.getWidth(), sprite.getHeight());
	}
	
	// Creates the SpriteElement at the desired location with the given dimensions and its origin at the center of the sprite.
	public SpriteElement(Sprite sprite, float x, float y, float width, float height) {
		this(sprite, x, y, width, height, width / 2, height / 2);
	}
	
	// Creates the SpriteElement at the desired location with the given origin and dimensions.
	public SpriteElement(Sprite sprite, float x, float y, float width, float height, float xOrigin, float yOrigin) {
		setSprite(sprite);
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		bounds = new Rectangle(x - xOrigin, y - yOrigin, width, height);
	}
	
	// A SpriteElement has no game logic.
	@Override
	public void update(float delta) {}
	
	// Draws the SpriteElement to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		if(visible) {
			sprite.setColor(color);
			sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			sprite.draw(batch);
		}
	}
	
	// Sets the sprite used to draw this element.
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	// Returns the SpriteElement's bounding box.
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Returns the x co-ordinate on the screen of the sprites origin
	public float getX() {
		return bounds.x + xOrigin;
	}
	
	// Returns the y co-ordinate on the screen of the sprites origin.
	public float getY() {
		return bounds.y + yOrigin;
	}
	
	// Sets the point on the sprite where its co-ordinates are assigned.
	public void setOrigin(float xOrigin, float yOrigin) {
		bounds.x += this.xOrigin;
		bounds.y += this.yOrigin;
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		setPosition(bounds.x, bounds.y);
	}
	
	// Sets the sprites position on the screen.
	public void setPosition(float x, float y) {
		bounds.x = x - xOrigin;
		bounds.y = y - yOrigin;
	}
}
