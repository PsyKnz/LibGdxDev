package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class EnemyElement implements GameElement {
    
	// List of all enemy objects on the screen.
	private Array<GameElement> enemies;
	
	// Timer used to determine when to spawn a new enemy.
	private float spawnTimer;
	
	public EnemyElement() {
		enemies = new Array<GameElement>();
		
		spawnTimer = MathUtils.random();
	}
	
	public void update(float delta) {
		spawnTimer -= delta;
		if(spawnTimer <= 0) {
			// SPAWN NEW RANDOM ENEMY.
			
			// Reset the spawnTimer.
			spawnTimer = MathUtils.random();
		}
		
		// Update game logic for all on-screen enemies.
		for(int i = 0; i < enemies.size; i++) {
			enemies.get(i).update(delta);
		}
	}
	
	public void draw(SpriteBatch batch) {
		// Draws all on-screen enemies
		for(int i = 0; i < enemies.size; i++) {
			enemies.get(i).draw(batch);
		}
	}
}
