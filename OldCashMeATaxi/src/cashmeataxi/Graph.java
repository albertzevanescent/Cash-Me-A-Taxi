package cashmeataxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection of nodes and edges. Represents the directed graph with nodes representing a
 * NYC zone, and edges between zones representing that taxi trips were made between these zones.
 */
public class Graph {
	
	/**
	 * Adjacency map
	 */
	private Map<Node, HashMap<Node, Edge>> adj;

	/**
	 * Constructor to initialize the graph
	 * @param nodes
	 */
	public Graph(ArrayList<Node> nodes) {
		adj = new HashMap<>();

		for (Node node : nodes)
			adj.put(node, new HashMap<Node, Edge>());
	}

	/**
	 * @param edges Constructor to create a Graph given the edges. Assumes that the
	 *              edges are valid, and no repeated (parallel) edges.
	 */
	public Graph(List<Edge> edges) {
		adj = new HashMap<Node, HashMap<Node, Edge>>();

		for (Edge edge : edges) {
			// if there is currently no key with this edge's FROM node, add it
			if (!adj.containsKey(edge.from())) {
				adj.put(edge.from(), new HashMap<Node, Edge>());
			}
			// add this node to this edge's FROM edge list
			// Note that given that the adj list for each node is a hashmap,
			// if there are parralel edges (which there shouldn't be for our graph,
			// this line will override the previous edge.
			adj.get(edge.from()).put(edge.to(), edge);
		}
	}

	/**
	 * Constructor to create a Graph without knowing the nodes ahead of time.
	 */
	public Graph() {
		adj = new HashMap<Node, HashMap<Node, Edge>>();
	}

	/**
	 * Function to add or update an edge to the graph given a new Trip object
	 * @param t
	 */
	public void addTrip(Trip t) {
		Node fromNode = new Node(t.fromZoneId());
		Node toNode = new Node(t.toZoneId());
		if (adj.containsKey(fromNode)) {
			if (adj.get(fromNode).containsKey(toNode)) {
				// this exact from-to edge already exists, so update it
				// with the new trip data.
				adj.get(fromNode).get(toNode).updateEdge(t);
			} else {
				// the FROM node exists in our adj list, but not the to.
				// so create a new edge.
				adj.get(fromNode).put(toNode, new Edge(t));
			}
		} else {
			// the FROM node doesn't exist yet in our adj list.
			adj.put(fromNode, new HashMap<Node, Edge>());
			adj.get(fromNode).put(toNode, new Edge(t));
		}
	}

	/**
	 * Function to return all edges to "node"
	 * @param node
	 * @return
	 */
	public Iterable<Edge> adj(Node node) {
		return adj.get(node).values();
	}

	/**
	 * @return All edges in the graph.
	 */
	public ArrayList<Edge> edges() {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (Node node : adj.keySet()) {
			edges.addAll(adj.get(node).values());
		}
		return edges;
	}
	
	/**
	 * Is this graph empty or not.
	 * @return
	 */
	public boolean isEmpty() {
		return adj.isEmpty();
	}
}
