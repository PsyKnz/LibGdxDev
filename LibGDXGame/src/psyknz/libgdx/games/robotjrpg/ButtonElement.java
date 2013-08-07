package psyknz.libgdx.games.robotjrpg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class ButtonElement extends BoundedElement {
	
	// Default length of time a button needs to be held down for its tooltip to be displayed.
	public static final float TOOLTIP_DELAY = 0.75f;
	
	// Reference to the ElementListener this ButtonElement reports to.
	private ElementListener listener;
	
	// The Sprite being used to draw this button and the decal yo draw over it.
	public Sprite decal = null;
	
	// The ButtonElement's label and its tooltip. Displayed while the button is selected.
	private TextElement label = null;
	private TextElement tooltip = null;
	
	// The current preferences for how the button should be displayed on screen.
	private ButtonStyle style;
	
	// Whether the button is currently visible on screen, whether it is currently disabled and if the user currently has it selected.
	public boolean visible = true;
	public boolean disabled = false;
	private boolean selected = false;
	
	// How long the ButtonElement has to be held for before the tooltip is displayed
	private float tooltipTimer = TOOLTIP_DELAY;
	
	public ButtonElement(ElementListener listener, ButtonStyle style, float x, float y) {
		super(0, 0, style.width, style.height);
		setX(x);
		setY(y);
		
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
					tooltipTimer -= delta;
				}
				else {
					selected = false;
					tooltipTimer = TOOLTIP_DELAY;
				}
			}
			
			/* If a finger is not currently touching the screen but this button is currently selected (indicating that the finger was
			 *  lifted off of the screen while hovering over this button) then the button is set back to not being selected and the
			 *  buttons listener is informed that this button has been intentionally touched by the user. If the button is disabled then
			 *  the listener is told nothing. */
			else if(selected) {
				selected = false;
				tooltipTimer = TOOLTIP_DELAY;
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
				if(tooltipTimer <= 0 && tooltip != null) {
					tooltip.draw(batch);
					if(label != null) label.draw(batch, tooltip.getBounds().x, tooltip.getBounds().y + tooltip.getBounds().height);
				}
				else if(label != null) {
					label.draw(batch, bounds.x + bounds.width / 2, bounds.y + bounds.height);
				}
			}
		}
	}
	
	// Sets the x co-ordinate of the button and moves its tooltip appropriately if it has one.
	@Override
	public void setX(float x) {
		super.setX(x);
		if(tooltip != null) {
			tooltip.setX(bounds.x + bounds.width / 2);
			
			// If the tooltip would vanish off the right hand side of the screen it is set to touch the right hand edge.
			if(tooltip.getBounds().x + tooltip.getBounds().width > style.screen.getVisibleWidth() - style.screen.getLeftOffset())
				tooltip.setX(style.screen.getVisibleWidth() - style.screen.getLeftOffset() - tooltip.xOrig);
				
			// If the tooltip would vanish off the left hand side of the screen it is set to touch the left hand edge.
			if(tooltip.getBounds().x < 0 - style.screen.getLeftOffset()) 
				tooltip.setX(0 - style.screen.getLeftOffset() + tooltip.xOrig);
		}
	}
	
	// Sets the y co-ordinate of the button and moves its tooltip appropriately if it has one.
	@Override
	public void setY(float y) {
		super.setY(y);
		if(tooltip != null) {
			tooltip.setY(bounds.y + bounds.height);
			
			// If the tooltip would vanish off the bottom of the screen it is set to touch the bottom.
			if(tooltip.getBounds().y < 0 - style.screen.getBottomOffset())
				tooltip.setY(0 - style.screen.getBottomOffset() + tooltip.yOrig);
				
			// If the tooltip would vanish off the top of the screen it is set to touch the top.
			if(tooltip.getBounds().y + tooltip.getBounds().height > style.screen.getVisibleHeight() - style.screen.getBottomOffset())
				tooltip.setY(style.screen.getVisibleHeight() - style.screen.getBottomOffset() - tooltip.getBounds().height + tooltip.yOrig);
		}
	}
	
	// Sets the tooltip to display for this button while it is selected.
	public void setDetails(String label, String tooltip) {
		
		// Sets the tooltip for the button and resets the buttons position so that the tooltip is positioned correctly.
		if(tooltip != null && tooltip != "") {
		    this.tooltip = new TextElement(tooltip, style.font, 0, 0, TextElement.CENTER, TextElement.BOTTOM);
		    this.tooltip.setWrapWidth(style.tooltipWidth);
			setX(getX());
			setY(getY());
		}
		else this.tooltip = null;
		
		// Sets the label for the button.
		if(label != null && label != "") {
			this.label = new TextElement(label, style.font, 0, 0, TextElement.CENTER, TextElement.BOTTOM);
		}
		else this.label = null;
	}
	
	// Returns the ButtonStyle used when generating this button.
	public ButtonStyle getStyle() {
		return style;
	}
	
	// A ButtonStyle object stores a set of style properties which are shared across a number of buttons.
	public static class ButtonStyle {
		
		// Reference to the GameScreen buttons using this style are being drawn on.
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
		
		public ButtonStyle(GameScreen screen, Sprite sprite, float width, float height, BitmapFont font) {
			this.screen = screen;
			this.sprite = sprite;
			this.width = width;
			this.height = height;
			this.font = font;
			
			tooltipWidth = width * 3;
		}
	}
}
