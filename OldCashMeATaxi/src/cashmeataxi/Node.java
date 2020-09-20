package cashmeataxi;

/**
 * A node represents a zone in New York City, with its id being the NYC taxi zone id.
 */
public class Node implements Comparable<Node> {
	private int zoneId; //Zone ID
	private double cost; // for use in the graph search alg
	
	/**
	 * @param zoneId
	 * @param borough
	 * @param numStartTrips
	 * @param numEndTrips
	 */
	public Node(int zoneId, String borough, int numStartTrips, int numEndTrips) {
		this.zoneId = zoneId;
		cost = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * @param zoneId
	 * Create a new Node given only the zone id.
	 */
	public Node(int zoneId) {
		this.zoneId = zoneId;
		cost = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * The zone id.
	 * @return
	 */
	public int zoneId() {
		return zoneId;
	}
	
	/**
	 * Set the zone id.
	 * @param zoneId
	 */
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	
	/**
	 * Whether this Node is considered to be equal to the given object.
	 * @return True if the other object is a Node instance and its zoneId is the same is
	 * this object’s zoneId.
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof Node && zoneId == ((Node) other).zoneId();
	}
	
	/**
	 * @return The String representation of this Node.
	 */
	@Override
	public String toString() {
		return "" + zoneId;
	}
	
	/**
	 * @return An integer hashcode which is the zone ID.
	 */
	@Override
	public int hashCode() {
		return zoneId;
	}
	
	/**
	 * Set the cost for this node - for use in the graph search alg.
	 * @param cost
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	/**
	 * For use in the graph search alg.
	 * @return
	 */
	public Double cost() {
		return cost;
	}

	/**
	 * For use in the graph search alg.
	 * @param other
	 * @return
	 */
	@Override
	public int compareTo(Node other) {
		if (cost > other.cost) return 1;
		else if (cost < other.cost) return -1;
		else return 0;
	}
}
