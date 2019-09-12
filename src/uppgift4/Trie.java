package uppgift4;

import java.util.HashMap;
import java.util.Map;

public class Trie {
	private final TrieNode root;
	
	/**
	 * constructor
	 */
	public Trie() {
		this.root = new TrieNode();
	}
	
	/**
	 * adds a string to the trie
	 * @param string, given string
	 */
	public void add(final String string) {
		 TrieNode current = root;
	        for (int i = 0; i < string.length(); i++) {
	            char ch = string.charAt(i);
	            TrieNode node = current.children.get(ch);
	            if (node == null) {
	                node = new TrieNode();
	                current.children.put(ch, node);
	            }
	            current = node;
	        }
	        current.endOfWord = true;  //mark the current nodes endOfWord as true
	}
	
	/**
	 * search for a given word in the trie
	 * @param word, given string to search for
	 * @return true if word exists in trie, else return false
	 */
    public boolean search(String word) {
        return searchRecursive(root, word, 0);
    }
    
    /**
     * search recursively
     * @param current, current node
     * @param word, given string
     * @param index, index
     * @return
     */
    private boolean searchRecursive(TrieNode current, String word, int index) {
        if (index == word.length()) {
            return current.endOfWord;  //return true of current's endOfWord is true else return false.
        }
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        
        if (node == null) {  //if node does not exist for given char then return false
            return false;
        }
        return searchRecursive(node, word, index + 1);
    }
	
	/**
	 * inner class for the node of a trie
	 * @author Therese Larsson
	 *
	 */
    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean endOfWord;
        
        public TrieNode() {
            children = new HashMap<>();
            endOfWord = false;
        }
    }
}
