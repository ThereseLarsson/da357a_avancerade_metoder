package uppgift5;

/**
 * node class for the Huffman class
 * @author Therese Larsson
 *
 */
public class Node implements Comparable<Node> {
	protected char character;
	protected int frequency;
	protected Node leftChild;
	protected Node rightChild;
	
	/**
	 * constructor
	 * @param character
	 * @param frequency, a characters number of occurrences
	 * @param leftChild, left child of a node
	 * @param rightChild, right child of a node
	 */
	public Node(char character, int frequency, Node leftChild, Node rightChild) {
		this.character = character;
		this.frequency = frequency;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	/**
	 * if a node don't have a left or right child, current node is a leaf
	 * @return
	 */
	public boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null;
	}

	/**
	 * compares two nodes based on their frequency
	 */
	public int compareTo(Node otherNode) {
		return frequency - otherNode.frequency;
	}
}
