package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
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
	public void loadElements() {
		pix = new Pixmap(128, 64, Pixmap.Format.RGBA8888);
		pix.setColor(1, 1, 1, 1);
		
		// Draws the texture for the snake sprites.
		pix.fillCircle(15,  15,  15);
		
		// Draws a dot to use for making rectangles.
		pix.fillRectangle(0, 33, 1, 1);
		
		// Create the texture and then dispose of the pixmap used for that texture.
		tex = new Texture(pix);
		pix.dispose();
		
		// Generate sprites which will be used GameElements
		Sprite circle = new Sprite(tex, 0, 0, 32, 32);
		Sprite dot = new Sprite(tex, 0, 33, 1, 1);
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		ShapeElement arena = new ShapeElement(this, dot, Color.RED, game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2,
											  game.GAME_WIDTH - 20, game.GAME_HEIGHT - 20);
		arena.filled = false;
		background.set((background.r * 2 + arena.color.r) / 3, (background.g * 2 + arena.color.g) / 3,
					   (background.b * 2 + arena.color.b) / 3, background.a);
		elements.add(arena);
		
		elements.add(new PlayerElement(this, circle, game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2));
		
		font.setScale(1.0f);
		elements.add(new TextElement("Play Screen", font, 0 - leftOffset, visibleHeight - bottomOffset));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		tex.dispose();
		font.dispose();
	}
}
