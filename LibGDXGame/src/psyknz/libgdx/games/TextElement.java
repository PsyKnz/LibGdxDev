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
	
	// Positional information.
	private int x;
	private int y;
	private int drawX;
	private int drawY;
	private int hAlign;
	private int vAlign;
	
	public TextElement(String text, BitmapFont font, int x, int y) {
		this(text, font, x, y, LEFT, TOP);
	}
	
	public TextElement(String text, BitmapFont font, int x, int y, int hAlign, int vAlign) {
		this.text = text;
		this.font = font;
		scale = font.getScaleX();
		this.x = x;
		this.y = y;
		align(hAlign, vAlign);
	}

	public void setY(int y) {
		this.y = y;
		align(hAlign, vAlign);
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
		align(hAlign, vAlign);
	}

	public int getX() {
		return x;
	}
	
	@Override
	public void update(float delta) {
		// INSERT CODE TO ANIMATE THE TEXT HERE.
	}
	
	@Override
	public void draw(SpriteBatch batch) {
	    font.setScale(scale);
		font.draw(batch, text, drawX, drawY);
	}
	
	public String getText() {
		return text.toString();
	}
	
	// Sets the alignment of the text relative to its position.
	public void align(int hAlign, int vAlign) {
		font.setScale(scale);
		BitmapFont.TextBounds bounds = font.getBounds(text);
		this.hAlign = hAlign;
		this.vAlign = vAlign;
		
		switch(hAlign) {
			case LEFT:
			drawX = x;
			break;
			case CENTER:
			drawX = x - (int) bounds.width / 2;
			break;
			case RIGHT:
			drawX = x - (int) bounds.width;
			break;
		}
		
		switch(vAlign) {
			case TOP:
			drawY = y;
			break;
			case CENTER:
			drawY = y + (int) bounds.height / 2;
			break;
			case BOTTOM:
			drawY = y + (int) bounds.height;
			break;
		}
	}
}
