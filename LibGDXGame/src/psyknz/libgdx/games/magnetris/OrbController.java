package psyknz.libgdx.games.magnetris;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class OrbController implements GameElement {

	// Reference to the screen this controller is being used by.
	private PlayScreen screen;
	
	/* Array contraining all of the orbs which are currently on the screen, a reference to the orb which the player currently has
	 *  selected and a reference to the sprite used to draw those orbs. */
	private Array<OrbElement> orbs;
	private Array<OrbElement> selectedOrbs;
	private Sprite orbSprite;
	
	// Timer to track when the next orb should be generated and a variable to track how frequently OrbElements should spawn (in seconds).
	private float spawnTimer, spawnRate;
	
	// Reference to the orb currently being processed.
	private OrbElement currentOrb;
	
	// Reference to the vector currently being processed.
	private Vector2 currentForce;
	
	// An array of 6 points which are tested against following collision with a stationary OrbElement.
	private Vector2[] stopPoints;
	
	public final float ySpacing;
	
	public OrbController(PlayScreen screen) {
		this.screen = screen;
		
		// Determines how far apart stationary OrbElements need to be on the y axis given their size.
		ySpacing = (int) Math.ceil(Math.sqrt(Math.pow(OrbElement.ORB_SIZE, 2) - Math.pow(OrbElement.ORB_SIZE / 2, 2)));
		
		// Initialises the Arrays used to track on-screen OrbElements.
		orbs = new Array<OrbElement>();
		selectedOrbs = new Array<OrbElement>();
		
		// Creates a new vector of 0 length.
		currentForce = new Vector2(Vector2.Zero);
		stopPoints = new Vector2[] {new Vector2(Vector2.Zero), new Vector2(Vector2.Zero), new Vector2(Vector2.Zero),
									new Vector2(Vector2.Zero), new Vector2(Vector2.Zero), new Vector2(Vector2.Zero)};
		
		// Loads the sprite used to draw the orbs.
		orbSprite = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 0, 0, 64, 64);
		
		// Starts the spawnTimer.
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
		
		// Updates the game logic for all of the on-screen OrbElements.
		for(int i = 0; i < orbs.size; i++) {
			currentOrb = orbs.get(i);
			currentOrb.update(delta);
			if(currentOrb.getState() == OrbElement.MOTION) applyForce(currentOrb);
		}
		
		// Positions all selected OrbElements along the path of the finger.
		for(int i = 0; i < selectedOrbs.size; i++) {
			
		}
		
		// Tests for collisions between all OrbElements (including testing against the magnets).
		for(int i = 0; i < orbs.size; i++) {
			for(int j = 0; j < orbs.size; j++) {
				
				/* Provided that the OrbElements being compared are not the same OrbElement, if they overlap a collision occurs and is
				 *  processed based on what each of their current states are. */
				if(i != j && orbs.get(i).overlaps(orbs.get(j))) {
					
					if(orbs.get(i).getState() == OrbElement.MOTION) {
						if(orbs.get(j).getState() == OrbElement.SELECTED) {}
						
						/* If OrbElement i is in MOTION and OrbElement j is STATIONARY then the 6 points (NE, E, SE, SW, W, NW) around
						 *  OrbElement j where OrbElement i could settle are tested to determine which is the closest appropriate fit
						 *  for OrbElement i. */
						if(orbs.get(j).getState() == OrbElement.STATIONARY) {
							
							// Sets the x co-ordinates of the 6 points.
							stopPoints[0].x = stopPoints[2].x = orbs.get(j).getX() + OrbElement.ORB_SIZE / 2;
							stopPoints[1].x = orbs.get(j).getX() + OrbElement.ORB_SIZE;
							stopPoints[3].x = stopPoints[5].x = orbs.get(j).getX() - OrbElement.ORB_SIZE / 2;
							stopPoints[4].x = orbs.get(j).getX() - OrbElement.ORB_SIZE;
							
							// Sets the y co-ordinates of the 6 points.
							stopPoints[0].y = stopPoints[5].y = orbs.get(j).getY() + ySpacing;
							stopPoints[1].y = stopPoints[4].y = orbs.get(j).getY();
							stopPoints[2].y = stopPoints[3].y = orbs.get(j).getY() - ySpacing;
							
							// Tests to see which of the 6 points are inside of OrbElement i and then places OrbElement i on that point. 
							for(int k = 0; k < stopPoints.length; k++) {
								if(orbs.get(i).contains(stopPoints[k].x, stopPoints[k].y)) {
									orbs.get(i).setStationary(stopPoints[k]);
									break;
								}
							}
						}
						
						if(orbs.get(j).getState() == OrbElement.MOTION) {
						}
					}
				}
			}
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
		case 0: color = Color.RED; break;
		case 1: color = Color.GREEN; break;
		case 2: color = Color.BLUE;	break;
		default: color = Color.BLACK;
		}
		
		// Generates the orb.
		currentOrb = new OrbElement(orbSprite, color);
		
		// Randomly selects which edge the orb should enter the screen from and where on that edge.
		switch(MathUtils.random(4)) {
		
		// Places the orb along the bottom edge.
		case 0: currentOrb.setY(0 - screen.getBottomOffset() - currentOrb.getBounds().height / 2);
			currentOrb.setX(0 - screen.getLeftOffset() - currentOrb.getBounds().width / 2 + MathUtils.random(screen.getVisibleWidth()));
			break;
			
		// Places the orb along the top edge.
		case 1: currentOrb.setY(screen.getVisibleHeight() - screen.getBottomOffset() + currentOrb.getBounds().height / 2);
			currentOrb.setX(0 - screen.getLeftOffset() - currentOrb.getBounds().width / 2 + MathUtils.random(screen.getVisibleWidth()));
			break;
			
		// Places the orb along the left hand edge.
		case 2: currentOrb.setX(0 - screen.getLeftOffset() - currentOrb.getBounds().width / 2);
			currentOrb.setY(0 - screen.getBottomOffset() - currentOrb.getBounds().height / 2 + MathUtils.random(screen.getVisibleHeight()));
			break;
			
		// Places the orb along the right hand edge.
		case 3: currentOrb.setX(screen.getVisibleWidth() - screen.getLeftOffset() - currentOrb.getBounds().width / 2);
			currentOrb.setY(0 - screen.getBottomOffset() - currentOrb.getBounds().height / 2 + MathUtils.random(screen.getVisibleHeight()));
			break;
		}
		
		// Sets the OrbElement into motion and adds it to the orbs array.
		applyForce(currentOrb);
		orbs.add(currentOrb);
	}
	
	// Applies a magnetic force to the given OrbElement based on its distance from on-screen magnets.
	public void applyForce(OrbElement orb) {
		for(int i = 0; i < screen.getMagnets().size; i++) {
			currentForce.set(screen.getMagnets().get(i).getX() - orb.getX(), screen.getMagnets().get(i).getY() - orb.getY());
			currentForce.scl(PlayScreen.MAGNET_CONSTANT / (PlayScreen.MAGNET_CONSTANT + currentForce.len()));
			orb.setMotion(currentForce);
		}
	}

}
