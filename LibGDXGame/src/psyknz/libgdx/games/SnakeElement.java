package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class SnakeElement extends GameElement {
    
	// Constants for presenting the SnakeBody parts.
	public final int BODY_SIZE;
	public final int HEAD_SIZE;
	
	// The base sprite to use for presenting the SnakeBody parts.
	private Sprite outline;
	private Sprite inside;
	
	// The color of the snake.
	private Color color;
	
	// Constant used to adjust the color of the snakes inside relative to its outline.
	private final Color COLOR_MASK = new Color(0.5f, 0.5f, 0.5f, 1);
	
	// List of the SnakeElements SnakeBody pieces.
	private ArrayList<SnakeBody> body;
	
	// Reference to the last piece of the snake, used for drawing and growing.
	private SnakeBody tail;
	
	public SnakeElement(Sprite outline, Sprite inside, int x, int y, int length, int bodySize, int headSize, Color color) {
		body = new ArrayList<SnakeBody>();
		
		// Sets the sprites to use for the snakes outline and inside its body.
		this.outline = outline;
		this.inside = inside;
		
		// Sets the constants for the snake.
		this.HEAD_SIZE = headSize;
		this.BODY_SIZE = bodySize;
		
		this.color = color;
		
		// Creates the head of the snake then adds the given number of SnakeBody parts.
		body.add(new SnakeBody(null, x, y, HEAD_SIZE));
		tail = body.get(0);
		for(int i = 0; i < length; i++) {
			grow();
		}
	}
	
	public void update(float delta) {
		for(int i = 0; i < body.size(); i++) {
			body.get(i).update(delta);
		}
		body.get(body.size() - 1).moved = false;
	}
	
	public void draw(SpriteBatch batch) {
		// Sets the color of the outline and inside of the snake.
		outline.setColor(color);
		inside.setColor(0, 1, 0, 1);
		
		// Draws the outline of the last piece of the snake (which wont draw itself).
		outline.setBounds(tail.bounds.x, tail.bounds.y, tail.bounds.width, tail.bounds.height);
		outline.draw(batch);
		
		// Loops through the entire SnakeBody drawing each piece from last to first.
		for(int i = body.size() - 1; i >= 0; i--) {
			body.get(i).draw(batch);
		}
	}
	
	// Causes the body of the snake to grow by 1 piece.
	public void grow() {
		body.add(new SnakeBody(tail, tail.getX(), tail.getY(), BODY_SIZE));
		tail = body.get(body.size() - 1);
	}
	
	// Returns the bounding box of the SnakeBody part at the given index.
	public Rectangle getBounds(int index) {
		return body.get(index).bounds;
	}
	
	// Moves the head of the SnakeElement to the given location. Its bounding box will be centered about the given co-ordinates.
	public void move(int x, int y) {
		body.get(0).bounds.x = x - body.get(0).bounds.width / 2;
		body.get(0).bounds.y = y - body.get(0).bounds.height / 2;
		body.get(0).moved = true;
	}
	
	/* Class representing a single section of the SnakeElements body. */
	private class SnakeBody extends GameElement {
		
		// The parent SnakeBody object is the one which this SnakeBody part follows around the screen.
		private SnakeBody parent;
		
		// The bounds of the SnakeBody part, used for drawing the sprite and collision detection.
		private Rectangle bounds;
		
		// Whether or not the SnakeBody has moved.
		public boolean moved = false;
		
		// Temporary variables used for movement calculation.
		private float xDif, yDif, angle, xShift, yShift;
		
		public SnakeBody(SnakeBody parent, int x, int y, int size) {
            this.parent = parent;			
			this.bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
		}
		
		// Moves the SnakeBody part around the screen, following its parent part. The head should have its parent set to null.
		public void update(float delta) {
			if(parent != null && parent.moved) {
				xDif = parent.bounds.x - bounds.x;
				yDif = parent.bounds.y - bounds.y;
				angle = MathUtils.atan2(yDif, xDif);
			    xShift = MathUtils.cos(angle) * (parent.bounds.width / 2 + 1);
				yShift = MathUtils.sin(angle) * (parent.bounds.width / 2 + 1);
				bounds.x = parent.bounds.x - xShift;
				bounds.y = parent.bounds.y - yShift;
				parent.moved = false;
				moved = true;
			}
		}
		
		// Sets the bounding box of the given sprite equal to the bounding box of this SnakePart.
		public void draw(SpriteBatch batch) {
			// If the SnakeBody has a parent SnakeBody its outline is drawn.
			if(parent != null) {
				outline.setBounds(parent.bounds.x, parent.bounds.y, parent.bounds.width, parent.bounds.height);
				outline.draw(batch);
			}
			
			// Draws the inside of the SnakeBody.
			inside.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			inside.draw(batch);
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
