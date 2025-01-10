/*
Author: Matt Farzaneh
Student number: 251370889

Description:
The graph class represents the an undirected graph that implements
GraphADT. This class uses an adjacency list to link each node (GraphNode) to
a list of its adjacent edges (GraphEdge)
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.*;

// Graph class implementing GraphADT
public class Graph implements GraphADT {

	private Map<GraphNode, List<GraphEdge>> adjacencyList;
	private Map<Integer, GraphNode> nodes;

	// Constructor
	public Graph(int n) {
		adjacencyList = new HashMap<>();
		nodes = new HashMap<>();
		for (int i = 0; i < n; i++) {
			GraphNode node = new GraphNode(i);
			nodes.put(i, node);
			adjacencyList.put(node, new ArrayList<>());
		}
	}
	
	// Adds edge between 2 nodes
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		if (!nodes.containsKey(nodeu.getName()) || !nodes.containsKey(nodev.getName())) {
			throw new GraphException("One or both nodes do not exist.");
		}
		// Check if edge already exists ** both directions **
		List<GraphEdge> edges = adjacencyList.get(nodeu);
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).secondEndpoint().equals(nodev)) {
				throw new GraphException("Edge already exists between the nodes.");
			}
		}

		GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
		adjacencyList.get(nodeu).add(edge);
		// Undirected graph so add the reverse edge
		adjacencyList.get(nodev).add(edge);
	}
	
	// Getter
	public GraphNode getNode(int u) throws GraphException {
		if (!nodes.containsKey(u)) {
			throw new GraphException("Node with the given name does not exist.");
		}
		return nodes.get(u);
	}

	// Iterates over all edges connected to a node
 	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
	 	if (!nodes.containsKey(u.getName())) {
	 		throw new GraphException("Node does not exist.");
	 	}
	 	List<GraphEdge> edges = adjacencyList.get(u);
	 	if (edges.isEmpty()) {
	 		return null;
	 	}
	 	return edges.iterator();
 	}


 	// Getter
 	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
 		if (!nodes.containsKey(u.getName()) || !nodes.containsKey(v.getName())) {
 			throw new GraphException("One or both nodes do not exist.");
 		}
 		List<GraphEdge> edges = adjacencyList.get(u);
 		for (int i = 0; i < edges.size(); i++) {
 			GraphEdge edge = edges.get(i);
 			if (edge.secondEndpoint().equals(v)) {
 				return edge;
 			}
 		}
 		throw new GraphException("No edge exists between the given nodes.");
 	}

 	// Checks if 2 nodes are neighbors (adjacent)
 	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
 		if (!nodes.containsKey(u.getName()) || !nodes.containsKey(v.getName())) {
 			throw new GraphException("One or both nodes do not exist.");
 		}
 		// Check for edge in both directions (since the graph is undirected)
 		List<GraphEdge> edges = adjacencyList.get(u);
 		boolean found = false;
 		for (int i = 0; i < edges.size(); i++) {
 			GraphEdge edge = edges.get(i);
 			if (edge.secondEndpoint().equals(v)) {
 				found = true;
 				break;
 			}
 		}
 		// Checking the reverse direction (u-v)
 		if (!found) {
 			edges = adjacencyList.get(v);
 			for (int i = 0; i < edges.size(); i++) {
 				GraphEdge edge = edges.get(i);
 				if (edge.secondEndpoint().equals(u)) {
 					found = true;
 					break;
 				}
 			}
 		}
 		return found;
 	}
}


