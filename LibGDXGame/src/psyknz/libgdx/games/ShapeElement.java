package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/* A ShapeElement is a GameElement object intended to draw simple shapes to the screen which implement some game logic.
 * Generally the ShapeElement should be constructed from two shape sprites, one which is used to produce the outline of the shape and one used to form the body of the shape.
 * The two shape sprites should be constructed from textures which are all white.
 * The color the outline is drawn is set when the object is constructed while the color of the shape body is a blend of the outline color and background color of the screen. If the background color of the screen is changed and you want to update the color of the object you should call setColor(getColor());.*/
public class ShapeElement extends GameElement {
    
	// The width in in-game units that all shapes outlines should be.
	public static final int OUTLINE_SIZE = 3;
	
	// Reference to the the screen the shape will be drawn to.
	protected GameScreen screen;
	
	// Sprite used to draw the shape.
	protected Sprite shape;
	
	// Color used when drawing the shape.
	public Color color;
	
	// The bounding box for the shape. Used to position the shape on the screen.
	protected Rectangle bounds;
	
	public ShapeElement(GameScreen screen, Sprite shape, Color color, int x, int y, int size) {
		this.screen = screen;
		this.shape = shape;
		this.color = color;
		bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
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
		shape.setColor(color.r * screen.background.r, color.g * screen.background.g, color.b * screen.background.b, 1);
		shape.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		shape.draw(batch);
	}
	
	// Returns the bounding box for the ShapeElement.
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Moves the ShapeElemeny to point (x, y) by centering its bounding box around it.
	public void move(int x, int y) {
		bounds.x = x - bounds.width / 2;
		bounds.y = y - bounds.height / 2;
	}
}
