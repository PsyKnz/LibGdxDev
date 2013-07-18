package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class BattleScreen extends GameScreen {
	
	// The element controlling the current game state.
	private BattleScreenElement battleController;
	
	// The BattleGroups of player and enemy units.
	private BattleGroup playerGroup, enemyGroup;
	
	// List of units which are currently in line to take their turn.
	private Array<UnitEntity> activeUnits;
	
	public BattleScreen(LibGDXGame game) {
		super(game);
		
		playerGroup = new BattleGroup();
		enemyGroup = new BattleGroup();
		
		activeUnits = new Array<UnitEntity>();
		
		battleController = new BattleScreenElement();
		elements.add(battleController);
	}
	
	// Helper GameElement which manages the BattleScreens state.
	private class BattleScreenElement implements GameElement {
		
		public void update(float delta) {
			/* If there is currently no activeUnits waiting to take their turn then all units turnTimers ticks down until at least one
			 * units turnTimer reaches 0. */
			if(activeUnits.size <= 0) {
				
				// Loops through the players units.
				for(int i = 0; i < playerGroup.getSize(); i++) {
					playerGroup.getUnit(i).turnTimer -= playerGroup.getUnit(i).speed * delta;
					
					// If the units timer reaches 0 it is inserted into the active units list in order of lowest turnTimers.
					if(playerGroup.getUnit(i).turnTimer <= 0) {
						for(int j = 0; j < activeUnits.size; j++) {
							if (playerGroup.getUnit(i).turnTimer < activeUnits.get(j).turnTimer) {
								activeUnits.insert(j, playerGroup.getUnit(i));
								break;
							}
						}
						activeUnits.add(playerGroup.getUnit(i));
					}
				}
				
				// Loops through the enemy units.
				for(int i = 0; i < enemyGroup.getSize(); i++) {
					enemyGroup.getUnit(i).turnTimer -= enemyGroup.getUnit(i).speed * delta;
					
					// If the units timer reaches 0 it is inserted into the active units list in order of lowest turnTimers.
					if(enemyGroup.getUnit(i).turnTimer <= 0) {
						for(int j = 0; j < activeUnits.size; j++) {
							if (enemyGroup.getUnit(i).turnTimer < activeUnits.get(j).turnTimer) {
								activeUnits.insert(j, enemyGroup.getUnit(i));
								break;
							}
						}
						activeUnits.add(enemyGroup.getUnit(i));
					}
				}
			}
			
			// If there is currently an activeUnit then shit happens.
			else {
				
			}
		}
		
		public void draw(SpriteBatch batch) {}
	}
}
