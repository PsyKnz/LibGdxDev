package psyknz.libgdx.games;

import com.badlogic.gdx.math.Rectangle;

public abstract class BoundedElement implements GameElement {
    
	// The bounding box to be used for the BoundedElement.
	protected Rectangle bounds;
	
	// The point inside of the bounding box used to take co-ordinates.
	public float xOrig, yOrig;
	
	// By default the x, y co-ordinates of a BoundedElement,s origin are at the center of its bounding box.
	public BoundedElement(float x, float y, float width, float height) {
		bounds = new Rectangle(x, y, width, height);
		xOrig = width / 2;
		yOrig = height / 2;
	}
	
	// Sets the x co-ordinate for the BoundedElement.
	public void setX(float x) {
		bounds.x = x - xOrig;
	}
	
	// Sets the y co-ordinate for the BoundedElement.
	public void setY(float y) {
		bounds.y = y - yOrig;
	}
	
	// Returns the x co-ordinate for the BoundedElement.
	public float getX() {
		return bounds.x + xOrig;
	}
	
	// Returns the y co-ordinate for the BoundedElement.
	public float getY() {
		return bounds.y + yOrig;
	}
	
	// Returns the BoundedElement's bounding box;
	public Rectangle getBounds() {
		return bounds;
	}
}
