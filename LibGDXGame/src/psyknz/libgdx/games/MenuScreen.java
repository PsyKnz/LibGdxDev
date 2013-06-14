package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuScreen extends GameScreen implements ElementListener {

	private Texture tex;
	private BitmapFont font;
	
	public CharSequence str;

	public MenuScreen(LibGDXGame game) {
		super(game);
	}
    
	@Override
	public void show() {
		super.show();
        
		Pixmap pix = new Pixmap(2, 2, Pixmap.Format.RGBA4444);
		pix.setColor(0, 0, 0, 1);
		pix.drawPixel(0, 0);
		pix.setColor(0, 0, 1, 1);
		pix.drawPixel(1, 0);
		pix.setColor(0.5f, 0.5f, 0.5f, 1);
		pix.drawPixel(0, 1);
		pix.setColor(1, 0, 0, 1);
		pix.drawPixel(1, 1);
		tex = new Texture(pix);
		pix.dispose();
		
		TextureRegion activeTex = new TextureRegion(tex, 1, 0, 1, 1);
		TextureRegion inactiveTex = new TextureRegion(tex, 0, 1, 1, 1);
		TextureRegion selectedTex = new TextureRegion(tex, 1, 1, 1, 1);
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		ButtonElement.ButtonPrefs prefs = new ButtonElement.ButtonPrefs(100, 50, activeTex, inactiveTex, selectedTex, font);
		font.setScale(2.0f);
		elements.add(new ButtonElement("Play", game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2, prefs, this, this));
		
		font.setScale(1.0f);
		elements.add(new TextElement("Menu Screen", font, 0, game.GAME_HEIGHT));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		tex.dispose();
		font.dispose();
	}
	
	public void action(String id) {
		game.setScreen(new PlayScreen(game));
	}
}
