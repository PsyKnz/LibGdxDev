package psyknz.libgdx.games.buildinglcg;

import com.badlogic.gdx.utils.Array;

public class Deck {
    
	public final Array<Card> cards;
	
	public Deck() {
		cards = new Array<Card>();
		
		cards.shuffle();
	}
	
	// Adds the card with the given index to the top of the deck.
	public void addCard(int cardId) {
		addCard(cardId, false);
	}
	
	// Adds the card with the given index to the top or bottom of the deck.
	public void addCard(int cardId, boolean bottom) {}
}
