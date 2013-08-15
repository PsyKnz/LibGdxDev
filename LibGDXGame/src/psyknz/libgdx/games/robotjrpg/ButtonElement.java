package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class ButtonElement extends BoundedElement {
	
	// Reference to the ElementListener this ButtonElement reports to.
	private ElementListener listener;
	
	// The Sprite being used to draw this button and the decal yo draw over it.
	public Sprite decal = null;
	
	// The ButtonElement's label and its tooltip. Displayed while the button is selected.
	private TextElement label = null;
	
	// The current preferences for how the button should be displayed on screen.
	private ButtonStyle style;
	
	// Whether the button is currently visible on screen, whether it is currently disabled and if the user currently has it selected.
	public boolean visible = true;
	public boolean disabled = false;
	private boolean selected = false;
	
	public ButtonElement(ElementListener listener, ButtonStyle style, float x, float y) {
		super(x, y, style.width, style.height);
		
		this.listener = listener;
		this.style = style;
	}
	
	@Override
	public void update(float delta) {
		if(visible) {
			/* If the user is touching the screen and their finger is inside of the buttons bounding box then the buttons becomes
			 *  selected. If the users finger is outsie of the buttons bounding box then the button becomes de-selected. */
			if(style.screen.isTouched()) {
				if(bounds.contains(style.screen.touchX(), style.screen.touchY())) {
					selected = true;
				}
				else {
					selected = false;
				}
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
			style.sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			
			// Sets the color to use to draw the sprite considering whether it is disabled or selected.
			if(disabled) style.sprite.setColor(style.disabledColor);
			else if(selected) style.sprite.setColor(style.selectedColor);
			else style.sprite.setColor(style.buttonColor);
			
			// Draws the button to the screen and its decal provided it has one..
			style.sprite.draw(batch);
			if(decal != null) {
				decal.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
				decal.draw(batch);
			}
			
			/* If the ButtonElement is currently selected then its label is drawn to the screen as well as the buttons tooltip provided
			 *  it currently has one and the ButtonElement has been held down for long enough. */
			if(selected) {
				if(label != null) {
					if(style.borderSprite != null) {
						style.borderSprite.setBounds(label.getBounds().x - style.labelBorder, label.getBounds().y - (style.labelBorder + 1), label.getBounds().width + style.labelBorder * 2, label.getBounds().height + style.labelBorder * 2);
						style.borderSprite.draw(batch);
					}
					label.draw(batch);
				}
			}
		}
	}
	
	// Sets the ButtonElement's label which is displayed when the button is hovered over.
	public void setLabel(String label) {
		
		// If the label is blank or null then the label becomes null.
		if(label != null && label != "") {
			this.label = new TextElement(label, style.font, bounds.x + bounds.width / 2, bounds.y + bounds.height + style.labelSpace, TextElement.CENTER, TextElement.BOTTOM);
		}
		else this.label = null;
	}
	
	// Overrides setX to allow for repositioning the label.
	@Override
	public void setX(float x) {
		super.setX(x);
		if(label != null) label.setX(bounds.x + bounds.width / 2);
	}
	
	// Overrides setY ti allow for repositioning the label.
	@Override
	public void setY(float y) {
		super.setY(y);
		if(label != null) label.setY(bounds.y + bounds.height + style.labelSpace);
	}
	
	// Returns the ButtonStyle used when generating this button.
	public ButtonStyle getStyle() {
		return style;
	}
	
	// A ButtonStyle object spores a set of style properties which are shared across a number of buttons.
	public static class ButtonStyle {
		
		// Reference to the GameScreen ButtonElements using this ButtonStyle are attached to.
		private GameScreen screen;
		
		// Reference to the sprite used to draw the button.
		private Sprite sprite;
		
		// The colors used to draw the button when not selected, selected, and disabled.
		public Color buttonColor = Color.CYAN;
		public Color selectedColor = Color.BLUE;
		public Color disabledColor = Color.GRAY;
		
		// The width and height of the buttons.
		public float width, height, tooltipWidth;
		
		// The font to use when drawing tool tips. Cannot be altered once set.
		private BitmapFont font;
		
		// How much distance the label should have from the button and its background.
		public float labelSpace = 5;
		public float labelBorder = 3;
		
		// Sprite used to draw the labels border.
		public Sprite borderSprite = null;
		
		public ButtonStyle(GameScreen screen, Sprite sprite, float width, float height, BitmapFont font) {
			this.screen = screen;
			this.sprite = sprite;
			this.width = width;
			this.height = height;
			this.font = font;
		}
	}
}
