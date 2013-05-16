package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LabelElement implements GameElement {
    
	// Label id. This is what's drawn to the screen.
	private CharSequence id;
	
	// Font and style information, should be set on the font before being passed to the constructor.
	private BitmapFont font;
	public float scale;
	
	// Positional information.
	public int x;
	public int y;
	
	public LabelElement(String id, BitmapFont font, int x, int y) {
		this.id = id;
		this.font = font;
		scale = font.getScaleX();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void update(float delta) {
		// insert different animation scripts here.
	}
	
	@Override
	public void draw(SpriteBatch batch) {
	    font.setScale(scale);
		font.draw(batch, id, x, y);
	}
}
