/**
* Author: Albert Zhou
* Revised: 27/08/2020
* Description: Module for a graph searching.
*/ 

package src;

import java.util.ArrayList;

/**
 * @brief A module for a graph searching.
 */
public class GraphSearch{
	
	private Graph g;

    /**
    * @brief Create a graph search.
	* @details Set the graph.
	* @param G the graph.
    */
	public GraphSearch(Graph G){
		this.g = G;
	}

	/**
    * @brief Get the edges of a node.
	* @details Get the edges of a node.
	* @param n the node.
	* @return e the edges.
    */
	public Seq<Edge> find(Node n){
		return this.g.adj(n);
	}

	// public static void main(String[] args) {

	// 	Graph g = new Graph();
	// 	Node a = new Node(1);
	// 	Node b = new Node(2);
	// 	Node c = new Node(3);
	// 	Node d = new Node(4);

	// 	ArrayList<Double> data = new ArrayList<Double>();
	// 	data.add(1.0);
	// 	data.add(1.0);
	// 	data.add(1.0);
	// 	data.add(1.0);
	// 	data.add(1.0);

	// 	Edge e1 = new Edge(a,b, data);
	// 	Edge e2 = new Edge(b,c, data);
	// 	Edge e3 = new Edge(a,c, data);
	// 	Edge e4 = new Edge(b,a, data);

	// 	g.add(e3);
	// 	g.add(e2);
	// 	g.add(e1);
	// 	g.add(e4);

	// 	GraphSearch q = new GraphSearch(g);
	// 	Seq<Edge> w = q.find(a);
	// 	w.print();

	// 	Seq<Node> z = new Seq<Node>();
	// 	Node y = new Node(1);

	// }

}
