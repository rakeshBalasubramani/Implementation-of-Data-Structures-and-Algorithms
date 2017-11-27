// starter code for Tries

package cs6301.g38;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Trie<T,V> {
    private class Entry {
	V value;
	HashMap<T,Entry> child;
	Entry(V value) {
	    this.value = value;
	    child = new HashMap<>();
	}
    }
    private Entry root;

    Trie() {
	root = null;
    }

    public V add(Iterator<T> iter, V value) {
	return null;
    }

    public V get(Iterator<T> iter) {
	return null;
    }

    public V remove(Iterator<T> iter) {
	return null;
    }

    // How many words in the dictionary start with this prefix?
    public int prefixCount(Iterator<T> iter) {
	return 0;
    }

    public static class StringIterator implements Iterator<Character> {
	char[] arr;  int index;
	public StringIterator(String s) { arr = s.toCharArray(); index = 0; }
	public boolean hasNext() { return index < arr.length; }
	public Character next() { return arr[index++]; }
	public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }

    public static void main(String[] args) {
	Trie<Character, Integer> trie = new Trie<>();
	int wordno = 0;
	Scanner in = new Scanner(System.in);
	while(in.hasNext()) {
	    String s = in.next();
	    if(s.equals("End")) { break; }
	    wordno++;
	    trie.add(new StringIterator(s), wordno);
	}

	while(in.hasNext()) {
	    String s = in.next();
	    Integer val = trie.get(new StringIterator(s));
	    System.out.println(s + "\t" + val);
	}
    }
}
