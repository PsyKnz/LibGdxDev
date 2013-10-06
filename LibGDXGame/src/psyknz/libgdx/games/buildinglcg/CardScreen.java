package psyknz.libgdx.games.buildinglcg;

import psyknz.libgdx.games.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardScreen extends GameScreen implements GameElement {
	
	public CardScreen(LibGDXGame game) {
		super(game);
		
		elements.add(this);
	}
	
	public void update(float delta) {}
	
	public void draw(SpriteBatch batch) {}
}
