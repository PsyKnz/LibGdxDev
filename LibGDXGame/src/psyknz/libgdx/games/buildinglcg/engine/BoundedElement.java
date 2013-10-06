package psyknz.libgdx.games.buildinglcg.engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public abstract class BoundedElement implements GameElement {

	// The bounding box to be used for the BoundedElement.
	public final Rectangle bounds;
	
	// The position of the BoundedElement on the screen.
	public final Vector2 pos;

	// Constructs a BoundedElement with no size or location.
	public BoundedElement() {
		this(0, 0, 0, 0);
	}

	// Builds a Bounded element at point x, y with the given width and height.
	public BoundedElement(float x, float y, float width, float height) {
		this(x, y, 0, 0, width, height);
	}
	
	public BoundedElement(float x, float y, float xOffset, float yOffset, float width, float height) {
		pos = new Vector2(x, y);
		bounds = new Rectangle(xOffset, yOffset, width, height);
	}
}
