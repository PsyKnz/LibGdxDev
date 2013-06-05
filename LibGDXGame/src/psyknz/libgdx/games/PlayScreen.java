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
		
		pix = new Pixmap(64, 32, Pixmap.Format.RGBA8888);
		pix.setColor(1, 1, 1, 1);
		pix.fillCircle(16,  16,  15);
		pix.fillCircle(48,  16,  12);
		
		tex = new Texture(pix);
		Sprite bigCircle = new Sprite(tex, 0, 0, 32, 32);
		Sprite smallCircle = new Sprite(tex, 32, 0, 32, 32);
		
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		elements.add(new PlayerElement(bigCircle, smallCircle));
		
		font.setScale(1.0f);
		elements.add(new TextElement("Play Screen", font, 0, LibGDXGame.height));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		pix.dispose();
		tex.dispose();
	}
}
