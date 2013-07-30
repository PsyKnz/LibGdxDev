package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class ButtonElement implements GameElement {
	
	// Reference to the GameScreen this button is attached to and the object which is listening to this button.
	private GameScreen screen;
	private ElementListener listener;
	
	// The Sprite being used to draw this button.
	private Sprite sprite;
	
	// The ButtonElement's label and its tooltip. Displayed while the button is selected.
	private TextElement label;
	private TextElement tooltip = null;
	
	// The current preferences for how the button should be displayed on screen.
	private ButtonStyle style;
	
	// The bounding box for the ButtonElement.
	private Rectangle bounds;
	
	// Whether the button is currently visible on screen, whether it is currently disabled and if the user currently has it selected.
	public boolean visible = true;
	public boolean disabled = false;
	private boolean selected = false;
	
	public ButtonElement(GameScreen screen, ElementListener listener, Sprite sprite, ButtonStyle style, String label, float x, float y) {
		this.screen = screen;
		this.listener = listener;
		this.sprite = sprite;
		this.style = style;
		
		// Creates the TextElement which will display the buttons label.
		this.label = new TextElement(label, style.font, 0, 0, TextElement.CENTER, TextElement.BOTTOM);
		
		// Creates the ButtonElements bounding box.
		bounds = new Rectangle(x, y, style.width, style.height);
	}
	
	@Override
	public void update(float delta) {
		if(visible) {
			/* If the user is touching the screen and their finger is inside of the buttons bounding box then the buttons becomes
			 *  selected. If the users finger is outsie of the buttons bounding box then the button becomes de-selected. */
			if(screen.isTouched()) {
				if(bounds.contains(screen.touchX(), screen.touchY())) selected = true;
				else selected = false;
			}
			
			/* If a finger is not currently touching the screen but this button is currently selected (indicating that the finger was
			 *  lifted off of the screen while hovering over this button) then the button is set back to not being selected and the
			 *  buttons listener is informed that this button has been intentionally touched by the user. If the button is disabled then
			 *  the listener is told nothing. */
			else if(selected) {
				selected = false;
				if(!disabled) listener.event(new ElementEvent(this, ElementEvent.EventType.TOUCHED));
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(visible) {
			// If the button is currently visible then its sprite is aligned with the buttons position.
			sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			
			// Sets the color to use to draw the sprite considering whether it is disabled or selected.
			if(disabled) sprite.setColor(style.disabledColor);
			else if(selected) sprite.setColor(style.selectedColor);
			else sprite.setColor(style.buttonColor);
			
			// Draws the button to the screen.
			sprite.draw(batch);
			
			/* If the ButtonElement is currently selected then its label is drawn to the screen as well as the buttons tooltip provided
			 *  it currently has one. */
			if(selected) {
				label.draw(batch, bounds.x - bounds.width / 2, bounds.y);
				if(tooltip != null) {}
			}
		}
	}
	
	// Sets the x, y co-ordinates of the button.
	public void setPos(float x, float y) {
		bounds.x = x;
		bounds.y = y;
	}
	
	// Sets the tooltip to display for this button while it is selected.
	public void setTooltip(String tooltip) {
		if(this.tooltip != null) removeTooltip();
		this.tooltip = new TextElement(tooltip, style.font, 0, 0, true, 100f, TextElement.CENTER, TextElement.BOTTOM);
	}
	
	// Removes the buttons tooltip.
	public void removeTooltip() {
		tooltip = null;
	}
	
	// A ButtonStyle object stores a set of style properties which are shared across a number of buttons.
	public static class ButtonStyle {
		
		// The colors used to draw the button when not selected, selected, and disabled.
		public Color buttonColor;
		public Color selectedColor;
		public Color disabledColor;
		
		// The width and height of the buttons.
		public float width;
		public float height;
		
		// The font to use when drawing tool tips. Cannot be altered once set.
		private BitmapFont font;
		
		public ButtonStyle(Color buttonColor, Color selectedColor, Color disabledColor, float width, float height, BitmapFont font) {
			this.buttonColor = buttonColor;
			this.selectedColor = selectedColor;
			this.disabledColor = disabledColor;
			this.width = width;
			this.height = height;
			this.font = font;
		}
	}
}