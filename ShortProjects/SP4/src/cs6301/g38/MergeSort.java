package cs6301.g38;

import java.util.Scanner;

public class MergeSort {

	private static int size = 10;
	private static int threshold = 1;
	
	//----------------merge sort as per text book---------------------
	private static void mergeSortAsPerTextBook(int[] arr) {
		 mergeSort1(arr, 0, arr.length-1);
		// printArray(arr);
	}
	private static void mergeSort1(int[] A, int start, int  end){
		if(start < end){
			int mid= (start+end)/2;
			mergeSort1(A, start, mid);
			mergeSort1(A, mid+1, end);
			merge1(A, start, mid, end);
		}
	}
	
	//merge method as described in text book
	private static void merge1(int[] A, int start, int mid, int end){
		long[] L = new long[mid-start+2]; // Long because we are using infinity as Long.MAX
		long[] R = new long[end-mid+1];
		
		//copying left half of A[] into L[] and copying right half into R[]
		int x=0;
		for(int i=start; i<=mid; i++){
			L[x++] = A[i];
		}
		int y=0;
		for(int j=mid+1; j<=end; j++){
			R[y++] = A[j];
		}
		
		//setting the boundaries
		L[mid-start+1] = R[end-mid] = Long.MAX_VALUE; // infinity
		
		// now comapre the left and right array and merge in the soretd order into A[]
		int i=0; 
		int j=0;
		for(int k=start; k<=end;k++){
			if(L[i] <= R[j]){
				A[k] = (int) L[i++];
			}
			else
			{
				A[k] = (int) R[j++];
			}
		}
	}

	//--------------mergesort passing temp array-------------------- 
	private static void mergeSortwithTemp(int[] arr) {
		int[] tmp = new int[arr.length];
		mergeSort2(arr, tmp, 0, arr.length - 1);
	//	printArray(tmp); // result stored in tmp[]
	}
	private static void mergeSort2(int[] A, int[] tmp, int start, int end) {
		if(start<end){
			int mid = (start+end)/2;
			mergeSort2(A, tmp, start, mid);
			mergeSort2(A, tmp,  mid+1, end);
			merge2(A,tmp, start, mid, end);
		}
	}	
	private static void merge2(int[] A, int[] tmp, int start, int mid, int end) {

		long[] L = new long [mid-start+2];
		long [] R = new long[end-mid+1];
		
		//copying left half of A[] into L[] and copying right half into R[]
		int x=0;
		for(int i=start; i<=mid; i++){
			L[x++] = A[i];
		}
		int y=0;
		for(int j=mid+1; j<=end; j++){
			R[y++] = A[j];
		}
		
		//setting the boundaries
		L[mid-start+1] = R[end-mid] = Long.MAX_VALUE;
		
		// now comapre the left and right array and merge in the soretd order into tmp[]
		int i=0; 
		int j=0;
		for(int k=start; k<=end;k++){
			if(L[i] <= R[j]){
				tmp[k] = (int) L[i++];
			}
			else
			{
				tmp[k] = (int) R[j++];
			}
		}
	}
	
	
	// --------------Using Insertion Sort when array size is smaller than threshold---------------------
	private static void mergeSortWithThresholdSize(int[] arr) {
		int[] tmp = new int[arr.length];
		mergeSort3(arr, tmp, 0, arr.length-1);
	//	printArray(arr);
	}
	private static void mergeSort3(int[] arr, int[] tmp, int start, int end){
		if(end-start < threshold){
			//System.out.println("Using Insertion Sort");
			insertionSort(arr);
		}
		else
		{
			//System.out.println("Using MergeSort");
			int mid = (start+end)/2;
			mergeSort3(arr, tmp, start, mid);
			mergeSort3(arr, tmp,  mid+1, end);
			merge3(arr,tmp, start, mid, end);
			
		}
	}
	
	private static void merge3(int[] arr, int[] tmp, int start, int mid, int end) {
		//copy arr into tmp
		for(int i=0;i<arr.length;i++){
			tmp[i] = arr[i];
		}
		int i = start;
		int j = mid+1;
		
		for(int k = start; k<end; k++){
			if((j>end) || (i<=mid && tmp[i]<=tmp[j])){
				arr[k] = tmp[i++];
			}
			else{
				arr[k] = tmp[j++];
			}
		}
	}
	
	//------------Avoid Copying to tmp array---------------------------------
	
	private static void mergeSortAvoidTmpCopy(int[] arr) {
		int[] tmp = new int[arr.length];
		//copy arr into tmp
		for(int i=0;i<arr.length;i++){
			tmp[i] = arr[i];
		}
		mergeSort4(arr, tmp, 0, arr.length-1);
	//	printArray(arr);
	}
	
	
	private static void mergeSort4(int[] arr, int[] tmp, int start, int end) {
		if(end-start < threshold){
			//System.out.println("Using Insertion Sort");
			insertionSort(arr);
		}
		else
		{
			//System.out.println("Using MergeSort");
			int mid = (start+end)/2;
			mergeSort4(tmp, arr, start, mid);
			mergeSort4(tmp, arr,  mid+1, end);
			merge4(tmp, arr, start, mid, end);
			
		}		
	}
	
	private static void merge4(int[] arr, int[] tmp, int start, int mid, int end) {
		int i= start;
		int j = mid+1;
		
		for(int k=start; k<end; k++){
			if((j>end) || (i<=mid && arr[i]<=arr[j])){
				tmp[k] = arr[i++];
			}
			else{
				tmp[k] = arr[j++];
			}
		}		
		
	}
	
	//-----------------Insertion Sort------------------
	public static  void insertionSort(int[] arr) {
		int size = arr.length;
		for (int i = 1; i < size; i++) {
			int key = arr[i];
			int j = i - 1;
			// Compare arr element with key and place the elements in correct
			// position
			while (j >= 0 && arr[j]>key){
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = key;
		}
	}
	
	
//	private static void printArray(int[] arr){
//		for(int i = 0; i<arr.length; i++){
//			System.out.println(arr[i]);
//		}
//	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the array size in millions ");
		int arrSize = in.nextInt();
		arrSize = arrSize * size;
		int[] arr = new int[arrSize];
		
		Timer t = new Timer();

		// Populating the array with random elements
		for (int i = 0; i < arrSize; i++) {
			arr[i] = (int) (Math.random() * arrSize);
		}
		System.out.println("Array to be sorted ");
	//	printArray(arr);
		
		
		
		System.out.println("3. Using Insertion Sort for array size smaller than threshold ");
		t.start(); // start timer
		mergeSortWithThresholdSize(arr);
		t.end();
		System.out.println(t); // end timer
		System.out.println();
		
		System.out.println("4. Avoid copying to tmp array ");
		t.start(); // start timer
		mergeSortAvoidTmpCopy(arr);
		t.end();
		System.out.println(t); // end timer
		System.out.println();
		
		
		System.out.println("Merge Sort");
		System.out.println("1. As described in TextBooks:");
		t.start(); // start timer
		mergeSortAsPerTextBook(arr);
		System.out.println(t.end()); // end timer
		System.out.println();
		
		System.out.println("2. Passing temp array as a parameter to merge:");
		t.start(); // start timer
		mergeSortwithTemp(arr);
		t.end();
		System.out.println(t); // end timer
		System.out.println();
		
		
		in.close();
		
	}
}
