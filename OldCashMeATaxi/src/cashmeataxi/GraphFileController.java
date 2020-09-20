package cashmeataxi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages the file IO to save graph data and read graph data from 
 * the graph data file.
 */
public class GraphFileController {
	
	private final String filename;
	
	public GraphFileController(String dataFileName) {
		filename = dataFileName;
		// examine existing file if such a file exists. The file must have the 
		// following content:
		// numRowsReadFromInput=<number of rows read from the input data file>
		// fromNode,toNode,numTrips,avgFare,avgTip,avgDistance,tolls
		// <each following row must contain the above fields for each edge in the graph>
		setupFile();
	}
	
	/**
	 * @return Creates a new Graph object given the data on the graph data file, 
	 * if there's no existing data, then return a new empty Graph.
	 * ASSUMES that this data file is VALID. 
	 */
	public Graph getGraph() {
		// read the graph data
		BufferedReader reader = null;
		List<Edge> edges = new LinkedList<Edge>();
		try {
			reader = new BufferedReader(new FileReader(filename));
			// read first line, we don't care about the number input rows 
			reader.readLine();
			// read second line, we can discard the table header
			reader.readLine();
			
			// finally, we get to the list of edge data
			String line = null;
			while ((line = reader.readLine()) != null) {
				// parse the edge data
				String[] vals = line.trim().split(",");
				Node fromNode = new Node(Integer.parseInt(vals[0].trim()));
				Node toNode = new Node(Integer.parseInt(vals[1].trim()));
				long numTrips = Integer.parseInt(vals[2].trim());
				double avgFare = Double.parseDouble(vals[3].trim());
				double avgTip = Double.parseDouble(vals[4].trim());
				double avgDistance = Double.parseDouble(vals[5].trim());
				double tolls = Double.parseDouble(vals[6].trim());
				
				// create new Edge object and add it to the list of edges
				Edge edge = new Edge(fromNode, toNode, numTrips, 
						avgFare, avgTip, avgDistance, tolls);
				edges.add(edge);
			}
			
			// if we reach here, that means we are successful in reading the graph data.
			reader.close();
			return new Graph(edges);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// if there were any failures during the parsing of the graph data, 
		// return a new empty graph.
		return new Graph();
	}
	
	/**
	 * Save the graph in the file.
	 * @param graph
	 * @param deltaNumRowsRead
	 */
	public void saveGraph(Graph graph, long deltaNumRowsRead) {
		long currentNumRowsRead = getNumRowsRead();
		
		// move the not tmp to the tmp
		new File(filename).renameTo(new File(filename + ".tmp"));

		WriterHelper.write(filename + ".tmp", "numRowsReadFromInput=" + 
					(currentNumRowsRead + deltaNumRowsRead), graph.edges());
		
		// move the tmp to the not tmp one once we reach here
		new File(filename + ".tmp").renameTo(new File(filename));
	}
	
	/**
	 * Generate a new data file name.
	 * @return
	 */
	public static String getNewDataSaveFileName() {
		String timestamp = new SimpleDateFormat("yyMMdd-HHmm").format(new Date());
		return "data/graph-" + timestamp + ".csv";
	}
	
	/**
	 * Sets up the graph data output file. If the file given by the state variable filename
	 * already exists, then verify its contents to make sure its in the correct format.
	 * If the file given by the state variable filename does not exist or has invalid
	 * contents, then create a new file (overriding the existing one) and write the skeleton
	 * contents (i.e. the table header).
	 */
	private void setupFile() {
		File file = new File(filename);
		boolean validFile = false;
		if (file.exists()) {
			// if file already exists, verify its content
			validFile = verifyFile(file);
		}
		
		// create a new file if file doesn't exist or if the existing file is invalid
		if (!file.exists() || !validFile) {
			try {
				// override existing file
				Writer fileWriter = new FileWriter(file, false);
				writeInitialContents(fileWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the number of rows that have been read from the input dataset. This number
	 * is saved in the first line of the graph data file specified by the state variable
	 * filename.
	 */
	private long getNumRowsRead() {
		long numRows = 0;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			numRows = Integer.parseInt(line.split("=")[1].trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return numRows;
	}
	
	/**
	 * Given the file writer fwriter, write the initial skeleton contents for the graph
	 * data file (i.e. the table header and the number of lines read from input dataset
	 * value to 0).
	 * @param fwriter
	 */
	private static void writeInitialContents(Writer fwriter) {
		BufferedWriter writer = new BufferedWriter(fwriter);
		try {
			writer.write("numRowsReadFromInput=0");
			writer.newLine();
			writer.write(WriterHelper.TABLE_HEADER);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Verify the given file by making sure its contents meet the required format for the
	 * graph data file.
	 * @param file
	 * @return
	 */
	private static boolean verifyFile(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			
			// first line must be: numRowsReadFromInput=<number of rows read from the input data file>
			line = reader.readLine();
			if (line == null || !line.contains("numRowsReadFromInput=")) 
				return false;
			
			// second line must be: fromNode,toNode,numTrips,avgFare,avgTip,avgDistance,tolls
			line = reader.readLine();
			if (line == null || !line.trim().equals
					(WriterHelper.TABLE_HEADER))
				return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
