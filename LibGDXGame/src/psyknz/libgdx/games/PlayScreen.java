package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayScreen extends GameScreen {
    
	public BitmapFont font;
	public Pixmap pix;
	public Texture tex;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		pix = new Pixmap(128, 64, Pixmap.Format.RGBA8888);
		pix.setColor(1, 1, 1, 1);
		
		// Draws the texture for the snake sprites.
		pix.fillCircle(15,  15,  15);
		pix.fillCircle(47,  15,  12);
		
		// Draws a dot to use for making rectangles.
		pix.fillRectangle(0, 32, 1, 1);
		
		tex = new Texture(pix);
		Sprite bigCircle = new Sprite(tex, 0, 0, 32, 32);
		Sprite smallCircle = new Sprite(tex, 32, 0, 32, 32);
		Sprite dot = new Sprite(tex, 0, 32, 1, 1);
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		elements.add(new PlayerElement(this, bigCircle, game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2));
		
		font.setScale(1.0f);
		elements.add(new TextElement("Play Screen", font, 0, game.GAME_HEIGHT));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		pix.dispose();
		tex.dispose();
		font.dispose();
	}
}
