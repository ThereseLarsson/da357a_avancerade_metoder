package uppgift4;

/**
 * class to test the Trie-class
 * @author Therese Larsson
 *
 */
public class TrieTester {
	public static void main(String[] args) {
		String word1 = "ad";
		String word2 = "add";
		String word3 = "hat";
		String word4 = "hate";
		String word5 = "abc";
		String word6 = "kaka";
		Trie trie = new Trie();
		trie.add(word1);
		trie.add(word2);
		trie.add(word3);
		trie.add(word4);
		
		System.out.println("the string " + "'" + word1 + "'" + " exists in the trie: " + trie.search(word1)); //should return true
		System.out.println("the string " + "'" + word2 + "'" + " exists in the trie: " + trie.search(word2)); //should return true
		System.out.println("the string " + "'" + word6 + "'" + " exists in the trie: " + trie.search(word6)); //should return false
		System.out.println("the string " + "'" + word3 + "'" + " exists in the trie: " + trie.search(word3)); //should return true
		System.out.println("the string " + "'" + word4 + "'" + " exists in the trie: " + trie.search(word4)); //should return true 
		System.out.println("the string " + "'" + word5 + "'" + " exists in the trie: " + trie.search(word5)); //should return false
	}
}
