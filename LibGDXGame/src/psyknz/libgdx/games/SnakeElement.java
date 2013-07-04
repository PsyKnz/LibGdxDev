package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class SnakeElement extends ShapeElement {
	// How much bigger the head should be relative to the body.
	public static final float HEAD_SCALE = 1.8f;
	
	// List of the SnakeElements SnakeBody pieces.
	private Array<SnakeBody> body;
	
	// Reference to the last piece of the snake, used for drawing and growing.
	private SnakeBody tail;
	
	public SnakeElement(GameScreen screen, Sprite shape, Color color, int x, int y, int size, int length) {
		super(screen, shape, color, x, y, size);
		
		body = new Array<SnakeBody>();
		
		// Creates the head of the snake then adds the given number of SnakeBody parts.
		body.add(new SnakeBody(null, x, y, (int) (size * HEAD_SCALE)));
		tail = body.get(0);
		for(int i = 0; i < length; i++) {
			grow();
		}
	}
	
	@Override
	public void update(float delta) {
		// updates the position of all SnakeBody parts from the head to the tail.
		for(int i = 0; i < body.size; i++) {
			body.get(i).update(delta);
		}
		
		// sets moved for the last part of the tail to false since the part can't do it itself.
		body.get(body.size - 1).moved = false;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// Draws the outline of the last piece of the snake (which wont draw itself).
		shape.setColor(color);
		shape.setBounds(tail.bounds.x - OUTLINE_SIZE, tail.bounds.y - OUTLINE_SIZE, 
						tail.bounds.width + OUTLINE_SIZE * 2, tail.bounds.height + OUTLINE_SIZE * 2);
		shape.draw(batch);
		
		// Loops through the entire SnakeBody drawing each piece from last to first.
		for(int i = body.size - 1; i >= 0; i--) {
			body.get(i).draw(batch);
		}
	}
	
	// Causes the body of the snake to grow by 1 piece.
	public void grow() {
		body.add(new SnakeBody(tail, tail.getX(), tail.getY(), (int) bounds.getWidth()));
		tail = body.get(body.size - 1);
	}
	
	// Returns the bounding box of the SnakeBody part at the given index.
	public Rectangle getBounds(int index) {
		return body.get(index).bounds;
	}
	
	// Overrides the move method so that it interacts with the SnakeElements in its body.
	@Override
	public void move(float x, float y) {
		super.move(x, y);
		
		// Moves the head of the snake to the position of the SnakeElement.
		body.get(0).move(x, y);
	}
	
	/* Class representing a single section of the SnakeElements body. */
	private class SnakeBody implements GameElement {
		
		// The parent SnakeBody object is the one which this SnakeBody part follows around the screen.
		private SnakeBody parent;
		
		// The bounds of the SnakeBody part, used for drawing the sprite and collision detection.
		private Rectangle bounds;
		
		// Whether or not the SnakeBody has moved.
		public boolean moved = false;
		
		// Temporary variables used for movement calculation.
		private float angle, xDif, yDif;
		
		public SnakeBody(SnakeBody parent, int x, int y, int size) {
            this.parent = parent;			
			this.bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
		}
		
		// Moves the SnakeBody part around the screen, following its parent part. The head should have its parent set to null.
		public void update(float delta) {
			if(parent != null && parent.moved) {
				// Find the distance between the two body parts.
				xDif = parent.getX() - getX();
				yDif = parent.getY() - getY();
				
				// If the distance is greater than the radius of the parent body part then this part moves.
				if(Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2)) >= bounds.width / 2 + 1) {
				    // Finds the angle of the vector produced by the difference in position of this SnakeBody piece and its parent.
				    angle = MathUtils.atan2(yDif, xDif);
				
				    // Finds the new co-ordinates for the snake body so that it is linked to its parent at its previous angle.
				    move(parent.getX() - MathUtils.cos(angle) * (parent.bounds.width / 2 + 1), parent.getY() - MathUtils.sin(angle) * (parent.bounds.height / 2 + 1));
				
				    // Declares the parent as having no longer moved and this as moved for the next piece of the snake.
				    parent.moved = false;
				}
			}
		}
		
		// Sets the bounding box of the given sprite equal to the bounding box of this SnakePart.
		public void draw(SpriteBatch batch) {
			// If the SnakeBody has a parent SnakeBody its outline is drawn.
			if(parent != null) {
				shape.setColor(color);
				shape.setBounds(parent.bounds.x - OUTLINE_SIZE, parent.bounds.y - OUTLINE_SIZE, parent.bounds.width + OUTLINE_SIZE * 2, parent.bounds.height + OUTLINE_SIZE + 2);
				shape.draw(batch);
			}
			
			// Draws the inside of the SnakeBody.
			shape.setColor((color.r + screen.background.r) / 2, (color.g + screen.background.g) / 2, (color.b + screen.background.b) / 2, color.a);
			shape.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			shape.draw(batch);
		}
		
		// Returns the x co-ordinate for the centre point of the SnakeBody part.
		public int getX() {
			return (int) (this.bounds.x + this.bounds.width / 2);
		}
		
		// Returns the y co-ordinate for the centre point of the SnakeBody part.
		public int getY() {
			return (int) (this.bounds.y + this.bounds.height / 2);
		}
		
		// Moves the body part so that it is centered about the given (x, y) co-ordinate.
		private void move(float x, float y) {
			bounds.x = x - bounds.width / 2;
			bounds.y = y - bounds.height / 2;
			moved = true;
		}
	}
}
