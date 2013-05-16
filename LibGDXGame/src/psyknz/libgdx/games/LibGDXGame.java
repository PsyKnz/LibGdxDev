package psyknz.libgdx.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/* The LibGDXGame object is the entry point for the game. It manages the screens being displayed.
 * This object will not be destroyed until the game closes thus it is responsible for looking after
 * the games AssetManager. To actually run the game you call this objects setScreen() method and set it
 * to an instance of a GameScreen object which requires this be passed as an argument. */
public class LibGDXGame extends Game {
	
	// Core AssetManager for the game.
	public static AssetManager assets;
	
	// Tge games PixmapAtlas, allows for dynamic texture editing.
	public static PixmapAtlas pixAtlas;
	
	// The aspect ratio which should be used to draw the game screen.
	public float aspectRatio = 16 / 9;
	
	// Width and height of the game screen in in-game units.
	public int width = 800;
	public int height = 450;
	
	@Override
	public void create() {
		assets = new AssetManager();
		pixAtlas = new PixmapAtlas(1, 1);
		
		setScreen(new SplashScreen(this));
	}
	
	@Override
	public void resume() {
		super.resume();
		// Reloads the PixmapAtlas in case it was disposed of as a result of context loss.
		pixAtlas.reloadTexture();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		pixAtlas.dispose();
		assets.dispose();
	}
}
