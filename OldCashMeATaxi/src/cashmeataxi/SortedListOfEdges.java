package cashmeataxi;

import java.util.ArrayList;

/**
 * Sorts a list of edges by a given field such as average distance, average fare, tips, tolls, and
 * the number of trips made between the nodes in that edge.
 */
public class SortedListOfEdges {
	
	/**
	 * Sorts by distance
	 * @param edgeList
	 */
	public static void sortByDist(ArrayList<Edge> edgeList) { 
		Mergesort merge = new Mergesort();
		merge.sort(edgeList, "dist");
	}

	/**
	 * sorts by fare
	 * @param edgeList
	 */
	public static void sortByFare(ArrayList<Edge> edgeList) { 
		Mergesort merge = new Mergesort();
		merge.sort(edgeList, "fare");
	}

	/**
	 * sorts by tip
	 * @param edgeList
	 */
	public static void sortByTip(ArrayList<Edge> edgeList) { 
		Mergesort merge = new Mergesort();
		merge.sort(edgeList, "tip");
	}

	/**
	 * sorts by toll
	 * @param edgeList
	 */
	public static void sortByToll(ArrayList<Edge> edgeList) { 
		Mergesort merge = new Mergesort();
		merge.sort(edgeList, "toll");
	}

	/**
	 * sorts by number of trips
	 * @param edgeList
	 */
	public static void sortByNumTrips(ArrayList<Edge> edgeList) { 
		Mergesort merge = new Mergesort();
		merge.sort(edgeList, "numTrips");
	}
}

/**
 * Merge sort class.
 */
class Mergesort {
	public void merge(Edge arr[], int l, int m, int r, String opCode) {
		int lMid = m - l + 1;
		int rMid = r - m;

		Edge L[] = new Edge[lMid];
		Edge R[] = new Edge[rMid];

		for (int i = 0; i < lMid; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < rMid; ++j)
			R[j] = arr[m + 1 + j];
		int i = 0, j = 0;

		int bot = l;
		boolean comp = false;
		while (i < lMid && j < rMid) {
			switch (opCode) {
			case "dist":
				comp = (L[i].getAvgDist() <= R[j].getAvgDist());
				break;
			case "fare":
				comp = (L[i].getAvgFare() <= R[j].getAvgFare());
				break;
			case "tip":
				comp = (L[i].getAvgTip() <= R[j].getAvgTip());
				break;
			case "toll":
				comp = (L[i].getTolls() <= R[j].getTolls());
				break;
			case "numTrips":
				comp = (L[i].getNumTrips() <= R[j].getNumTrips());
				break;
			}
			if (comp) {
				arr[bot] = L[i];
				i++;
			} else {
				arr[bot] = R[j];
				j++;
			}
			bot++;
		}

		while (i < lMid) {
			arr[bot] = L[i];
			i++;
			bot++;
		}

		while (j < rMid) {
			arr[bot] = R[j];
			j++;
			bot++;
		}
	}

	public void sort(ArrayList<Edge> arr, String opCode) {
		Edge newArr[] = new Edge[arr.size()];

		for (int i = 0; i < arr.size(); i++)
			newArr[i] = arr.get(i);

		sort(newArr, 0, arr.size() - 1, opCode);

		for (int i = 0; i < arr.size(); i++)
			arr.set(i, newArr[i]);
	}

	public void sort(Edge arr[], int l, int r, String opCode) {
		if (l < r) {
			int m = (l + r) / 2;

			sort(arr, l, m, opCode);
			sort(arr, m + 1, r, opCode);

			merge(arr, l, m, r, opCode);
		}
	}
}