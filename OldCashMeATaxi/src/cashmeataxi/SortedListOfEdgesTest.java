package cashmeataxi;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SortedListOfEdgesTest {
	
	private static ArrayList<Edge> edges;
	private static SortedListOfEdges sort;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Edge edge1 = new Edge(new Node(1, "Queens", 1, 2), new Node(1, "EWR", 1, 2), 3, 35.42, 1.05, 35.0, 4.0);
		Edge edge2 = new Edge(new Node(1, "Manhattan", 1, 2), new Node(1, "Queens", 1, 2), 5, 20.5, 5.05, 17.0, 4.0);
		Edge edge3 = new Edge(new Node(1, "Manhattan", 1, 2), new Node(1, "Brooklyn", 1, 2), 6, 16.50, 15.05, 20.0, 4.0);
		
		edges = new ArrayList<Edge>();
		edges.addAll(Arrays.asList(edge1, edge2, edge3));
		
		sort = new SortedListOfEdges();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSortByDist() {
		sort.sortByDist(edges);
		for (int i = 0; i < edges.size() - 1; i++) {
			assertTrue(edges.get(i).getAvgDist() <= edges.get(i+1).getAvgDist());
		}
	}

	@Test
	public void testSortByFare() {
		sort.sortByFare(edges);
		for (int i = 0; i < edges.size() - 1; i++) {
			assertTrue(edges.get(i).getAvgFare() <= edges.get(i+1).getAvgFare());
		}
	}

	@Test
	public void testSortByTip() {
		sort.sortByTip(edges);
		for (int i = 0; i < edges.size() - 1; i++) {
			assertTrue(edges.get(i).getAvgTip() <= edges.get(i+1).getAvgTip());
		}
	}

	@Test
	public void testSortByToll() {
		sort.sortByToll(edges);
		for (int i = 0; i < edges.size() - 1; i++) {
			assertTrue(edges.get(i).getTolls() <= edges.get(i+1).getTolls());
		}
	}

	@Test
	public void testSortByNumTrips() {
		sort.sortByNumTrips(edges);
		for (int i = 0; i < edges.size() - 1; i++) {
			assertTrue(edges.get(i).getNumTrips() <= edges.get(i+1).getNumTrips());
		}
	}

}
