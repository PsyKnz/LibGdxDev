package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextElement extends BoundedElement {
    
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
	
	// Positional and alignment information.
	private int hAlign, vAlign;
	
	/* By Default a new TextElement will be aligned so that the top left corner of its bounding box rests on its x, y co-ordinate.
	 *  It will also not wrap the text. */
	public TextElement(String text, BitmapFont font, float x, float y) {
		this(text, font, x, y, LEFT, BOTTOM);
	}
	
	// Constructor for the TextElement which allows for the alignment to be set as well as whether or not the text should be wrapped.
	public TextElement(String text, BitmapFont font, float x, float y, int hAlign, int vAlign) {
		super(0, 0, font.getBounds(text).width, font.getBounds(text).height);
		
		this.text = text;
		this.font = font;
		
		align(hAlign, vAlign);
		setX(x);
		setY(y);
	}
	
	@Override
	public void update(float delta) {}
	
	// Draws the TextElement at its current location.
	@Override
	public void draw(SpriteBatch batch) {
		font.drawWrapped(batch, text, bounds.x, bounds.y + bounds.height, bounds.width);
	}
	
	// Overloaded draw function draws the TextElement at the given location.
	public void draw(SpriteBatch batch, float x, float y) {
	    font.drawWrapped(batch, text, x - xOrig, y - yOrig + bounds.height, bounds.width);
	}
	
	// Returns the CharSequence this TextElement draws to the screen.
	public CharSequence getText() {
		return text;
	}
	
	public void align(int hAlign, int vAlign) {
		this.hAlign = hAlign;
		this.vAlign = vAlign;
		
		// Aligns the text along the x axis.
		switch(hAlign) {
			case LEFT: xOrig = 0;
			break;
			case CENTER: xOrig = bounds.width / 2;
			break;
			case RIGHT: xOrig = bounds.width;
		}
		
		// Aligns the text along the y axis.
		switch(vAlign) {
			case TOP: yOrig = bounds.height;
			break;
			case CENTER: yOrig = bounds.height / 2;
			break;
			case BOTTOM: yOrig = 0;
			break;
		}
	}
	
	// Sets the width of the TextElement and re-aligns its origin. If the width is <= 0 then the text is unwrapped.
	public void setWrapWidth(float wrapWidth) {
		if(wrapWidth <= 0) bounds.width = font.getBounds(text).width;
		else bounds.width = wrapWidth;
		bounds.height = font.getWrappedBounds(text, bounds.width).height;
		align(hAlign, vAlign);
	}
}
