package cashmeataxi;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Controller for GUI application.
 */
public class ControllerForGUI {
	
	private static final String defaultInputDataFile = "data/yellow_tripdata_2019-01.csv";
	private String inputDataFile; // Input dataset file
	private String saveDataFile; // Graph data file to save the graph
	private Graph graph; // graph
	private State state; // Current state of the GUI application
	private Function function; // The current function that is to be run
	
	/**
	 * Current state of our GUI application.
	 */
	public enum State {
		PROMPT_INPUT_FILENAME,
		PROMPT_FUNCTION,
		PROMPT_OPTPATH_ARGS,
		PROMPT_FZONE_ARGS,
		PROMPT_CRITERIA,
		PROMPT_EXIST_DATAFILE,
	}
	
	/**
	 * Current function to run that is set by the user.
	 */
	public enum Function {
		OPTIMAL_PATH,
		FIND_ZONE,
		BUILD_GRAPH,
		SORT_EDGES,
		NONE,
	}
	
	/**
	 * Create a new ControllerForGUI object.
	 */
	public ControllerForGUI() {
		state = State.PROMPT_FUNCTION;
		function = Function.NONE;
		saveDataFile = "";	
		inputDataFile = "";
	}
	
	/**
	 * @return Current state.
	 */
	public State state() {
		return state;
	}
	
	/**
	 * Set the input dataset filename.
	 * @param filename
	 */
	public void setInputFilename(String filename) {
		inputDataFile = filename;
	}
	
	/**
	 * Set the function to run. 
	 * @param f
	 */
	public void setFunction(Function f) {
		this.function = f;

		if (saveDataFile.isBlank())
			state = State.PROMPT_EXIST_DATAFILE;
		else {
			switch(function) {
			case BUILD_GRAPH:
				state = State.PROMPT_INPUT_FILENAME;
				break;
			case OPTIMAL_PATH:
				state = State.PROMPT_OPTPATH_ARGS;
				break;
			case FIND_ZONE:
				state = State.PROMPT_FZONE_ARGS;
				break;
			case SORT_EDGES:
				state = State.PROMPT_CRITERIA;
				break;
			default:
				break;
			
			}
		}
	}

	/**
	 * Get the message to display on the GUI.
	 * @return
	 */
	public String getMsg() {
		String m = "Function: ";
		switch(function) {
		case BUILD_GRAPH:
			m += "Build graph.\n\n";
			break;
		case OPTIMAL_PATH:
			m += "Optimal path.\n\n";
			break;
		case FIND_ZONE:
			m += "Find zone.\n\n";
			break;
		case SORT_EDGES:
			m += "Sort edges.\n\n";
			break;
		default:
			m = "";
			break;
		}
		
		if (!saveDataFile.isEmpty()) {
			m += "Graph data file: " + saveDataFile + "\n\n";
		}
		
		m += "Input data file: " + getDatasetFilename() + "\n\n";
		
		switch(state) {
		case PROMPT_CRITERIA:
			m += "Enter a number to select which field to sort by:\n";
			m += "[1] sort by distance\n";
			m += "[2] sort by fare\n";
			m += "[3] sort by tip\n";
			m += "[4] sort by toll\n";
			m += "[5] sort by number of trips\n";
			return m;
		case PROMPT_EXIST_DATAFILE:
			return m + "Enter filepath of existing graph data file.\nIf no existing data file, press enter.";
		case PROMPT_FUNCTION:
			return "Please read the user guide before using.\nChoose a function from above menu.";
		case PROMPT_INPUT_FILENAME:
			return m + "Enter filepath of input dataset file.\nPress enter to use the default dataset specified above.";
		case PROMPT_OPTPATH_ARGS:
			return m + "Enter start and end zone id, separated by space.";
		case PROMPT_FZONE_ARGS:
			return m + "Enter zone id.";
		}
		return "";
	}
	
	/**
	 * Get the current function to run.
	 * @return
	 */
	public Function function() {
		return function;
	}
	
	/**
	 * Get the output graph data file. 
	 * @return
	 */
	public String graphDataFile() {
		return saveDataFile;
	}
	
	/**
	 * Set the graph data file name.
	 * @param filename
	 */
	public void setGraphDataFilename(String filename) {
		saveDataFile = filename;
	}
	
	/**
	 * Set the current state.
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * Build the graph.
	 * @param readInputData Whether to read from the input data set or not.
	 * @return The filename where we saved the graph data.
	 */
	public String buildGraph(boolean readInputData) {
		String msg = "";
		
		if (saveDataFile.equals("") || saveDataFile.equals("data/yellow_tripdata_2019-01.csv")) {
			 // Prevent overwriting original data
			saveDataFile = GraphFileController.getNewDataSaveFileName();
		}
		// create a graph, if there's existing data, create the graph based on this data
		GraphFileController graphFileC = new GraphFileController(saveDataFile);
		graph = graphFileC.getGraph();
		
		if (readInputData) {
			// now read the input data file and update graph as we go
			// read 1000000 rows at a time, save, and repeat.
			
			Parser parser = new Parser(getDatasetFilename());
			final long numRowsToRead = 1000000; // number of rows to read at a time
			long numRowsReadThisTime = 0;
			long totalNumRowsRead = 0;
			do {
				numRowsReadThisTime = parser.parseRows(graph, totalNumRowsRead, numRowsToRead);
				totalNumRowsRead += numRowsReadThisTime;
				graphFileC.saveGraph(graph, numRowsReadThisTime);
			} while (numRowsReadThisTime >= numRowsToRead);
		}
		
		return msg;
	}
	
	/**
	 * Find the optimal path given a start and end zone.
	 * @param startZone
	 * @param endZone
	 * @return The filename that the path data is saved in.
	 */
	public String findOptimalPath(int startZone, int endZone) {
		buildGraph();
		GraphSearch graphSearch = new GraphSearch(graph.edges());
		LinkedList<Node> optimalPath = 
				graphSearch.findOptimalPath(startZone, endZone);
		String outFile = "data/optimalPath-" + startZone + "-to-" + endZone + ".csv";
		WriterHelper.writeNodes(outFile, optimalPath);
		return outFile;
	}
	
	/**
	 * Find the zone given by its id.
	 */
	public String findZone(int zoneId) {
		buildGraph();
		GraphFind graphFind = new GraphFind(graph.edges());
		ArrayList<Edge> edges = graphFind.find(new Node(zoneId));
		String outFile = "data/zone-" + zoneId + "-data.csv";
		WriterHelper.write(outFile, edges);
		return outFile;

	}
	
	/**
	 * Sort the graph edges given a field to sort by.
	 * Sorted in ascending order.
	 * @param criteria
	 * @return The filename that the sorted data is saved in.
	 */
	public String sortEdges(int criteria) {
		buildGraph();
		ArrayList<Edge> edges = graph.edges();
		String suffix = "-sortedby-";
		
		switch(criteria) {
		case 1:
			SortedListOfEdges.sortByDist(edges);
			suffix += "dist.csv";
			break;
		case 2:
			SortedListOfEdges.sortByFare(edges);
			suffix += "fare.csv";
			break;
		case 3:
			SortedListOfEdges.sortByTip(edges);
			suffix += "tips.csv";
			break;
		case 4:
			SortedListOfEdges.sortByToll(edges);
			suffix += "toll.csv";
			break;
		case 5:
			SortedListOfEdges.sortByNumTrips(edges);
			suffix += "trips.csv";
			break;
		}
		
		String sortedFilename = saveDataFile.replace(".csv", suffix);
		WriterHelper.write(sortedFilename, edges);
		return sortedFilename;
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
	
	/**
	 * Get the input dataset file to use.
	 * @return The inputDataFile if it is not blank, otherwise the defaultInputDataFile.
	 */
	private String getDatasetFilename() {
		if (inputDataFile.isBlank()) return defaultInputDataFile;
		else return inputDataFile;
	}
}
