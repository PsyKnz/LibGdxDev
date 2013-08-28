package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.math.Vector2;

public class TouchElement implements GameElement {
	
	// Reference to the GameScreen the TouchElement is attached to.
	public final GameScreen screen;
	
	// Array of points on the screen the user has touched represented as 2D vectors and a temporary Vector2 used during processing.
	public final Array<Vector2> touchHistory;
	public final Pool<Vector2> touchPool;
	
	// The maximum length the touchHistory can be in pixels.
	private float currentLength, maxLength = 0;
	
	// The minimum distance the finger has to move from the 2nd touch Vector2 for it to be recorded into the touchHistory.
	public float touchThreshold = 4;
	
	// Sprite used to draw the touchHistory to the screen provided that it is currently set to visible. (By default not visible).
	public boolean visible = false;
	public Sprite touchPoint = null;
	public Color touchColor = Color.YELLOW;
	
	// Variables used during line drawing.
	private float startX, startY, endX, endY, gradient;
	
	// Constructs a new TouchElement which maintains a record of user touch input for a single finger.
	public TouchElement(GameScreen screen) {
		this.screen = screen;
		
		// Initialises the touchPoint Array and Pool object to supply and recycle used Vector2 objects.
		touchHistory = new Array<Vector2>();
		touchPool = new Pool<Vector2>() {
			@Override
			protected Vector2 newObject() {
				return new Vector2(0, 0);
			}
		};
	}
	
	@Override
	public void update(float delta) {
		
		// If the screen is touched the location of the finger is stored in a Vector2.
		if(screen.isTouched()) {
			
			// Obtains a new Vector2 and sets it to the current location of the finger on the screen.
			Vector2 currentTouch = touchPool.obtain();
			currentTouch.x = screen.touchX();
			currentTouch.y = screen.touchY();			
			
			// If there are currently no recorded screen touches then an initial Vector2 is placed in the Array.
			if(touchHistory.size < 1) touchHistory.add(currentTouch);
			
			/* Overwise the current location of the finger only becomes a historical point once it moves a minimum distance from the
			 *  previous point. */
			else if (touchHistory.get(0).dst(currentTouch) > touchThreshold) {
				touchHistory.insert(0, currentTouch);
			}
			
			// As long as the length of the touchHistory in pixels is greater than the maxLength the last item in the history is removed.
			while(getLength() > maxLength) {
				touchPool.free(touchHistory.pop());
			}
		}
		
		// If the screen is currently not touched but there is a history of screen touches the history is cleared.
		else if(touchHistory.size > 0) touchPool.freeAll(touchHistory);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		// Draws the touchHistory to screen Provided the TouchElement is visible and has a sprite available to use.
		if(visible && touchPoint != null) {
			touchPoint.setColor(touchColor);
			
			// Provided there are two or more points recorded in touchHistory they are all connected together on screen.
			if(touchHistory.size > 1) for(int i = 1; i < touchHistory.size; i++) {
				drawLine(batch, touchHistory.get(i - 1), touchHistory.get(i));
			}
		}
	}
	
	// Sets the maximum length in pixels the touchHistory can be.
	public void setMaxLength(float maxLength) {
		this.maxLength = maxLength;
	}
	
	// Returns the maximum length in pixels the touchHistory can be.
	public float getMaxLength() {
		return maxLength;
	}
	
	// Finds and returns the current length of the touchHistory in pixels.
	public float getLength() {
		
		// If there are currently no screen touches recorded a value of -1 is returned.
		if(touchHistory.size < 1) currentLength = -1;
		
		// Otherwise currentLength is set to 0 and the distances between consecutive Vector2's are added to currentLength.
		else {
			currentLength = 0;
			if(touchHistory.size > 1) for(int i = 1; i < touchHistory.size; i++) {
				currentLength += touchHistory.get(i).dst(touchHistory.get(i - 1));
			}
		}
		
		// Returns the currentLength of the touchHistory in pixels.
		return currentLength;
	}
	
	// Draws a line between v1 and v2 using a 1x1 sized sprite.
	private void drawLine(SpriteBatch batch, Vector2 v1, Vector2 v2) {
		
		// Finds the starting and ending x, y co-ordinates for the line. 
		if(v1.x < v2.x) {
			startX = v1.x; 
			endX = v2.x;
		}
		else {
			startX = v2.x;
			endX = v1.x;
		}
		if(v1.y < v2.y) {
			startY = v1.y;
			endY = v2.y;
		}
		else {
			startY = v2.y;
			endY = v1.y;
		}
		
		// Determines the gradient of the line.
		gradient = (v1.y - v2.y) / (v1.x - v2.x);
		
		// If the line has a gradient of less than 1 then the x values are used for point drawing.
		if(gradient < 1) {
			for(float x = 0; x < endX - startX; x++) {
				touchPoint.setBounds(x + startX, x * gradient + startY, 1, 1);
				touchPoint.draw(batch);
			}
		}
		
		// If the line has a gradient of more than 1 then the y values are used for point drawing.
		else {
			for(float y = 0; y < endY - startY; y++) {
				touchPoint.setBounds(y / gradient + startX, y = startY, 1, 1);
				touchPoint.draw(batch);
			}
		}
		
	}
}
