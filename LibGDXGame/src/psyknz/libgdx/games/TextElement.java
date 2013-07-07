package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextElement implements GameElement {
    
	// Constants for aligning the text.
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	public static final int TOP = 0;
	public static final int BOTTOM = 2;
	
	// Label id. This is what's drawn to the screen.
	private CharSequence text;
	
	// Font and style information, should be set on the font before being passed to the constructor.
	private BitmapFont font;
	private float scale;
	
	// Positional and alignment information.
	public float x, y;
	private float xOffset, yOffset;
	
	// By Default a new TextElement will be aligned so that the top left corner of its bounding box rests on its x, y co-ordinate.
	public TextElement(String text, BitmapFont font, float x, float y) {
		this(text, font, x, y, LEFT, TOP);
	}
	
	// Constructor allows for the TextElements alignment to be set.
	public TextElement(String text, BitmapFont font, float x, float y, int hAlign, int vAlign) {
		this.text = text;
		this.font = font;
		scale = font.getScaleX();
		this.x = x;
		this.y = y;
		align(hAlign, vAlign);
	}
	
	@Override
	public void update(float delta) {
		// INSERT CODE TO ANIMATE THE TEXT HERE.
	}
	
	// Draws the TextElement at its current location.
	@Override
	public void draw(SpriteBatch batch) {
	    font.setScale(scale);
		font.draw(batch, text, x - xOffset, y + yOffset);
	}
	
	// Overloaded draw function draws the TextElement at the given location.
	public void draw(SpriteBatch batch, float x, float y) {
		font.setScale(scale);
		font.draw(batch, text, x - xOffset, y + yOffset);
	}
	
	// Returns the CharSequence this TextElement draws to the screen.
	public CharSequence getText() {
		return text;
	}
	
	// Sets the alignment of the text relative to its position.
	public void align(int hAlign, int vAlign) {
		font.setScale(scale);
		BitmapFont.TextBounds bounds = font.getBounds(text);
		
		switch(hAlign) {
			case LEFT:
			xOffset = 0;
			break;
			case CENTER:
			xOffset = bounds.width / 2;
			break;
			case RIGHT:
			xOffset = bounds.width;
			break;
		}
		
		switch(vAlign) {
			case TOP:
			yOffset = 0;
			break;
			case CENTER:
			yOffset = bounds.height / 2;
			break;
			case BOTTOM:
			yOffset = bounds.height;
			break;
		}
	}
}
