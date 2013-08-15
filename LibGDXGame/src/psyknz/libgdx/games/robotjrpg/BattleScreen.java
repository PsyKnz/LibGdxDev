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
	
	// The BattleGroups of player and enemy units.
	public BattleGroup playerGroup, enemyGroup;
	
	// List of units which are currently in line to take their turn.
	public Array<UnitElement> turnList;
	
	// The buttons which will be drawn directly to the screen and supporting menus.
	private ButtonElement pause;
	private PauseMenu pauseMenu;
	
	// The TurnListBar which will be displayed at the bottom of the screen.
	private TurnListBar turnListBar;
	
	// 1x1 Sprite used to draw basic shapes to the screen.
	private Sprite pixel;
	
	// Fonts used to draw different bits of information to the screen.
	public BitmapFont textFont, healthFont, energyFont;
	
	public BattleScreen(LibGDXGame game) {
		super(game);
		background = Color.WHITE;
		
		// Creates the fonts used to draw information to the screen.
		textFont = new BitmapFont();
		healthFont = new BitmapFont();
		healthFont.setColor(Color.RED);
		energyFont = new BitmapFont();
		energyFont.setColor(Color.CYAN);
		
		// Initialises the players party and the enemy party.
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
		turnListBar = new TurnListBar();
		updateTurnList();
		elements.add(turnListBar);
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
		turnListBar.updateBounds();
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
		turnListBar.maxScroll = turnList.size * (turnListBar.padding + turnListBar.iconWidth) + turnListBar.padding;
		turnListBar.currentScroll = 0;
	}
	
	//
	private class TurnListBar extends BoundedElement {
		
		// Sprite used to draw the bars background.
		private Sprite barBackground;
		
		// Amount of space there should be between different elements.
		private float padding = 16;
		
		// The width and height of the icon used to represent each unit.
		private float iconWidth = 48, iconHeight = 48;
		
		// Sets the constraints for opening and closing the menu.
		private float minBound, maxBound, pointOfNoReturn;
		
		// Variables used to control horizontal scrolling of the turnList.
		private float maxScroll, currentScroll, xPrev;
		
		// Title of the TurnListBar visible at the top and its bounding box.
		private TextElement title;
		private Rectangle titleBounds;
		
		// Tracks whether or not the menu is selected.
		private boolean titleSelected = false, listSelected = false;
		
		public TurnListBar() {
			
			// Constructs the bounding boxes for the TurnListBar.
			super();
			titleBounds = new Rectangle(0, 0, 0, 24);
			
			// Sets the height of the bar itself and places its y origin at the top.
			bounds.height = padding * 2 + iconHeight;
			yOrig = bounds.height;
			
			// Loads a 1x1 sprite to use for the background and sets it to a semi-transparent black.
			barBackground = new Sprite((Texture) game.assets.get("data/ShapeImageTemplate.png"), 32, 32, 1, 1);
			barBackground.setColor(0, 0, 0, 0.5f);
			
			// Loads the TurnListBar's title and the font for it.
			BitmapFont font = new BitmapFont();
			title = new TextElement("Turn List", font, 0, 0, TextElement.CENTER, TextElement.CENTER);
		}
		
		@Override
		public void update(float delta) {
			
			// If the title bar is currently selected it tracks after the finger.
			if(titleSelected) setY(touchY() - titleBounds.height / 2);
			
			// If the turnList is currently selected it adjusts its scrolling by how much the finger has moved.
			if(listSelected) {
				currentScroll -= touchX() - xPrev;
				if(currentScroll > maxScroll - visibleWidth) currentScroll = maxScroll - visibleWidth;
				if(currentScroll < 0) currentScroll = 0;
			}
			
			// If the user is touching the screen within the title bar it becomes selected.
			if(isTouched() && titleBounds.contains(touchX(), touchY())) titleSelected = true;
			
			// If the user isn't touching the screen then the TurnListBar rolls towards its closest position; open or closed.
			else {
				titleSelected = false;
			    if(getY() > pointOfNoReturn) setY(getY() + bounds.height * delta * 2);
				else setY(getY() - bounds.height * delta * 2);
			}
			
			// If the user is touching the turnListBar it becomes selected and allows them to scroll through the list.
			if(isTouched() && bounds.contains(touchX(), touchY())) {
				listSelected = true;
				xPrev = touchX();
			}
			else listSelected = false;
		}
		
		@Override
		public void draw(SpriteBatch batch) {
			
			// Draws the TurnListBar's background.
			barBackground.setBounds(bounds.x, bounds.y, bounds.width, bounds.height + titleBounds.height);
			barBackground.draw(batch);
			
			// Draws icons for all units in the TurnList in order from left to right.
			for(int i = 0; i < turnList.size; i++) {
				turnList.get(i).faceSprite.setBounds(padding + (padding + iconWidth) * i - currentScroll, bounds.y + padding, iconWidth, iconHeight);
				turnList.get(i).faceSprite.draw(batch);
				turnList.get(i).id.draw(batch, iconWidth / 2 + padding + (padding + iconWidth) * i - currentScroll, bounds.y + padding + iconHeight / 2);
			}
			
			// Draws the TurnListBar's title.
			barBackground.setBounds(titleBounds.x, titleBounds.y, titleBounds.width, titleBounds.height);
			barBackground.draw(batch);
			title.draw(batch);
		}
		
		// Resets the bounding box for the TurnListBar so that it will stretch across the bottom of the screen. Must be called at least once, preferably in the screens resize function.
		public void updateBounds() {
			bounds.width = visibleWidth;
			titleBounds.width = visibleWidth;
			minBound = 0 - bottomOffset;
			maxBound = minBound + bounds.height;
			pointOfNoReturn = minBound + bounds.height / 2;
			setX(0 - leftOffset);
			setY(minBound);
		}
		
		// Overrides setX and setY so that they also shift the bounding box for the title area.
		@Override
		public void setX(float x) {
			super.setX(x);
			
			titleBounds.x = bounds.x;
			title.setX(titleBounds.x + titleBounds.width / 2);
		}
		
		@Override
		public void setY(float y) {
			
			// If the user attempts to drag the turnListBar outside of its min/maxBound it is constrained.
			if(y < minBound) super.setY(minBound);
			else if(y > maxBound) super.setY(maxBound);
			else super.setY(y);
			
			titleBounds.y = getY();
			title.setY(titleBounds.y + titleBounds.height / 2);
		}
	}
}
