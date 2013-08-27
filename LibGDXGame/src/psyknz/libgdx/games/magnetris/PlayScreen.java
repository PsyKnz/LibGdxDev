package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class PlayScreen extends GameScreen implements GameElement {
	
	// Reference to the CircleElement which all of the OrbElements needs to remain within 
	public CircleElement arena;
	
	/* Reference to the OrbController which manages all on-screen OrbElements as well as the size they should be and their spacing aong
	 *  the y-axis. */
	private OrbController orbController;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
		
		// Adds the screen to the GameElement list.
		elements.add(this);
		
		// Loads the resources that are used by this screen.
		Sprite circle = new Sprite((Texture) game.assets.get("data/Shapes.png"), 0, 0, 64, 64);
		
		// Creates the arena, which all stationary OrbElements must remain within.
		arena = new CircleElement(circle, Color.WHITE, 386);
		arena.setX(game.width / 2);
		arena.setY(game.height / 2);
		elements.add(arena);
		
		// Creates the orbController which keeps track of everything to do with the orbs on screen.
		orbController = new OrbController(this, new float[] {game.width / 2, game.height / 2});
		elements.add(orbController);
	}
	
	// Runs screen specific game logic
	@Override
	public void update(float delta) {}
	
	// Draws PlayScreen specific elements to the screen.
	@Override
	public void draw(SpriteBatch batch) {}
	
	// Disposes of all resources loaded by this screen that require disposing of.
	@Override
	public void dispose() {}
}
