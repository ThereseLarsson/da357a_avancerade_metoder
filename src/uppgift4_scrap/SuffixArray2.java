package uppgift4_scrap;

import java.util.Arrays;

public final class SuffixArray2 {

    private String[] suffixArray;

    public SuffixArray2(String string) {
    	build(string);
    }

	
	/**
	 * builds a suffix array from a given string.
	 * @param string
	 */
	public String[] build(String string) {
		suffixArray = new String[string.length()];
		for (int i = 0; i < string.length(); i++) { //builds the suffix array
			suffixArray[i] = string.substring(i, string.length() - 1);
		}
		Arrays.sort(suffixArray); //sorts the vector
		return suffixArray;
	}

    /**
     * Returns  0, if the string starts with the pattern.
     * Returns  1, if the string is greater than 1.
     * Returns -1, if the string is smaller than pattern.  
     * 
     * 
     * @param str           the string to be matched with the pattern.
     * @param pattern       the pattern to be searched in the string
     * @return              -1, 0, or 1.
     */
    private int compare(String str, String pattern) {
        int length = str.length() < pattern.length() ? str.length() : pattern.length();

        for (int i = 0; i < length; i++) {
            if (str.charAt(i) < pattern.charAt(i)) return -1;
            if (str.charAt(i) > pattern.charAt(i)) return  1;
        }

        // string was greater than myself. thus return 0.
        return str.length() < pattern.length() ? -1 : 0;
    }


    private boolean binarySearch(int lb, int hb, String pattern) {
        if (lb > hb) {
            return false;
        }
        int mid = (lb + hb) / 2;

        int match = compare(suffixArray[mid], pattern);

        if (match == 0) return true;

        if (match > 0) { 
            return binarySearch(lb, mid -1, pattern);
        } else {
            return binarySearch(mid + 1, hb, pattern);
        }
    }

    /**
     * Checks the pattern if present in the string
     * 
     * @param pattern   the pattern to be searched in the string
     * @return          true if pattern is found else false.
     */
    public boolean search (String pattern) {
        return binarySearch(0, suffixArray.length - 1, pattern);
    }
}