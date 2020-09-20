package cashmeataxi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import cashmeataxi.Trip;

/**
 * Parse input dataset values into Trip data and then a Graph. 
 */
public class Parser {
	
	private final String inputFilename;
	
	/**
	 * Create new Parser given the filename for the dataset.
	 * @param inputDataFile
	 */
	public Parser(String inputDataFile) {
		this.inputFilename = inputDataFile;
	}
	
	/**
	 * @param graph
	 * @param startRow
	 * @param numRowsToRead
	 * @return The number of rows successfully parsed.
	 */
	public long parseRows(Graph graph, long startRow, long numRowsToRead) {
		BufferedReader reader = null;
		long numRows = 0;
		
		try {
			reader = new BufferedReader(new FileReader(inputFilename));
			
			// read and discard first row.
			// also read and discard startRow number of rows. 
			for (long i = 0; i < 1 + startRow; i++) {
				reader.readLine();
			}
			
			// now parse each following row
			String line = null;
			while ((line = reader.readLine()) != null && (numRowsToRead - numRows > 0)) {
				// parse the data
			    String[] data = line.split(",");
			    int zoneA = Integer.parseInt(data[7]);
			    int zoneB = Integer.parseInt(data[8]);

		    	double fare = Double.parseDouble(data[16]);
		    	double tip = Double.parseDouble(data[13]);
		    	double distance = Double.parseDouble(data[4]);
		    	double tolls = Double.parseDouble(data[14]);
		    	String startTime = data[1];
		    	String endTime = data[2];
		    	
		    	// create a new Trip object
		    	Trip trip = new Trip(zoneA, zoneB, fare, tip, distance, tolls, startTime, endTime);
		    	
		    	// add the new Trip to the Graph
		    	graph.addTrip(trip);
		    	
		    	numRows++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return numRows;
	}
}
