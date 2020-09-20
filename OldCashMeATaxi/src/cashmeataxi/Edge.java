package cashmeataxi;

/**
 * An edge represents a connection from one Node to another. Its state variables include ID,
 * starting Node, ending Node, average fare, average tips, average distance, tolls, trip count,
 * and weight. It has a function update that updates its state when given a new Trip.
 */
public class Edge implements Comparable<Edge> {
	private Node nodeFrom; // source node
	private Node nodeTo; // destination node
	private double avgFare; // average fare
	private double avgTip; // average tip earned
	private double avgDist; // average distance traveled
	private double tolls; // Tolls paid
	private long numTrips; // total number of trips between nodes

	/**
	 * Create an Edge with the given data.
	 * @param nodeFrom The source node.
	 * @param nodeTo The destination node.
	 * @param numTrips The number of trips made between these nodes.
	 * @param fare 
	 * @param tip
	 * @param dist
	 * @param tolls
	 */
	public Edge(Node nodeFrom, Node nodeTo, long numTrips, double fare, 
			double tip, double dist, double tolls) {
		this.nodeFrom = nodeFrom;
		this.nodeTo = nodeTo;
		this.avgFare = fare;
		this.avgTip = tip;
		this.avgDist = dist;
		this.tolls = tolls;
		this.numTrips = numTrips;
	}

	/**
	 * Create an Edge with the given data.
	 * @param nodeFrom
	 * @param nodeTo
	 * @param fare
	 * @param tip
	 * @param dist
	 * @param tolls
	 */
	public Edge(Node nodeFrom, Node nodeTo, double fare, 
			double tip, double dist, double tolls) {
		this(nodeFrom, nodeTo, 1, fare, tip, dist, tolls);
	}

	/**
	 * @param t Create a new Edge given a Trip object.
	 */
	public Edge(Trip t) {
		this(new Node(t.fromZoneId()), new Node(t.toZoneId()), t.fare(), t.tip(), t.distance(), t.tolls());
	}

	/**
	 * @return from Node
	 */
	public Node from() {
		return nodeFrom;
	}

	/**
	 * @return to Node
	 */
	public Node to() {
		return nodeTo;
	}

	/**
	 * @return Average fare of this edge.
	 */
	public double getAvgFare() {
		return avgFare;
	}

	/**
	 * @return Average tip of this edge.
	 */
	public double getAvgTip() {
		return avgTip;
	}

	/**
	 * @return Average distance of this edge.
	 */
	public double getAvgDist() {
		return avgDist;
	}

	/**
	 * @return Average tolls of this edge.
	 */
	public double getTolls() {
		return tolls;
	}

	/**
	 * @return Number of trips in this edge.
	 */
	public long getNumTrips() {
		return numTrips;
	}

	/**
	 * Update this edge given the Trip object.
	 * Recalculates its average fare, average tip and average distance fields given the
	 * new Trip object data.
	 * @param t
	 */
	public void updateEdge(Trip t) {
		avgFare = ((avgFare * numTrips) + t.fare()) / (numTrips + 1);
		avgTip = ((avgTip * numTrips) + t.tip()) / (numTrips + 1);
		avgDist = ((avgDist * numTrips) + t.distance()) / (++numTrips);
	}

	/**
	 * Get Edge data in String with fields separated by commas.
	 * @return
	 */
	public String toRowString() {
		return toString();
	}

	/**
	 * @return The "cost" of this edge.
	 */
	public double cost() {
		return avgDist / (avgFare + avgTip);
	}

	/**
	 * Compares this Edge with the given edge.
	 */
	@Override
	public int compareTo(Edge other) {
		if (cost() > other.cost())
			return 1;
		else if (cost() < other.cost())
			return -1;
		else
			return 0;
	}

	/**
	 * The hash code is the integer equivalent of nodeFrom.zoneId() concatenated with
	 * nodeTo.zoneId()
	 * @return The hashcode for this edge.
	 */
	@Override
	public int hashCode() {
		return Integer.parseInt("" + nodeFrom.zoneId() + nodeTo.zoneId());
	}

	/**
	 * Determines whether this Edge is considered equal to the given edge.
	 * This object is considered equivalent to the given object if its source nodes and
	 * destination nodes are equal.
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof Edge && nodeFrom.equals(((Edge) other).nodeFrom)
				&& nodeTo.equals(((Edge) other).nodeTo);
	}

	/**
	 * @return The String representation of this Edge.
	 */
	@Override
	public String toString() {
		return nodeFrom.toString() + "," + nodeTo.toString() + "," + numTrips + "," + avgFare + "," + avgTip + ","
				+ avgDist + "," + tolls;
	}
}
