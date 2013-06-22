package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/* A ShapeElement is a GameElement object intended to draw simple shapes to the screen which implement some game logic. Generally the
 * ShapeElement should be constructed from two shape sprites, one which is used to produce the outline of the shape and one used to form
 * the body of the shape. The two shape sprites should be constructed from textures which are all white. The color the outline is drawn
 * is set when the object is constructed while the color of the shape body is a blend of the outline color and background color of the
 * screen. */
public class ShapeElement implements GameElement {
    
	// The width in in-game units that all shapes outlines should be.
	public static final int OUTLINE_SIZE = 3;
	
	// Reference to the the screen the shape will be drawn to.
	protected GameScreen screen;
	
	// Sprite used to draw the shape.
	protected Sprite shape;
	
	// Color used when drawing the shape.
	public Color color;
	public boolean filled = true;
	
	// The bounding box for the shape. Used to position the shape on the screen.
	protected Rectangle bounds;
	
	public ShapeElement(GameScreen screen, Sprite shape, Color color, int x, int y, int size) {
		this(screen, shape, color, x, y, size, size);
	}
	
	public ShapeElement(GameScreen screen, Sprite shape, Color color, int x, int y, int width, int height) {
		this.screen = screen;
		this.shape = shape;
		this.color = color;
		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	// Updates the game logic for the ShapeElement. Intended for overriding.
	public void update(float delta) {
		// Game logic for the shape goes here.
	}
    
	// Draws the shape to the screen. For complex shapes made from interlocking units it will be necessary to override this.
	public void draw(SpriteBatch batch) {
		// Draws the outline of the shape.
        shape.setColor(color);
		shape.setBounds(bounds.x - OUTLINE_SIZE, bounds.y - OUTLINE_SIZE, bounds.width + OUTLINE_SIZE * 2, bounds.height + OUTLINE_SIZE * 2);
		shape.draw(batch);
		
		// Draws the body of the shape.
		if(filled) shape.setColor((color.r + screen.background.r) / 2, (color.g + screen.background.g) / 2, (color.b + screen.background.b) / 2, color.a);
		else shape.setColor(screen.background);
		shape.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		shape.draw(batch);
	}
	
	// Returns the bounding box for the ShapeElement.
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Moves the ShapeElemeny to point (x, y) by centering its bounding box around it.
	public void move(float x, float y) {
		bounds.x = x - bounds.width / 2;
		bounds.y = y - bounds.height / 2;
	}
}
