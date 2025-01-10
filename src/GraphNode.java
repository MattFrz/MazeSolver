/*
Author: Matt Farzaneh
Student number: 251370889

Description:
The GraphNode class represents a node in the graph
*/
public class GraphNode {
	private int name;
	private boolean mark;
	
	// Constructor
	public GraphNode(int name) {
		this.name=name;
		if(name < 0) {
			throw new IllegalArgumentException("Node name must be a non-negative integer.");
		}
		this.mark = false;
	}

	
//	setters and getters, should be fun
	// Setter
	public void mark(boolean mark) {
		this.mark = mark;
	}
	
	// Getter
	public boolean isMarked() {
		return this.mark;
	}
	
	// Getter
	public int getName() {
		return this.name;
	}
	
}
