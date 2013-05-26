package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class SnakeElement extends GameElement {
    
	// Constants for presenting the SnakeBody parts.
	public final int BODY_SIZE;
	public final int HEAD_SIZE;	
	public final Color BODY_COLOR;
	
	// The base sprite to use for presenting the SnakeBody parts.
	private Sprite sprite;
	
	// List of the SnakeElements SnakeBody pieces.
	private ArrayList<SnakeBody> body;
	
	public SnakeElement(Sprite sprite, int x, int y, int length, int bodySize, int headSize, Color bodyColor) {
		body = new ArrayList<SnakeBody>();
		
		this.sprite = sprite;
		this.HEAD_SIZE = headSize;
		this.BODY_SIZE = bodySize;
		this.BODY_COLOR = bodyColor;
		
		body.add(new SnakeBody(null, x, y, HEAD_SIZE));
		body.get(0).color = new Color(1, 0, 0, 1);
		for(int i = 0; i < length; i++) {
			grow();
		}
	}
	
	public void update(float delta) {
		for(int i = 0; i < body.size(); i++) {
			body.get(i).update(delta);
		}
	}
	
	public void draw(SpriteBatch batch) {
		for(int i = body.size() - 1; i >= 0; i--) {
			body.get(i).draw(batch);
		}
	}
	
	public void grow() {
		SnakeBody tail = body.get(body.size() - 1);
		body.add(new SnakeBody(tail, tail.getX(), tail.getY(), BODY_SIZE));
	}
	
	// Returns the bounding box of the SnakeBody part at the given index.
	public Rectangle getBounds(int index) {
		return body.get(index).bounds;
	}
	
	// Moves the head of the SnakeElement to the given location. Its bounding box will be centered about the given co-ordinates.
	public void move(int x, int y) {
		body.get(0).bounds.x = x - body.get(0).bounds.width / 2;
		body.get(0).bounds.y = y - body.get(0).bounds.height / 2;
	}
	
	/* Class representing a single section of the SnakeElements body. */
	private class SnakeBody extends GameElement {
		
		// The parent SnakeBody object is the one which this SnakeBody part follows around the screen.
		private SnakeBody parent;
		
		// The bounds of the SnakeBody part, used for drawing the sprite and collision detection.
		private Rectangle bounds;
		
		// The color of the SnakeBody part. Generally only the head part of the players SnakeElement should change.
		public Color color;
		
		public SnakeBody(SnakeBody parent, int x, int y, int size) {			
			this.bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
			this.color = BODY_COLOR;
		}
		
		// Moves the SnakeBody part around the screen, following its parent part. The head should have its parent set to null.
		public void update(float delta) {
			if(parent != null) {
				// Code for following the parent SnakeBody part around the screen.
			}
		}
		
		// Draws the SnakeBody section.
		public void draw(SpriteBatch batch) {
			sprite.setColor(color);
			sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			sprite.draw(batch);
		}
		
		// Returns the x co-ordinate for the centre point of the SnakeBody part.
		public int getX() {
			return (int) (bounds.x + bounds.width / 2);
		}
		
		// Returns the y co-ordinate for the centre point of the SnakeBody part.
		public int getY() {
			return (int) (bounds.y + bounds.height / 2);
		}
		
	}
}
