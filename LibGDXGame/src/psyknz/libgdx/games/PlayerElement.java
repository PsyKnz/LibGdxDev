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
		sprites[0][0] = tex;
		body.add(new PlayerBody(sprites[0][0], x, y));
		body.add(new PlayerBody(sprites[0][0], x, y));
		body.add(new PlayerBody(sprites[0][0], x, y));
		body.add(new PlayerBody(sprites[0][0], x, y));
	}
	
	public void update(float delta) {
		
	}
	
	public void draw(SpriteBatch batch) {
		for(int i = body.size() - 1; i >= 0; i--) {
			batch.draw(body.get(i).tex, body.get(i).x, body.get(i).y);
		}
	}
}
