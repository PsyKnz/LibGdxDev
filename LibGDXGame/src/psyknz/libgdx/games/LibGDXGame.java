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
	
	// Width and height of the game screen in in-game units.
	public static final int width = 800;
	public static final int height = 450;
	
	// The width and height of the visible game area in in-game units.
	public static int visibleWidth;
	public static int visibleHeight;
	
	@Override
	public void create() {
		assets = new AssetManager();
		
		// Loads the first screen to use in the game.
		setScreen(new SplashScreen(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
	}
}
