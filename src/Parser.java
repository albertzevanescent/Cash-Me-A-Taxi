/**
* Author: Albert Zhou
* Revised: 19/09/2020
* Description: Module for the parser.
*/ 

package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @brief A module for the parser.
 */	
public class Parser{
	
    /**
    * @brief Create the parser.
    */
	public Parser(){
		;
	}

	/**
    * @brief Parse the file.
	* @details For each line, create 2 nodes and an edge and add it to a graph.
	* @param f the file name.
	* @return g the graph.
    */
	public static Graph parse(String f){

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
		reader.nextLine();
		
		while (reader.hasNext()) {
			line = reader.nextLine();
			data = line.split(",");

			int zoneA = Integer.parseInt(data[7]);
			int zoneB = Integer.parseInt(data[8]);
			d = new ArrayList<Double>();
			d.add(1.0);
			d.add(Double.parseDouble(data[16]));
			d.add(Double.parseDouble(data[13]));
			d.add(Double.parseDouble(data[4]));
			d.add(Double.parseDouble(data[14]));
			
			a = new Node(zoneA);
			b = new Node(zoneB);
			e = new Edge(a, b, d);
			
			g.addNode(a);
			g.addNode(b);
			g.addEdge(e);
		}

		reader.close();
		return g;

	}

	// public static void main(String[] args) {

	// 	Parser p = new Parser();
	// 	Graph g = p.parse("testdata.csv");

	// 	g.print();

	// }

}
