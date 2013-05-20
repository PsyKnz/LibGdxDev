package psyknz.libgdx.games;

import java.util.ArrayList;

/* A GameElementArray provides a means of collecting together GameElement objects
 * that interacts directly with the structure of a GameScreen objects render function.
 * It allows for objects to be inserted into the array based on the order of rendering.
 * Further it provides a means of generating unique identifiers for GameElements
 * which can then be used to find those objects, or groups of related objects. */
public class GameElementArray {
    
	private ArrayList<GameElement> elements;
	
	public GameElementArray() {
		elements = new ArrayList<GameElement>();
	}
	
	/* Adds the given GameElement to the array and sets its id. A sequential number
	 * is added to the end of the id to make it unique. The unique id is then returned 
	 * so that it can be used to find the GameElement object later on. The GameElement
	 * is placed at the specified depth of the array and after any other GameElements
	 * at the same depth. */
	public String add(GameElement element, String id, float depth) {
		int count = 0;
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).id.contains(id)) {
				count++;
			}
		}
		element.id = id + count;
		
		element.depth = depth;
		count = 0;
		while(element.depth < elements.get(count).depth) {
			count++;
		}
		elements.add(count, element);
		return element.id;
	}
	
	// Returns the first GameElement which contains the id in question inside of its id. Returns null if there is no match.
	public GameElement get(String id) {
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).id.contains(id)) {
				return elements.get(i);
			}
		}
		return null;
	}
	
	// Returns the GameElement at the given position in the array. If the position is outside of the bounds of the array; returns null.
	public GameElement get(int index) {
		if(index >= 0 && index < elements.size()) {
		    return elements.get(index);
		}
	    return null;
	}
	
	// Returns the position of the first GameElement which contains the id in question inside of its id. Returns -1 if there is no match.
	public int getIndex(String id) {
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).id.contains(id)) {
				return i;
			}
		}
		return -1;
	}
	
}
