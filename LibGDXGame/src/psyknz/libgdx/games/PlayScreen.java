package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayScreen extends GameScreen {
    
	public BitmapFont font;
	public Pixmap pix;
	public Texture tex;
	public Sprite sprite;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		pix = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		Pixmap.setBlending(Pixmap.Blending.None);
		pix.setColor(1, 1, 1, 1);
		pix.fillCircle(16,  16,  15);
		pix.setColor(0, 0, 0, 0);
		pix.fillCircle(16,  16,  12);
		
		tex = new Texture(pix);
		sprite = new Sprite(tex, 0, 0, 32, 32);
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		elements.add(new PlayerElement(sprite));
		
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
