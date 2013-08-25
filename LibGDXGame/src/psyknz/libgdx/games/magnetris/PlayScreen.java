package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class PlayScreen extends GameScreen implements GameElement {
    
	// Constant which effects the magnitude of effect distance from a magnet has on an OrbElement.
	public static final float MAGNET_CONSTANT = 1;
	
	// Reference to the CircleElement which all of the OrbElements are attracted to.
	private Array<CircleElement> magnets;
	
	// Reference to the CircleElement which all of the OrbElements needs to remain within 
	public CircleElement arena;
	
	/* Reference to the OrbController which manages all on-screen OrbElements as well as the size they should be and their spacing aong
	 *  the y-axis. */
	private OrbController orbController;
	public final int orbSize = 64;
	public final int ySpacing;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
		
		// Determines how far apart stationary OrbElements need to be on the y axis given their size.
		ySpacing = (int) Math.ceil(Math.sqrt(Math.pow(orbSize, 2) - Math.pow(orbSize / 2, 2)));
		
		// Adds the screen to the GameElement list.
		elements.add(this);
		
		// Loads the resources that are used by this screen.
		Sprite circle = new Sprite((Texture) game.assets.get("data/ShapeImageTemplate.png"), 0, 0, 64, 64);
		
		// Creates the arena, which all stationary OrbElements must remain within.
		arena = new CircleElement(circle, Color.WHITE, 386);
		arena.setX(game.width / 2);
		arena.setY(game.height / 2);
		elements.add(arena);
		
		// Creates the magnets Array which will hold all of the onscreen magnets that draw OrbElements in motion to them.
		magnets = new Array<CircleElement>();
		
		// Generates all of the magnets which are on screen.
		magnets.add(new CircleElement(circle, Color.DARK_GRAY, orbSize));
		magnets.get(0).setX(game.width / 2);
		magnets.get(0).setY(game.height / 2);
		
		// Adds all of the magnets to the list of GameElements
		elements.addAll(magnets);
		
		// Creates the orbController which keeps track of everything to do with the orbs on screen.
		orbController = new OrbController(this);
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
	
	// Returns a reference to the Array of on-screen magnets.
	public Array<CircleElement> getMagnets() {
		return magnets;
	}
}
