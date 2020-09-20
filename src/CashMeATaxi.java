/**
* Author: Albert Zhou
* Revised: 19/09/2020
* Description: Module for the Cash Me A Taxi.
*/ 

package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * @brief A module for Cash Me A Taxi.
 */	
public class CashMeATaxi{

	private static Parser parser;
	private static FileController control;
	private static Graph g;
	
    /**
    * @brief Create Cash Me A Taxi.
    */
	public static void init(){
		parser = new Parser();
		control = new FileController();
		g = new Graph();
	}

	/**
    * @brief Create graph from data file.
	* @details Parse the file.
	* @param f the file name.
    */
	public static void read(String f){
		g = parser.parse(f);
	}

	/**
    * @brief Save the graph.
	* @details Save the graph.
	* @param f the file name.
    */
	public static void save(String f){
		control.save(g, f);
	}

	/**
    * @brief Load a graph.
	* @details Load the graph.
	* @param f the file name.
    */
	public static void load(String f){
		g = control.read(f);
	}

	/**
    * @brief Get the edges of a node.
	* @details Search g for n.
	* @param n the node.
	* @return e the edges.
    */
	public static Seq<Edge> search(Node n){
		GraphSearch search = new GraphSearch(g);
		return search.find(n);
	}

	/**
    * @brief Sort the edges.
	* @details Sort the edges by i.
	* @param i 0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll.
	* @param j 1 -> ascending, -1 -> descending.
	* @return e the sorted edges.
    */
	public static Seq<Edge> sort(int i, int j){
		GraphSort sort = new GraphSort(g);
		return sort.sort(i, j);
	}

	/**
    * @brief Find the shortest path.
	* @details Find the path.
	* @param a the starting node.
	* @param b the ending node.
	* @return p the shortest path.
    */
	public static Seq<Edge> path(Node a, Node b){
		GraphPath path = new GraphPath(g);
		return path.path(a, b);
	}

	public static void printout(Seq<Edge> e, String f){
		PrintStream  writer = null;
		String line;

		try{
			writer = new PrintStream(new File(f));
		} catch (FileNotFoundException exc) {
			throw new IllegalArgumentException("File does not exist.");
		}

		// TODO FORMAT BUFFER

		for(int i = 0; i < e.size(); i++){
			line = e.get(i).start().toString() + ",";
			line += e.get(i).end().toString() + ",";
			line += e.get(i).data().get(0) + ",";
			line += e.get(i).data().get(1) + ",";
			line += e.get(i).data().get(2) + ",";
			line += e.get(i).data().get(3) + ",";
			line += e.get(i).data().get(4);

			writer.println(line);
		}

		writer.close();
	}


	public static void main(String[] args) {

		init();

		Scanner in = new Scanner(System.in);
		String s;
		Seq<Edge> e;
		int option;

		while(true){

			System.out.println("Enter option:");
			System.out.println("1: Enter data file.");
			System.out.println("2: Save graph.");
			System.out.println("3: Load graph.");
			System.out.println("4: Search Node.");
			System.out.println("5: Sort Edges.");
			System.out.println("6: Shortest path.");

			s = in.nextLine();
			option = Integer.parseInt(s);

			switch(option){
				case 1:
				System.out.println("Enter file name.");
				s = in.nextLine();
				read(s);
				System.out.println("Graph successfully created.\n");
				break;

				case 2:
				System.out.println("Enter save file name.");
				s = in.nextLine();
				save(s);
				System.out.println("Graph successfully saved.\n");
				break;

				case 3:
				System.out.println("Enter save file name.");
				s = in.nextLine();
				load(s);
				System.out.println("Graph successfully loaded.\n");
				break;

				case 4:
				System.out.println("Enter node id.");
				s = in.nextLine();
				e = search(new Node(Integer.parseInt(s)));
				printout(e, "CMATOut.txt");
				System.out.println("Output printed to \"CMATOut.txt\".\n");
				break;

				case 5:
				System.out.println("Enter sort criteria.");
				System.out.println("0 -> trps, 1-> fr, 2 -> tp, 3 -> dst, 4 -> tll \n " +
									"+ \",\" 1 -> ascending, -1 -> descending.");
				s = in.nextLine();
				e = sort(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1]));
				printout(e, "CMATOut.txt");
				System.out.println("Output printed to \"CMATOut.txt\".\n");
				break;

				case 6:
				System.out.println("Enter start and end node ids.");
				System.out.println("Id #1 + \",\" Id #2.");
				s = in.nextLine();
				e = path(new Node(Integer.parseInt(s.split(",")[0])),
								new Node(Integer.parseInt(s.split(",")[1])));
				printout(e, "CMATOut.txt");
				System.out.println("Output printed to \"CMATOut.txt\".\n");
				break;

				default:
				System.out.println("Error try again.");

			}

		}

	}

}
