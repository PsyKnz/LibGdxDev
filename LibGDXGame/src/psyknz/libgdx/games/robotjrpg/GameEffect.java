package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

// A GameEffect represents something happening in the game which will be processed
public abstract class GameEffect implements GameElement {
    
	// Reference to the effect list this GameEffect has been added to.
	private Array<GameEffect> effectList;
	
	// How long the GameEffect's animation should be played for in seconds.
	private float timer;
	
	public GameEffect(Array<GameEffect> effectList, float timer) {
		this.effectList = effectList;
		this.timer = timer;
	}
	
	@Override
	public void update(float delta) {
		// Counts down the animation timer.
		timer -= delta;
		
		// Once the timer reaches 0 the GameEffect's effect plays and it is removed from the effect list. 
		if(timer <= 0) {
			effect();
			effectList.removeValue(this, false);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {}
	
	public abstract void effect();
}
