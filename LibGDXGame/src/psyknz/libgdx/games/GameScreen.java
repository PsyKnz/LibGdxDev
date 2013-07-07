package psyknz.libgdx.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class GameScreen implements Screen {
	
    // Constraints when to determine how to focus the camera on the screen. Used by the overloaded resize function.
	public static final int CONSTRAIN_WIDTH = 0;
	public static final int CONSTRAIN_HEIGHT = 1;
	public static final int CONSTRAIN_MAX = 2;
	public static final int CONSTRAIN_MIN = 3;
	
	// Reference to the core game object.
	protected LibGDXGame game;
	
	/* Reference to the core GameElement and overriding GameElement which will generally be a menu of some form. While there is an
	 * overriding element present it will block the core GameElements update(float) function while allowing it to draw itself. */
	protected Array<GameElement> elements;
	protected GameElement overridingElement = null;
	
	// Member objects used to draw the screen.
	protected int resizeConstraint = GameScreen.CONSTRAIN_MAX;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Variables set during screen resizing. Used for converting between screen co-ordinates in pixels and in-game units.
	protected int visibleWidth, visibleHeight, leftOffset, bottomOffset;
	
	// The color used when clearing the screen. Black by default.
	public Color background = new Color(0, 0, 0, 1);
	
	// The next screen to be moved to after this screen. Set using the setScreen(GameScreen) function.
	private GameScreen nextScreen = null;
	
	// All core screen objects are loaded in the contructor call.
	public GameScreen(LibGDXGame game) {
		this.game = game;
		
		elements = new Array<GameElement>();
		
		game.console.log(this.toString() + " loaded.");
	}
	
	// Sets up the core objects necessary to show the screen.
	@Override
	public void show() {		
		game.console.log("Show() called for " + this.toString());
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		// Updates the core game element unless there is currently a menu element open for the player to interact with.
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
		
		// Draws all game elements to the screen.
		batch.begin();
		for(int i = 0; i < elements.size; i++) {
			elements.get(i).draw(batch);
		}
		if(overridingElement != null) overridingElement.draw(batch);
		batch.end();
		
		// Loads the next GameScreen. Objects should not call game.setScreen(Screen) directly as it will unload game assets too early.
		if(nextScreen != null) {
			game.setScreen(nextScreen);
		}
	}
	
	// Resizes the screen using the desired resizing conatraint, sets the camera, and then centers it.
	@Override
	public void resize(int width, int height) {
		game.console.log("Resize(int, int) called for " + this.toString());
		
		// Determines the size of the resized screen area in in-game units while preserving the current aspect ratio.
		resize(width, height, resizeConstraint);
		
		// Sets the camera to fill the screen and shifts it as far left and down as necessary to center the camera.
		camera.setToOrtho(false, visibleWidth, visibleHeight);
		leftOffset = (visibleWidth - game.GAME_WIDTH) / 2;
		bottomOffset = (visibleHeight - game.GAME_HEIGHT) / 2;
		camera.position.x -= leftOffset;
		camera.position.y -= bottomOffset;
	}
	
	/* Overloaded resize function which allows for the camera to be variably set based on which dimension is most necessary to have
	 * visible.	Should only be called from within the standard resize method. */
	private void resize(int width, int height, int constrain) {
		switch(constrain) {
			// Sets the camera so that the entire internal game width is visible.
			case CONSTRAIN_WIDTH:
			    visibleWidth = game.GAME_WIDTH;
			    visibleHeight = game.GAME_WIDTH * height / width;
			    break;

			// Sets the camera so that the entire internal game height is visible.
			case CONSTRAIN_HEIGHT:
			    visibleHeight = game.GAME_HEIGHT;
			    visibleWidth = game.GAME_HEIGHT * width / height;
			    break;

			// Sets the camera so that the entire internal game area is visible.
			case CONSTRAIN_MAX:
			    if(game.GAME_WIDTH / game.GAME_HEIGHT >= width / height) resize(width, height, CONSTRAIN_WIDTH);
				else resize(width, height, CONSTRAIN_HEIGHT);
			    break;

			// Sets the camera so that the viewport is entirely filled.
			case CONSTRAIN_MIN:
			    if(game.GAME_WIDTH / game.GAME_HEIGHT >= width / height) resize(width, height, CONSTRAIN_HEIGHT);
				else resize(width, height, CONSTRAIN_WIDTH);
			    break;
		}
	}
	
	// Called whenever context is restored to the game.
	@Override
	public void resume() {
		game.console.log("Resume() called for " + this.toString());
	}
	
	// Called whenever the context is lost.
	@Override
	public void pause() {
		game.console.log("Pause() called for " + this.toString());
	}
	
	// Called whenever the currentScreen of the core game is changed off of this screen.
	@Override
	public void hide() {
		game.console.log("Hide() called for " + this.toString());
		
		// Disposes of all native resources generated by this screen.
		this.dispose();
	}
	
	// Disposes of all native resources generated by this screen.
	@Override
	public void dispose() {
		game.console.log("Dispose() called for " + this.toString());
		
		batch.dispose();
	}
	
	// Returns whether or not the screen is currently being touched.
	public boolean isTouched() {
		return Gdx.input.isTouched();
	}
	
	// Returns the x co-ordinate of where the screen is being touched in in-game units.
	public int touchX() {
		return Gdx.input.getX() * visibleWidth / Gdx.graphics.getWidth() + leftOffset;
	}
	
	// Returns the y co-ordinate of where the screen is being touched in in-game units.
	public int touchY() {
		return visibleHeight - Gdx.input.getY() * visibleHeight / Gdx.graphics.getHeight() - bottomOffset;
	}
	
	// Use this function to change the current game screen. Forces screen changes to only occur at the end of the rendering cycle.
	public void setScreen(GameScreen screen) {
		nextScreen = screen;
	}
}