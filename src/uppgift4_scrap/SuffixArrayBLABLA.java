package uppgift4_scrap;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SuffixArrayBLABLA {
	private String string;
	private String[] suffixArray;
	
	/**
	 * constructor
	 * @param string
	 */
	public SuffixArrayBLABLA(String string) {
		build(string);
	}
	
	/**
	 * builds a suffix tree from a given string.
	 * @param string
	 */
	public void build(String string) {
		suffixArray = new String[string.length()];
		for (int i = 0; i < string.length(); i++) { //builds the suffix array
			suffixArray[i] = string.substring(i, string.length() - 1);
		}
		Arrays.sort(suffixArray); //sorts the vector in alfabetical order
	}
	
	/**
	 * sorts a vector of strings
	 */
	public void sort() {
		boolean isSwapped = false;
		String temp;
		do {
		    isSwapped = false;
		    for(int i = 0; i < suffixArray.length - 1; i++){
		        if(suffixArray[i].compareTo(suffixArray[i + 1]) > 0){
		            temp = suffixArray[i + 1];
		            suffixArray[i + 1] = suffixArray[i];
		            suffixArray[i] = temp;
		            isSwapped = true;
		        }
		    }
		} while((isSwapped));
	}

	/**
	 * finds the longest prefix of a string
	 * @param string
	 * @return
	 */
	public int longestCommonPrefix(String prefix) {
		//binär sökning
		return 0;
	}
	
//-----------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * finds and return the minimum length of a prefix in the suffix vector
	 * @return
	 */
	private int findMinimumLength() {
		int minLength = suffixArray[0].length();
		for(int i = 1; i < suffixArray.length; i++) {
			if(suffixArray[i].length() < minLength)  {
				minLength = suffixArray[i].length();
			}
		}
		return minLength;
	}
	
	/**
	 * checks if the all of the prefixes in the suffixArray contains a given prefix
	 * @param prefix, the given prefix
	 * @return
	 */
	private boolean allContainsPrefix(String prefix, int start, int end) {
		for (int i = start; i <= end; i++) {
			if (!(suffixArray[i].contains(prefix))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * for testing
	 */
	public void printVector() {
		for(int i = 0; i < suffixArray.length; i++) {
			System.out.println(suffixArray[i]);
		}
	}
	
	/**
	 * for testing
	 * @param args
	 */
	public static void main(String[] args) {
		SuffixArrayBLABLA s = new SuffixArrayBLABLA("banana$");
		s.printVector();
	}
}
