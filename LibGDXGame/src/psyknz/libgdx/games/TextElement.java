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
	private float xOffset, yOffset, wrapWidth;
	private boolean wrapped;
	
	/* By Default a new TextElement will be aligned so that the top left corner of its bounding box rests on its x, y co-ordinate.
	 *  It will also not wrap the text. */
	public TextElement(String text, BitmapFont font, float x, float y) {
		this(text, font, x, y, false, 0);
	}
	
	// This constructor should be used to create wrapped text.
	public TextElement(String text, BitmapFont font, float x, float y, boolean wrapped, float wrapWidth) {
		this(text, font, x, y, wrapped, wrapWidth, LEFT, TOP);
	}
	
	// Constructor allows for the TextElements alignment to be set.
	public TextElement(String text, BitmapFont font, float x, float y, int hAlign, int vAlign) {
		this(text, font, x, y, false, 0, hAlign, vAlign);
	}
	
	// Constructor for the TextElement which allows for the alignment to be set as well as whether or not the text should be wrapped.
	public TextElement(String text, BitmapFont font, float x, float y, boolean wrapped, float wrapWidth, int hAlign, int vAlign) {
		this.text = text;
		this.font = font;
		scale = font.getScaleX();
		this.x = x;
		this.y = y;
		this.wrapped = wrapped;
		this.wrapWidth = wrapWidth;
		align(hAlign, vAlign);
	}
	
	@Override
	public void update(float delta) {}
	
	// Draws the TextElement at its current location.
	@Override
	public void draw(SpriteBatch batch) {
	    font.setScale(scale);
	    if(wrapped) font.drawWrapped(batch, text, x - xOffset, y + yOffset, wrapWidth);
	    else font.draw(batch, text, x - xOffset, y + yOffset);
	}
	
	// Overloaded draw function draws the TextElement at the given location.
	public void draw(SpriteBatch batch, float x, float y) {
		font.setScale(scale);
		if(wrapped) font.drawWrapped(batch, text, x - xOffset, y + yOffset, wrapWidth);
	    else font.draw(batch, text, x - xOffset, y + yOffset);
	}
	
	// Returns the CharSequence this TextElement draws to the screen.
	public CharSequence getText() {
		return text;
	}
	
	// Sets the alignment of the text relative to its position.
	public void align(int hAlign, int vAlign) {
		font.setScale(scale);
		BitmapFont.TextBounds bounds;
		if(wrapped) bounds = font.getWrappedBounds(text, wrapWidth);
		else bounds = font.getBounds(text);
		
		// Adjusts the xOffset based on the hAlign parameter.
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
		
		//Adjusts the yOffset based on the vAlign parameter.		
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
