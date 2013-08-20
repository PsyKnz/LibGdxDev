package psyknz.libgdx.games.magnetris;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class OrbElement extends CircleElement {
	
	// The size all orbs should be drawn at.
	public static final float ORB_SIZE = 64;
	
	// The distance an orb should be able to cover in a straight line in 1 second given a speed of 1.
	public static final float BASE_SPEED = 225;
	
	// Constants used to determine the current state of the orb.
	public static final int SELECTED = 0;
	public static final int STATIONARY = 1;
	public static final int ATTACHED = 2;
	public static final int MOTION = 3;
	
	// The current state of the OrbElement.
	private int state = STATIONARY;
	
	/* Relative distances the orb needs to travel along the x and y axis while in motion and the speed it should travel at to reach that
	 *  spot. Speed represents how many seconds it should take to close that distance. */
	private float xMove, yMove, speed, angle;
	
	// Reference to the OrbElement this OrbElement is attached to (if attached).
	private OrbElement parent, child;
	
	public OrbElement(Sprite sprite, Color color) {
		super(sprite, color, ORB_SIZE);
	}
	
	// Updates the game logic for the OrbElement based on its current state.
	@Override
	public void update(float delta) {
		switch(state) {
			
		/* If the OrbElement is selected or stationary it does nothing. (In the event it is selected its motion will be managed by the
		 *  OrbController). */
		case SELECTED:
		case STATIONARY:
			break;
			
		// If the OrbElement is attached or in motion then it moves according to its xMove and yMove.
		case ATTACHED:
		case MOTION:
			bounds.x += xMove * speed * delta;
			bounds.y += yMove * speed * delta;
			break;
		}
	}
	
	// Sets the xMove and yMove values so that the OrbElement will move towards the given x, y co-ordinates.
	public void setMotion(float x, float y) {
		xMove = x - getX();
		yMove = y - getY();
		speed = 1;
		state = MOTION;
	}
	
	// Attaches this OrbElement to the parent OrbElement passed as an argument and registers itself as its child.
	public void setAttached(OrbElement parent) {
		
		// Deregisters itself from its previous parent provided it is still its child.
		if(parent != null && parent.child == this) parent.child = null;
		
		// Registers its parent and itself as a child.
		this.parent = parent;
		parent.child = this;
		state = ATTACHED;
	}
	
	// Returns the OrbElement's current state.
	public int getState() {
		return state;
	}
}
