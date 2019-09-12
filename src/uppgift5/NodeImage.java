package uppgift5;

/**
 * node class for the HuffmanImage class
 * @author Therese Larsson
 *
 */
public class NodeImage implements Comparable<NodeImage> {
	protected int colorValue;
	protected int frequency;
	protected NodeImage leftChild;
	protected NodeImage rightChild;

	/**
	 * constructor
	 * @param colorValue, integer value of a color
	 * @param frequency, a colors number of occurrences
	 * @param leftChild, left child of a node
	 * @param rightChild, right child of a node
	 */
	public NodeImage(int colorValue, int frequency, NodeImage leftChild, NodeImage rightChild) {
		this.colorValue = colorValue;
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
	public int compareTo(NodeImage otherNode) {
		return frequency - otherNode.frequency;
	}
}
