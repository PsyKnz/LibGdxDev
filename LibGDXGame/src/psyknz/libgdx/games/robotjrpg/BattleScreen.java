package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.GameScreen;
import psyknz.libgdx.games.GameElement;
import psyknz.libgdx.games.LibGDXGame;

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
		
		// Any pending effects are processed.
		if(effectList.size > 0) {}
		
		// If there are no pending effects then the next unit in the activeUnit list has its turn processed.
		else if(activeUnits.size > 0) {
			activeUnits.get(0).update(delta);
		}
		
		// If there are currently no effects or units pending processing then unit turnTimers tick down.
		else {
			playerGroup.update(delta);
			enemyGroup.update(delta);
			
			// If more than one unit is added to the activeUnit list during the same update call they are sorted.
			if(activeUnits.size > 1) {
				
				// Creates the ordered list and adds the first unit from the activeUnits list to it.
				Array<UnitEntity> orderedActiveUnits = new Array<UnitEntity>();
				orderedActiveUnits.add(activeUnits.get(0));
				
				// Every additional activeUnit is placed in the orderedActiveUnit list in order of their turnTimer.
				for(int i = 1; i < activeUnits.size; i++) {
					for(int j = 0; j < orderedActiveUnits.size; j++) {
						if(activeUnits.get(i).turnTimer < orderedActiveUnits.get(j).turnTimer) {
							orderedActiveUnits.insert(j, activeUnits.get(i));
							break;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {}
}
