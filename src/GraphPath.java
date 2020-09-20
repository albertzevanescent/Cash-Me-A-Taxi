/**
* Author: Albert Zhou
* Revised: 1/09/2020
* Description: Module for a graph path.
*/ 

package src;

import java.util.ArrayList;
import java.util.Random;

/**
 * @brief A module for a graph path.
 */
public class GraphPath{
	
	private Graph g;

    /**
    * @brief Create a graph path.
	* @details Set the graph.
	* @param G the graph.
    */
	public GraphPath(Graph G){
		this.g = G;
	}

	/**
    * @brief Find the shortest path.
	* @details Use Dijkstra's algorithm.
	* @param a the starting node.
	* @param b the ending node.
	* @return p the shortest path.
	* @throws IllegalArgumentException if g.find(a) == -1 or .g.find(b) == -1.
    */
	public Seq<Edge> path(Node a, Node b){
		if (this.g.find(a) == -1 || this.g.find(b) == -1)
			throw new IllegalArgumentException("Cannot path with incorrect nodes.");

		int size = this.g.edgeList().size();
		Edge[] edgeTo = new Edge[size];
		double[] distTo = new double[size];
		IndexMinPQ<Double> pq = new IndexMinPQ<Double>(size);
		int s = this.g.find(a);

		for (int v = 0; v < size; v++)
			distTo[v] = Double.POSITIVE_INFINITY;

		distTo[s] = 0.0;
		pq.insert(s, 0.0);
		int min = 0;
		while (!(pq.isEmpty())) {
			min = pq.deleteMin();
			this.relax(min, edgeTo, distTo, pq);
		}
		
		return this.trace(a, b, edgeTo);
	}

	/**
    * @brief Relax a node.
	* @details Relax a node.
	* @param i the index of the node.
	* @param edgeTo the edge array of min edges.
	* @param distTo the double array of min weights.
	* @param pq the min priority queue.
    */
	private void relax(int i, Edge[] edgeTo, double[] distTo, IndexMinPQ<Double> pq) {
		Seq<Edge> adj = this.g.adj(this.g.nodeList().get(i));
		for (int j = 0; j < adj.size(); j++) {
			Edge e = this.g.edgeList().get(i).get(j);
			int w = g.nodeList().find(e.end());
			if (distTo[w] > distTo[i] + e.weight()) {
				distTo[w] = distTo[i] + e.weight();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.changeKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}

		}
	}

	/**
    * @brief Get a path from a to b.
	* @details Jump backwards in edgeTo from b to a and then reverse.
	* @param a the starting node.
	* @param b the ending node.
	* @param edgeTo the edge array.
	* @return p the shortest path.
    */
	private Seq<Edge> trace(Node a, Node b, Edge[] edgeTo) {
		int start = this.g.nodeList().find(a);
		int end = this.g.nodeList().find(b);
		Seq<Edge> p = new Seq<Edge>();

		while(edgeTo[end] != null){
			p.append(edgeTo[end]);
			end = this.g.nodeList().find(edgeTo[end].start());
			if (start == end) break;
		}

		Seq<Edge> path = new Seq<Edge>();
		for(int i = p.size() - 1; i >= 0; i--)
			path.append(p.get(i));

		return path;

	}

	public static double randD(double min, double max){
		Random r = new Random();
		return (double) (int) (min + (max - min) * r.nextDouble());
	}

	public static Node[] makeNode(int n){
		Node[] nodes = new Node[n];
		for(int i = 0; i < n; i++)
			nodes[i] = new Node(i);
		return nodes;
	}

	public static ArrayList<Double> makeData(){
		ArrayList<Double> d = new ArrayList<Double>() { 
            { 
                add(randD(1.0, 20.0)); add(randD(1.0, 20.0)); add(randD(1.0, 20.0)); add(randD(1.0, 20.0)); add(randD(1.0, 20.0)); 
            } 
		};

		return d;
	}

	public static boolean unique(Edge[] e, int m, Edge x){
		for(int i = 0; i < m; i++){
			if(e[i] == null)
				continue;

			if(x.equals(e[i]))
				return false;
		}
		return true;
	}

	public static Edge[] makeEdge(Node[] nodes, int m){
		Edge[] edges = new Edge[m];
		int start = 0;
		int end = 0;
		Edge e = null;
		for(int i = 0; i < m; i++){
			start = end;
			while(start == end){
			start = (int) randD(0.0, nodes.length);
			end = (int) randD(0.0, nodes.length);
			}
			e = new Edge(nodes[start], nodes[end], makeData());
			if (unique(edges, i, e))
				edges[i] = e;
			else
				edges[i] = null;
		}
		return edges;
	}

	// public static void main(String[] args) {

	// 	Graph g = new Graph();
	// 	Node[] nodes = makeNode(5);
	// 	Edge[] edges = makeEdge(nodes, 99);

	// 	for(int i = 0; i < nodes.length; i++)
	// 		g.addNode(nodes[i]);

	// 	for(int i = 0; i < edges.length; i++)
	// 		if(edges[i] != null)
	// 			g.addEdge(edges[i]);

	// 	Node a = new Node(0);
	// 	Node b = new Node(1);
	// 	Node c = new Node(2);
	// 	Node d = new Node(3);

	// 	g.print();

	// 	System.out.println();

	// 	for(int i = 0; i < edges.length; i++)
	// 		if(edges[i] != null)
	// 			System.out.println(edges[i] + " " + edges[i].data());
	// 	System.out.println();


	// 	GraphPath gp = new GraphPath(g);
	// 	System.out.println("Path from " + d + " to " + c);

	// 	Seq<Edge> p = gp.path(d,c);
	// 	if (p.size() != 0)
	// 		p.print();
	// 	else
	// 		System.out.println("No path");





	// }

}
