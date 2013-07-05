package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/* A ShapeElement is a GameElement object intended to draw simple shapes to the screen which implement some game logic. Generally the
 * ShapeElement should be constructed from two shape sprites, one which is used to produce the outline of the shape and one used to form
 * the body of the shape. The two shape sprites should be constructed from textures which are all white. The color the outline is drawn
 * is set when the object is constructed while the color of the shape body is a blend of the outline color and background color of the
 * screen. */
public class ShapeElement extends SpriteElement {
    
	// The width in in-game units that all shapes outlines should be.
	public static final int OUTLINE_SIZE = 3;
	
	// Reference to the the screen the shape will be drawn to.
	protected GameScreen screen;
	
    // Whether or not to fill the inside of the object with a solid color.
	public boolean filled = true;
	
	public ShapeElement(GameScreen screen, Sprite sprite, Color color, float x, float y, float size) {
		this(screen, sprite, color, x, y, size, size);
	}
	
	public ShapeElement(GameScreen screen, Sprite sprite, Color color, float x, float y, float width, float height) {
		super(sprite, x, y, width, height);
		this.screen = screen;
		this.color = color;
	}
    
	// Draws the shape to the screen with an outline in its color.
	@Override
	public void draw(SpriteBatch batch) {
		if(visible) {
		    // Draws the outline of the shape.
            sprite.setColor(color);
		    sprite.setBounds(bounds.x - OUTLINE_SIZE, bounds.y - OUTLINE_SIZE, bounds.width + OUTLINE_SIZE * 2, bounds.height + OUTLINE_SIZE * 2);
		    sprite.draw(batch);
		
		    // Draws the body of the shape or removes it if its not a filled shape.
		    if(filled) sprite.setColor((color.r + screen.background.r) / 2, (color.g + screen.background.g) / 2, (color.b + screen.background.b) / 2, color.a);
		    else sprite.setColor(screen.background);
		    sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		    sprite.draw(batch);
		}
	}
}
