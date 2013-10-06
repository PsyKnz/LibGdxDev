package psyknz.libgdx.games.buildinglcg;

import com.badlogic.gdx.utils.Array;

public class PlayerElement {
    
	public int gold, wood, stone;
	
	public final Array<Card> hand;
	
	public PlayerElement() {
		hand = new Array<Card>();
	}
}
