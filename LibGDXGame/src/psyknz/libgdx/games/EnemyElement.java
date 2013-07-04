package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class EnemyElement implements GameElement {
    // Reference to the screen the enemies are being drawn on.
	private PlayScreen screen;
	
	// Array of sprites used to generate the enemies.
	private Sprite[] shapes;
	
	// List of all enemy objects on the screen.
	public Array<ShapeElement> elements;
	public Array<ShapeElement> collidedElements;
	
	// Size of the enemy elements in in-game units.
	private final int enemySize = 24;
	
	// Timer used to determine when to spawn a new enemy.
	private float spawnTimer;
	private float maxSpawnTime = 2f;
	private float minSpawnTime = 0.5f;
	
	public EnemyElement(PlayScreen screen, Sprite[] shapes) {
		this.screen = screen;
		this.shapes = shapes;
		
		elements = new Array<ShapeElement>();
		collidedElements = new Array<ShapeElement>();
		
		spawnTimer = MathUtils.random(maxSpawnTime - minSpawnTime) + minSpawnTime;
	}
	
	@Override
	public void update(float delta) {
		spawnTimer -= delta;
		if(spawnTimer <= 0) {
			elements.add(new ShapeElement(screen, shapes[0], Color.GREEN,
			                             (int) (MathUtils.random(screen.arena.bounds.width - enemySize) + screen.arena.bounds.x + enemySize / 2),
										 (int) (MathUtils.random(screen.arena.bounds.height - enemySize) + screen.arena.bounds.y + enemySize / 2),
										 enemySize));

			// Reset the spawnTimer.
			spawnTimer = MathUtils.random(maxSpawnTime - minSpawnTime) + minSpawnTime;
		}
		
		// Update game logic for all on-screen enemies.
		for(int i = 0; i < elements.size; i++) {
			elements.get(i).update(delta);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// Draws all on-screen enemies
		for(int i = 0; i < elements.size; i++) {
			elements.get(i).draw(batch);
		}
	}
	
	public ShapeElement testCollision(Rectangle bounds) {
		for(int i = 0; i < elements.size; i++) {
			if(elements.get(i).getBounds().overlaps(bounds)) {
				return elements.get(i);
			}
		}
		return null;
	}
	
	// Removes the indicated element from the current array of enemy elements.
	public void destroyElement(ShapeElement element) {
		elements.removeValue(element, false);
	}
}
