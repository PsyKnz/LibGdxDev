package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.GameElement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class BattleUnit implements GameElement {
	
	// Maximum number of LoadoutEntity's a UnitEntity can hold.
	public static final int MAX_LOADOUT = 12;
	
	// The phases of a UnitEntity's turn.
	public static final int UNIT_START = 0;
	public static final int UNIT_ACTION = 1;
	public static final int UNIT_END = 2;
	
	// References to the UnitEntity's specific pilot, chasis, legs, arms and loadout.
	private PilotEntity pilot;
	private PartEntity chasis, legs, arms;
	private Array<LoadoutEntity> loadout;
	
	// Key stats for the UnitEntity.
	public int speed, energy, armor;
	
	// How long the unit has to wait before it can act. (1000 = 1 second).
	public float turnTimer = 1000;
	
	// The current phase the turn is in.
	private int currentPhase = UNIT_START;

	public BattleUnit() {
		loadout = new Array<LoadoutEntity>(MAX_LOADOUT);
	}
	
	@Override
	public void update(float delta) {
		if(currentPhase == UNIT_START) {}
		
		else if(currentPhase == UNIT_ACTION) {}
		
		else if(currentPhase == UNIT_END) {}
	}
	
	@Override
	public void draw(SpriteBatch batch) {}
	
	public void draw(SpriteBatch batch, float x, float y) {}

}
