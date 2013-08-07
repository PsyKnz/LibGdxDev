package psyknz.libgdx.games;

import psyknz.libgdx.games.robotjrpg.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LoadingScreen extends GameScreen {
	
	// Font used to draw the loading message and progress.
	private BitmapFont font;
	private String str = "Loading ";
	private float progress, xOffset, yOffset;
	
	public LoadingScreen(LibGDXGame game) {
		super(game);
		
		// Forcefully loads all assets required to present the loading screen here.
		font = new BitmapFont();
		font.setScale(2.0f);
		BitmapFont.TextBounds bounds = font.getBounds(str);
		xOffset = bounds.width;
		yOffset = bounds.height;
		
		game.assets.finishLoading();
				
		// Queues up all other assets to load here.
		game.assets.load("data/ShapeImageTemplate.png", Texture.class);
				
		// Adds all elements which are part of the loading screen here.
		elements.add(new GameAssetLoader());
		
		elements.add(game.console);
	}
	
	// Disposes of the font used to display the loading message.
	@Override
	public void dispose() {
		super.dispose();
		font.dispose();
	}
	
	/* A GameAssetLoader Element is intended solely to be used by a LoadingScreen to keep the AssetManager
	 * updating and to draw some form of progress metre that tracks the updating process. Once the AssetManager
	 * has reached 100% the finish() method is called on the LoadingScreen object. */
	private class GameAssetLoader implements GameElement {
		
		// Continues to update the AssetManager until it reaches 100% progress.
		@Override
		public void update(float delta) {
			progress = game.assets.getProgress() * 100;
			
			// Keeps the game assets loading until they're 100 complete. Once done the next screen is loaded.
			if(progress < 100) {
				game.assets.update();
			}
			else setScreen(new BattleScreen(game));
		}
		
		/* Draws a progress metre for the AssetManager. Assumes all assets required to draw the metre have
		 * been forcefully loaded prior to creation of the GameAssetLoader. */
		@Override
		public void draw(SpriteBatch batch) {
			font.draw(batch, str + progress, game.GAME_WIDTH - xOffset, game.GAME_HEIGHT + yOffset);
		}
	}
}
