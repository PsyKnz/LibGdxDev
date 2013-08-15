package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.GameElement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class BattleGroup implements GameElement {
	
	// Reference to the BattleScreen this unit is attached to.
	private BattleScreen screen;
	
	// List of all units in the BattleGroup and a reference to the unit currently being processed.
	private Array<UnitElement> units;
	
	public BattleGroup(BattleScreen screen, int numUnits, Color unitColor, String idPrefix) {
		this.screen = screen;
		
		// Sets default dprites to use for the unit and its face.
		Sprite faceSprite = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 64, 64, 64, 64);
		faceSprite.setColor(unitColor);
		
		// Creates placeholder units with random speeds.
		units = new Array<UnitElement>(numUnits);
		for(int i = 0; i < numUnits; i++) units.add(new UnitElement(faceSprite, MathUtils.random(50) + 50, idPrefix + "U " + i, screen.textFont));
	}
	
	@Override
	public void update(float delta) {}
	
	@Override
	public void draw(SpriteBatch batch) {
		// Draws the units to screen.
		for(int i = 0; i < units.size; i++) {
			units.get(i).draw(batch);
		}
	}
	
	// Returns the number of units in this BattleGroup.
	public int getSize() {
		return units.size;
	}
	
	// Returns the UnitEntity in this BattleGroup at the given index.
	public UnitElement getUnit(int index) {
		return units.get(index);
	}
}
