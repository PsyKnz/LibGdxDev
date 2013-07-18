package psyknz.libgdx.games.robotjrpg;

public class GameEvent {
	
	// List of ennumerated event types.
	public enum event {
		TURN_START, TURN_ACT, TURN_END
	}
	
	// The type of event which has occured.
	private event eventType;
	
	public GameEvent(event eventType) {
		this.eventType = eventType;
	}
	
	// Returns the type of event which has occured.
	public event getType() {
		return eventType;
	}
}
