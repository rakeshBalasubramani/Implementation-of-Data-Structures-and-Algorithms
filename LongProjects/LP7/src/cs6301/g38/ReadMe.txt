/* Readme.txt
						CS 6301.502 IMPLEMENTATION OF ADVANCED DATA STRUCTURES AND ALGORITHMS
						LONG PROJECT 7
GROUP 38
	RAKESH BALASUBRAMANI - rxb162130
	HARIPRIYAA MANIAN – hum160030
	RAJKUMAR PANNEER SELVAM - rxp162130
	AVINASH VENKATESH – axv165330

*/
Problem 1: 
	Implement Dinitz's algorithm for maximum flow.
Files included:
	1- LP7.java // Driver code
	2- Flow.java 
	3- MaxFlowGraph.java
	4- BFSFlowGraph.java

Input for LP7.java:
6 8
1 2 1
1 3 1
2 4 1
3 5 1
4 3 1
4 6 1
5 2 1
5 6 1
1 6

1 15
2 4
3 12
4 10
5 3
6 7
7 5
8 10
end

Output:
Running Dinitz's Algorithm
14
Time: 6 msec.
Memory: 1 MB / 121 MB.


Problem 2: 
	Implement Relabel-to-front algorithm for maximum flow.
Files included:
	1- LP7.java // Driver code
	2- Flow.java 
	3- MaxFlowGraph.java

Input for LP7.java:
6 8
1 2 1
1 3 1
2 4 1
3 5 1
4 3 1
4 6 1
5 2 1
5 6 1
1 6

1 15
2 4
3 12
4 10
5 3
6 7
7 5
8 10
end

Output:
Running relabelToFront Algorithm
14
Time: 6 msec.
Memory: 1 MB / 121 MB.
	