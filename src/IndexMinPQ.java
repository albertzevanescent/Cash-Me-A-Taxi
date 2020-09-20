package src;

// From Algorithms https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/IndexMinPQ.java.html
// with removal of unused functionality
public class IndexMinPQ<Key extends Comparable<Key>> {
	private Key[] keys; // Keys of PQ
	private int[] pq; // Binary heap PQ
	private int[] qp; // Inverse of pq
	private int size = 0; // Size of PQ

	public IndexMinPQ(int size) {
		@SuppressWarnings("unchecked")
		Key[] k = (Key[]) new Comparable[size + 1];
		keys = k;
		pq = new int[size + 1];
		qp = new int[size + 1];

		for (int index = 0; index < qp.length; index++) {
			qp[index] = -1;
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public boolean contains(int index) {
		return qp[index] != -1;
	}

	// Insert a key at an index
	public void insert(int index, Key key) {
		if (size != keys.length - 1) {
			size++;

			keys[index] = key;
			pq[size] = index;
			qp[index] = size;

			swim(size);
		}
	}

	// Remove the minimal key and return its index
	public int deleteMin() {
		int minElementIndex = pq[1];
		exchange(1, size);
		size--;
		sink(1);

		keys[pq[size + 1]] = null;
		qp[pq[size + 1]] = -1;

		return minElementIndex;
	}

	// Change the key at an index
	public void changeKey(int index, Key key) {
		keys[index] = key;

		swim(qp[index]);
		sink(qp[index]);
	}

	private void swim(int index) {
		while (index / 2 >= 1 && more(index / 2, index)) {
			exchange(index / 2, index);
			index = index / 2;
		}
	}

	private void sink(int index) {
		while (index * 2 <= size) {
			int selectedChildIndex = index * 2;

			if (index * 2 + 1 <= size && more(index * 2, index * 2 + 1)) {
				selectedChildIndex = index * 2 + 1;
			}

			if (less(selectedChildIndex, index)) {
				exchange(index, selectedChildIndex);
			} else {
				break;
			}

			index = selectedChildIndex;
		}
	}

	private boolean less(int keyIndex1, int keyIndex2) {
		return keys[pq[keyIndex1]].compareTo(keys[pq[keyIndex2]]) < 0;
	}

	private boolean more(int keyIndex1, int keyIndex2) {
		return keys[pq[keyIndex1]].compareTo(keys[pq[keyIndex2]]) > 0;
	}

	private void exchange(int keyIndex1, int keyIndex2) {
		int temp = pq[keyIndex1];
		pq[keyIndex1] = pq[keyIndex2];
		pq[keyIndex2] = temp;

		qp[pq[keyIndex1]] = keyIndex1;
		qp[pq[keyIndex2]] = keyIndex2;
	}
}