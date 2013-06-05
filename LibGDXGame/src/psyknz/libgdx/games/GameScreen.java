package psyknz.libgdx.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	// Constraints used for determining how the cameras focus should be stretched to fit the screen.
	private static final int CONSTRAIN_WIDTH = 0;
	private static final int CONSTRAIN_HEIGHT = 1;
	private static final int CONSTRAIN_MAX = 2;
	private static final int CONSTRAIN_MIN = 3;
	
	// Reference to the core game object.
	public LibGDXGame game;
	
	// Collection of all game elements present in the current screen.
	protected GameElementArray elements;
	
	// Member objects used to draw the screen.
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public GameScreen(LibGDXGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		elements = new GameElementArray();
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		// Runs the logic for all game elements.
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).update(delta);
		}
		
		// Resets the screens projection properties.
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Clears the contents of the screen.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Draws all game elements to the screen.
		batch.begin();
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).draw(batch);
		}
		batch.end();
	}
	
	// Extensions of this class should only override this method and should call the overloaded form with the desired resizing constraint.
	@Override
	public void resize(int width, int height) {
		resize(width, height, CONSTRAIN_WIDTH);
	}
	
	// Overloaded resize function which allows for the camera to be variably set based on which dimension is most necessary to have visible.
	public void resize(int width, int height, int constrain) {
		switch(constrain) {
			// Sets the camera so that the entire internal game width is visible.
			case CONSTRAIN_WIDTH:
			    LibGDXGame.visibleWidth = LibGDXGame.width;
			    LibGDXGame.visibleHeight = LibGDXGame.width * height / width;
				camera.setToOrtho(false, LibGDXGame.visibleWidth, LibGDXGame.visibleHeight);
				camera.position.y -= (LibGDXGame.visibleHeight - LibGDXGame.height) / 2;
			    break;
				
			// Sets the camera so that the entire internal game height is visible.
			case CONSTRAIN_HEIGHT:
			    LibGDXGame.visibleHeight = LibGDXGame.height;
			    LibGDXGame.visibleWidth = LibGDXGame.height * width / height;
				camera.setToOrtho(false, LibGDXGame.visibleWidth, LibGDXGame.visibleHeight);
				camera.position.x -= (LibGDXGame.visibleWidth - LibGDXGame.width) / 2;
			    break;
				
			// Sets the camera so that the entire internal game area is visible.
			case CONSTRAIN_MAX:
			    if(LibGDXGame.width / LibGDXGame.height >= width / height)
					resize(width, height, CONSTRAIN_WIDTH);
				else resize(width, height, CONSTRAIN_HEIGHT);
			    break;
			
			// Sets the camera so that the viewport is entirely filled.
			case CONSTRAIN_MIN:
			    if(LibGDXGame.width / LibGDXGame.height >= width / height)
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
		this.dispose();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
