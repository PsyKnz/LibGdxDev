package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen extends GameScreen {
	
	public LoadingScreen(LibGDXGame game) {
		super(game);
	}
	
	/* Any assets required to show the loading screen are forced to load first, and then all other game
	 * assets are queued up. A GameAssetLoader Element is then constructed to ensure the AssetManager continues
	 * to load all game assets and to display the progress of this process. */
	@Override
	protected void loadElements() {
		// Forcefully loads all assets required to present the loading screen here.
		LibGDXGame.assets.load("data/libgdx.png", Texture.class);
		LibGDXGame.assets.finishLoading();
				
		// Queues up all other assets to load here.
				
		// Adds all elements which are part of the loading screen here.
		elements.add(new GameAssetLoader(this));
	}
	
	// Called once all assets have finished loading.
	public void finish() {
		// game.setScreen(new GameScreen(game));
	}
	
	/* A GameAssetLoader Element is intended solely to be used by a LoadingScreen to keep the AssetManager
	 * updating and to draw some form of progress metre that tracks the updating process. Once the AssetManager
	 * has reached 100% the finish() method is called on the LoadingScreen object. */
	private class GameAssetLoader implements GameElement {
		
		private LoadingScreen parent;
		
		public GameAssetLoader(LoadingScreen parent) {
			this.parent = parent;
		}
		
		// Continues to update the AssetManager until it reaches 100% progress.
		public void update(float delta) {
			if(LibGDXGame.assets.getProgress() < 1) {
				LibGDXGame.assets.update();
			}
			else {
				parent.finish();
			}
		}
		
		/* Draws a progress metre for the AssetManager. Assumes all assets required to draw the metre have
		 * been forcefully loaded prior to creation of the GameAssetLoader. */		
		public void draw(SpriteBatch batch) {
			// Code for drawing some form of progress metre goes in here.
		}

	}

}
