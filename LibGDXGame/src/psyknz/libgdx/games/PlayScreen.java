package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayScreen extends GameScreen implements ElementListener {
	
	// References to all core GameElements on the screen.
	public ShapeElement arena;
	public PlayerElement player;
	public EnemyElement enemies;
	
	public PlayScreen(LibGDXGame game) {
		super(game);
		
		Sprite circle = new Sprite(game.assets.get("data/ShapeImageTemplate.png", Texture.class), 0, 0, 64, 64);
		Sprite dot = new Sprite(game.assets.get("data/ShapeImageTemplate.png", Texture.class), 16, 16, 1, 1);		
		
		arena = new ShapeElement(this, dot, Color.RED, game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2, game.GAME_WIDTH - 20, game.GAME_HEIGHT - 20);
		arena.filled = false;
		elements.add(arena);
		
		player = new PlayerElement(this, circle, game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2);
		elements.add(player);
		
		enemies = new EnemyElement(this, new Sprite[] {circle});
		elements.add(enemies);
		
		elements.add(game.console);
	}
	
	// Processes events from GameElements on this screen.
	@Override
	public void event(ElementEvent event) {}
}
