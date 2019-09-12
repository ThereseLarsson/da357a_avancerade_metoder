package uppgift4;

/**
 * class to test the SuffixArray-class.
 * @author Therese Larsson
 *
 */
public class SuffixArrayTester {
	public static void main(String[] args) {
		String textT = "abriyz";
		String pattern = "rix";
		SuffixArray sa = new SuffixArray(textT);
		sa.createSuffixArray();
		sa.prefix(pattern);
		sa.printArray();
		sa.testRunBinarySearch(textT, pattern);
	}
}
