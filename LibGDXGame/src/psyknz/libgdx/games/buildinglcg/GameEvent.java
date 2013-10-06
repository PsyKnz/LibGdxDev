package psyknz.libgdx.games.buildinglcg;

public class GameEvent {
    
	// List of all events which can occur in a game.
	public static enum EventType {
		TURN_START,
		TURN_END
	}
	
	// The type of event being generated.
	public final EventType type;
	
	// Reference to the Card generating this event.
	public final Card card;
	
	// Creates a new GameEvent of the given type generated by the given card. 
	public GameEvent(EventType type, Card card) {
		this.type = type;
		this.card = card;
	}
}