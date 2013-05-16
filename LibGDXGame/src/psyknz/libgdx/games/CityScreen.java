package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class CityScreen extends GameScreen {

	private BitmapFont font;

	public CityScreen(LibGDXGame game) {
		super(game);
	}

	public void show() {
		super.show();

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		elements.add(new LabelElement("City Screen", font, 0, game.height));
	}
}
