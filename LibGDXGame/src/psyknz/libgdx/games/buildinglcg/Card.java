package psyknz.libgdx.games.buildinglcg;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Card {
    
	// The different types of cards which exist in the game.
	public static enum CardType {
		BUILDING, ACTION
	}
	
	// The name of the card and its effect.
	public final String name, text;
	
	// The type of card this is.
	public final CardType type;
	
	// The sprite used for this cards picture.
	public final Sprite sprite;
	
	// Costs for playing the card and its influence value if it's a building.
	public final int gold, wood, stone, influence;
	
	// Constructs a new Card using the given parameters.
	public Card(String name, CardType type, Sprite sprite, int gold, int wood, int stone, int influence, String text) {
		this.name = name;
		this.type = type;
		this.sprite = sprite;
		
		this.gold = gold;
		this.wood = wood;
		this.stone = stone;
		
		if(this.type == CardType.BUILDING) this.influence = influence;
		else this.influence = 0;
		
		this.text = text;
	}
	
	// This function should be overwritten to give the card in-game effects.
	public abstract void effect(GameEvent event);
}
