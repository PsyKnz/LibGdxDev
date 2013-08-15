package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PauseMenu extends BoundedElement implements ElementListener {
	
	// Reference to the GameScreen the PauseMenu is attached to.
	private GameScreen screen;
	
	// The buttons which will be displayed in the menu.
	private ButtonElement resume, options, saveQuit, quit;
	
	// The sprite used to draw the PauseMenu's background.
	private Sprite background;
	
	public PauseMenu(GameScreen screen, float x, float y) {
		super();
		this.screen = screen;
		
		// Loads a 1x1 pixel sprite to use to draw the background and sets it to transparent black.
		background = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 32, 96, 1, 1);
		background.setColor(0, 0, 0, 0.75f);
		
		// Sets the style for the buttons on the PauseMenu and loads their assets.
		Sprite buttonSprite = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 0, 64, 64, 64);
		BitmapFont font = new BitmapFont();
		ButtonElement.ButtonStyle style = new ButtonElement.ButtonStyle(screen, buttonSprite, 64, 64, font);
		style.borderSprite = background;
		
		// Sets how much space there should be between elements in the menu.
		float padding = 32;
		
		// Sets the size of the menu to hold all 4 buttons in a row with adequate padding around them.
		bounds.width = style.width * 4 + padding * 5;
		bounds.height = style.height + padding * 2;
		
		// Centers the PauseMenu's origin and positions it at the given co-ordinates.
		xOrig = bounds.width / 2;
		yOrig = bounds.height / 2;
		setX(x);
		setY(y);
		
		// Loads the "Resume" button.
		resume = new ButtonElement(this, style, bounds.x + padding, bounds.y + padding);
		resume.setLabel("Resume");
		
		// Loads the "Options" button.
		options = new ButtonElement(this, style, bounds.x + padding * 2 + style.width, bounds.y + padding);
		options.setLabel("Options");
		options.disabled = true;
		
		// Loads the "Save and Quit" button.
		saveQuit = new ButtonElement(this, style, bounds.x + padding * 3 + style.width * 2, bounds.y + padding);
		saveQuit.setLabel("Save and Quit");
		saveQuit.disabled = true;
		
		// Loads the "Quit" button.
		quit = new ButtonElement(this, style, bounds.x + padding * 4 + style.width * 3, bounds.y + padding);
		quit.setLabel("Quit");
		quit.disabled = true;
	}
	
	@Override
	public void update(float delta) {
		resume.update(delta);
		options.update(delta);
		saveQuit.update(delta);
		quit.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		// Draws the background for the PauseMenu.
		background.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		background.draw(batch);
		
		// Draws the PauseMenu's buttons.
		resume.draw(batch);
		options.draw(batch);
		saveQuit.draw(batch);
		quit.draw(batch);
	}
	
	@Override
	public void event(ElementEvent event) {
		
		// If the "Resume" button is pressed the PauseMenu closes.
		if(event.element == resume) {
			if(event.type == ElementEvent.EventType.TOUCHED) screen.overridingElement = null;
		}
	}
}
