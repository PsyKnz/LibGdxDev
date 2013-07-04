package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class ConsoleElement implements GameElement {
    
	// Screen the console is being utilised on.
	private LibGDXGame game;
	
	// Font used to draw text to the console.
	private BitmapFont font;
	
	// Identifier for the screen.
	private TextElement screenLabel;
	
	// The log of events which have been written to the screen.
	private Array<TextElement> log;
	
	// Maximum number of logged events the console can track.
	private int maxLogLength = 100;
	
	// Variables for tracking the current fps.
	private int fps = 0;
	private float deltaCount = 0;
	private int updateCount = 0;
	private String fpsLabel = "FPS: ";
	
	// Whether or not the console should be drawn to the screen.
	public boolean visible = true;
	
	public ConsoleElement(LibGDXGame game) {
		this.game = game;
		
		font = new BitmapFont();
		
		screenLabel = new TextElement(screenID, font, screen.leftOffset, 0 - screen.bottomOffset, TextElement.LEFT, TextElement.BOTTOM);
		
		log = new Array<TextElement>(maxLogLength);
	}
	
	public void update(float delta) {
		// Updates the current fps.
		updateCount += 1;
		deltaCount += delta;
		if(deltaCount >= 1) {
			fps = updateCount;
			deltaCount = 0;
			updateCount = 0;
		}
	}
	
	// Draws all elements which are part of the console.
	public void draw(SpriteBatch batch) {
		if(visible) {
			font.draw(batch, fpsLabel + fps, screen.leftOffset, screen.visibleHeight - screen.bottomOffset);
			
			screenLabel.draw(batch);
			
		    for(int i = 0; i < log.size; i++) {
			    log.get(i).draw(batch);
		    }
		}
	}
	
	// Adds the given message to the log.
	public void write(String msg) {
		for(int i = 0; i < log.size; i++) {
			log.get(i).setY(log.get(i).getY() + 16);
		}
		log.insert(0, new TextElement(msg, font, screen.visibleWidth + screen.leftOffset, 0 - screen.bottomOffset, TextElement.RIGHT, TextElement.BOTTOM));
	}
}
