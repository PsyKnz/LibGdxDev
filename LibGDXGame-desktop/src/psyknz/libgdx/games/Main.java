package psyknz.libgdx.games;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LibGDXGame";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 450;
		
		new LwjglApplication(new LibGDXGame(), cfg);
	}
}
