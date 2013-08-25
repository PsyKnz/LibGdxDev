package psyknz.libgdx.games;

import psyknz.libgdx.games.robotjrpg.ButtonElement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class TestScreen extends GameScreen implements ElementListener {
	
	// Reference to the font used to draw ButtonElement labels.
	private BitmapFont labelFont;
	
	// Individual references to all of the on-screen buttons.
	private ButtonElement magnetris;
	
	// Array containing all of the ButtonElements.
	private Array<ButtonElement> buttons;
	
	// Builds a simple screen with buttons for loading other screens currently in testing.
	public TestScreen(LibGDXGame game) {
		super(game);
		
		// Sets up the resources used to construct the ButtonElements.
		Sprite sprite = new Sprite((Texture) game.assets.get("data/ShapeImageTemplate.png"), 0, 64, 64, 64);
		labelFont = new BitmapFont();
		ButtonElement.ButtonStyle style = new ButtonElement.ButtonStyle(this, sprite, 64, 64, labelFont);
		
		// Sets up the Magnetris testing button.
		magnetris = new ButtonElement(this, style, 0, 0);
		magnetris.setLabel("Magnetris");
		buttons.add(magnetris);
		
		elements.addAll(buttons);
	}
	
	// Positions all of the ButtonElements after the screen is sized.
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// Scrolls through all ButtonElements placing them on the screen going left to right and moving up when running out of space.
		int yVal = 0;
		for(int xVal = 0; xVal < buttons.size; xVal++) {
			if(xVal * 64 - yVal * (game.width - 96) > game.width - 96) yVal += 1;
			buttons.get(xVal).setX(16 + xVal * 64);
			buttons.get(xVal).setY(16 + yVal * 96);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		labelFont.dispose();
	}
	
	// Processes ButtonElement events.
	public void event(ElementEvent event) {
		
		// Loads the Magnetris PlayScreen currently in testing.
		if(event.element == magnetris) setScreen(new psyknz.libgdx.games.magnetris.PlayScreen(game));
	}
}
