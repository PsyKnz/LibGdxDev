package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class EnemyElement extends GameElement {
    
	// List of all enemy objects on the screen.
	private ArrayList<GameElement> enemies;
	
	// Timer used to determine when to spawn a new enemy.
	private float spawnTimer;
	
	public EnemyElement() {
		enemies = new ArrayList<GameElement>();
		
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
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(delta);
		}
	}
	
	public void draw(SpriteBatch batch) {
		// Draws all on-screen enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(batch);
		}
	}
}
