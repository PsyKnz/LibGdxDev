package psyknz.libgdx.games.buildinglcg.engine;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen, InputProcessor {

    // Constants used to determine how the resize the screen while maintaining the target aspect ratio.
	public static enum Resize {
		WIDTH, HEIGHT, MAX, MIN
	}

	// Reference to the core game object.
	public final LibGDXGame game;

	/* Reference to the core GameElement and overriding GameElement which will generally be a menu of some form. While there is an
	 * overriding element present it will block the core GameElements update(float) function while allowing it to draw itself. */
	public final Array<GameElement> elements;
	public GameElement overridingElement = null;

	// Constants used to resize the screen.
	protected Resize resizeConstant = Resize.MAX;
	
	// Objects used to draw the GameScreen.
	private OrthographicCamera camera;
	private SpriteBatch batch;

	// Variables set during screen resizing. Used for converting between screen co-ordinates in pixels and in-game units.
	public final Rectangle viewport;

	// The color used when clearing the screen. Black by default.
	public Color background = new Color(0, 0, 0, 1);

	// The next screen to be moved to after this screen. Set using the setScreen(GameScreen) function.
	private GameScreen nextScreen = null;

	// All core screen objects are loaded in the contructor call.
	public GameScreen(LibGDXGame game) {
		this.game = game;
		viewport = new Rectangle();
		elements = new Array<GameElement>();
		
		// Logs that the screen was constructed.
		game.console.log(this.toString() + " successfully loaded.");
	}

	// Sets up the core objects necessary to show the screen.
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		
		// Logs that the screen was successfully shown.
		game.console.log("Show() successfully called for " + this.toString());
	}

	@Override
	public void render(float delta) {
		// Updates the core GameElements game logic unless there is currently an overriding element present (such as a menu).
		if(overridingElement == null) {
			for(int i = 0; i < elements.size; i++) {
				elements.get(i).update(delta);
			}
		}
		else overridingElement.update(delta);

		// Resets the screens projection properties.
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		// Clears the contents of the screen.
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// Draws all game elements to the screen relative to the position of the viewport.
		batch.begin();
		for(int i = 0; i < elements.size; i++) {
			elements.get(i).draw(batch, (int) viewport.x, (int) viewport.y);
		}
		if(overridingElement != null) overridingElement.draw(batch, (int) viewport.x, (int) viewport.y);
		batch.end();

		// Loads the next GameScreen if one is currently set to be loaded. Objects should not call game.setScreen(Screen) directly as it will unload game assets too early.
		if(nextScreen != null) {
			game.setScreen(nextScreen);
		}
	}

	// Resizes the screen using the desired resizing constant, sets the camera, and then centers it.
	@Override
	public void resize(int width, int height) {
		// Determines the size of the resized screen area in in-game units while preserving the current aspect ratio.
		resize(width, height, resizeConstant);

		// Sets the camera to fill the screen and shifts it as far left and down as necessary to center the camera.
		camera.setToOrtho(false, viewport.width, viewport.height);
		viewport.x = (game.targetWidth - viewport.width) / 2;
		viewport.y = (game.targetHeight - viewport.height) / 2;
		camera.position.x += viewport.x;
		camera.position.y += viewport.y;
		
		// Logs that the screen was resized.
		game.console.log("Screen successfully resized to " + width + "x" + height + " for " + this.toString());
	}

	/* Overloaded resize function which allows for the camera to be variably set based on which dimension is most necessary to have
	 * visible.	Should only be called from within the standard resize method. */
	private void resize(int width, int height, Resize constant) {
		// Sets the camera so that the full game width is visible.
		if(resizeConstant == Resize.WIDTH) {
			viewport.width = game.targetWidth;
			viewport.height = game.targetHeight * height / width;
		}
		
		// Sets the camera so that the full game height is visible.
		else if(resizeConstant == Resize.HEIGHT) {
			viewport.height = game.targetHeight;
			viewport.width = game.targetHeight * width / height;
		}
		
		// Sets the camera so that the entire game area is visible.
		else if(resizeConstant == Resize.MAX) {
			if(game.targetWidth / game.targetHeight >= width / height) resize(width, height, Resize.WIDTH);
			else resize(width, height, Resize.HEIGHT);
		}
		
		// Sets the camera so that the entire screen area is filled with game area.
		else if(resizeConstant == Resize.MIN) {
			if(game.targetWidth / game.targetHeight >= width / height) resize(width, height, Resize.HEIGHT);
			else resize(width, height, Resize.WIDTH);
		}
	}

	// Called whenever context is restored to the game.
	@Override
	public void resume() {
		// Logs that the GameScreen has been resumed.
		game.console.log("Resume() successfully called for " + this.toString());
	}

	// Called whenever the context is lost.
	@Override
	public void pause() {
		// Logs that the GameScreen has been paused.
		game.console.log("Pause() successfully called for " + this.toString());
	}

	// Called whenever the currentScreen of the core game is changed off of this screen.
	@Override
	public void hide() {
		this.dispose();
		
		// Logs that the GameScreen has been hidden.
		game.console.log("Hide() successfully called for " + this.toString());
	}

	// Disposes of all native resources generated by this screen.
	@Override
	public void dispose() {
		// Logs that dispose has been called for the GameScreen.
		game.console.log("Dispose() called for " + this.toString());

		batch.dispose();
	}

	// Returns whether or not the screen is currently being touched.
	public boolean isTouched() {
		return Gdx.input.isTouched();
	}

	// Returns the x co-ordinate of where the screen is being touched in in-game units.
	public float touchX() {
		return Gdx.input.getX() * viewport.height / Gdx.graphics.getWidth() + viewport.x;
	}

	// Returns the y co-ordinate of where the screen is being touched in in-game units.
	public float touchY() {
		return viewport.height - Gdx.input.getY() * viewport.height / Gdx.graphics.getHeight() - viewport.y;
	}

	// Use this function to change the current game screen. Forces screen changes to only occur at the end of the rendering cycle.
	public void setScreen(GameScreen screen) {
		nextScreen = screen;
	}
	
	// Called when the specified key is pressed.
	@Override
	public boolean keyDown(int p1) {
		return false;
	}
	
	// Called when the specified key is released.
	@Override
    public boolean keyUp(int p1) {
		return false;
	}
	
	// Called when the specified character is typed.
	@Override
    public boolean keyTyped(char p1) {
		return false;
	}
	
	// Called when a finger touches the touchscreen.
	@Override
    public boolean touchDown(int p1, int p2, int p3, int p4) {
		return false;
	}
	
	// Called when a finger releases the touchscreen.
	@Override
    public boolean touchUp(int p1, int p2, int p3, int p4) {
		return false;
	}
	
	// Called when a finger is dragged across the touchscreen.
	@Override
    public boolean touchDragged(int p1, int p2, int p3) {
		return false;
	}
	
	// Called when the mouse is moved across the GameScreen.
	@Override
    public boolean mouseMoved(int p1, int p2) {
		return false;
	}
	
	// Called when the mouses scroll wheel is used.
	@Override
    public boolean scrolled(int p1) {
		return false;
	}
}
