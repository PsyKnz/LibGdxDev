package psyknz.libgdx.games;

import java.util.ArrayList;

/* A GameElementArray provides a means of collecting together GameElement objects that interacts directly with the structure of a
 * GameScreen objects render function. It allows for objects to be inserted into the array based on the order of rendering. Further it
 * provides a means of generating unique identifiers for GameElements which can then be used to find those objects, or groups of related
 * objects. */
public class GameElementArray {
    
	private ArrayList<GameElement> elements;
	
	public GameElementArray() {
		elements = new ArrayList<GameElement>();
	}
	
	/* Adds the given GameElement to the array and sets its id. A sequential number is added to the end of the id to make it unique. The
	 * unique id is then returned so that it can be used to find the GameElement object later on. The GameElement is placed at the
	 * specified depth of the array and after any other GameElements at the same depth. Additional constructors are available which do
	 * not require the tag be set or the depth, or ether. */
	public String add(GameElement element, String tag, float depth) {
		if(elements.isEmpty()) {
			element.tag = tag + "-" + 0;
			elements.add(element);
			return element.tag;
		}
		
		int count = 0;
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).tag.contains(tag)) {
				count++;
			}
		}
		element.tag = tag + "-" + count;
		
		element.depth = depth;
		count = 0;
		while(count < elements.size() && element.depth <= elements.get(count).depth) {
			count++;
		}
		elements.add(count, element);
		return element.tag;
	}
	
	public String add(GameElement element) {
		return this.add(element, "NoTag", 0);
	}
	
	public String add(GameElement element, float depth) {
		return this.add(element, "NoTag", depth);
	}
	
	public String add(GameElement element, String tag) {
		return this.add(element, tag, 0);
	}
	
	// Returns the first GameElement which contains the id in question inside of its id. Returns null if there is no match.
	public GameElement get(String tag) {
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).tag.contains(tag)) {
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
	public int getIndex(String tag) {
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).tag.contains(tag)) {
				return i;
			}
		}
		return -1;
	}
	
	// NOT IMPLEMENTED. Returns an ArrayList containing all GameElements in this list which contain the desired tags.
	public ArrayList<GameElement> filter(String[] tag) {
		return null;
	}
	
	// Returns the number of elements present in the ArrayList.
	public int size() {
		return elements.size();
	}
	
}
