package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class DockableMenu extends BoundedElement {
	
	// Constants used to determine where on the screen the DockableMenu should be drawn.
	public static final int DOCK_NORTH = 0;
	public static final int DOCK_SOUTH = 1;
	public static final int DOCK_EAST = 2;
	public static final int DOCK_WEST = 3;
	
	// Which of the 4 polar positions the menu should be drawn to.
	private int dockEdge;
	
	// Reference to the screen the menu is being drawn on.
	protected GameScreen screen;
	
	// Reference to the sprite used to draw the menu's components.
	protected Sprite background;
	
	/* The title bar for the menu, used to control whether the menu is open or closed. Made up of a TextElement on a bounding box which
	 *  should stretch edge to edge. */
	private TextElement title;
	private Rectangle titleBounds;
	
	// The size of the title bar and menu as well as the two bounding edges used for opening and closing the menu. Set using updateBounds().
	public float titleSize, menuSize, minBound, maxBound;
	
	// Booleans to track whether or not the menu or its title are selected.
	protected boolean titleSelected = false, menuSelected = false;
	
	public DockableMenu(GameScreen screen, String title, int dockEdge, float titleSize, float menuSize) {
		
		// Constructs the menu's bounding boxes.
		super();
		titleBounds = new Rectangle(0, 0, 0, 0);
		
		// Sets the screen this menu is attached to.
		this.screen = screen;
		
		// Sets the orientation of the menu as well as its size.
		this.dockEdge = dockEdge;
		this.titleSize = titleSize;
		this.menuSize = menuSize;
		
		/* Loads the sprite to use to draw the menu components. 
		 *  Note: ShapeImageTemplate.png must be in the assets/data folder of the android project using a DockableMenu. */
		background = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 32, 32, 1, 1);
		background.setColor(0, 0, 0, 0.5f);
		
		// Builds the TextElement used to draw the title of the menu to the screen.
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		this.title = new TextElement(title, font, titleBounds.width / 2, titleBounds.height / 2, TextElement.CENTER, TextElement.CENTER);
	}
	
	// Updates the menus game logic.
	@Override
	public void update(float delta) {
		
		// If the title bar is currently selected it tracks after the finger along either the x or y axis depending on its orientation.
		if(titleSelected) {
			if(dockEdge < DOCK_EAST) setY(screen.touchY() - titleBounds.height / 2);
			else setX(screen.touchX() - titleBounds.width / 2);
		}
		
		// Sets both the menu and title to not selected.
		titleSelected = menuSelected = false;
					
		// If the user is touching the screen within the title bar it becomes selected.
		if(screen.isTouched() && titleBounds.contains(screen.touchX(), screen.touchY())) titleSelected = true;
		
		// Otherwise if they are touching the screen within the menu bar it becomes selected.
		else if(screen.isTouched() && bounds.contains(screen.touchX(), screen.touchY())) menuSelected = true;
					
		/* If the user isn't touching the screen inside either the title or menu the menu scrolls to either open or closed, depending
		 *  on which of the two options is closest. */
		else {
			if(dockEdge < DOCK_EAST) {
				if(getY() > minBound + menuSize / 2) setY(getY() + menuSize * delta * 2);
				else setY(getY() - menuSize * delta * 2);
			}
			else {
				if(getX() > minBound + menuSize / 2) setX(getX() + menuSize * delta * 2);
				else setX(getX() - menuSize * delta * 2);
			}
		}
	}
	
	// Draws the menu to the screen.
	@Override
	public void draw(SpriteBatch batch) {
		
		// Draws the TurnListBar's background.
		background.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		background.draw(batch);
					
		// Draws the TurnListBar's title.
		background.setBounds(titleBounds.x, titleBounds.y, titleBounds.width, titleBounds.height);
		background.draw(batch);
		title.draw(batch);
	}
	
	/* Resets the bounding boxes for the TurnListMenu given the current dimensions of the screen area as well as which edge the menu
	 *  should be docked against. */
	public void updateBounds() {
		
		// Sets the default origin for the bounding boxes.
		xOrig = yOrig = 0;
		
		// If the dockEdge is NORTH or SOUTH its bounds are set from the left to the right side of the screen.
		if(dockEdge <= DOCK_SOUTH) {
			bounds.width = screen.visibleWidth;
			titleBounds.width = bounds.width;
			bounds.height = menuSize + titleSize;
			titleBounds.height = titleSize;
			
			// Sets the menus position, origin and min/max bounds for it being NORTH.
			if(dockEdge == DOCK_NORTH) {
				maxBound = screen.visibleHeight - screen.bottomOffset - titleSize;
				minBound = maxBound - menuSize;
				setY(maxBound);
			}
			
			// Sets the menus position, origin and min/max bounds for it being SOUTH.
			else {
				yOrig = menuSize;
				minBound = 0 - screen.bottomOffset;
				maxBound = minBound + menuSize;
				setY(minBound);
			}
			
			// Sets shared position information.
			setX(0 - screen.leftOffset);
		}
		
		// If the dockEdge if EAST or WEST its bounds are set from the top to the bottom of the screen.
		else {
			bounds.height = screen.visibleHeight;
			titleBounds.height = bounds.height;
			bounds.width = menuSize + titleSize;
			titleBounds.width = titleSize;
			
			// Sets the menus position, origin and min/max bounds for it being EAST.
			if(dockEdge == DOCK_EAST) {
				maxBound = screen.visibleWidth - screen.leftOffset - titleSize;
				minBound = maxBound - menuSize;
				setX(maxBound);
			}
			
			// Sets the menus position, origin and min/max bounds for it being WEST.
			else {
				xOrig = menuSize;
				minBound = 0 - screen.leftOffset;
				maxBound = minBound + menuSize;
				setX(minBound);
			}
			
			// Sets shared position information.
			setY(0 - screen.bottomOffset);
		}
		
		// Sets the orientation of the TextElement.
		title.setWrapWidth(titleBounds.width / 3);
	}
	
	// Overrides the setX function so that it shifts the title bar and doesn't exceed the min/max bounds.
	@Override
	public void setX(float x) {
		
		// If the current dockEdge is EAST or WEST then the x value is constrained by bounds.
		if(x < minBound && dockEdge > DOCK_SOUTH) super.setX(minBound);
		else if(x > maxBound && dockEdge > DOCK_SOUTH) super.setX(maxBound);
		else super.setX(x);
		
		titleBounds.x = getX();
		title.setX(titleBounds.x + titleBounds.width / 2);
	}
	
	// Overrides the setY function so that it shifts the title bar and doesn't exceed the min/max bounds.
	@Override
	public void setY(float y) {
		
		// If the current dockEdge is NORTH or SOUTH then the y value is constrained by bounds.
		if(y < minBound && dockEdge < DOCK_EAST) super.setY(minBound);
		else if(y > maxBound && dockEdge < DOCK_EAST) super.setY(maxBound);
		else super.setY(y);
				
		titleBounds.y = getY();
		title.setY(titleBounds.y + titleBounds.height / 2);
	}
}