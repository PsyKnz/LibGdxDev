package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerElement extends GameElement {
    
	public final int BODY_SIZE = 32;
	public final int HEAD_SIZE = 40;
	
	private ArrayList<PlayerBody> body;
	private TextureRegion[][] sprites;
	
	public PlayerElement(TextureRegion tex, int x, int y) {
		sprites = tex.split(tex.getRegionHeight(), tex.getRegionHeight());
		body.add(new PlayerBody(sprites[0][0], x, y));
		body.add(new PlayerBody(sprites[1][0], x, y));
		body.add(new PlayerBody(sprites[1][0], x, y));
		body.add(new PlayerBody(sprites[1][0], x, y));
	}
	
	public void update(float delta) {
		
	}
	
	public void draw(SpriteBatch batch) {
		
	}
}
