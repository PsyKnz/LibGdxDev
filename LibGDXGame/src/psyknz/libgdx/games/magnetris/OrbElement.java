package psyknz.libgdx.games.magnetris;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OrbElement extends CircleElement {
	
	public static final float ORB_SIZE = 64;
	
	public OrbElement(Sprite sprite, Color color) {
		super(sprite, color, ORB_SIZE);
	}

}
