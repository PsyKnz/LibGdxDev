package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameElement {
    
	public void update(float delta);
	
	public void draw(SpriteBatch batch);
}
