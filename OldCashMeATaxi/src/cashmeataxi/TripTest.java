package cashmeataxi;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TripTest {
	
	private static Trip a;
	private static int fromZoneId;
	private static int toZoneId;
	private static double fare;
	private static double tip;
	private static double distance;
	private static double tolls;
	private static String startTime;
	private static String endTime;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fromZoneId = 1;
		toZoneId = 2;
		fare = 12.50;
		tip = 10.20;
		distance = 50.36;
		tolls = 2.06;
		startTime = "9:06:05";
		endTime = "9:35:05";
		
		a = new Trip(fromZoneId, toZoneId, fare, tip, distance, tolls, startTime, endTime);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFromZoneId() {
		assertTrue(a.fromZoneId() == fromZoneId);
	}
	
	@Test
	public void testToZoneId() {
		assertTrue(a.toZoneId() == toZoneId);
	}

	@Test
	public void testFare() {
		assertTrue(a.fare() == fare);
	}

	@Test
	public void testTip() {
		assertTrue(a.tip() == tip);
	}

	@Test
	public void testDistance() {
		assertTrue(a.distance() == distance);
	}

	@Test
	public void testTolls() {
		assertTrue(a.tolls() == tolls);
	}

	@Test
	public void testStartTime() {
		assertEquals(a.startTime(), startTime);	
		}

	@Test
	public void testEndTime() {
		assertEquals(a.endTime(), endTime);	
	}

}
