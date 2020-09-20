/**
* Author: Albert Zhou
* Revised: 1/09/2020
* Description: Module for an edge.
*/ 

package src;

import java.util.ArrayList;

/**
 * @brief A module for an edge.
 */
public class Edge{
	
	private Node s;
	private Node e;
	private double trps;
	private double fr;
	private double tp;
	private double dst;
	private double tll;

    /**
    * @brief Create an edge.
    * @details Sets given data.
	* @param start the starting node.
	* @param end the ending node.
	* @param d the sequence of data.
	* @throws IllegalArgumentException if |d| ! 5.
    */
	public Edge(Node start, Node end, ArrayList<Double> d){
		if (d.size() != 5)
			throw new IllegalArgumentException("Cannot create edge with invalid data.");
		this.s = start;
		this.e = end;
		this.trps = d.get(0);
		this.fr = d.get(1);
		this.tp = d.get(2);
		this.dst = d.get(3);
		this.tll = d.get(4);
	}

    /**
    * @brief Get s.
    * @details Returns the s object.
    * @return s the starting node.
    */
	public Node start(){
		return this.s;
	}

    /**
    * @brief Get e.
    * @details Returns the e object.
    * @return e the ending node.
    */
	public Node end(){
		return this.e;
	}

    /**
    * @brief Get trps, fr, tp, dst, toll.
    * @details Returns the trps, fr, tp, dst, toll as a sequence.
    * @return d the trps, fr, tp, dst, toll.
    */
	public ArrayList<Double> data(){
		ArrayList<Double> d = new ArrayList<Double>();
		d.add(this.trps);
		d.add(this.fr);
		d.add(this.tp);
		d.add(this.dst);
		d.add(this.tll);
		return d;
	}

    /**
    * @brief Update trps, fr, tp, dst, toll.
	* @details Increases the trps, fr, tp, dst, toll.
	* @param d the sequence of data.
	* @throws IllegalArgumentException if |d| ! 5.
    */
	public void update(ArrayList<Double> d){
		if (d.size() != 5)
			throw new IllegalArgumentException("Cannot update with invalid data.");
		this.trps += d.get(0);
		this.fr += d.get(1);
		this.tp += d.get(2);
		this.dst += d.get(3);
		this.tll += d.get(4);
	}

	/**
    * @brief Get the weight.
    * @details The weight is dst/(fr + tp).
    * @return w the weight.
    */
	public Double weight(){
		return this.dst / (this.fr + this.tp);
	}

	/**
    * @brief Compare with an edge based on a data segment.
	* @details Compare based on i.
	* @param i 0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll.
	* @param e the other edge.
    * @return c 1 -> greater, -1 -> less, 0 -> equal.
    */
	public int comp(Edge e, int i){
		if (i < 0 || i > 4)
			throw new IllegalArgumentException("Illegal comparison.");

		if (this.data().get(i) > e.data().get(i))
			return 1;
		else if (this.data().get(i) < e.data().get(i))
			return -1;
		
		return 0;
	}

	/**
	* @brief Check for equality
	* @details Checks for equality by the starting and ending nodes.
	* @param E the other Edge
	* @returns whether a is equal to self
	*/
	public boolean equals(Object e){
		if (e == null || e.getClass() != this.getClass())
			return false;
		Edge e2 = (Edge) e;
		return this.s.equals(e2.start()) && this.e.equals(e2.end());
    }
    
    public String toString(){
		return Integer.toString(this.s.getId()) + " + " + Integer.toString(this.e.getId());
	}

	// public static void main(String[] args) {

	// 	Node x = new Node(1);
	// 	Node y = new Node(2);
	// 	Node z = new Node(3);

	// 	Edge a = new Edge(x,y,1,2,3,4,5);
	// 	Edge b = new Edge(y,z,2,2,2,2,2);

	// 	System.out.println(a.data());
	// 	a.update(b.data());
	// 	System.out.println(a.data());
	// 	System.out.println(a.weight());

	// }

}
