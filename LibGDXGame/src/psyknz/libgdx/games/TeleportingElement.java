package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class TeleportingElement extends ShapeElement {
	
	/* The minimum amount of time before the element should teleport in seconds and a variable amount of time before the element should
	 * teleport. The variable time should be multiplied by a random float so that the element moves unpredictably. Its value is the
	 * maximu, number of seconds before the element should move again. */
	private final int minimumTime = 3;
	private int variableTime = 10;
	
	// Timer to keep track of when the element should teleport.
	private float teleportTimer;
    
	public TeleportingElement(GameScreen screen, Sprite shape, Color color, int x, int y, int size) {
		super(screen, shape, color, x, y, size);
		
		// Sets the teleporting timer.
		teleportTimer = MathUtils.random() * variableTime + minimumTime;
	}
	
	@Override
	public void update(float delta) {
		teleportTimer -= delta;
		
		// Once the timer reaches 0 the element teleports to a new location. EVENTUALLY NEEDS TO FIND A LOCATION WHICH DOESN'T COLLIDE.
		if(teleportTimer <= 0) {
			setPosition(MathUtils.random(screen.visibleWidth), MathUtils.random(screen.visibleHeight));
			teleportTimer = MathUtils.random() * variableTime + minimumTime;
		}
		
		// Once the timer gets under the minimum amount of time before it should teleport it starts spinning to signal what will happen.
		else if(teleportTimer <= minimumTime) {
			// CODE TO SPIN THE ELEMENT GOES HERE. AMOUNT OF SPIN SHOULD BE DETERMINED BY HOW CLOSE THE TIMER IS TO 0.
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		// EVENTUALLY THE DRAW NEEDS TO BE CHANGED SO THAT THE ELEMENT UTILISES A SPINNING ANIMATION.
	}
}
