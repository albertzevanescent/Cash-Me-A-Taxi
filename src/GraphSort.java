/**
* Author: Albert Zhou
* Revised: 27/08/2020
* Description: Module for a graph sort.
*/ 

package src;

import java.util.ArrayList;
import java.util.Random;
/**
 * @brief A module for a graph sort.
 */
public class GraphSort{
	
	private Graph g;

    /**
    * @brief Create a graph sort.
	* @details Set the graph.
	* @param G the graph.
    */
	public GraphSort(Graph G){
		this.g = G;
	}

	/**
    * @brief Get the sorted edges by a data segment.
	* @details Combine the edges and sort by i.
	* @param i 0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll.
	* @param j 1 -> ascending, -1 -> descending.
	* @return e the sorted edges.
	* @throws IllegalArgumentException if i < 0 or i > 4 or (j != -1 and j != 1).
    */
	public Seq<Edge> sort(int i, int j){
		if (i < 0 || i > 4 || (j != -1 && j != 1))
			throw new IllegalArgumentException("Illegal sort.");

		Seq<Edge> e = arrange();
		Edge[] sorted = new Edge[e.size()];
		for(int k = 0; k < e.size(); k++)
			sorted[k] = (e.get(k));

		quicksort(sorted, i, j);

		e = new Seq<Edge>();
		for(int k = 0; k < sorted.length; k++)
			e.append(sorted[k]);

		return e;	
	}			

	/**
    * @brief Arrange the edges in to 1 sequence.
	* @details Adds the edges to 1 sequence.
	* @return e the sorted edges.
    */
	public Seq<Edge> arrange(){
		Seq<Edge> e = new Seq<Edge>();

		Seq<Seq<Edge>> edges = this.g.edgeList();

		for (int i = 0; i < edges.size(); i++) {
			for (int j = 0; j < edges.get(i).size(); j++) {
				e.append(edges.get(i).get(j));
			}
		}
		return e;
	}

	/**
    * @brief Swap 2 elemements of an array.
	* @details Swap the elements.
	* @param a the array.
	* @param i Index of the 1st element.
	* @param j Index of the 2nd element.
    */
	private void exch(Edge[] a, int i, int j) {
		Edge t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	/**
    * @brief Quicksort with sorting paramters.
	* @details Quicksort with sorting paramters.
	* @param a the array.
	* @param i 0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll.
	* @param j 1 -> ascending, -1 -> descending.
    */
	private void quicksort(Edge[] a, int i, int j) {
		quicksort(a, 0, a.length - 1, i, j);
	}

	/**
    * @brief Quicksort with sorting paramters.
	* @details Quicksort with sorting paramters.
	* @param a the array.
	* @param low Low end.
	* @param high High end.
	* @param i 0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll.
	* @param j 1 -> ascending, -1 -> descending.
    */
	private void quicksort(Edge[] a, int low, int high, int i, int j) {
		if (low >= high)
			return;

		int p = part(a, low, high, i, j);
		quicksort(a, low, p - 1, i, j);
		quicksort(a, p + 1, high, i, j);

	}

	/**
    * @brief Quicksort with sorting paramters.
	* @details Quicksort with sorting paramters.
	* @param a the array.
	* @param low Low end.
	* @param high High end.
	* @param i 0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll.
	* @param j 1 -> ascending, -1 -> descending.
    */
	private int part(Edge[] a, int low, int high, int i, int j) {

		int l = low, r = high + 1;

		while (true) {
			while (a[++l].comp(a[low], i) * j <= 0)
				if (l == high)
					break;

			while (a[--r].comp(a[low], i) * j >= 0)
				if (r == low)
					break;
			if (l >= r)
				break;
			exch(a, l, r);
		}

		exch(a, low, r);
		return r;

	}

	public static double randD(double min, double max){
		Random r = new Random();
		return (double) (int) (min + (max - min) * r.nextDouble());
	}

	// public static void main(String[] args) {

	// 	Graph g = new Graph();
	// 	Node a = new Node(1);
	// 	Node b = new Node(2);
	// 	Node c = new Node(3);
	// 	Node d = new Node(4);

	// 	ArrayList<Double> data1 = new ArrayList<Double>() { 
    //         { 
    //             add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); 
    //         } 
	// 	};
		
	// 	ArrayList<Double> data2 = new ArrayList<Double>() { 
    //         { 
    //             add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); 
    //         } 
	// 	};

	// 	ArrayList<Double> data3 = new ArrayList<Double>() { 
    //         { 
    //             add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); 
    //         } 
	// 	};

	// 	ArrayList<Double> data4 = new ArrayList<Double>() { 
    //         { 
    //             add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); add(randD(0.0, 20.0)); 
    //         } 
	// 	};

	// 	Edge e1 = new Edge(a,b, data1);
	// 	Edge e2 = new Edge(b,c, data2);
	// 	Edge e3 = new Edge(a,c, data3);
	// 	Edge e4 = new Edge(b,a, data4);

	// 	g.add(e1);
	// 	g.add(e2);
	// 	g.add(e3);
	// 	g.add(e4);

	// 	// g.print();

	// 	// g.add(e3);

	// 	// ArrayList<Double> data2 = g.edgeList().get(0).get(0).data();
	// 	// System.out.println(data2);

	// 	// Seq<Edge> x = g.adj(a);
	// 	// x.print();

	// 	// System.out.println(e4.comp(e3,0));

	// 	System.out.println("1 + 2 " + e1.data());
	// 	System.out.println("2 + 3 " + e2.data());
	// 	System.out.println("1 + 3 " + e3.data());
	// 	System.out.println("2 + 1 " + e4.data());
	// 	System.out.println();


	// 	GraphSort gs = new GraphSort(g);

	// 	Seq<Edge> sorted = gs.sort(4, -1);

	// 	for(int i = 0; i < sorted.size(); i++)
	// 		System.out.println(sorted.get(i) + " " + sorted.get(i).data());




	// }

}
