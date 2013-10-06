package psyknz.libgdx.games.buildinglcg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.*;

public class ResourceMenu implements GameElement {
	
	// Sprites used to draw the ResourceMenu
	private Sprite background, button, goldDecal, woodDecal, stoneDecal;
	
	public ResourceMenu(CardScreen screen) {
		// Loads a 1x1 sprite to use for the background and makes it semi-transparent;
		background = new Sprite((Texture) screen.getGame().assets.get("data/Shapes.png"), 32, 32, 1, 1);
		background.setColor(1, 1, 1, 0.5f);
		
		// Loads the sprite to use when drawing the resource buttons.
		button = new Sprite((Texture) screen.getGame().assets.get("data/ShapeImageTemplate.png"), 0, 64, 64, 64);
	}
	
	public void update(float delta) {}
	
	public void draw(SpriteBatch batch) {}
}
