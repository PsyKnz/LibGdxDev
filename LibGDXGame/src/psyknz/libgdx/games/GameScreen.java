package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    // Constraints used for determining how the cameras focus should be stretched to fit the screen.
	public static final int CONSTRAIN_WIDTH = 0;
	public static final int CONSTRAIN_HEIGHT = 1;
	public static final int CONSTRAIN_MAX = 2;
	public static final int CONSTRAIN_MIN = 3;
	
	// Reference to the core game object.
	protected LibGDXGame game;
	
	// Collection of all game elements present in the current screen. The superElement will override all other elements when present.
	protected GameElement elementOverride;
	protected ArrayList<GameElement> elements;
	
	// Member objects used to draw the screen.
	protected int resizeConstraint = GameScreen.CONSTRAIN_MAX;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Variables set during screen resizing. Used for converting between screen co-ordinates in pixels and in-game units.
	protected int visibleWidth;
	protected int visibleHeight;
	protected int leftOffset;
	protected int bottomOffset;
	
	// The color used when clearing the screen.
	public Color background;
	
	public GameScreen(LibGDXGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		elementOverride = null;
		elements = new ArrayList<GameElement>();

		background = new Color(0, 0, 0, 1);
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		// Runs the logic for all game elements. If there is an elementOverride only its update method is called.
		if(elementOverride == null) {
		    for(int i = 0; i < elements.size(); i++) {
			    elements.get(i).update(delta);
            }
		}
		else elementOverride.update(delta);
		
		// Resets the screens projection properties.
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Clears the contents of the screen.
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Draws all game elements to the screen.
		batch.begin();
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).draw(batch);
		}
		if(elementOverride != null) elementOverride.draw(batch);
		batch.end();
	}
	
	// Resizes the screen using the desired resizing conatraint, sets the camera, and then centers it.
	@Override
	public void resize(int width, int height) {
		resize(width, height, resizeConstraint);
		leftOffset = (visibleWidth - game.GAME_WIDTH) / 2;
		bottomOffset = (visibleHeight - game.GAME_HEIGHT) / 2;
		camera.setToOrtho(false, visibleWidth, visibleHeight);
		camera.position.x -= leftOffset;
		camera.position.y -= bottomOffset;
	}
	
	// Overloaded resize function which allows for the camera to be variably set based on which dimension is most necessary to have visible
	public void resize(int width, int height, int constrain) {
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
			    if(game.GAME_WIDTH / game.GAME_HEIGHT >= width / height)
					resize(width, height, CONSTRAIN_WIDTH);
				else resize(width, height, CONSTRAIN_HEIGHT);
			    break;

				// Sets the camera so that the viewport is entirely filled.
			case CONSTRAIN_MIN:
			    if(game.GAME_WIDTH / game.GAME_HEIGHT >= width / height)
					resize(width, height, CONSTRAIN_HEIGHT);
				else resize(width, height, CONSTRAIN_WIDTH);
			    break;
		}
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void hide() {
		// this.dispose();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
	
	// Methods for testing user input on the touchscreen.
	public boolean isTouched() {
		return Gdx.input.isTouched();
	}
	
	public int touchX() {
		return Gdx.input.getX() * visibleWidth / Gdx.graphics.getWidth() + leftOffset;
	}
	
	public int touchY() {
		return visibleHeight - Gdx.input.getY() * visibleHeight / Gdx.graphics.getHeight() - bottomOffset;
	}
}
