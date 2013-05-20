package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	// Reference to the core game object.
	public LibGDXGame game;
	
	// Collection of all game elements present in the current screen..
	protected ArrayList<GameElement> elements;
	
	// Member objects used to draw the screen.
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public GameScreen(LibGDXGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		elements = new ArrayList<GameElement>();
		
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
	
	@Override
	public void resize(int width, int height) {
		// Stores the screen width and height then adjusts the camera accordingly.
		camera.setToOrtho(false, LibGDXGame.width, LibGDXGame.height);
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void hide() {
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
