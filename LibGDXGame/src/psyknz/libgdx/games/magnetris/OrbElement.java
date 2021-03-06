package psyknz.libgdx.games.magnetris;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class OrbElement extends CircleElement {
	
	// The size all orbs should be drawn at.
	public static final float ORB_SIZE = 32;
	
	// The distance an orb should be able to cover in a straight line in 1 second given a speed of 1.
	public static final float MAX_SPEED = (float) Math.pow(ORB_SIZE, 2);
	
	// Constants used to determine the current state of the orb.
	public static final int SELECTED = 0;
	public static final int STATIONARY = 1;
	public static final int MOTION = 2;
	
	// The current state of the OrbElement.
	private int state = STATIONARY;
	
	// Determines whether this OrbElement applies a force to other OrbElements.
	public boolean magnetic;
	
	// Vector2 used to track the motion of the OrbElement.
	private Vector2 motion;
	
	// The OrbElement this OrbElement has to wait to touch before it connects to the chain of selected orbs..
	private OrbElement parentOrb;
	public boolean parentCollided;
	
	// Constructs a new OrbElement of the given color.
	public OrbElement(Sprite sprite, Color color) {
		super(sprite, color, ORB_SIZE);
		
		// Initialises the motion vector to stationary and clamps it to the MAX_SPEED.
		motion = new Vector2(0, 0);
	}
	
	// Updates the game logic for the OrbElement considering its state.
	@Override
	public void update(float delta) {
		
		// If the OrbElement is in MOTION it moves according to its motion vector.
		if(state == MOTION) {
			bounds.x += motion.x * delta;
			bounds.y += motion.y * delta;
		}
	}
	
	// Sets the OrbElement to selected, registers its parent, and itself to the selected array.
	public void setSelected(OrbElement parentOrb) {
		this.parentOrb = parentOrb;
		parentCollided = false;
		state = SELECTED;
	}
	
	// Sets the OrbElement to STATIONARY and fits it into the given co-ordinates.
	public void setStationary(float x, float y) {
		setX(x);
		setY(y);
		state = STATIONARY;
	}
	
	// Applies motion to the OrbElement along a given vector.
	public void setMotion(Vector2 force) {
		motion.add(force);
		motion.limit(MAX_SPEED);
		state = MOTION;
	}
	
	// Returns the OrbElement's current state.
	public int getState() {
		return state;
	}
}
