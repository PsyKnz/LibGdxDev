package psyknz.libgdx.games.buildinglcg.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/* The LibGDXGame object is the entry point for the game. It manages the screens being displayed.
 * This object will not be destroyed until the game closes thus it is responsible for looking after
 * the games AssetManager. To actually run the game you call this objects setScreen() method and set it
 * to an instance of a GameScreen object which requires this be passed as an argument. */
public class LibGDXGame extends Game {

	// Width and height of the game screen in in-game units.
	public final int targetWidth = 800, targetHeight = 450;

	// Core AssetManager for the game.
	public AssetManager assets;

	// Reference to the games console. Has to be added to the GameElement array for a screen to be interactable with.
	public ConsoleElement console;
	
	/* */
	@Override
	public void create() {
		assets = new AssetManager();

		// Creates the console, used for debugging during runtime.
		console = new ConsoleElement(this);

		// Loads the first screen to use in the game, the LoadingScreen.
		setScreen(new LoadingScreen(this));
	}

	// Disposes of all core assets used by the game.
	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
	}

	/* Returns the current screen being presented by the game. The Screen is cast to a GameScreen but all LibGDXGame objects should only
	 *  be presenting objects derived from GameScreen anyway. */
	@Override
	public GameScreen getScreen() {
		return (GameScreen) super.getScreen();
	}
}
