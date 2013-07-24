package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.GameElement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class BattleGroup implements GameElement {
	
	// Reference to the BattleScreen this unit is attached to.
	private BattleScreen screen;
	
	// List of all units in the BattleGroup and a reference to the unit currently being processed.
	private Array<UnitEntity> units;
	private UnitEntity currentUnit;
	
	public BattleGroup(BattleScreen screen) {
		this.screen = screen;
		
		units = new Array<UnitEntity>();
	}
	
	@Override
	public void update(float delta) {
		
		// Goes through each unit and ticks down their timer.
		for(int i = 0; i < units.size; i++) {
			currentUnit = units.get(i);
			currentUnit.turnTimer -= currentUnit.speed * delta;
			
			// If a units turnTimer goes below 0 then it is added into the BattleScreen's activeUnit list.
			if(currentUnit.turnTimer <= 0) screen.activeUnits.add(currentUnit);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {}
	
	// Returns the number of units in this BattleGroup.
	public int getSize() {
		return units.size;
	}
	
	// Returns the UnitEntity in this BattleGroup at the given index.
	public UnitEntity getUnit(int index) {
		return units.get(index);
	}
}
