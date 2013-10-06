package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayScreen extends GameScreen implements GameElement {
	
	// Reference to the CircleElement which all of the OrbElements needs to remain within 
	public final CircleElement arena;
	
	// Reference to the TouchElement used by the screen to track finger movement for placement of SELECTED OrbElements.
	public final TouchElement touchElement;
	
	public BitmapFont font;
	
	/* Reference to the OrbController which manages all on-screen OrbElements as well as the size they should be and their spacing aong
	 *  the y-axis. */
	public final OrbController orbController;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
		
		// Loads the resources that are used by this screen.
		Sprite circle = new Sprite((Texture) game.assets.get("data/Shapes.png"), 0, 0, 64, 64);
		Sprite dot = new Sprite((Texture) game.assets.get("data/Shapes.png"), 32, 32, 1, 1);
		
		// Creates the arena, which all stationary OrbElements must remain within.
		arena = new CircleElement(circle, Color.GRAY, game.height - OrbElement.ORB_SIZE);
		arena.setX(game.width / 2);
		arena.setY(game.height / 2);
		elements.add(arena);
		
		// Creates the orbController which keeps track of everything to do with the orbs on screen.
		orbController = new OrbController(this, new float[] {game.width / 2, game.height / 2});
		elements.add(orbController);
		
		// Creates the TouchElement for finger tracking and adds it to the screens GameElement Array.
		touchElement = new TouchElement(this);
		touchElement.sprite = dot;
		touchElement.maxLength = 256;
		touchElement.visible = true;
		elements.add(touchElement);
		
		font = new BitmapFont();
		
		// Adds the screen to the GameElement list.
		elements.add(this);
	}
	
	// Runs screen specific game logic
	@Override
	public void update(float delta) {}
	
	// Draws PlayScreen specific elements to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		font.draw(batch, "touchX: " + touchX() + ", touchY: " + touchY(), 0, game.height);
		font.draw(batch, "maxLength: " + touchElement.maxLength + ", currentLength: " + touchElement.getLength(), 0, game.height - 32);
	}
	
	// Disposes of all resources loaded by this screen that require disposing of.
	@Override
	public void dispose() {}
}
