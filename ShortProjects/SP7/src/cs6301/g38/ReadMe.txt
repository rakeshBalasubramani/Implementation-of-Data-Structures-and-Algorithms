/* Readme.txt
CS 6301.502. IMPLEMENTATION OF ADVANCED DATA STRUCTURES AND ALGORITHMS
Short Project 7

GROUP 38
	RAKESH BALASUBRAMANI - rxb162130
	HARIPRIYAA MANIAN – hum160030
	RAJKUMAR PANNEER SELVAM - rxp162130
	AVINASH VENKATESH – axv165330

*/
------------------------------------------------------------------------------------------------------------------------------
1.Implement binary search trees (BST), with the following public methods:
   contains, add, remove, iterator.

File: BST.java
 
Sample Execution:

Input:

100
50
20
75
98
49
10
15
-20
48
5
3
46
-50
-15
50
99
-46
-75
55
-5
-48
-49
-3
15
12
17
9
-100
0

Output:

Add 100 : 100 
Add 50 : 50 100 
Add 20 : 20 50 100 
Add 75 : 20 50 75 100 
Add 98 : 20 50 75 98 100 
Add 49 : 20 49 50 75 98 100 
Add 10 : 10 20 49 50 75 98 100 
Add 15 : 10 15 20 49 50 75 98 100 
Remove 20 : 10 15 49 50 75 98 100 
Add 48 : 10 15 48 49 50 75 98 100 
Add 5 : 5 10 15 48 49 50 75 98 100 
Add 3 : 3 5 10 15 48 49 50 75 98 100 
Add 46 : 3 5 10 15 46 48 49 50 75 98 100 
Remove 50 : 3 5 10 15 46 48 49 75 98 100 
Remove 15 : 3 5 10 46 48 49 75 98 100 
Add 50 : 3 5 10 46 48 49 50 75 98 100 
Add 99 : 3 5 10 46 48 49 50 75 98 99 100 
Remove 46 : 3 5 10 48 49 50 75 98 99 100 
Remove 75 : 3 5 10 48 49 50 98 99 100 
Add 55 : 3 5 10 48 49 50 55 98 99 100 
Remove 5 : 3 10 48 49 50 55 98 99 100 
Remove 48 : 3 10 49 50 55 98 99 100 
Remove 49 : 3 10 50 55 98 99 100 
Remove 3 : 10 50 55 98 99 100 
Add 15 : 10 15 50 55 98 99 100 
Add 12 : 10 12 15 50 55 98 99 100 
Add 17 : 10 12 15 17 50 55 98 99 100 
Add 9 : 9 10 12 15 17 50 55 98 99 100 
Remove 100 : 9 10 12 15 17 50 55 98 99 
Final: 
9 10 12 15 17 50 55 98 99 


--------------------------------------------------------------------------------------------------------------------------------

2. Extend BST to AVL trees (AVLTree).

File: AVLTree.java


Sample Execution:

Input:

100
50
20
75
98
49
10
15
-20
48
5
3
46
-50
-15
50
99
-46
-75
55
-5
-48
-49
-3
15
12
17
9
-100
0

Output:
Add 100 : 100 
Add 50 : 50 100 
Add 20 : 20 50 100 
Add 75 : 20 50 75 100 
Add 98 : 20 50 75 98 100 
Add 49 : 20 49 50 75 98 100 
Add 10 : 10 20 49 50 75 98 100 
Add 15 : 10 15 20 49 50 75 98 100 
Remove 20 : 10 15 49 50 75 98 100 
Add 48 : 10 15 48 49 50 75 98 100 
Add 5 : 5 10 15 48 49 50 75 98 100 
Add 3 : 3 5 10 15 48 49 50 75 98 100 
Add 46 : 3 5 10 15 46 48 49 50 75 98 100 
Remove 50 : 3 5 10 15 46 48 49 75 98 100 
Remove 15 : 3 5 10 46 48 49 75 98 100 
Add 50 : 3 5 10 46 48 49 50 75 98 100 
Add 99 : 3 5 10 46 48 49 50 75 98 99 100 
Remove 46 : 3 5 10 48 49 50 75 98 99 100 
Remove 75 : 3 5 10 48 49 50 98 99 100 
Add 55 : 3 5 10 48 49 50 55 98 99 100 
Remove 5 : 3 10 48 49 50 55 98 99 100 
Remove 48 : 3 10 49 50 55 98 99 100 
Remove 49 : 3 10 50 55 98 99 100 
Remove 3 : 10 50 55 98 99 100 
Add 15 : 10 15 50 55 98 99 100 
Add 12 : 10 12 15 50 55 98 99 100 
Add 17 : 10 12 15 17 50 55 98 99 100 
Add 9 : 9 10 12 15 17 50 55 98 99 100 
Remove 100 : 9 10 12 15 17 50 55 98 99 
Final: 
9 10 12 15 17 50 55 98 99 


--------------------------------------------------------------------------------------------------------------------------------
3. Extend BST to Red Black Trees (RedBlacktree).

File: RedBlackTree.java

Sample Execution:
1
Add 1 : Tree:
Element: 1 Color: Black

2
Add 2 : Tree:
Element: 1 Right: 2 Color: Black
Element: 2 Color: Red

4
Add 4 : Tree:
Element: 1 Color: Red
Left: 1 Element: 2 Right: 4 Color: Black
Element: 4 Color: Red


6
Add 6 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 4 Color: Black
Element: 4 Right: 6 Color: Black
Element: 6 Color: Red

20
Add 20 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 6 Color: Black
Element: 4 Color: Red
Left: 4 Element: 6 Right: 20 Color: Black
Element: 20 Color: Red

35
Add 35 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 6 Color: Black
Element: 4 Color: Black
Left: 4 Element: 6 Right: 20 Color: Red
Element: 20 Right: 35 Color: Black
Element: 35 Color: Red

70
Add 70 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 6 Color: Black
Element: 4 Color: Black
Left: 4 Element: 6 Right: 35 Color: Red
Element: 20 Color: Red
Left: 20 Element: 35 Right: 70 Color: Black
Element: 70 Color: Red

25
Add 25 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 4 Color: Red
Element: 4 Color: Black
Left: 2 Element: 6 Right: 35 Color: Black
Element: 20 Right: 25 Color: Black
Element: 25 Color: Red
Left: 20 Element: 35 Right: 70 Color: Red
Element: 70 Color: Black


100
Add 100 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 4 Color: Red
Element: 4 Color: Black
Left: 2 Element: 6 Right: 35 Color: Black
Element: 20 Right: 25 Color: Black
Element: 25 Color: Red
Left: 20 Element: 35 Right: 70 Color: Red
Element: 70 Right: 100 Color: Black
Element: 100 Color: Red

120
Add 120 : Tree:
Element: 1 Color: Black
Left: 1 Element: 2 Right: 4 Color: Red
Element: 4 Color: Black
Left: 2 Element: 6 Right: 35 Color: Black
Element: 20 Right: 25 Color: Black
Element: 25 Color: Red
Left: 20 Element: 35 Right: 100 Color: Red
Element: 70 Color: Red
Left: 70 Element: 100 Right: 120 Color: Black
Element: 120 Color: Red



------------------------------------------------------------------------------------------------------------------------------
4.Extend BST to Splay Trees (SplayTree). 

File: SplayTree.java

Sample Execution:
Input :

100
50
75
55
25
20
200
-55
0

Outptu:

Add 100 : 100 
Add 50 : 50 100 
Add 75 : 50 75 100 
Add 55 : 50 55 75 100 
Add 25 : 25 50 55 75 100 
Add 20 : 20 25 50 55 75 100 
Add 200 : 20 25 50 55 75 100 200 
Remove -55 : 20 25 50 75 100 200 

Final :
20 25 50 75 100 200 

------------------------------------------------------------------------------------------------------------------------------

5.Implement BSTMap (like a TreeMap), on top of one of the BST classes.

File: AVLTreeMap.java

Sample Execution:

Input:

100 1
50 1
20 1
75 1
98 1
49 1
10 1
15 1
-20
48 1
5 1
3 1
46 1
-50
-15
50 0
99 2
-46
-75
55 32
-5
-48
-49
-3
15 2
12 65
17 65
9 655
-100
0

Output:

Add 100 : 1100 
Add 50 : 150 100 
Add 20 : 120 50 100 
Add 75 : 120 50 75 100 
Add 98 : 120 50 75 98 100 
Add 49 : 120 49 50 75 98 100 
Add 10 : 110 20 49 50 75 98 100 
Add 15 : 110 15 20 49 50 75 98 100 
Remove -20 : 10 15 49 50 75 98 100 
Add 48 : 110 15 48 49 50 75 98 100 
Add 5 : 15 10 15 48 49 50 75 98 100 
Add 3 : 13 5 10 15 48 49 50 75 98 100 
Add 46 : 13 5 10 15 46 48 49 50 75 98 100 
Remove -50 : 3 5 10 15 46 48 49 75 98 100 
Remove -15 : 3 5 10 46 48 49 75 98 100 
Add 50 : 03 5 10 46 48 49 50 75 98 100 
Add 99 : 23 5 10 46 48 49 50 75 98 99 100 
Remove -46 : 3 5 10 48 49 50 75 98 99 100 
Remove -75 : 3 5 10 48 49 50 98 99 100 
Add 55 : 323 5 10 48 49 50 55 98 99 100 
Remove -5 : 3 10 48 49 50 55 98 99 100 
Remove -48 : 3 10 49 50 55 98 99 100 
Remove -49 : 3 10 50 55 98 99 100 
Remove -3 : 10 50 55 98 99 100 
Add 15 : 210 15 50 55 98 99 100 
Add 12 : 6510 12 15 50 55 98 99 100 
Add 17 : 6510 12 15 17 50 55 98 99 100 
Add 9 : 6559 10 12 15 17 50 55 98 99 100 
Remove -100 : 9 10 12 15 17 50 55 98 99 
Print: 
9 10 12 15 17 50 55 98 99 

Final : 
key 9 value 655
key 10 value 1
key 12 value 65
key 15 value 2
key 17 value 65
key 50 value 0
key 55 value 32
key 98 value 1
key 99 value 2


------------------------------------------------------------------------------------------------------------------------------

6.Given an array A of integers, and an integer X, find how many pairs of
   elements of A sum to X:

File : PairsSum.java
Input: 
	Array size, array elements and the sum
Ouput:
	Count of pairs
Sample Execution:
Enter the array size:
6
Enter the array elements:
3 3 4 5 3 5
Enter the Integer X
8
Number of pairs in arr sum to X: 6

-----------------------------------------------------------------------------------------------------------------------------


8.Given an array A of integers, find the length of a longest streak of
   consecutive integers that occur in A 

File:
	LongestStreak.java

Input:
	Enter the array size:
10
Enter the array elements:
1 7 9 4 1 7 4 8 7 1
Longest Streak: 3

-----------------------------------------------------------------------------------------------------------------------



