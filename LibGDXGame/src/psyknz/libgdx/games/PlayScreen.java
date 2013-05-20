package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayScreen extends GameScreen {
    
	private BitmapFont font;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		
		font.setScale(1.0f);
		elements.add(new TextElement("Play Screen", font, 0, game.height));
	}
	
	private class PlayScreenElement implements GameElement {
		
		public PlayScreenElement() {
			
		}
		
		public void update(float delta) {
			
		}
		
		public void draw(SpriteBatch batch) {
			
		}
	}
}
