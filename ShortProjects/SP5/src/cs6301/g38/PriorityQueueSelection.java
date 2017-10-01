package cs6301.g38;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author Rajkumar PanneerSelvam - rxp162130 <br>
 *         Avinash Venkatesh - axv165330 <br>
 *         Rakesh Balasubramani - rxb162130 <br>
 *         HariPriyaa Manian - hum160030
 * 
 * @Description Implementation of PriorityQueue Selection algorithm using minHeap and maxHeap.
 * 
 */
public class PriorityQueueSelection<T> {

	private PriorityQueue<T> priorityQueue; // priority queue

	/**
	 * Method used to create maxHeap.
	 */
	private void createMaxHeap(int heapSize) {
		priorityQueue = new PriorityQueue<T>(heapSize,Collections.reverseOrder());
	}

	/**
	 * Method used to create minHeap.
	 */
	private void createMinHeap(int heapSize) {
		priorityQueue = new PriorityQueue<T>(heapSize);
	}

	/**Method used to add element into the heap.
	 * @param t - Element to be added.
	 */
	private void add(T t) {
		priorityQueue.add(t);
	}


	/**
	 *Method used to remove root element from the heap.
	 */
	private T remove() {
		if (priorityQueue.isEmpty()) {
			return null;
		}
		
		return priorityQueue.remove();
	}

	/**Method used to retrieve priority/root element from the heap.
	 * @return - Root element of the heap.
	 */
	private T peek() {

		return priorityQueue.peek();
	}

	/**Method used to check the emptiness the heap.
	 * @return - true if empty, otherwise false.
	 */
	private boolean isEmpty() {

		return priorityQueue.isEmpty();
	}
	
	/**Method used to find K largest elements using minHeap.
	 * @param arr - Given array
	 * @param k - number of largest elements to be selected.
	 * @return - K largest elements from the given array.
	 */
	public static <T extends Comparable<? super T>> T[] minHeapSelectAlgo(
			T[] arr, int k) {

		if (k <= 0) {

			return null;
		}

		T[] result = Arrays.copyOf(arr, k);

		if (k > arr.length) {
			return arr;
		}

		PriorityQueueSelection<T> minPq = new PriorityQueueSelection<>();
		minPq.createMinHeap(k);

		for (int i = 0; i < k; i++) {
			minPq.add(arr[i]);
		}

		for (int i = k; i < arr.length; i++) {
			if (!minPq.isEmpty()&& (minPq.peek().compareTo(arr[i]) < 0)) {
				minPq.remove();
				minPq.add(arr[i]);
			}
		}

	 T val;

		int a = 0;
		while ((val = minPq.remove()) != null) {
			result[a] = val;

			a++;
		}
		return result;
	}


	/**Method used to find K largest elements using maxHeap.
	 * @param arr - Given array
	 * @param k - number of largest elements to be selected.
	 * @return - Array of K largest elements from the given array.
	 */
	public static <T> T[] maxHeapSelectAlgo(T arr[], int k) {
		if (k <= 0) {

			return null;
		}

		T[] result = Arrays.copyOf(arr, k);

		if (k > arr.length) {
			return arr;
		}

		PriorityQueueSelection<T> maxPq = new PriorityQueueSelection<>();
		maxPq.createMaxHeap(arr.length);

		for (T i : arr) {
			maxPq.add(i);
		}

	
		T val;

		int a = 0;
		while ((val = maxPq.remove()) != null && k != 0) {
			result[a] = val;
			k--;
			a++;
		}

		return result;
	}

	
}
