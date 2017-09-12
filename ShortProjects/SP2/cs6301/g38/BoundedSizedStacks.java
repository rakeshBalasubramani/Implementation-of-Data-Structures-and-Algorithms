package cs6301.g38;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Avinash Venkatesh - axv165330 <br>
 * 		   HariPriyaa - hum160030 <br>
 * 		   Rakesh Balasubramani - rxb162130 <br>
 * 		   Raj Kumar Panneer Selvam - rxp162130 
 *
 * @description This class implements functions for bounded size stacks
 */


public class BoundedSizedStacks{

	private Object[] arr;
	private int size = 0;
	
	
	/** Constructor - intilaizes array  size
	 * @param size - stack size
	 */
	public BoundedSizedStacks(int size){
		arr = new Object[size];
	}
	
	 
	/** Method: Performs push operation
	 * @param element - element to be pushed inside the stack
	 */
	public void push(Object element) {
		if(size == arr.length){ // if array is full, exception is thrown
			throw new IllegalStateException("Stack size exceeded");
		}
		arr[size++] = element;
				
	}

	
	/**
	 * Method: Performs pop operation
	 */
	public Object pop(){
		if(size == 0){ // if stack empty, exception is thrown 
			throw new NoSuchElementException("Stack empty Cannot pop");
		}
		Object top = arr[size-1];
		size-=1;
		return top;
	}
	
	
	/** 
	 * Method: Performs Peep operation
	 * @return - returns the top element of the stack
	 */
	public Object peep(){
		if(size == 0){ // if stack empty, exception si thrown 
			throw new NoSuchElementException("Stack empty No elements");
		}
		
		return arr[size-1];
	}
	
	/**
	 * Method: to check whether the stack is empty or not
	 * 
	 * @return - true if stack empty, else returns false
	 */
	public boolean isEmpty() {
		return size==0;
	}

	/**
	 * Driver code 
	 * @param args - args[0] - stack size
	 */
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the stack size: ");
		int n = in.nextInt();
       	BoundedSizedStacks bss = new BoundedSizedStacks(n);
		//System.out.println("Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size");
		boolean isDone = false;
		whileloop:
		while(!isDone){
			System.out.println("Enter: 1-push 2-pop 3-peep 4-isEmpty 5-size 6-Exit");
			int choice = in.nextInt();
			try{
			switch(choice){
			case 1:
				System.out.println("Enter the element");
				bss.push(in.next()) ;
				System.out.println("Element pushed");
				break;
				
			case 2:
				System.out.println(bss.pop());
				System.out.println("Element popped");
				break;
				
			case 3:
				System.out.println("Top of the stack : "+bss.peep());
				break;
				
			case 4:
				System.out.println("Stack is Empty? "+ bss.isEmpty());
				break;
				
			case 5:
				System.out.println("Stack size: " + bss.size);
				break;
				
			default:
				isDone=true;
				break whileloop;
				
			}
			}
			catch (IllegalStateException e)
			{
				System.out.println(e.getMessage());
			}
			catch (NoSuchElementException e1)
			{
				System.out.println(e1.getMessage());
				
			}
		}
		in.close();
	}
	
}
