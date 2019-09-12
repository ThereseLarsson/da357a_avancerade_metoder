package uppgift1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextSearch {
	private static int nbrOfMatches;
	private static int nbrOfCompares;
	private static String filename;
	private static String searchPhrase;
	private static String text;
	
	/**
	 * Läser in textfil
	 * @param filename namnet på inläsningsfilen
	 * @throws IOException om filen inte hittas
	 */
	public static String readFile(String filename) throws IOException {
		File file = new File(filename);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return text = fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
	
	/**
	 * genererar en text till worst case scenario, skriver strängen som en txt-fil
	 * @param length, längden på texten som ska genereras
	 * @throws IOException 
	 */
	public static void generateWorstCaseText(int length) throws IOException {
		text = "";
		for(int i = 0; i < length; i++) {
			if(i == length - 1) { //om vi är på sista indexet i itereringen
				text += "b";
			} else {
				text += "a";
			}
		}
		PrintWriter out = new PrintWriter("files/worstCase_1000000.txt", "UTF-8");
		out.println(text);
		out.close();
	}
	
	/**
	 * testmetod för att kontrollera att testfilen läses in korrekt
	 * användes vid felsökning
	 */
	public static void printFile() {
		for(int i = 0; i < text.length(); i++) {
			System.out.print(text.charAt(i));
		}
	}
	
	/**
	 * gör om en sträng till en lista med tecken
	 * @param string strängen som ska göras om
	 * @return returnerar listan med tecken
	 */
	public static char[] stringToCharList(String string) {
		char[] list = new char[string.length()];
		for(int i = 0; i < string.length(); i++) {
			list[i] = string.charAt(i);
		}
		return list;
	}
	
	/**
	 * Hittar antalet gånger ett visst ord förekommer i en text
	 * @param file den textmängd som ska genomsökas
	 * @param search sökordet
	 */
	public static void searchForPhrase(String n, String m) {
		//n = textmängd, m = sökningsfras
		nbrOfMatches = 0;
		nbrOfCompares = 0;
		
		char[] text = stringToCharList(n);
		char[] searchPhrase = stringToCharList(m);
		for(int i = 0; i <= text.length - searchPhrase.length; i++) {
			int j;
			for(j = 0; j < searchPhrase.length; j++) {
				nbrOfCompares++;
				if(text[i + j] != searchPhrase[j]) {
					break;
				}	
			}
			if(j == searchPhrase.length) { //har vi kommit hit så har vi hittat vår sökningsfras
				nbrOfMatches++;
			}
		}
	}
	
	/**
	 * skriver ut hur många gånger det givna sökordet förekom i textmängden
	 */
	public static void printResult() {
		System.out.println("Antal jämförelser: " + nbrOfCompares);
		if(nbrOfMatches == 1) {
			System.out.println("Ordet " + "`" + searchPhrase  + "`" + " förekom " + nbrOfMatches + " gång");
		} else {
			System.out.println("Ordet " + "`" + searchPhrase  + "`" + " förekom " + nbrOfMatches + " gånger");
		}
	}
	
	public static void main(String[] args) throws IOException {
		searchPhrase = "and";
		filename = "files/bible_1000000.txt";
		text = readFile(filename);

		long start = System.currentTimeMillis();
		searchForPhrase(text, searchPhrase);
		long stop = System.currentTimeMillis();
		
		printResult();
		System.out.println("Exekveringstid: " + (stop-start) / 1000.0 + " sekunder");
	}
}
