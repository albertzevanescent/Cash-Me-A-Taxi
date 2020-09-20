/**
* Author: Albert Zhou
* Revised: 19/09/2020
* Description: Module for the file controller.
*/ 

package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * @brief A module for the file controller.
 */	
public class FileController{
	
    /**
    * @brief Create the file controller.
    */
	// public static void init(){
	// 	;
	// }

	/**
    * @brief Read the file.
	* @details For each line, create 2 nodes and an edge and add it to a graph.
	* @param f the file name.
	* @return g the graph.
    */
	public static Graph read(String f){

		int n;
		int m;
		Node a;
		Node b;
		ArrayList<Double> d;
		Edge e;
		String line;
		String[] data;
		Graph g = new Graph();
		Scanner reader = null;

		try{
			reader = new Scanner(new File(f));
		} catch (FileNotFoundException exc) {
			throw new IllegalArgumentException("File does not exist.");
		}
		line = reader.nextLine();
		data = line.split(",");
		n = Integer.parseInt(data[0]);
		m = Integer.parseInt(data[1]);
		
		for(int i = 0; i < n + m; i++) {
			line = reader.nextLine();
			data = line.split(",");
			a = new Node(Integer.parseInt(data[0]));

			if (i < n){
				g.addNode(a);
			}
			else{
				b = new Node(Integer.parseInt(data[1]));
				d = new ArrayList<Double>();
				d.add(Double.parseDouble(data[2]));
				d.add(Double.parseDouble(data[3]));
				d.add(Double.parseDouble(data[4]));
				d.add(Double.parseDouble(data[5]));
				d.add(Double.parseDouble(data[6]));
				e = new Edge(a, b, d);
				g.addEdge(e);
			}
		}

		reader.close();
		return g;

	}

	/**
    * @brief Write the graph to a file.
	* @details Write to a file named f the following: the first line contains n, the number of
				nodes, and m the number of edges in g. The next n lines contain the id of the
				n nodes and the next m lines contain the starting node id, ending node id,
				number of trips, fare, tip, distance, and toll of the m edges.
	* @param g the graph.
	* @param f the file name.
    */
	public static void save(Graph g, String f){

		Edge e;
		String line;
		PrintStream writer;
		try{
			writer = new PrintStream(new File(f));
		} catch (FileNotFoundException exc) {
			throw new IllegalArgumentException("File does not exist.");
		}
		line = g.n() + "," + g.m();
		writer.println(line);

		for(int i = 0; i < g.n(); i++){
			line = g.nodeList().get(i).toString();
			writer.println(line);
		}

		for(int i = 0; i < g.n(); i++){
			for(int j = 0; j < g.edgeList().get(i).size(); j++){
				e = g.edgeList().get(i).get(j);
				line = e.start().toString();
				line += "," + e.end().toString();
				line += "," + e.data().get(0).toString();
				line += "," + e.data().get(1).toString();
				line += "," + e.data().get(2).toString();
				line += "," + e.data().get(3).toString();
				line += "," + e.data().get(4).toString();
				writer.println(line);

			}

		}

		writer.close();
	}

	// public static void main(String[] args) {

	// 	Parser p = new Parser();
	// 	Graph g = p.parse("testdata.csv");

	// 	g.print();
	// 	for(int i = 0; i < g.n(); i++){
	// 		for(int j = 0; j < g.edgeList().get(i).size(); j++){
	// 			System.out.println(g.edgeList().get(i).get(j).data());
	// 		}
	// 	}
	// 	System.out.println();
	// 	FileController fc = new FileController();
	// 	fc.save(g, "what.csv");

	// 	Graph g2 = fc.read("what.csv");
	// 	g2.print();
	// 	for(int i = 0; i < g2.n(); i++){
	// 		for(int j = 0; j < g2.edgeList().get(i).size(); j++){
	// 			System.out.println(g2.edgeList().get(i).get(j).data());
	// 		}
	// 	}
	// 	System.out.println();
	// }

}
