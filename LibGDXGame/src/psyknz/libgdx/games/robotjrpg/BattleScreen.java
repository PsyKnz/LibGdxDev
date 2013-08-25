package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public class BattleScreen extends GameScreen implements GameElement, ElementListener {
	
	// References to the Players BattleGroup and the enemy BattleGroup.
	public BattleGroup playerGroup, enemyGroup;
	
	// References to a list of all units in the order they will take their turn and a menu which displays this information.
	public Array<UnitElement> turnList;
	private TurnListMenu turnListMenu;
	
	// The buttons which will be drawn directly to the screen and supporting menus.
	private ButtonElement pause;
	private PauseMenu pauseMenu;
	
	// 1x1 Sprite used to draw basic shapes to the screen.
	private Sprite pixel;
	
	// Fonts used to draw different bits of information to the screen.
	public BitmapFont textFont, healthFont, energyFont;

	// Constructor creates all the objects which are a part of the screen but leaves positioning them to th resize call.
	public BattleScreen(LibGDXGame game) {
		super(game);
		background = Color.WHITE;
		
		// Creates the fonts used to draw information to the screen.
		textFont = new BitmapFont();
		healthFont = new BitmapFont();
		healthFont.setColor(Color.RED);
		energyFont = new BitmapFont();
		energyFont.setColor(Color.CYAN);
		
		// Initialises the player and enemy parties.
		playerGroup = new BattleGroup(this, 4, Color.BLUE, "P-");
		enemyGroup = new BattleGroup(this, 5, Color.DARK_GRAY, "E-");
		
		// Sets the position of the player parties units.
		for(int i = 0; i < playerGroup.getSize(); i++) {
			playerGroup.getUnit(i).x = game.width * 0.75f;
			playerGroup.getUnit(i).y = 25 + 100 * i;
		}
		
		// Sets the position of the enemy parties units.
		for(int i = 0; i < enemyGroup.getSize(); i++) {
			enemyGroup.getUnit(i).y = 25 + 75 * i;
			enemyGroup.getUnit(i).x = game.width * 0.2f + game.width * 0.1f * (i % 2);
		}
		
		// Creates the list of UnitElements which are ordered by when they are expected to take their turn.
		turnList = new Array<UnitElement>();
		
		// Registers the BattleScreen to its own GameElement list.
		elements.add(this);
		
		// Loads the sprites which will be used on this screen.
		Sprite buttonSprite = new Sprite((Texture) game.assets.get("data/ShapeImageTemplate.png"), 0, 64, 64, 64);
		pixel = new Sprite((Texture) game.assets.get("data/ShapeImageTemplate.png"), 32, 32, 1, 1);
		pixel.setColor(0, 0, 0, 0.5f);
		
		// Sets the style preferences for buttons which will be presented on this screen.
		BitmapFont font = new BitmapFont();
		ButtonElement.ButtonStyle style = new ButtonElement.ButtonStyle(this, buttonSprite, 64, 64, font);
		style.borderSprite = pixel;
		
		// Creates a pause button and adds it to the list of GameElements.
		pause = new ButtonElement(this, style, 0, 0);
		pause.xOrig = pause.getBounds().width;
		pause.yOrig = pause.getBounds().height;
		pause.setLabel("Pause");
		elements.add(pause);
		
		// Creates the menu to display while paused.
		pauseMenu = new PauseMenu(this, game.width / 2, game.height / 2);
		
		// Creates the TurnListBar and registers it.
		turnListMenu = new TurnListMenu(this);
		turnListMenu.updateTurnList();
		elements.add(turnListMenu);
	}
	
	/* The resize method is overrided to allow for GUI elements attached to the edges of the screen to have their x and y adjusted
	 *  following a call to resize. */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// The pause button is placed at the top right corner of the screen.
		pause.setX(visibleWidth - leftOffset);
		pause.setY(visibleHeight - bottomOffset);
		
		// The turnListBar is repositioned.
		turnListMenu.updateBounds();
	}
	
	@Override
	public void update(float delta) {}
	
	// Draws all elements to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		
		// Draws the units to the screen.
		playerGroup.draw(batch);
		enemyGroup.draw(batch);
	}
	
	@Override
	public void event(ElementEvent event) {
		
		// If the "Pause" button is touched the PauseMenu is brought up.
		if(event.element == pause) {
			if(event.type == ElementEvent.EventType.TOUCHED) overridingElement = pauseMenu;
		}
	}
	
	// Disposes of all resources loaded by th screen.
	@Override
	public void dispose() {
		super.dispose();
		
		// Disposes of the fonts.
		textFont.dispose();
		healthFont.dispose();
		energyFont.dispose();
	}
	
	//
	private class TurnListMenu extends DockableMenu {
		
		// Sets the constants for drawing the unit icons.
		private float padding = 16, iconWidth = 48, iconHeight = 48;
		
		// Variables used to control horizontal scrolling of the turnList.
		private float maxScroll, currentScroll, xPrev;
		
		/* Constructs the menu so that it sits at the bottom of the screen and has enough space to display slightly scaled down icons
		 *  for units in the turnList. */
		public TurnListMenu(BattleScreen screen) {
			super(screen, "Turn List", DockableMenu.DOCK_SOUTH, 24, 80);
		}
		
		@Override
		public void update(float delta) {
			
			/* If the menu area is selected then the unit list scrolls along the distance the finger was moved during the last render
			 *  call whilst ensuring items on the menu do not go out of bounds. */
			if(menuSelected) {
				currentScroll -= touchX() - xPrev;
				if(currentScroll > maxScroll - visibleWidth) currentScroll = maxScroll - visibleWidth;
				if(currentScroll < 0) currentScroll = 0;
			}
			
			// Runs the standard menu game logic.
			super.update(delta);
			
			// If the user has the menu selected then the current location of the users finger is recorded.
			if(menuSelected) xPrev = touchX();
		}
		
		@Override
		public void draw(SpriteBatch batch) {
			
			// Draws the menu itself.
			super.draw(batch);
			
			// Draws icons for all units in the TurnList in order from left to right.
			for(int i = 0; i < turnList.size; i++) {
				turnList.get(i).faceSprite.setBounds(padding + (padding + iconWidth) * i - currentScroll, bounds.y + padding, iconWidth, iconHeight);
				turnList.get(i).faceSprite.draw(batch);
				turnList.get(i).id.draw(batch, iconWidth / 2 + padding + (padding + iconWidth) * i - currentScroll, bounds.y + padding + iconHeight / 2);
			}
		}
		
		// Creates an updated list of units in order of when they'll take their turn and places it into an array.
		private void updateTurnList() {
			
			// Clears the current turnList and sets the projected turn timers for all units.
			turnList.clear();
			for(int i = 0; i < playerGroup.getSize(); i++) playerGroup.getUnit(i).projTurnTimer = playerGroup.getUnit(i).turnTimer;
			for(int i = 0; i < enemyGroup.getSize(); i++) enemyGroup.getUnit(i).projTurnTimer = enemyGroup.getUnit(i).turnTimer;
			
			// Tracks size of the turnList before starting a loop.
			int listSize;
			
			// Keeps filling the turnList until it has 12 units in it.
			while(turnList.size < 18) {
				listSize = turnList.size;
				
				// Goes through each player unit adding them to the turnList if their turnTimer has counted down.
				for(int i = 0; i < playerGroup.getSize(); i++) {
					
					// If the turnList is currently empty all projected changes to the turnTimer are saved.
					if(listSize == 0) playerGroup.getUnit(i).turnTimer = playerGroup.getUnit(i).projTurnTimer;
					
					// Counts down the units turnTimer and adds it to the turnList if it reaches 0.
					playerGroup.getUnit(i).projTurnTimer -= playerGroup.getUnit(i).speed;
					if(playerGroup.getUnit(i).projTurnTimer <= 0) {
						
						// Resets the units turnTimer.
						playerGroup.getUnit(i).projTurnTimer += UnitElement.TURN_TIMER_MAX;
						
						// Inserts the unit into the turnList in order of how far their turnTimer has counted down.
						for(int p = listSize; p <= turnList.size; p++) {
						    if(p == turnList.size) {
								turnList.add(playerGroup.getUnit(i));
								break;
							}
							else if(playerGroup.getUnit(i).projTurnTimer < turnList.get(p).projTurnTimer) {
								turnList.insert(p, playerGroup.getUnit(i));
								break;
							}
						}
					}
				}

				// Goes through each enemy unit adding them to the turnList if their turnTimer has counted down.
				for(int i = 0; i < enemyGroup.getSize(); i++) {
					
					// If the turnList is currently empty all projected changes to the turnTimer are saved.
					if(listSize == 0) enemyGroup.getUnit(i).turnTimer = enemyGroup.getUnit(i).projTurnTimer;

					// Counts down the units turnTimer and adds it to the turnList if it reaches 0.
					enemyGroup.getUnit(i).projTurnTimer -= enemyGroup.getUnit(i).speed;
					if(enemyGroup.getUnit(i).projTurnTimer <= 0) {

						// Resets the units turnTimer.
						enemyGroup.getUnit(i).projTurnTimer += UnitElement.TURN_TIMER_MAX;

						// Inserts the unit into the turnList in order of how far their turnTimer has counted down.
						for(int p = listSize; p <= turnList.size; p++) {
						    if(p == turnList.size) {
								turnList.add(enemyGroup.getUnit(i));
								break;
							}
							else if(enemyGroup.getUnit(i).projTurnTimer < turnList.get(p).projTurnTimer) {
								turnList.insert(p, enemyGroup.getUnit(i));
								break;
							}
						}
		            }
				}
			}
			
			// Sets the maximum amount of freedom in horizontal scrolling for the turnListBar and resets its current amount of scrolling.
			turnListMenu.maxScroll = turnList.size * (turnListMenu.padding + turnListMenu.iconWidth) + turnListMenu.padding;
			turnListMenu.currentScroll = 0;
		}
	}
}
