package psyknz.libgdx.games.robotjrpg;

import com.badlogic.gdx.utils.Array;

public class BattleGroup{
	
	public Array<UnitEntity> units;
	
	public BattleGroup() {
		units = new Array<UnitEntity>();
	}
	
	// Returns the number of units in this BattleGroup.
	public int getSize() {
		return units.size;
	}
	
	// Returns the UnitEntity in this BattleGroup at the given index.
	public UnitEntity getUnit(int index) {
		return units.get(index);
	}
}
