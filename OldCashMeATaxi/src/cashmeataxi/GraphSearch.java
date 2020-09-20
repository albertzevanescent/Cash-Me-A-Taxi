package cashmeataxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Traverses through the graph to search for the most optimal route between the given source
 * node to the given destination node.
 */
public class GraphSearch {
	
	private Map<Node, HashSet<Edge>> graphAdjList;
	
	/**
	 * @param edges
	 * Create a new GraphSearch object given a list of edges.
	 * Assumes there are no repeated/parallel edges.
	 */
	public GraphSearch(List<Edge> edges) {
		graphAdjList = new HashMap<Node, HashSet<Edge>>();
		for (Edge edge : edges) {
			// if the FROM doesn't exist yet, add it to the graph
			if (!graphAdjList.containsKey(edge.from())) {
				graphAdjList.put(edge.from(), new HashSet<Edge>());
			} 
			graphAdjList.get(edge.from()).add(edge);
		}
	}
	
	/**
	 * Finds the most optimal path between the given start and end zones.
	 * Uses Dijkstra's search algorithm.
	 * @param start
	 * @param end
	 * @return The list of edges containing the optimal path.
	 */
	public LinkedList<Node> findOptimalPath(int s, int e) {
		ArrayList<Node> pathAr = new ArrayList<Node>();
		LinkedList<Node> path = new LinkedList<Node>();
		Set<Node> visitedNodes = new HashSet<Node>();
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		Map<Node, Edge> immediateParents = new HashMap<Node, Edge>();
		
		// find the start and end nodes
		Node start = new Node(s);
		Node end = new Node(e);
		if (!graphAdjList.containsKey(start) || !graphAdjList.containsKey(end)) return path;
		
		// set the cost to start node as 0
		start.setCost(0.0);
		
		// set all immediate parents to null
		for (Node node : graphAdjList.keySet()) immediateParents.put(node, null);
		
		// add start node to priority queue
		pq.add(start);
		Node current;
		
		while (!pq.isEmpty()) {
			// remove the minimum cost node from priority queue
			current = pq.remove();
			
			if (visitedNodes.contains(current)) continue;
						
			visitedNodes.add(current);
			// visit this node's outgoing edges
			for (Edge outgoingEdge : graphAdjList.get(current)) {
				Node n = outgoingEdge.to();
				// if we haven't yet visited this neighbour
				if (!visitedNodes.contains(n)) {
					Double newCost = current.cost() + outgoingEdge.cost();
					// if the new cost is less than the current cost of the neighbour
					// then update the cost for the neighbour
					if (newCost < n.cost()) {
						n.setCost(newCost);
						immediateParents.put(n, outgoingEdge);
					}
					// add neighbour to priority queue to process
					pq.add(n);
				}
			}
		}
		
		/*
		 * Now we should have found the shortest path. 
		 * To get the path, start with the destination node, and append immediate parents to 
		 * a list until we reach the source node.
		 */
		current = end;
		Edge parent;
		pathAr.add(end);
		while ((parent = immediateParents.get(current)) != null) {
			pathAr.add(parent.from());
			current = parent.from();
		}
		
		// reverse path and return it
		for (int i = pathAr.size() - 1; i >= 0; i--) {
			path.add(pathAr.get(i));
		}
		return path;
	}
}
