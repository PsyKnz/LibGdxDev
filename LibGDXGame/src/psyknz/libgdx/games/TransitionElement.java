package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionElement implements GameElement {
    
	public static final int FADE_IN = 0;
	public static final int FADE_OUT = 1;
	
	private int texRef;
	private float[] timer;
	
	public TransitionElement(int texRef, float[] timer) {
		this.texRef = texRef;
		this.timer = timer;
	}
	
	public void update(float delta) {
		if(timer[0] > 0) {
			timer[0] -= delta;
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		
	}
}
