package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class BattleScreen extends GameScreen implements GameElement {
	
	// The BattleGroups of player and enemy units.
	public BattleGroup playerGroup, enemyGroup;
	
	// List of units which are currently in line to take their turn.
	public Array<UnitEntity> activeUnits;
	
	// List of all effects which currently need to be resolved.
	public Array<GameElement> effectList;
	
	public BattleScreen(LibGDXGame game) {
		super(game);
		
		playerGroup = new BattleGroup(this);
		enemyGroup = new BattleGroup(this);
		
		activeUnits = new Array<UnitEntity>();
		effectList = new Array<GameElement>();
		
		elements.add(this);
	}
	
	@Override
	public void update(float delta) {
		if(effectList.size > 0) {}
		else if(activeUnits.size > 0) {}
		
		// If there are currently no effects or units pending processing then unit turnTimers tick down.
		else {
			playerGroup.update(delta);
			enemyGroup.update(delta);
			
			// If more than one unit is added to the activeUnit list during the same update call they are sorted.
			if(activeUnits.size > 1) {}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {}
}
