package cashmeataxi;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeTest {

	private static Node node;
	private static int zoneId;
	private static String borough;
	private static int numStartTrips;
	private static int numEndTrips;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		zoneId = 1;
		borough = "Bronx";
		numStartTrips = 3;
		numEndTrips = 5;
		node = new Node(zoneId, borough, numStartTrips, numEndTrips);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testZoneId() {
		assertTrue(node.zoneId() == zoneId);
	}

	@Test
	public void testSetZoneId() {		
		int newZoneId = 4;
		node.setZoneId(newZoneId);
		assertTrue(node.zoneId() == newZoneId);
		node.setZoneId(zoneId);
	}
	
	@Test
	public void testEquals() {		
		Node node2 = new Node(2, "Manhattan", 2, 4);
		assertFalse(node.equals(node2));
		
		Node node3 = new Node(1, "Bronx", 3, 5);
		assertTrue(node.equals(node3));
	}
	
	@Test
	public void testToString() {		
		assertEquals(node.toString(), Integer.toString(zoneId));
	}
	
	@Test
	public void testHashCode() {		
		assertTrue(node.hashCode() == zoneId);
	}
	
	@Test
	public void testCost() {		
		assertTrue(node.cost() == Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void testSetCost() {	
		node.setCost(12.0);
		assertTrue(node.cost() == 12.0);
	}
	
	@Test
	public void testCompareTo() {
		Node node2 = new Node(2, "Manhattan", 2, 4);
		node.setCost(15.0);
		node2.setCost(20.0);
		assertTrue(node.compareTo(node2) == -1);
		node2.setCost(15.0);
		assertTrue(node.compareTo(node2) == 0);
		node2.setCost(10.0);
		assertTrue(node.compareTo(node2) == 1);
	}

}
