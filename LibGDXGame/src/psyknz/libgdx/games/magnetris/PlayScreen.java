package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayScreen extends GameScreen implements GameElement {
    
	// Reference to the CircleElement which all of the OrbElements are attracted to.
	private CircleElement magnet;
	
	// Reference to the orbController 
	private OrbController orbController;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
		
		// Adds the screen to the GameElement list.
		elements.add(this);
		
		// Loads the resources that are used by this screen.
		Sprite circle = new Sprite((Texture) game.assets.get("data/ShapeImageTemplate.png"), 0, 0, 64, 64);
		
		// Creates the magnet, which will sit in the middle of the screen and all OrbElements will be drawn to it.
		magnet = new CircleElement(circle, Color.DARK_GRAY, 64);
		magnet.setX(game.width / 2);
		magnet.setY(game.height / 2);
		elements.add(magnet);
		
		// Creates the orbController which keeps track of everything to do with the orbs on screen.
		orbController = new OrbController(this, 18);
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