package psyknz.libgdx.games;

import com.badlogic.gdx.math.Rectangle;

/* The PlayerBody class is a representation of a single piece of the players snakelike body. In the long run this class will likely be
 * removed in favour of directly adjusting Rectangle objects in the PlayerElement object. */
public class PlayerBody {
    // The bounding box used by the PlayerBody object. The bounding box will always be square.
	private Rectangle bounds;
	
	// Initialises the PlayerBody with a bounding box of the given size centered around the given x, y co-ordinate.
	public PlayerBody(int x, int y, int size) {
		bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
	}
	
	// Returns the PlayerBody's bounding box.
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Returns the size of the PlayerBody.
	public int getSize() {
		return (int) bounds.width;
	}
	
	// Moves the bounding box by the given number of units on the x and y axis.
	public void move(int vectorX, int vectorY) {
		bounds.x += vectorX;
		bounds.y += vectorY;
	}
	
	// Adjusts the size of the PlayerBody bounding box, shrinking or enlarging it as necessary.
	public void setSize(int size) {
		// Finds the centre of the PlayerBody bounding box. 
		float x = bounds.x += bounds.width / 2;
		float y = bounds.y += bounds.height / 2;
		
		// Adjusts the PlayerBody bounding box to its new size around the centre of the old bounding box.
		bounds.x = x - size / 2;
		bounds.y = y - size / 2;
		bounds.width = size;
		bounds.height = size;
	}
}
