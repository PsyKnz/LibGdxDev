package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

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
	public float touchThreshold = 16;
	
	// Sprite used to draw the touchHistory to the screen provided that it is currently set to visible. (By default not visible).
	public boolean visible = false;
	public Sprite touchPoint = null;
	
	// Constructs a new TouchElement which maintains a record of user touch input for a single finger.
	public TouchElement(GameScreen screen) {
		this.screen = screen;
		
		// Initialises the touchPoint Array and Pool object to supply and recycle used Vector2 objects.
		touchHistory = new Array<Vector2>();
		touchPool = new Pool<Vector2>() {
			@Override
			protected Vector2 newObject() {
				return new Vector2(Vector2.Zero);
			}
		};
	}
	
	@Override
	public void update(float delta) {
		
		// If the screen is touched the location of the finger is stored in a Vector2.
		if(screen.isTouched()) {
			Vector2 currentTouch = touchPool.obtain();
			currentTouch.x = screen.touchX();
			currentTouch.y = screen.touchY();
			
			// The location of the finger is added to the touchHistory if there is less than 2 Vector2's recorded.
			if(touchHistory.size < 2) touchHistory.insert(0, currentTouch);
			else if (touchHistory.get(1).dst(currentTouch) < touchThreshold) {
				
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		// Draws the touchHistory to screen Provided the TouchElement is visible and has a sprite available to use.
		if(visible && touchPoint != null) {}
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

}
