package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class ConsoleElement implements GameElement {
    
	// Screen the console is being utilised on.
	private LibGDXGame game;
	
	// Font used to draw text to the console.
	private BitmapFont font;
	
	// The log of events which have been written to the screen.
	private Array<TextElement> log;
	
	// Maximum number of logged events the console can track.
	private int maxLogLength = 100;
	
	// Variables for tracking the current fps.
	private float fps, deltaCount = 0;
	private int updateCount = 0;
	private String fpsLabel = "FPS: ";
	
	// Whether or not the console should be drawn to the screen.
	public boolean visible = true;
	
	public ConsoleElement(LibGDXGame game) {
		this.game = game;
		
		font = new BitmapFont();
		
		log = new Array<TextElement>(maxLogLength);
	}
	
	public void update(float delta) {
		// Updates the current fps by looking at how many times render has been called in the last second.
		updateCount += 1;
		deltaCount += delta;
		if(deltaCount >= 1) {
			fps = updateCount / deltaCount;
			deltaCount = 0;
			updateCount = 0;
		}
	}
	
	// Draws all elements which are part of the console.
	public void draw(SpriteBatch batch) {
		if(visible) {
			// Draws the current FPS to the screen.
			font.draw(batch, fpsLabel + fps, game.getScreen().leftOffset, game.getScreen().visibleHeight - game.getScreen().bottomOffset);
			
			// Draws the GameScreens ID to the screen.
			font.draw(batch, game.getScreen().toString(), game.getScreen().leftOffset, 16 - game.getScreen().bottomOffset);
			
			// Draws all logged messages to the screen, starting with the latest at the bottom of the screen.
		    for(int i = 0; i < log.size; i++) {
			    log.get(i).draw(batch, game.getScreen().visibleWidth + game.getScreen().leftOffset, 0 - game.getScreen().bottomOffset + i * 18 + 4);
		    }
		}
	}
	
	// Adds the given message to the log.
	public void log(String msg) {		
		log.insert(0, new TextElement(msg + " <<", font, 0, 0, TextElement.RIGHT, TextElement.BOTTOM));
	}
}
