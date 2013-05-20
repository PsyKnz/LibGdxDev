package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerBody {
    
	public TextureRegion tex;
	public int x;
	public int y;
	
	public PlayerBody(TextureRegion tex, int x, int y) {
	    this.tex = tex;
		this.x = x;
		this.y = y;
	}
}
