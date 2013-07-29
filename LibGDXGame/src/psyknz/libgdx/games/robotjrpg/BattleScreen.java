package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class BattleScreen extends GameScreen implements GameElement, ElementListener {
	
	// The BattleGroups of player and enemy units.
	public BattleGroup playerGroup, enemyGroup;
	
	// List of units which are currently in line to take their turn.
	public Array<BattleUnit> activeUnits;
	
	// List of all effects which currently need to be resolved.
	public Array<GameElement> effectList;
	
	// The buttons which will be drawn directly to the screen.
	private ButtonElement pause, console;
	
	public BattleScreen(LibGDXGame game) {
		super(game);
		
		// Initialises the players party and the enemy party.
		playerGroup = new BattleGroup(this);
		enemyGroup = new BattleGroup(this);
		
		// Creates the list of active BattleUnits and GameEffects to be processed.
		activeUnits = new Array<BattleUnit>();
		effectList = new Array<GameElement>();
		
		// Registers the BattleScreen to its own GameElement list.
		elements.add(this);
		
		// Loads the sprites which will be used on this screen.
		Sprite buttonSprite = new Sprite((Sprite) game.assets.get("data/ShapeImageTemplate.png"), 0, 64, 64, 64);
		
		// Sets the style preferences for buttons which will be presented on this screen.
		BitmapFont font = new BitmapFont();
		ButtonElement.ButtonStyle style = new ButtonElement.ButtonStyle(Color.CYAN, Color.BLUE, Color.GRAY, 64, 64, font);
		
		// Creates a pause button and adds it to the list of GameElements.
		pause = new ButtonElement(this, this, buttonSprite, style, "Pause.", 0, 0);
		elements.add(pause);
		
		// Creates a button to access the console, disables its functionality, and adds it to the list of GameElements.
		console = new ButtonElement(this, this, buttonSprite, style, "Console.", 0, 0);
		console.disabled = true;
		elements.add(console);
	}
	
	/* The resize method is overrided to allow for GUI elements attached to the edges of the screen to have their x and y adjusted
	 *  following a call to resize. */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// The pause button is placed on the bottom left corner of the screen.
		pause.setPos(0 - leftOffset, 0 - bottomOffset);
		
		// The console button is placed on the bottom right corner of the screen.
		console.setPos(visibleWidth + leftOffset, 0 - bottomOffset);
	}
	
	@Override
	public void update(float delta) {
		
		// Any pending effects are processed.
		if(effectList.size > 0) {
			effectList.get(0).update(delta);
		}
		
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
				Array<BattleUnit> orderedActiveUnits = new Array<BattleUnit>();
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
	
	@Override
	public void event(ElementEvent event) {}
}
