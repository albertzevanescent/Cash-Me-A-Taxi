package cashmeataxi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class to help write data onto csv files, specifically a list of edges or nodes from a graph.
 */
public class WriterHelper {
	
	/**
	 * The graph data table must have this header.
	 * Each row represents an edge in the graph.
	 */
	public final static String TABLE_HEADER = 
			"fromNode,toNode,numTrips,avgFare,avgTip,avgDistance,tolls";
	
	/**
	 * Write the given edges data to the given file. 
	 * @param filename
	 * @param edges
	 */
	public static void write(String filename, List<Edge> edges) {
		write(filename, "", edges);
	}
	
	/**
	 * Write the given edges data to the given file, after a pre message that
	 * will come before the table.
	 * @param filename
	 * @param pre
	 * @param edges
	 */
	public static void write(String filename, String pre, List<Edge> edges) {
		if (filename.equals("data/yellow_tripdata_2019-01.csv")) { // Prevent overwriting original data
			return;
		}
		BufferedWriter writer = null;
		File file = new File(filename);
		try {
			writer = new BufferedWriter(new FileWriter(file, false));
			// write the first row(s)
			if (!pre.isBlank()) {
				writer.write(pre);
				writer.newLine();
			}
			
			// write the table header
			writer.write(TABLE_HEADER);
			writer.newLine();
			
			// now write the graph data - each line has a new edge
			for (Edge edge : edges) {
				writer.write(edge.toRowString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Write the given nodes data in the given file.
	 * @param filename
	 * @param nodes
	 */
	public static void writeNodes(String filename, List<Node> nodes) {
		if (filename.equals("data/yellow_tripdata_2019-01.csv")) { // Prevent overwriting original data
			return;
		}
		BufferedWriter writer = null;
		File file = new File(filename);
		try {
			writer = new BufferedWriter(new FileWriter(file, false));
			
			// now write the graph data - each line has a new edge
			for (Node node : nodes) {
				writer.write(node.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
