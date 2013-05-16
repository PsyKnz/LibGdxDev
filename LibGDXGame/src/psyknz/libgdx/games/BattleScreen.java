package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class BattleScreen extends GameScreen {

	private BitmapFont font;
	
	public BattleScreen(LibGDXGame game) {
		super(game);
	}
	
	public void show() {
		super.show();
		
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		elements.add(new LabelElement("Battle Screen", font, 0, game.height));
	}
}
