package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class BouncingEnemyElement extends ShapeElement {
	
	// Reference to the PlayScreen this element is on.
	@Override
	protected PlayScreen screen;
	
	// Variables used to move the element around the screen.
	private float angle, speed, xMove, yMove;
	
	public BouncingEnemyElement(PlayScreen screen, Sprite shape, Color color, int x, int y, int size) {
		super(screen, shape, color, x, y, size);
		
		speed = MathUtils.random(2f) + 1;
		angle = MathUtils.PI * (MathUtils.random(2f) - 1);
	}
	
	@Override
	public void update(float delta) {
		// Determine how far the element should move on the x and y axis.
		xMove = MathUtils.cos(angle) * speed * delta;
		yMove = MathUtils.sin(angle) * speed * delta;
		move(xMove, yMove);
	}
	
	private void bounce(float collisionAngle) {
		
	}
}

