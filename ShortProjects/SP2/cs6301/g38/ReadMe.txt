/* Readme.txt
CS 6301.502. IMPLEMENTATION OF ADVANCED DATA STRUCTURES AND ALGORITHMS
				SHORT PROJECT 2
GROUP 38
	RAKESH BALASUBRAMANI - rxb162130
	HARIPRIYAA MANIAN – hum160030
	RAJKUMAR PANNEER SELVAM - rxp162130
	AVINASH VENKATESH – axv165330

*/
Problem 1:  Operations on Lists implementing Sorted Sets

Files:
	1- SortedSets.java

Input:
	Two lists on which the set operations are done 

Output:
	Result of union, intersection and difference operations
	

Sample Execution:
	List1 
	[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
	List2 
	[4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
	Intersect:  [4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
	Union:  [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
	Difference:  [0, 1, 2, 3, 14, 15, 16, 17, 18, 19]
	List1 
	[Angel, Banana, Kumar, Raj, zebra]
	List2 
	[Raj, Rakesh, zebra]
	Intersect:  [Raj, zebra]
	Union:  [Angel, Banana, Kumar, Raj, Rakesh, zebra]
	Difference:  [Angel, Banana, Kumar]

--------------------------------------------------------------------------------------------------------


Problem 2: Merge Sort on Linked Lists
Files :
	SortableList.java
Input:
	List to be Sorted
Output:
	Sorted List
Sample Execution:

 Interger inputs :
11: 10 9 8 7 6 5 4 3 2 1 0 
After sorting the integer list :
11: 0 1 2 3 4 5 6 7 8 9 10 
String inputs :
5: zebra Lion Tiger Animal Monkey 
After sorting the string list :
5: Animal Lion Monkey Tiger zebra 


------------------------------------------------------------------------------------------------------------------


Problem 3: Multi Unzip
File:
	SinglyLinkedList.java	
Input:
	List	
Output:
	Re-ordered List
Sample Execution:

Enter size of the list:
10
Enter multiUnzip parameter:
4
Enter values:
1
2
3
4
5
6
7
8
9
10
10:1 2 3 4 5 6 7 8 9 10 
ReOrdered List
10:1 5 9 2 6 10 3 7 4 8 


---------------------------------------------------------------------------------------------------------


Problem 4: Reverse the order of elements of the SinglyLinkedList and to print the elements of SinglyLinkedList  in reverse order using recursive and non-recursive functions.


Files:
	1-ReverseLinkedList.java
Input: 
	Size of the SinglyLinkedList
Output:
	 SinglyLinkedList in reverse order and SinglyLinkedList printed in reverse order.

Sample Execution:

Enter the size of the LinkedList
20
Elements of the LinkedList
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 
Elements of the reversed LinkedList using Non-recursive function
20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 
Elements of the reversed LinkedList using recursive function
20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 
Elements of the LinkedList printed in reverse order using Non-recursive function
20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 
Elements of the LinkedList printed in reverse order using recursive function
20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 


--------------------------------------------------------------------------------------------------------


Problem 6: Implementation of Bounded-sized Stacks

Files:
	1- BoundedSizedStacks.java
	
Input: 
		Stack size and stack operation to be done 
Ouptut:
		Result of the operation
		
Sample Execution:
	
	Enter the stack size: 
	3
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	1
	Enter the element
	1
	Element pushed
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	1
	Enter the element
	2
	Element pushed
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	1
	Enter the element
	3
	Element pushed
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	1
	Enter the element
	4
	Stack size exceeded
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	3
	Top of the stack : 3
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	4
	Stack is Empty? false
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	5
	Stack size: 3
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	2
	3
	Element popped
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	2
	2
	Element popped
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	2
	1
	Element popped
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	3
	Stack empty No elements
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit
	2
	Stack empty Cannot pop
	Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit


----------------------------------------------------------------------------------------------------------


Problem 8: Shunting Yard Algorithm

File:
	ShuntingYard.java
Input:
	Infix Expression
Output:
	Corresponding postfix Expression
Sample Execution:
	Enter expression
3+4*2/(1-5)^2^3
Output : 
342*15-23^^/+


-----------------------------------------------------------------------------------------------------------


Problem 9: Arithmetic on Sparse Polynomial

File:
	SparsePolynomial.java
Input:
	Input polynomials on which the operation is to be performed 
Output:
	Result of the operation
Sample Execution:

Polynomial operations:
Enter 1 for Addition 
Enter 2 for Multiplication 
Enter 3 for evaluation 
Enter 4 for exit
1
No of entries for the polynomial
2
 Enter from lower order to higher order coefficients
Enter Coefficient : 
0
Enter the Value : 
1
Enter Coefficient : 
2
Enter the Value : 
10
No of entries for the polynomial
1
 Enter from lower order to higher order coefficients
Enter Coefficient : 
0
Enter the Value : 
0
Given polynomials
Polynomial1 :
Value : 1 coefficient : 0
Value : 10 coefficient : 2
Polynomial2 :
Value : 0 coefficient : 0
 Added Polynomial
Value : 1 coefficient : 0
Value : 10 coefficient : 2
Polynomial operations:
Enter 1 for Addition 
Enter 2 for Multiplication 
Enter 3 for evaluation 
Enter 4 for exit
2
No of entries for the polynomial
2
 Enter from lower order to higher order coefficients
Enter Coefficient : 
1
Enter the Value : 
1
Enter Coefficient : 
2
Enter the Value : 
10
No of entries for the polynomial
2
 Enter from lower order to higher order coefficients
Enter Coefficient : 
0
Enter the Value : 
0
Enter Coefficient : 
1
Enter the Value : 
20
Given polynomials
Polynomial1 :
Value : 1 coefficient : 1
Value : 10 coefficient : 2
Polynomial2 :
Value : 0 coefficient : 0
Value : 20 coefficient : 1
 Multiplied Polynomial
Value : 0 coefficient : 1
Value : 20 coefficient : 2
Value : 200 coefficient : 3
Polynomial operations:
Enter 1 for Addition 
Enter 2 for Multiplication 
Enter 3 for evaluation 
Enter 4 for exit
3
No of entries for the polynomial
3
 Enter from lower order to higher order coefficients
Enter Coefficient : 
0
Enter the Value : 
1
Enter Coefficient : 
2
Enter the Value : 
10
Enter Coefficient : 
3
Enter the Value : 
0
Given polynomial
Value : 1 coefficient : 0
Value : 10 coefficient : 2
Value : 0 coefficient : 3
 Enter the value to evaluate given polynomial
1
Solution is 11
Polynomial operations:
Enter 1 for Addition 
Enter 2 for Multiplication 
Enter 3 for evaluation 
Enter 4 for exit
4




