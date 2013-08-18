package psyknz.libgdx.games.magnetris;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OrbElement extends CircleElement {
	
	// The size all orbs should be drawn at.
	public static final float ORB_SIZE = 64;
	
	// Constants used to determine the current state of the orb.
	public static final int STATIONARY = 0;
	public static final int MOTION = 1;
	public static final int ATTACHED = 2;
	public static final int SELECTED = 3;
	
	// The current state of the OrbElement.
	private int state = STATIONARY;
	
	// Relative distances the orb needs to travel along the x and y axis while in motion and the speed it should travel at to reach that spot. Speed represents how many seconds it should take to close that distance.
	private float xMove, yMove, speed;
	
	// Reference to the OrbElement this OrbElement is attached to (if attached).
	private OrbElement parent;
	
	public OrbElement(Sprite sprite, Color color) {
		super(sprite, color, ORB_SIZE);
	}
	
	// Updates the game logic for the OrbElement based on its current state.
	@Override
	public void update(float delta) {
		switch(state) {
			
		// If the OrbElement is stationary and out of bounds it's game over.
		case STATIONARY:
			break;
			
	    // If the OrbElement is in motion it moves and then is tested for collisions.
		case MOTION:
			bounds.x += xMove * speed * delta;
			bounds.y += yMove * speed * delta;
			break;
			
		// If the OrbElement is attached to another OrbElement it follows it around.
		case ATTACHED:
			xMove = parent.getBounds().x - bounds.x;
			yMove = parent.getBounds().y - bounds.y;
			if(Math.sqrt(Math.pow(xMove, 2) + Math.pow(yMove, 2)) > bounds.width) {
				
			}
			break;
			
		// If the OrbElement is selected it tracks along behind the finger on screen.
		case SELECTED:
			break;
		}
	}
	
	// Sets the xMove and yMove values so that the OrbElement will move towards the given x, y co-ordinates.
	public void moveTo(float x, float y) {
		xMove = x - getX();
		yMove = y - getY();
		speed = 1;
		state = MOTION;
	}
	
	public void attachTo(OrbElement parent) {
		this.parent = parent;
		state = ATTACHED;
	}
}
