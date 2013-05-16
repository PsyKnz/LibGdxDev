package psyknz.libgdx.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameButton implements GameElement {
	
	// Reference to the listener this button reports to and the id the object passes on and draws onto itself.
	private ElementListener parent;
	private CharSequence id;
	public Rectangle bounds;
	
	// Texture regions for different button states.
	private TextureRegion activeTex;
	private TextureRegion inactiveTex;
	private TextureRegion selectedTex;
	
	// Button font information.
	private BitmapFont font;
	private float scale;
	private Color textColor;
	private BitmapFont.TextBounds textBounds;
	
	// Status variables, track the current state of the button.
	private boolean selected = false;
	public boolean active = true;
	
	/* When a button is created it requires a unique identifier, a position, a reference
	 * to the object listening for its state to change and a reference to the ButtonPrefs object
	 * which stores the settings which should be shared across the buttons being created. */
	public GameButton(String id, int x, int y, ButtonPrefs prefs, ElementListener parent) {
		this.parent = parent;
		this.id = id;
		
		bounds = new Rectangle(x, y, prefs.width, prefs.height);
		activeTex = prefs.activeTex;
		inactiveTex = prefs.inactiveTex;
		selectedTex = prefs.selectedTex;
		font = prefs.font;
		scale = font.getScaleX();
		textColor = font.getColor();
		textBounds = font.getBounds(id);
	}
	
	/* The update method polls the Gdx input device for whether the screen is being touched. If it
	 * is then the position of the finger is determined. If it is within the button then the button
	 * is currently selected. As soon as the finger is lifted off of the screen, if the button was
	 * selected it will inform the listener that it has now been touched. */
	public void update(float delta) {
		if(Gdx.input.isTouched()) {
			if(bounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
				selected = true;
			}
			else {
				selected = false;
			}
		}
		else if(selected) parent.action(id.toString());
	}
	
	// Draws the button. The texture used depends on the current state of the button.
	public void draw(SpriteBatch batch) {
        if(active) {
			if(selected) {
				batch.draw(selectedTex, bounds.x - bounds.width / 2, bounds.y + bounds.height / 2, bounds.width, bounds.height);
				font.setScale(scale);
				font.setColor(textColor);
				font.draw(batch, id, bounds.x - textBounds.width / 2, bounds.y + textBounds.height / 2);
			}
			else {
				batch.draw(activeTex, bounds.x - bounds.width / 2, bounds.y + bounds.height / 2, bounds.width, bounds.height);
				font.setScale(scale);
				font.setColor(textColor);
				font.draw(batch, id, bounds.x - textBounds.width / 2, bounds.y + textBounds.height / 2);
			}
		}
		else {
			batch.draw(inactiveTex, bounds.x - bounds.width / 2, bounds.y + bounds.height / 2, bounds.width, bounds.height);
			font.setScale(scale);
			font.setColor(textColor);
			font.draw(batch, id, bounds.x - textBounds.width / 2, bounds.y + textBounds.height / 2);
		}
	}
	
	// Helper object, stores shared preferences for all button objects.
	public class ButtonPrefs {		
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
