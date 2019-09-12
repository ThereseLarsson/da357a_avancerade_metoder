package uppgift5;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/**
 * implementation of a Huffman Tree
 * @author Therese Larsson
 *
 */
public class HuffmanImage {
	private TreeMap<Integer, Integer> frequencyMap; //holds the letters frequency
	private TreeMap<Integer, String> codeMap; //holds the codes
	private NodeImage root;
	private int nbrOfPixels;
	
	/**
	 * constructor
	 */
	public HuffmanImage() {}
	
	/**
	 * reads an image
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public BufferedImage readImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
	
	/**
	 * builds a list of the occurring characters in a text and
	 * counts the frequency of these characters
	 */
	public void buildFrequencyMap(BufferedImage image) {
		frequencyMap = new TreeMap<Integer, Integer>(); //key, value
		int width = image.getWidth();
		int height = image.getHeight();
		int colors = image.getColorModel().getNumColorComponents();
		WritableRaster raster = image.getRaster();
		nbrOfPixels = 0;
		
		if(colors == 3) { //if color image as input
			for(int i = 0; i < width; i++)  {
				for(int j = 0; j < height; j++)  {
					Color color = new Color(image.getRGB(i, j));
					int colorValue = (65536 * color.getRed()) + (256 * color.getGreen()) + color.getBlue();
					
					if(frequencyMap.containsKey(colorValue)) { //if the color has not been stored yet, store it
						frequencyMap.put(colorValue, frequencyMap.get(colorValue) + 1);
					} else {
						frequencyMap.put(colorValue, 1);
					}
					nbrOfPixels++;
				}
			}
			
		} else if(colors == 1) {  //if black/white image as input
			for(int i = 0; i < width; i++)  {
				for(int j = 0; j < height; j++)  {
					int colorValue = raster.getSample(i, j, 0); //0 ---> det finns 1 färgkanal
					
					if(frequencyMap.containsKey(colorValue)) { //if the color has not been stored yet, store it
						frequencyMap.put(colorValue, frequencyMap.get(colorValue) + 1);
					} else {
						frequencyMap.put(colorValue, 1);
					}
					nbrOfPixels++;
				}
			}
		}
	}
	
	/**
	 * builds the huffman tree
	 */
	public NodeImage buildTree() {
		PriorityQueue<NodeImage> queue = new PriorityQueue<NodeImage>();
		
		for(Integer colorValue: frequencyMap.keySet()) {
			if(frequencyMap.get(colorValue) > 0) {
				queue.add(new NodeImage(colorValue, frequencyMap.get(colorValue), null, null)); //create new leaf
			}
		}
		//if the indata only consists of 1 single character
		if(queue.size() == 1) {
			queue.add(new NodeImage('\0', 1, null, null));
		}
		//otherwise
		//continuously merge nodes (two with the lowest frequency) to build the tree until only one node remains --> becomes the root
		while(queue.size() > 1) {
			NodeImage left = queue.poll(); //first extracted child becomes the left child
			NodeImage right = queue.poll(); //second extracted child becomes the right child
			NodeImage parent = new NodeImage('\0', (left.frequency + right.frequency), left, right);
			queue.add(parent);
		}
		return root = queue.poll(); //the remaining node in the queue becomes the root
	}
	
	/**
	 * builds the codemap
	 */
	public void buildCodeMap() {
		codeMap = new TreeMap<Integer, String>(); //key, value
		buildCode(root, "", codeMap);
	}
	
	public void buildCode(NodeImage node, String code, TreeMap<Integer, String> codeMap) {
		if(!node.isLeaf()) {
			buildCode(node.leftChild, code + '0', codeMap); //each time we go down at the left side of the tree, encode a 0
			buildCode(node.rightChild, code + '1', codeMap);
		} else {
			codeMap.put(node.colorValue, code);
		}
	}
	
	/**
	 * prints the symbols with corresponding frequency value and code
	 */
	public void printData() {
		int bitsum = 0;
		System.out.println("SYMBOL\t\tFREQ.\t\tHUFFMAN CODE");
		for(Integer colorValue: frequencyMap.keySet()) {
			System.out.println(colorValue + "\t" + "\t" + frequencyMap.get(colorValue) + "\t" + "\t" + codeMap.get(colorValue)); //codeHereLater --> codeMap.get(c);
			bitsum += frequencyMap.get(colorValue) * codeMap.get(colorValue).length();
		}
		System.out.println("-----------------------------------------------");
		System.out.println("Compressed length: " + bitsum + "\n");
		
		int nbrOfColors = frequencyMap.size();
		System.out.println("(Number of Pixels: " + nbrOfPixels + ")");
		System.out.println("(Number of colors: " + nbrOfColors + ")");
		
		int bitsPerPixel = calculateNeededBitsPerPixel(nbrOfColors);
		System.out.println("(Bits per pixel: " + bitsPerPixel + ")");
		System.out.println("Size of image with binary code: " + (bitsPerPixel * nbrOfPixels));
	}
	
	/**
	 * calculates and returns the number of bits needed per pixel
	 * @param nbrOfColors, number of colors present in an image
	 * @return
	 */
	private int calculateNeededBitsPerPixel(int nbrOfColors) {
		int exponent = -1; //represents the bits needed per pixel
		int possibleValues = 0;
		while(nbrOfColors > possibleValues) {
			exponent++;
			possibleValues = (int) Math.pow(2, exponent);
		}
		return exponent;
	}
	
	/**
	 * main for testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		HuffmanImage h = new HuffmanImage();
		BufferedImage image = h.readImage("files/template_3.png");
		h.buildFrequencyMap(image);
		h.buildTree();
		h.buildCodeMap();
		h.printData();
	}
}
