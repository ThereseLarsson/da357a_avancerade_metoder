package uppgift5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * implementation of a Huffman Tree
 * @author Therese Larsson
 *
 */
public class Huffman {
	private static String string;
	private HashMap<Character, Integer> frequencyMap; //holds the letters frequency
	private HashMap<Character, String> codeMap; //holds the codes
	private Node root;
	
	/**
	 * constructor
	 */
	public Huffman() {}
	
	/**
	 * reads a text file into a string
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return string = new String(encoded, encoding);
	}
	
	/**
	 * builds a list of the occurring characters in a text and
	 * counts the frequency of these characters
	 */
	public void buildFrequencyMap(String string) {
		frequencyMap = new HashMap<Character, Integer>(); //key, value
		char[] stringArray = string.toCharArray();
		
		for(char c : stringArray) {
			if(frequencyMap.containsKey(c)) { //if the character has not been stored yet, store it
				frequencyMap.put(c, frequencyMap.get(c) + 1);
			} else {
				frequencyMap.put(c, 1);
			}
		}
	}
	
	/**
	 * builds the huffman tree
	 */
	public Node buildTree() {
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		
		//fill the queue with nodes constructed from a character and its frequency
		for(char i = 0; i < 256; i++ ) { //256 - size of ASCII alphabet
			if(frequencyMap.containsKey(i) && frequencyMap.get(i) > 0) {
				queue.add(new Node(i, frequencyMap.get(i), null, null)); //create new leaf
			}
		}
		
		//if the indata only consists of 1 single character
		if(queue.size() == 1) {
			queue.add(new Node('\0', 1, null, null));
		}
		//otherwise
		//continuously merge nodes (two with the lowest frequency) to build the tree until only one node remains --> becomes the root
		while(queue.size() > 1) {
			Node left = queue.poll(); //first extracted child becomes the left child
			Node right = queue.poll(); //second extracted child becomes the right child
			Node parent = new Node('\0', (left.frequency + right.frequency), left, right);
			queue.add(parent);
		}
		return root = queue.poll(); //the remaining node in the queue becomes the root
	}
	
	/**
	 * builds the codemap
	 */
	public void buildCodeMap() {
		codeMap = new HashMap<Character, String>(); //key, value
		buildCode(root, "", codeMap);
	}
	
	public void buildCode(Node node, String code, HashMap<Character, String> codeMap) {
		if(!node.isLeaf()) { //if the current node is NOT a leaf
			buildCode(node.leftChild, code + '0', codeMap); //each time we go down at the LEFT side of the tree, encode a 0
			buildCode(node.rightChild, code + '1', codeMap); //each time we go down at the RIGHT side of the tree, encode a 1
		} else { //otherwise
			codeMap.put(node.character, code);
		}
	}
	
	/**
	 * prints the symbols with corresponding frequency and code
	 */
	public void printData() {
		int bitsum = 0;
		System.out.println("Input: " + string);
		System.out.println("-----------------------------------------------");
		System.out.println("SYMBOL\tFREQ.\tHUFFMAN CODE");
		for(Character c: frequencyMap.keySet()) {
			System.out.println(c + "\t" + frequencyMap.get(c) + "\t" + codeMap.get(c)); //codeHereLater --> codeMap.get(c);
			bitsum += frequencyMap.get(c) * codeMap.get(c).length();
		}
		System.out.println("-----------------------------------------------");
		System.out.println("Compressed length: " + bitsum); //one character size = codeLength * frequency
	}
	
	/**
	 * main for testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Huffman h = new Huffman();
//		String string = h.readFile("files/a.txt", StandardCharsets.UTF_8);
		String string = h.readFile("files/teacher.txt", StandardCharsets.UTF_8);
		h.buildFrequencyMap(string);
		h.buildTree();
		h.buildCodeMap();
		h.printData();
		
		//nlog(n) per insättning --> n=antal unika tecken
		//komplett binary tree with n leaves has 2n-1 nodes and huffman coding tree is a binary tree
	}
}
