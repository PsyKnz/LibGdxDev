package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UnitElement implements GameElement {

	// Value a units turnTimer is reset to after taking a turn.
	public static final int TURN_TIMER_MAX = 1000;
	
	// Sprite used to draw the unit and TextElement used to identify it.
	public Sprite faceSprite;
	public TextElement id;
	
	// Position of the unit on the screen, set by formation.
	public float x = 0, y = 0;
	
	// Unit stats.
	public int speed;
	
	// turnTimer to keep track of when the unit takes its turn and a projected turnTimer for estimating turn order.
	public int turnTimer = TURN_TIMER_MAX;
	public int projTurnTimer;
	
	public UnitElement(Sprite faceSprite, int speed, String id, BitmapFont font) {
		this.faceSprite = faceSprite;
		this.speed = speed;
		this.id = new TextElement(id, font, x, y, TextElement.CENTER, TextElement.BOTTOM);
	}
	
	@Override
	public void update(float delta) {}
	
	// Draws the unit to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		faceSprite.setBounds(x - 32, y, 64, 64);
		faceSprite.draw(batch);
		id.draw(batch, x, y + 8);
	}
}
