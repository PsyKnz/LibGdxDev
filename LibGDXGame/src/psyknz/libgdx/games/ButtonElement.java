package psyknz.libgdx.games;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ButtonElement implements GameElement {
	
	private GameScreen screen;
	
	// Reference to the listener this button reports to and the id the object passes on and draws onto itself.
	private ElementListener listener;
	private TextElement label;
	private Rectangle bounds;
	
	// Texture regions for different button states.
	private TextureRegion activeTex;
	private TextureRegion inactiveTex;
	private TextureRegion selectedTex;
	
	// Status variables, track the current state of the button.
	private boolean selected = false;
	public boolean active = true;
	
	/* When a button is created it requires a unique identifier, a position, a reference
	 * to the object listening for its state to change and a reference to the ButtonPrefs object
	 * which stores the settings which should be shared across the buttons being created. */
	public ButtonElement(String label, int x, int y, ButtonPrefs prefs, ElementListener listener, GameScreen screen) {
		this.listener = listener;
		this.label = new TextElement(label, prefs.font, x, y, TextElement.CENTER, TextElement.CENTER);
		bounds = new Rectangle(x - prefs.width / 2, y - prefs.height / 2, prefs.width, prefs.height);
		activeTex = prefs.activeTex;
		inactiveTex = prefs.inactiveTex;
		selectedTex = prefs.selectedTex;
		
		this.screen = screen;
	}
	
	/* The update method polls the Gdx input device for whether the screen is being touched. If it
	 * is then the position of the finger is determined. If it is within the button then the button
	 * is currently selected. As soon as the finger is lifted off of the screen, if the button was
	 * selected it will inform the listener that it has now been touched. */
	public void update(float delta) {
		if(screen.isTouched()) {
			if(bounds.contains(screen.touchX(), screen.touchY())) {
				selected = true;
			}
			else {
				selected = false;
			}
		}
		else if(selected) listener.action(label.getText());
	}
	
	// Draws the button. The texture used depends on the current state of the button.
	public void draw(SpriteBatch batch) {
        if(active) {
			if(selected) {
				batch.draw(selectedTex, bounds.x, bounds.y, bounds.width, bounds.height);
			}
			else {
				batch.draw(activeTex, bounds.x, bounds.y, bounds.width, bounds.height);
			}
		}
		else {
			batch.draw(inactiveTex, bounds.x, bounds.y, bounds.width, bounds.height);
		}
		label.draw(batch);
	}
	
	// Helper object, stores shared preferences for all button objects.
	public static class ButtonPrefs {		
		/* All preference variables are intended for direct manipulation, however the effects
		 * of that manipulation will only be visible to buttons created after the changes. 
		 * The values passed in as texture reference variables are indexed values for some form of
		 * texture manager. The manager should be a static object created at the start of the game,
		 * such as an AssetManager, whish is known to the listener. */
		public int width;
		public int height;
		
		// May look at changing tgese to int references.
		public TextureRegion activeTex;
		public TextureRegion inactiveTex;
		public TextureRegion selectedTex;
		
		// All font information is pulled directly from the font itself when a button is constructed.
		public BitmapFont font;
		
		public ButtonPrefs(int width, int height, TextureRegion activeTex, TextureRegion inactiveTex, TextureRegion selectedTex, BitmapFont font) {
			this.width = width;
			this.height = height;
			
			this.activeTex = activeTex;
			this.inactiveTex = inactiveTex;
			this.selectedTex = selectedTex;
			
			this.font = font;
		}
	}
}
