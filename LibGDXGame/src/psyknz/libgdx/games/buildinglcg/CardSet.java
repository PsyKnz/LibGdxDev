package psyknz.libgdx.games.buildinglcg;

public class CardSet {
    
	// Name of the CardSet.
	public final String name;
	
	// Array of all the cards the the CardSet.
	public final Card[] cards;
	
	// Creates a new CardSet. Individual sets should override this constructor and manually fill the cards array.
	public CardSet(String name, int size) {
		this.name = name;
		
		// Creates an array to hold all of tge cards which will be loaded into the set. 
		cards = new Card[size];
	}
	
	// Returns the card with the given name in the set.
	public Card getCard(String name) {
		
		// Returns a reference to the card in the set with the given name.
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].name == name) return cards[i];
		}
		
		// If no card is found with a matching name then null is returned.
		return null;
	}
	
	// Returns the card with the given index in the set.
	public Card getCard(int index) {
		return cards[index];
	}
	
	// Adds the given card to the first free space in the set. If there are no free spaces the card is not added.
	protected void addCard(Card card) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i] == null) {
				cards[i] = card;
				break;
			}
		}
	}
}
