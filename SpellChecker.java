import java.util.*;
import java.io.*;

class 


/**
 * A custom Spell Checker program which uses a Trie as the data structure
 * 
 * The executable builds it's dictionary by reading in a defined dictionary file,
 * or "/usr/share/dict/words" as default, and then prompts the user to type a word.
 * Based on the input word, 3 rules are checked to see if a match is found or a
 * suggestion can be made. If no word is found, then NO SUGGESTION is printed.
 * 
 * The current rules this software checks for are:
 * 1. ImPROper cAPITalIZAioN. The entire dictionary is treated as lower case and all
 *    inputs are suggested as lowercase.
 * 2. Misplacad vuwals. If the input word is not found, the vowels A,E,I,O,U are
 *    substituted until a suggestion is found. Through a brute force method, all
 *    vowel combinations are attempted. For example, in the input "tost", "test",
 *    "tast", "tist", "tust" are all attempted words. While "toast" tries "toest",
 *    "toust", "teost", "teust", etc...
 * 3. Reeeepeeeated Letttters. Any repeated letters are removed and the word is
 *    checked again.
 *    
 * Any combination of these rules is acceptable. Capitalizations can occur on the same
 * section of the string as repeated letters or incorrect vowels.
 * 
 * @author Kartik Satoskar
 * 
 */
	
	
class SpellCheckerException extends Exception
{
	spellCheckerException(String s){
	
		super(s);
	}
}	



public class SpellChecker {
	private static final char PROMPT = '>';
	private static Trie dict = new Trie();
	private static Scanner input = new Scanner(System.in);
	private static boolean DEBUG=false;

	private static String getWord() {
		System.out.println(PROMPT+"");
		return input.next();
	}

	private static void printDictionary() {
		System.out.println(dict.toString());
	} 

	private static void buildDictionary(String filename) {
		try {
				BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(filename))));
				String line;
				while((line = br.readLine()) != null) {
				dict.insert(line.toLowerCase());
				}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}		
	}

	public static void main(String[] args) {
		if(args.length > 0) DEBUG = Boolean.parseBoolean(args[0]);
		String word, found;
		
		if(DEBUG) {
			buildDictionary("/home/debugger2017/Github/SpellChecker/words.txt");
			printDictionary();
		} else {
			buildDictionary("/usr/share/dict/words");
		}
	
		try {
			while(true) {
				word = getWord().toLowerCase();
				found = dict.find(word);
				if(DEBUG) System.out.print("Trying: "+word+". Got: ");
				if(found != null) {
					System.out.println(found);
				} else {
					System.out.println("NO SUGGESTION");
				}
			}
		} catch(Exception e) {
			throw new Exception("Word not found");
		}
	}
}
