package cashmeataxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Finds a node in the graph and retrieve its edges.
 */
public class GraphFind{
	
	private Map<Node, HashSet<Edge>> graphAdjList;
	
	/**
	 * @param edges
	 * Create a new GraphFind object given a list of edges.
	 * Assumes there are no repeated/parallel edges.
	 */
	public GraphFind(List<Edge> edges) {
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
	 * Return the Edge list of the node.
	 * @param node
	 * @return The ArrayList<Edge> of outgoing edges from the given node.
	 */
	public ArrayList<Edge> find(Node node) {
		ArrayList<Edge> list = new ArrayList<Edge>();
		for(Edge i : graphAdjList.get(node))
			list.add(i);
		return list;
	}

}
