package uppgift4_scrap;

public class Trie2 {
	private final TrieNode root;
	
	/**
	 * constructor
	 */
	public Trie2() {
		this.root = new TrieNode();
	}
	
	/**
	 * adds a string to the trie
	 * @param string
	 */
//	public void add(final String string) {
//		TrieNode current = root; //börjar från roten
//		for(int i = 0; i < string.length(); i++) { //kollar tecken för tecken
//			if(current.trieNodes[string.charAt(i) - 'a'] == null) { //om noden inte finns så skapar vi den
//				current.trieNodes[string.charAt(i) - 'a'] = new TrieNode();
//			}
//			current = current.next(string.charAt(i));
//		}
//		current.terminating++;
//	}
	
//	/**
//	 * gets the number of occurrences of a string in a trie
//	 * @param string
//	 * @return
//	 */
//	public int getNbrOfOccurrences(String string) {
//		TrieNode current = root.next(string.charAt(0));
//		for(int i = 1; i < string.length(); i++) {
//			if(current == null) {
//				return 0;
//			}
//			current = current.next(string.charAt(i));
//		}
//		return current.terminating;
//	}
	
	/**
     * Recursive implementation of search into trie.
     */
//    public boolean searchRecursive(String word) {
//        return searchRecursive(root, word, 0);
//    }
    
//    private boolean searchRecursive(TrieNode current, String word, int index) {
//        if (index == word.length()) {
//            //return true of current's endOfWord is true else return false.
//            return current.endOfWord;
//        }
//        char ch = word.charAt(index);
//        TrieNode node = current.children.get(ch);
//        //if node does not exist for given char then return false
//        if (node == null) {
//            return false;
//        }
//        return searchRecursive(node, word, index + 1);
//    }
	
	/**
	 * inner class for the node of a trie
	 * @author Therese Larsson
	 *
	 */
	private class TrieNode {
//		private int terminating;
		private boolean endOfWord;
		private final TrieNode[] trieNodes = new TrieNode[26];
		
		public TrieNode next(final char c) {
			endOfWord = false;
			return trieNodes[c - 'a'];
		}
	}
	
	public void printTrie() {
		
	}
}
