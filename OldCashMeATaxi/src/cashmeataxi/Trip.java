package cashmeataxi;

/**
 * Wrapper class to encapsulate a single Trip record, whose data is retrieved from a single row
 * in the dataset.
 */
public class Trip {
	
	private final int from;	// source zone ID
	private final int to; // destination zone ID
	private final double fare; // the fare
	private final double tip; // the tip
	private final double distance; // the distance for this trip
	private final double tolls; // the tolls
	private final String startTime; // the start time
	private final String endTime; // the end time
	
	/**
	 * Create a new Trip object.
	 * @param fromZoneId
	 * @param toZoneId
	 * @param fare
	 * @param tip
	 * @param distance
	 * @param tolls
	 * @param startTime
	 * @param endTime
	 */
	public Trip(int fromZoneId, int toZoneId, double fare, 
			double tip, double distance, double tolls, String startTime, String endTime) {
		from = fromZoneId;
		to = toZoneId;
		this.fare = fare;
		this.tip = tip;
		this.distance = distance;
		this.tolls = tolls;
		this.startTime = startTime;
		this.endTime = endTime;		
	}
	
	/**
	 * The src zone.
	 * @return
	 */
	public int fromZoneId() {
		return from;
	}
	
	/**
	 * The dst zone.
	 * @return
	 */
	public int toZoneId() {
		return to;
	}
	
	/**
	 * The trip fare.
	 * @return
	 */
	public double fare(){
		return this.fare;
	}

	/**
	 * The trip tip.
	 * @return
	 */
	public double tip(){
		return this.tip;
	}
	
	/**
	 * The trip distance.
	 * @return
	 */
	public double distance(){
		return this.distance;
	}
	
	/**
	 * The trip tolls.
	 * @return
	 */
	public double tolls(){
		return this.tolls;
	}
	
	/**
	 * The trip start time.
	 * @return
	 */
	public String startTime(){
		return this.startTime;
	}
	
	/**
	 * The trip end time.
	 * @return
	 */
	public String endTime(){
		return this.endTime;
	}
}
