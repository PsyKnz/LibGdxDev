package psyknz.libgdx.games.robotjrpg;

import com.badlogic.gdx.utils.Array;

public class UnitEntity {
	
	public static final int MAX_LOADOUT = 12;
	
	private PilotEntity pilot;
	private PartEntity chasis, legs, arms;
	private Array<LoadoutEntity> loadout;
	
	public int speed, energy, armor;
	
	public float turnTimer = 1000;

	public UnitEntity() {
		loadout = new Array<LoadoutEntity>(MAX_LOADOUT);
	}

}
