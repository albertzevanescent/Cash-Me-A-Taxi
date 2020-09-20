package cashmeataxi;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Command-line controller. 
 */
public class ControllerForCmdLine {
	
	private String inputDataFile;
	private String saveDataFile;
	private Graph graph;

	/*
	 * Command-line arguments: csvFilename functionToRun args loadExistingDataFile
	 * 
	 * functionToRun:
	 *	· 1: find optimal paths from zone A to zone B. Args required: startZone endZone
	 *	· 2: find zone. Args required: zone id
	 *	· 3: sort edges based on given a critera. Args required: criteriaId
	 *			criteriaId can be:
	 *				1 - sort by distance
	 *				2 - sort by fare
	 *				3 - sort by tip
	 *				4 - sort by toll
	 *				5 - sort by number of trips
	 *	· 4: build graph. 
	 *	required: None
	 * 
	 * loadExistingDataFile:
	 *	· load existing graph data that was previously saved by this program. Eg. graphdata-200309-1619.csv 
	 */
	public void run(String[] args) {
		saveDataFile = "";
		graph = null;
		
		/* read the command-line arguments */
		if (args.length < 2) {
			throw new IllegalArgumentException("Please enter the csvDataFilename and the functionToRun.");
		}
		inputDataFile = args[0];
		byte functionToRun = (byte) Integer.parseInt(args[1]);
		
		/* Run the appropriate function based on functionToRun argument */
		switch(functionToRun) {
		case 1:
			if (args.length < 4)
				throw new IllegalArgumentException("Please enter the start and end zone ids.");
			if (args.length > 4) saveDataFile = args[4];
			findOptimalPath(args[2], args[3]);
			break;
		case 2:
			if (args.length < 3)
				throw new IllegalArgumentException("Please enter the zone id.");
			if (args.length > 3) saveDataFile = args[3];
			findZone(Integer.parseInt(args[2]));
			break;
		case 3:
			if (args.length < 3)
				throw new IllegalArgumentException("Please enter the criteria to sort by.");
			int criteria = Integer.parseInt(args[2]);
			if (args.length > 3) saveDataFile = args[3];
			sortEdges(criteria);
			break;
		case 4:
			saveDataFile = args[2];
			buildGraph(true);
			break;
		default:
			throw new IllegalArgumentException("Invalid functionToRun.");
		}
		
	}
	
	/**
	 * Build a graph and save the graph data into a file. This also sets the state variable
	 * graph with the Graph object created.
	 * If readInputData is true, then read the input dataset specified by the state 
	 * variable inputDataFile to build the graph. 
	 * If readInputData is false, then build the graph based on an existing graph data
	 * file specified by the state variable saveDataFile.
	 * @param readInputData
	 */
	private void buildGraph(boolean readInputData) {
		if (saveDataFile.equals("")) {
			saveDataFile = GraphFileController.getNewDataSaveFileName();
		}
		// create a graph, if there's existing data, create the graph based on this data
		GraphFileController graphFileC = new GraphFileController(saveDataFile);
		System.out.println("Building the graph (" + saveDataFile + ")");
		graph = graphFileC.getGraph();
		
		if (readInputData) {
			// now read the input data file and update graph as we go
			// read 1000000 rows at a time, save, and repeat.
			Parser parser = new Parser(inputDataFile);
			final long numRowsToRead = 1000000; // number of rows to read at a time
			long numRowsReadThisTime = 0;
			long totalNumRowsRead = 0;
			do {
				numRowsReadThisTime = parser.parseRows(graph, totalNumRowsRead, numRowsToRead);
				totalNumRowsRead += numRowsReadThisTime;
				graphFileC.saveGraph(graph, numRowsReadThisTime);
			} while (numRowsReadThisTime >= numRowsToRead);
		}
	}
	
	/**
	 * Find the optimal path from one zone to another. Writes the result into a file.
	 * @param startZone
	 * @param endZone
	 */
	private void findOptimalPath(String startZone, String endZone) {
		buildGraph();
		System.out.println("Finding the most optimal path between zones " + startZone + " and " + endZone);
		GraphSearch graphSearch = new GraphSearch(graph.edges());
		LinkedList<Node> optimalPath = graphSearch.findOptimalPath(
				Integer.parseInt(startZone), Integer.parseInt(endZone));
		WriterHelper.writeNodes("data/optimalPath-" + startZone + "-to-" + endZone + ".csv", optimalPath);
	}
	
	/**
	 * Find a zone given by the zone ID. Writes the result into a text file.
	 * @param zoneId
	 */
	private void findZone(int zoneId) {
		buildGraph();
		System.out.println("Finding a zone.");
		GraphFind graphFind = new GraphFind(graph.edges());
		ArrayList<Edge> edges = graphFind.find(new Node(zoneId));
		String searchFilename = saveDataFile.replace(".csv", "-") + zoneId + ".csv";
		System.out.println("Writing zone data to  " + searchFilename);
		WriterHelper.write(searchFilename, edges);	
	}
	
	/**
	 * Sort the graph edges based on a criteria.
	 * @param criteria
	 */
	private void sortEdges(int criteria) {
		buildGraph();
		ArrayList<Edge> edges = graph.edges();
		String suffix = "-sortedby-";
		
		switch(criteria) {
		case 1:
			System.out.println("Sorting edges by distance.");
			SortedListOfEdges.sortByDist(edges);
			suffix += "dist.csv";
			break;
		case 2:
			System.out.println("Sorting edges by fare.");
			SortedListOfEdges.sortByFare(edges);
			suffix += "fare.csv";
			break;
		case 3:
			System.out.println("Sorting edges by tips.");
			SortedListOfEdges.sortByTip(edges);
			suffix += "tips.csv";
			break;
		case 4:
			System.out.println("Sorting edges by tolls.");
			SortedListOfEdges.sortByToll(edges);
			suffix += "toll.csv";
			break;
		case 5:
			System.out.println("Sorting edges by number of trips.");
			SortedListOfEdges.sortByNumTrips(edges);
			suffix += "trips.csv";
			break;
		}
		
		String sortedFilename = saveDataFile.replace(".csv", "") + suffix;
		System.out.println("Writing sorted edges result to " + sortedFilename);
		WriterHelper.write(sortedFilename, edges);
	}
	
	/**
	 * Builds the graph.
	 * First checks if there is an existing graph data file specified by a non-empty
	 * saveDataFile. If there is, then build the graph without reading the input dataset.
	 * If there is no existing graph data file specified, then builds the graph by reading
	 * the input dataset.
	 */
	private void buildGraph() {
		if (!saveDataFile.equals("")) buildGraph(false);
		if (graph == null || graph.isEmpty()) buildGraph(true);
	}
}
