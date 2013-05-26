package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayScreen extends GameScreen {
    
	private BitmapFont font;
	private Pixmap atlas;
	public Texture tex;
	public TextureRegion playerSprites;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		atlas = new Pixmap(64, 32, Pixmap.Format.RGBA8888);
		Pixmap.setBlending(Pixmap.Blending.None);
		atlas.setColor(0, 0, 1, 1);
		atlas.fillCircle(16,  16,  15);
		atlas.setColor(0, 0, 0, 0);
		atlas.fillCircle(16,  16,  12);
		atlas.setColor(1, 0, 1, 1);
		atlas.fillCircle(48, 16, 15);
		atlas.setColor(0, 0, 0, 0);
		atlas.fillCircle(48, 16, 12);
		
		tex = new Texture(atlas);
		playerSprites = new TextureRegion(tex, 0, 0, 32, 32);
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		elements.add(new PlayerElement(playerSprites, LibGDXGame.width / 2, LibGDXGame.height / 2));
		
		font.setScale(1.0f);
		elements.add(new TextElement("Play Screen", font, 0, LibGDXGame.height));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		atlas.dispose();
		tex.dispose();
	}
}
