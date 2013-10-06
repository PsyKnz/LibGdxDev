package psyknz.libgdx.games.buildinglcg;

public class TestSet extends CardSet {
    
	public TestSet() {
		super("Internal Set", 4);
		
		// Adds a blank card to the set.
		addCard(new Card("Blank", Card.CardType.BUILDING, null, 0, 0, 0, 0, "") {
			@Override
			public void effect(GameEvent event) {}
		});
	}
}
