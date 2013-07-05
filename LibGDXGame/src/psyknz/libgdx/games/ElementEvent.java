package psyknz.libgdx.games;

public class ElementEvent {
	
	/* The following types of events can be generated:
	 * - TOUCHED: Should be used when the user has interacted directly with the GameElement. 
	 * - COLLIDED: Should be used when the GameElement has collided with some other object on the current GameScreen. 
	 * - TIMER_FINISHED: Should be used when a timer has run down to 0. */
	public static enum EventType {
		TOUCHED, COLLIDED, FINISHED, TIMER_FINISHED
	}
	
	// Reference to the element generating the event.
	public GameElement element;
	
	// The type of event generated.
	public EventType type;
	
	public ElementEvent(GameElement element, EventType type) {
		this.element = element;
		this.type = type;
	}
}
