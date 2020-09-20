package cashmeataxi;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EdgeTest {
	
	private static Edge edge;
	private static Node nodeFrom;
	private static Node nodeTo;
	private static long numTrips;
	private static double fare;
	private static double tip;
	private static double dist;
	private static double tolls;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		nodeFrom = new Node(3, "Bronx", 1, 2);
		nodeTo = new Node(4, "Manhattan", 4, 5);
		fare = 35.42; 
		tip = 5.05;
		tolls = 4.0;
		numTrips = 1;
		dist = 1;
		
		edge = new Edge(nodeFrom, nodeTo, numTrips, fare, tip, dist, tolls);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFrom() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertEquals(edge.from(), nodeFrom);
	}

	@Test
	public void testTo() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertEquals(edge.to(), nodeTo);
	}

	@Test
	public void testGetAvgFare() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertTrue(edge.getAvgFare() == fare);
	}

	@Test
	public void testGetAvgTip() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertTrue(edge.getAvgTip() == tip);
	}

	@Test
	public void testGetAvgDist() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertTrue(edge.getAvgDist() == dist);
	}

	@Test
	public void testGetTolls() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertTrue(edge.getTolls() == tolls);
	}

	@Test
	public void testGetNumTrips() {
		edge = new Edge(nodeFrom, nodeTo, fare, tip, dist, tolls);
		assertTrue(edge.getNumTrips() == 1);
	}

	@Test
	public void testUpdateEdge() {
		long trips = edge.getNumTrips();
		Trip trip = new Trip(2, 6, 15.05, 5.06, 20.10, 3.10, "9:06:05", "9:45:20");
		edge.updateEdge(trip);
		assertTrue(edge.getAvgFare() == ((fare*trips) + trip.fare())/(trips+1));
		assertTrue(edge.getAvgTip() == ((tip*trips) + trip.tip())/(trips+1));
		assertTrue(edge.getAvgDist() == ((dist*trips) + trip.distance())/(trips+1));
	}
	
	@Test
	public void testCost() {
		assertTrue(edge.cost() == edge.getAvgDist()/(edge.getAvgFare() + edge.getAvgTip()));
	}
	
	@Test
	public void testCompareTo() {
		Node nodeFrom2 = new Node(3, "Bronx", 1, 2);
		Node nodeTo2 = new Node(4, "Manhattan", 4, 5);
		double fare2 = 35.42; 
		double tip2 = 5.05;
		double tolls2 = 4.0;
		long numTrips2 = 1;
		double dist2 = 1.0;
		
		Edge edge2 = new Edge(nodeFrom2, nodeTo2, numTrips2, fare2, tip2, dist2, tolls2);
		assertTrue(edge.compareTo(edge2) == 0);
	}
	
	@Test
	public void testHashCode() {
		assertTrue(edge.hashCode() == Integer.parseInt("" + nodeFrom.zoneId() + nodeTo.zoneId()));
	}
	
	@Test
	public void testEquals() {
		Node nodeFrom2 = new Node(3, "Bronx", 1, 2);
		Node nodeTo2 = new Node(4, "Manhattan", 4, 5);
		double fare2 = 35.42; 
		double tip2 = 5.05;
		double tolls2 = 4.0;
		long numTrips2 = 1;
		double dist2 = 1.0;
		Edge edge2 = new Edge(nodeFrom2, nodeTo2, numTrips2, fare2, tip2, dist2, tolls2);
		assertTrue(edge.equals(edge2));
		
		Node nodeFrom3 = new Node(1, "Manhattan", 1, 2);
		Node nodeTo3 = new Node(2, "NY", 4, 5);
		double fare3 = 35.42; 
		double tip3 = 5.05;
		double tolls3 = 4.0;
		long numTrips3 = 1;
		double dist3 = 1.0;
		Edge edge3 = new Edge(nodeFrom3, nodeTo3, numTrips3, fare3, tip3, dist3, tolls3);
		assertFalse(edge.equals(edge3));
	}


}
