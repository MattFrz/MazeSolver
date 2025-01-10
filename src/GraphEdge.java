/*
Author: Matt Farzaneh
Student number: 251370889

Description:
The GraphEdge class represents a neighboring (adjacent) edge in the graph
*/


public class GraphEdge {
//	I need an origin and a destination
//	I need an int type variable
//	and I need a string label
	private GraphNode endpointU;
	private GraphNode endpointV;
	private int edgeType;
	private String edgeLabel;
	
	// Constructor
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
//		I should probably initialize everything
		if(u == null || v == null) {
			throw new IllegalArgumentException("Endpoints cannot be null.");
		}
		if(label == null || label.isEmpty()) {
			throw new IllegalArgumentException("Label cannot be null or empty.");
		}
		if(type < 0) {
			throw new IllegalArgumentException("Type cannot be negative.");
		}
		this.endpointU = u;
		this.endpointV = v;
		this.edgeType = type;
		this.edgeLabel = label;
	}
	
//	I should probably fill in the bodies of those setters and getters
	// Getter
	public GraphNode firstEndpoint() {
		return this.endpointU; 
	}
	
	// Getter
	public GraphNode secondEndpoint() {
		return this.endpointV;
	}
	
	// Getter
	public int getType() {
		return this.edgeType;
	}
	
	// Setter
	public void setType(int type) {
		if(type <  0) {
			throw new IllegalArgumentException("Type cannot be negative.");
		}
		this.edgeType = type;
	}
	
	// Getter
	public String getLabel() {
		return this.edgeLabel;
	}
	
	// Setter
	public void setLabel(String label) {
		if(label == null || label.isEmpty()) {
			throw new IllegalArgumentException("Label cannot be null or empty.");
		}
		this.edgeLabel = label;
	}
}
