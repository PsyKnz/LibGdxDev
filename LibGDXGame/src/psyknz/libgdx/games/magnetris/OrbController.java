package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class OrbController implements GameElement {	

	// Reference to the screen this controller is being used by.
	private PlayScreen screen;
	
	/* Array contraining all of the orbs which are currently on the screen, a reference to the orb which the player currently has
	 *  selected and a reference to the sprite used to draw those orbs. */
	private OrbElement selectedOrb = null;
	private Array<OrbElement> orbs;
	private Sprite orbSprite;
	
	// Timer to track when the next orb should be generated and a variable to track how frequently OrbElements should spawn (in seconds).
	private float spawnTimer, spawnRate;
	
	public OrbController(PlayScreen screen, int startSize) {
		this.screen = screen;
		
		// Loads the sprite used to draw the orbs.
		orbSprite = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 0, 0, 64, 64);
		
		// Generates all of the starting orbs for the level.
		orbs = new Array<OrbElement>();
		for(int i = 0; i < startSize; i++) {
			generateOrb();
		}
		
		// Sets the spawnRate for OrbElements and starts the timer.
		spawnTimer = spawnRate = 2.5f;
	}
	
	// Updates the OrbController's game logic.
	@Override
	public void update(float delta) {
		
		// Counts down the spawn timer and generates new OrbElements until the spawnTimer is back above 0.
		spawnTimer -= delta;
		while(spawnTimer < 0) {
			generateOrb();
			spawnTimer += spawnRate;
		}
	}
	
	// Draws all of the OrbElements to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		for(int i = 0; i < orbs.size; i++) {
			orbs.get(i).draw(batch);
		}
	}
	
	// Generates a new orb of a random color and places it at a random location on the outside of the screen.
	public void generateOrb() {
		
		// Selects a random color to make the orb.
		Color color;
		switch(MathUtils.random(2)) {
		case 0: color = Color.RED;
			break;
		case 1: color = Color.GREEN;
			break;
		case 2: color = Color.BLUE;
			break;
		default: color = Color.BLACK;
		}
		
		// Generates the orb.
		OrbElement orb = new OrbElement(orbSprite, color);
		
		// Randomly selects which edge the orb should enter the screen from and where on that edge.
		switch(MathUtils.random(4)) {
		
		// Places the orb along the bottom edge.
		case 0: orb.setY(0 - screen.getBottomOffset() - orb.getBounds().height / 2);
			orb.setX(0 - screen.getLeftOffset() - orb.getBounds().width / 2 + MathUtils.random(screen.getVisibleWidth()));
			break;
			
		// Places the orb along the top edge.
		case 1: orb.setY(screen.getVisibleHeight() - screen.getBottomOffset() + orb.getBounds().height / 2);
			orb.setX(0 - screen.getLeftOffset() - orb.getBounds().width / 2 + MathUtils.random(screen.getVisibleWidth()));
			break;
			
		// Places the orb along the left hand edge.
		case 2: orb.setX(0 - screen.getLeftOffset() - orb.getBounds().width / 2);
			orb.setY(0 - screen.getBottomOffset() - orb.getBounds().height / 2 + MathUtils.random(screen.getVisibleHeight()));
			break;
			
		// Places the orb along the right hand edge.
		case 3: orb.setX(screen.getVisibleWidth() - screen.getLeftOffset() - orb.getBounds().width / 2);
			orb.setY(0 - screen.getBottomOffset() - orb.getBounds().height / 2 + MathUtils.random(screen.getVisibleHeight()));
			break;
		}
		
		// Aims the OrbElement at a random on-screen magnet.
		int magnet = MathUtils.random(screen.getMagnets().size);
		orb.setMotion(screen.getMagnets().get(magnet).getX(), screen.getMagnets().get(magnet).getY());
		
		// Adds the OrbElement to the orbs array.
		orbs.add(orb);
	}

}