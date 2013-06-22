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
	private Array<ShapeElement> enemies;
	private Array<ShapeElement> collidedEnemies;
	
	// Size of the enemy elements in in-game units.
	private final int enemySize = 24;
	
	// Timer used to determine when to spawn a new enemy.
	private float spawnTimer;
	private float maxSpawnTime = 2f;
	private float minSpawnTime = 0.5f;
	
	public EnemyElement(PlayScreen screen, Sprite[] shapes) {
		this.screen = screen;
		this.shapes = shapes;
		
		enemies = new Array<ShapeElement>();
		collidedEnemies = new Array<ShapeElement>();
		
		spawnTimer = MathUtils.random(maxSpawnTime - minSpawnTime) + minSpawnTime;
	}
	
	@Override
	public void update(float delta) {
		spawnTimer -= delta;
		if(spawnTimer <= 0) {
			enemies.add(new ShapeElement(screen, shapes[0], Color.GREEN,
			                             (int) (MathUtils.random(screen.arena.bounds.width - enemySize) + screen.arena.bounds.x + enemySize / 2),
										 (int) (MathUtils.random(screen.arena.bounds.height - enemySize) + screen.arena.bounds.y + enemySize / 2),
										 enemySize));

			// Reset the spawnTimer.
			spawnTimer = MathUtils.random(maxSpawnTime - minSpawnTime) + minSpawnTime;
		}
		
		// Update game logic for all on-screen enemies.
		for(int i = 0; i < enemies.size; i++) {
			enemies.get(i).update(delta);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// Draws all on-screen enemies
		for(int i = 0; i < enemies.size; i++) {
			enemies.get(i).draw(batch);
		}
	}
	
	public Array<ShapeElement> testCollision(Rectangle bounds) {
		collidedEnemies.clear();
		
		for(int i = 0; i < enemies.size; i++) {
			if(enemies.get(i).getBounds().overlaps(bounds)) {
			    collidedEnemies.add(enemies.get(i));
			}
		}
		
		if(collidedEnemies.size == 0) return null;
		else return collidedEnemies;
	}
	
	// 
	public void destroyElement(ShapeElement element) {
		enemies.removeValue(element, false);
	}
}
