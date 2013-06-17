package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen extends GameScreen {

	private Pixmap fadePix;
	private Texture fadeTex;
	public BitmapFont font;
	
	public SplashScreen(LibGDXGame game) {
		super(game);
	}
	
	@Override
	public void loadElements() {
		font = new BitmapFont();
		
		font.setScale(2.0f);
		BitmapFont.TextBounds bounds = font.getBounds("Company Logo");
		elements.add(new TextElement("Company Logo", font, game.GAME_WIDTH / 2 - (int) (bounds.width / 2), game.GAME_HEIGHT / 2 + (int) (bounds.height / 2)));
		
		elements.add(new SplashScreenElement());
		
		font.setScale(1.0f);
		elements.add(new TextElement("Splash Screen", font, 0, game.GAME_HEIGHT));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		fadePix.dispose();
		fadeTex.dispose();
		font.dispose();
	}
	
	private class SplashScreenElement implements GameElement {
		
		private float[] fadeTimer = {1.0f, 3.0f, 1.0f};
		private Color fadeColor = new Color(0, 0, 0, 1);
		
		public SplashScreenElement() {
			fadePix = new Pixmap(1, 1, Pixmap.Format.RGBA4444);
			Pixmap.setBlending(Pixmap.Blending.None);
			fadePix.setColor(fadeColor);
			fadePix.drawPixel(0, 0);
			fadeTex = new Texture(fadePix);
		}
		
		public void update(float delta) {
			if(fadeTimer[0] > 0) {
				fadeColor.a = fadeTimer[0];
				fadePix.setColor(fadeColor);
				fadePix.drawPixel(0, 0);
				fadeTex.draw(fadePix, 0, 0);
				fadeTimer[0] -= delta;
			}
			else if(fadeTimer[1] > 0) {
			    fadeColor.a = 0;
	    		fadePix.setColor(fadeColor);
				fadePix.drawPixel(0, 0);
				fadeTex.draw(fadePix, 0, 0);
				if(isTouched()) {
					fadeTimer[1] = 0;
				}
				else {
					fadeTimer[1] -= delta;
				}
			}
			else if(fadeTimer[2] > 0) {
				fadeColor.a = 1 - fadeTimer[2];
				fadePix.setColor(fadeColor);
				fadePix.drawPixel(0, 0);
				fadeTex.draw(fadePix, 0, 0);
				fadeTimer[2] -= delta;
			}
			else {
				setScreen(new MenuScreen(game));
			}
		}
		
		public void draw(SpriteBatch batch) {
			batch.draw(fadeTex, 0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);
		}
	}
}
