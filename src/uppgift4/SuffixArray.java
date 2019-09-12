package uppgift4;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class "SuffixArray" create a suffix array, find a longest common subsequence
 * of two string
 */

public class SuffixArray {
	private String[] textT;
	private String originalText;
	private int length;
	private int[] indexArray;
	private String[] suffixArray;
	private String[] prefixes;

	/**
	 * constructor
	 * @param text
	 */
	public SuffixArray(String text) {
		this.originalText = text;
		this.textT = new String[text.length()];

		for (int i = 0; i < text.length(); i++) {
			this.textT[i] = text.substring(i, i + 1);
		}

		this.length = text.length();
		this.indexArray = new int[length];
		for (int i = 0; i < length; i++) {
			indexArray[i] = i;
		}
		suffixArray = new String[length];
	}

	/**
	 * creates a suffix array from a string
	 */
	public void createSuffixArray() {
		for (int i = 0; i < length; i++) {
			String text = "";
			for (int text_index = i; text_index < length; text_index++) {
				text += this.textT[text_index];
			}
			suffixArray[i] = text;
		}

		int back;
		for (int i = 1; i < length; i++) {
			String key = suffixArray[i];
			int keyindex = indexArray[i];

			for (back = i - 1; back >= 0; back--) {
				if (suffixArray[back].compareTo(key) > 0) {
					suffixArray[back + 1] = suffixArray[back];
					indexArray[back + 1] = indexArray[back];
				} else {
					break;
				}
			}
			suffixArray[back + 1] = key;
			indexArray[back + 1] = keyindex;
		}
	}

	/**
	 * find a longest common prefix subsequence of two strings
	 * @param text
	 * @param pattern
	 */

	public static String lcs(String text, String pattern) {
		int textLenghth = text.length();
		int patternLenght = pattern.length();
		if (textLenghth == 0 || patternLenght == 0) {
			return "";
		} else if (text.charAt(textLenghth - 1) == pattern.charAt(patternLenght - 1)) {
			return lcs(text.substring(0, textLenghth - 1), pattern.substring(0, patternLenght - 1))
					+ text.charAt(textLenghth - 1);
		} else {
			String x = lcs(text, pattern.substring(0, patternLenght - 1));
			String y = lcs(text.substring(0, textLenghth - 1), pattern);
			return (x.length() > y.length()) ? x : y;
		}
	}

	/**
	 * compares two characters with each other
	 * @param i, index
	 * @param pattern, pattern string
	 * @return compare value
	 */
	private int compareWithSuffix(int i, String pattern) {
		int c = 0, j = 0;
		String[] patternArray = prefix(pattern);

		while (j < pattern.length() && c == 0) {
			if (i + j <= suffixArray.length) {

				c = patternArray[j].compareTo(suffixArray[i + j]);
			} else {
				c = 1;
			}
			j++;
		}
		return c;
	}

	/**
	 * performs a binary search to find the index of the longest common substring for two stings
	 * @param pattern
	 * @return
	 */
	public int binarySearch(String pattern) {
		int left = 0, right = suffixArray.length;
		int mid, c = 1;
		
		while (c != 0 && left <= right) {
			mid = left + (right - left) / 2;
			c = compareWithSuffix(mid, pattern);
			
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

	/**
	 * creates a prefix array of a given string
	 * @param input
	 * @return
	 */
	public String[] prefix(String input) {
		prefixes = new String[input.length()];
		int j = 0;
		for (int i = input.length() - 1; i >= 0; i--) {
			prefixes[i] = input.substring(j, i + 1);
		}
		return prefixes;
	}
	
	/**
	 * for testing
	 */
	public void printArrayContent() {
		for(int i = 0; i < suffixArray.length; i++) {
			System.out.println(suffixArray[i]);
		}
	}
	
	/**
	 * prints the suffix array
	 */
	public void printArray() {
		System.out.println("Original text: " + "'" + originalText + "'");
		System.out.println("SUFFIX ARRAY IN ORDER: " + Arrays.toString(suffixArray));
		System.out.println("SUFFIX \t INDEX");
		for (int i = 0; i < length; i++) {
			System.out.println(suffixArray[i] + "\t" + indexArray[i]);
		}
		System.out.println("----------------" + "\n" + "----------------" + "\n");
	}
	
	/**
	 * testrun the binary search method to fins the index of the longest common substring
	 * @param textT
	 * @param pattern
	 */
	public void testRunBinarySearch(String textT, String pattern) {
		System.out.println("Longest common prefix of: " + "'" + pattern + "'" + " in " + "'" + textT + "'" + " beginning at index: " + indexArray[binarySearch(pattern)]);
	}
	
//	public static void main(String[] arg) throws IOException {
//	String textT = "abriyz";
//	String pattern = "rix";
//
//	System.out.println("TEXT FOR MAKING SUFFIX ARRAY: '" + textT + "'");
//
//	SuffixArray suffixarray = new SuffixArray(textT);
//	suffixarray.createSuffixArray();
//	suffixarray.prefix(pattern);
//	String lcs = SuffixArray.lcs(textT, pattern);
//	System.out.println("Longest common prefix string for text and pattern: " + "'" + SuffixArray.lcs(textT, pattern) + "'");
//	Pattern word = Pattern.compile(lcs);
//	Matcher match = word.matcher(textT);
//
//	while (match.find()) {
//		System.out.println("Found LCS at index " + match.start() + " - " + (match.end() - 1));
//	}
//	System.out.println("Longest common prefix of " + "'" + pattern + "'" + " in " + "'" + textT + "'" + " beginning at index: " + suffixarray.indexArray[suffixarray.binarySearch(pattern)]);
//}
}