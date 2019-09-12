package uppgift4_scrap;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SuffixArray_SuffixArray {
	private String text;
	private static String[] suffixArray;
	private static int[] suffixIndexArray;
	
	/**
	 * constructor
	 * @param string
	 */
	public SuffixArray_SuffixArray(String text) {
		this.text = text;
		createSuffixArray(text);
	}
	
//	/**
//	 * builds a suffix array from a given string.
//	 * @param string
//	 */
//	public String[] build(String string) {
//		suffixArray = new String[string.length()];
//		for (int i = 0; i < string.length(); i++) { //builds the suffix array
//			suffixArray[i] = string.substring(i, string.length());
//		}
//		Arrays.sort(suffixArray); //sorts the vector
//		
//		//suffix vector som är av typen int som består av suffixernas position-värden
//		
//		return suffixArray;
//	}
	
	public int[] buildSuffixIndexArray(String text) {
		suffixArray = new String[text.length()];
		suffixIndexArray = new int[text.length()];
		
		for (int i = 0; i < text.length(); i++) {
			suffixArray[i] = text.substring(i, text.length());
//			suffixIndexArray[i] = string.indexOf("");
		}
		Arrays.sort(suffixArray);
		
		//suffix vector som är av typen int som består av suffixernas position-värden
		
		return suffixIndexArray;
	}
	
	public int[] createSuffixArray(String text) {
		suffixArray = new String[text.length()];
		suffixIndexArray = new int[text.length()];
		
		for (int i = 0; i < text.length(); i++) {
			for (int textIndex = i; textIndex < text.length(); textIndex++) {
				text += this.text.charAt(textIndex);
			}
			suffixArray[i] = text;
		}

		int back;
		for (int i = 1; i < text.length(); i++) {
			String key = suffixArray[i];
			int keyindex = suffixIndexArray[i];

			for (back = i - 1; back >= 0; back--) {
				if (suffixArray[back].compareTo(key) > 0) {
					suffixArray[back + 1] = suffixArray[back];
					suffixIndexArray[back + 1] = suffixIndexArray[back];
				} else {
					break;
				}
			}
			suffixArray[back + 1] = key;
			suffixIndexArray[back + 1] = keyindex;
		}
		System.out.println("SUFFIX ARRAY : " + Arrays.toString(suffixArray));
		System.out.println("SUFFIX \t INDEX");
		for (int i = 0; i < text.length(); i++) {
			System.out.println(suffixArray[i] + "\t" + suffixIndexArray[i]);
		}
		System.out.println("----------------");
		
		return suffixIndexArray;
	}
	
	//------------------------------
	
	/** 
     * find a longest common prefix subsequence of two string
     * @param text
     * @param pattern
     */
    
    public static String lcs(String text, String pattern){
        int textLenghth = text.length();
        int patternLenght = pattern.length();
        if(textLenghth == 0 || patternLenght == 0){
            return "";
        }else if(text.charAt(textLenghth-1) == pattern.charAt(patternLenght-1)){
            return lcs(text.substring(0,textLenghth-1),pattern.substring(0,patternLenght-1))
                + text.charAt(textLenghth-1);
        }else{
            String x = lcs(text, pattern.substring(0,patternLenght-1));
            String y = lcs(text.substring(0,textLenghth-1), pattern);
    //       System.out.println("x: "+x+"  y: "+ y);
            return (x.length() > y.length()) ? x : y;
        }
    }
	
    //compare with suffix starting at suffixArray[mid]
	public int compareWithSuffix(int mid, String pattern) {
		int c = 0;
		int j = 0;
		
		while (j < pattern.length() && c == 0) {
			if (mid + j <= text.length()) {
				c = pattern.charAt(1 + j) - text.charAt(mid + j);
			} else {
				c = 1;
			}
			j++;
		}
		return c;
	}

	//I - suffixvektor av typen int 
	public int binarySearch(String pattern) {
		int left = 1;
		int right = suffixIndexArray.length;
		int mid; 
		int c = 1;
		
		while (c != 0 && left <= right) {
			mid = left + (right - left) / 2;
			c = compareWithSuffix(suffixIndexArray[mid], pattern);

			if (c < 0) {
				right = mid - 1;
			} else if (c > 0) {
				left = mid + 1;
			} else if (c == 0) {
				return mid;
			}
		}
		return left;
	}
	
	//------------------------------
	
//-----------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * for testing
	 */
	public void printArray() {
		for(int i = 0; i < suffixArray.length; i++) {
			System.out.println(suffixArray[i]);
		}
	}
	
//	/**
//	 * for testing
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		SuffixArray s = new SuffixArray("abracadabra$");
//		s.printVector();
//	}
}
