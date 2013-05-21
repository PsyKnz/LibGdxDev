package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayScreen extends GameScreen {
    
	private BitmapFont font;
	private Pixmap atlas;
	private Texture tex;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
		
		atlas = new Pixmap(64, 32, Pixmap.Format.RGBA4444);
		Pixmap.setBlending(Pixmap.Blending.None);
		atlas.setColor(0, 0, 1, 1);
		atlas.fillCircle(15,  15,  16);
		atlas.setColor(0, 0, 0, 0);
		atlas.fillCircle(15,  15,  12);
		atlas.setColor(1, 0, 1, 1);
		atlas.fillCircle(47, 15, 16);
		atlas.setColor(0, 0, 0, 0);
		atlas.fillCircle(47, 15, 12);
	}
	
	@Override
	public void show() {
		super.show();
		
		tex = new Texture(atlas);
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		elements.add(new PlayerElement(new TextureRegion(tex), LibGDXGame.width, LibGDXGame.height));
		
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
