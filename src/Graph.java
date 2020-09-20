/**
* Author: Albert Zhou
* Revised: 1/09/2020
* Description: Module for a graph.
*/ 

package src;

import java.util.ArrayList;

/**
 * @brief A module for a graph.
 */
public class Graph{
	
	private Seq<Node> nodes;
	private Seq<Seq<Edge>> edges;

    /**
    * @brief Create a graph.
    * @details Create an empty graph.
    */
	public Graph(){
		this.nodes = new Seq<Node>();
		this.edges = new Seq<Seq<Edge>>();

	}

	/**
    * @brief Get the nodes.
	* @details Get the nodes.
	* @param n the nodes.
    */
	public Seq<Node> nodeList(){
		return this.nodes;
	}

	/**
    * @brief Get the edges.
	* @details Get the edges.
	* @param e the edges.
    */
	public Seq<Seq<Edge>> edgeList(){
		return this.edges;
	}

	/**
    * @brief Add a node.
	* @details Add n to nodes and an empty edge seq to edges if it is not already there.
    * @param n the node.
    */
	public void addNode(Node n){
		if (!(this.nodes.member(n))){
			this.nodes.append(n);
			this.edges.append(new Seq<Edge>());
		}
	}

	/**
    * @brief Add an edge.
	* @details Add e to edges with the same index as its starting node. Otherwise update e.
    * @param e the edge.
    */
	public void addEdge(Edge e){
		if (!(this.nodes.member(e.start()) && this.nodes.member(e.end())))
			throw new IllegalArgumentException("Cannot add edge without nodes.");
		Seq<Edge> s = this.edges.get(this.find(e.start()));
		if (!(s.member(e)))
			s.append(e);
		else{
			int x = s.find(e);
			s.get(x).update(e.data());
		}
	}

	/**
    * @brief Get the edges of a node.
	* @details Get the edge seq from edges based on the index of n if n is in the graph.
	* @param n the node.
    * @param e the edges of n.
    */
	public Seq<Edge> adj(Node n){
		if (this.find(n) != -1)
			return this.edges.get(this.find(n));
		else
			return new Seq<Edge>();
	}

	/**
    * @brief Get the index of a node.
	* @details Find where the node appears in nodes.
    * @param n the node.
    * @return i the index of n.
    */
	public int find(Node n){
		return this.nodes.find(n);
	}

	/**
    * @brief Get the number of nodes.
	* @details Get the size of nodes.
    * @return n the number of n.
    */
	public int n(){
		return this.nodes.size();
	}

	/**
    * @brief Get the number of edges.
	* @details Get the sum of sizes of edge seq.
    * @return m the number of edges.
    */
	public int m(){
		int m = 0;
		for(int i = 0; i < this.n(); i++)
			m += this.edges.get(i).size();
		return m;
	}
    
    public void print(){
		for (int i = 0; i < this.nodes.size(); i++) {
			System.out.print(this.nodes.get(i));
			for (int j = 0; j < this.edges.get(i).size(); j++) {
				System.out.print(" - " + this.edges.get(i).get(j).end());
			}
			System.out.println();

		}	
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

	// 	g.print();

	// 	g.add(e3);

	// 	ArrayList<Double> data2 = g.edgeList().get(0).get(0).data();
	// 	System.out.println(data2);

	// 	Seq<Edge> x = g.adj(d);
	// 	x.print();
	// 	System.out.println(x.size());
	// }

}
