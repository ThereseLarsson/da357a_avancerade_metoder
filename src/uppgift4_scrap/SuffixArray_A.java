package uppgift4_scrap;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Class "SuffixArray" create a suffix array, find a longest common subsequence of two string
 */

public class SuffixArray_A {
    private String[] text;
    private int length;
    private int[] indexArray;
    private String[] suffixArray;
    private String[] prefixes;
    private String originalText;

	public SuffixArray_A(String text) {
		this.originalText = text;
		this.text = new String[text.length()];

		for (int i = 0; i < text.length(); i++) {
			this.text[i] = text.substring(i, i + 1);
		}

		this.length = text.length();
		this.indexArray = new int[length];
		for (int i = 0; i < length; i++) {
			indexArray[i] = i;
		}
		suffixArray = new String[length];
	}
    
    /** 
     *  create a suffix array
     */

    public void createSuffixArray() {   
        for(int i = 0; i < length; i++)	{
            String text = "";
            for (int textIndex = i; textIndex < length; textIndex++) {
                text += this.text[textIndex];
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
    
    public void print() {
    	System.out.println("SUFFIX ARRAY : " + Arrays.toString(suffixArray));
		System.out.println("SUFFIX \t INDEX");
		for (int i = 0; i < length; i++) {
			System.out.println(suffixArray[i] + "\t" + indexArray[i]);
		}
		System.out.println("----------------");
    }

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
        } else if(text.charAt(textLenghth-1) == pattern.charAt(patternLenght-1)) {
            return lcs(text.substring(0,textLenghth-1),pattern.substring(0,patternLenght-1))
                + text.charAt(textLenghth-1);
        } else {
            String x = lcs(text, pattern.substring(0,patternLenght-1));
            String y = lcs(text.substring(0,textLenghth-1), pattern);
    //       System.out.println("x: "+x+"  y: "+ y);
            return (x.length() > y.length()) ? x : y;
        }
    }
    
//-------------------------------------------------------------------------------------------------

	private int compareWithSuffix(int i, String pattern) {
		int c = 0, j = 0;

		while (j < pattern.length() && c == 0) {
			if (i + j <= originalText.length()) {

				if (j == 0) {
					c = pattern.charAt(1 + j) - originalText.charAt(i + j);
				} else if (j != 0) {
					c = pattern.charAt(j) - originalText.charAt(i + j);
				}
			} else {
				c = 1;
			}
			j++;
		}
		return c;
	}
    
	public int binarySearch(String pattern) {
		int left = 1, right = indexArray.length; //put left=1 and right=|I|
		int m;
		int c = 1; //cannot be 0
		
		while (c != 0 && left <= right) { //while c≠0 and left ≤ right
			m = left + (right - left) / 2; //put m to left + (right−left)/2
			c = compareWithSuffix(indexArray[m], pattern); //put c to compareWithSuffix(I[M])
			
			if (c < 0) { //if c<0
				right = m - 1; //put right to m−1
			} else if (c > 0) { //if c>0
				left = m + 1; //put left to m+1
			} else if (c == 0) { //of c=0
				return m; //return m
			}
		}
		return left; //Returnera L
	}
	
//-------------------------------------------------------------------------------------------------
    
    public String[] prefix(String input) {
        prefixes = new String[input.length()];
        int j=0;
        for (int i = input.length() - 1; i >= 0; i--) {
            prefixes[i] = input.substring(j, i + 1);
    //        System.out.println(prefixes[i]);
        }
//        Arrays.sort(prefixes);
//        for(int i=0; i<input.length();i++){
//        	System.out.println("--"+prefixes[i]);
//        }
        return prefixes;
    }    
    
    public static void main(String[] arg) throws IOException {
        String originalText = "aryq";
        String pattern = "r";
        SuffixArray_A s = new SuffixArray_A(originalText);
        s.createSuffixArray();
        
//        System.out.println("TEXT FOR MAKING SUFFIX ARRAY: '" + originalText + "'");
//        SuffixArray suffixarray = new SuffixArray(originalText);
//        suffixarray.createSuffixArray();
//        suffixarray.prefix(pattern);
//        String lcs= SuffixArray.lcs(originalText, pattern);
//        System.out.println("Longest common prefix string for text and pattern: " + "'" + SuffixArray.lcs(originalText, pattern) + "'");
//        Pattern word = Pattern.compile(lcs);
//        Matcher match = word.matcher(originalText);
//
//        while (match.find()) {
//             System.out.println("Found LCS at index " + match.start() + " - " + (match.end()-1));
//        }
     //   suffixarray.prefix(P);
        
        System.out.println("Original text: " + "'" + originalText + "'");
        System.out.println("Pattern string " + "'" + pattern + "'" + " found at position: " + s.binarySearch(pattern));
    }	
}