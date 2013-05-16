package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameMenu implements GameElement, ElementListener{
	
	private GameScreen screen;
	private ArrayList<GameElement> elements;
	private Rectangle bounds;
	
	private int backgroundTex;
	private int font;
	
	public GameMenu(GameScreen screen, int x, int y, int width, int height) {
		this.screen = screen;
		bounds = new Rectangle(x, y, width, height);
		elements = new ArrayList<GameElement>();
	}
	
	public void update(float delta) {
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).update(delta);
		}
	}
	
	public void draw(SpriteBatch batch) {
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).draw(batch);
		}
	}
	
	public void action(String id) {
		
	}
}
