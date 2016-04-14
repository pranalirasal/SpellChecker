/**
 *	A Trie
 *
 *	@author Kartik Satoskar
 */

public class Trie {
	private TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	public void insert(String toAdd) {
		insert(root,toAdd,0);
	}

	/**
	 *Insert String into Trie
	 *@param curr TriNode and String toAdd 
	 */
	private static void insert(TrieNode curr,String toAdd,int pos) {
		if(toAdd != null && toAdd.length() > 0) {
			if(pos == toAdd.length()) {
				curr.setWord(toAdd);
			} else {
				TrieNode currNode = curr;
				TrieNode found,newly;
				char charToFind = toAdd.charAt(pos);
				if((found = currNode.findChild(charToFind))==null) {
					newly = new TrieNode(charToFind,currNode);
					currNode.getChildren().add(newly);
					insert(newly,toAdd,pos+1);
				} else {
					insert(found,toAdd,pos+1);
				}
			}
		}
	}

	/**
	 *Check to see if a String is in Trie
	 *@param String toFind
	 */
	public String find(String toFind) {
		String found;

		found = simpleFind(root,toFind,0);
		if(found != null) {
			return found;
		} else {
			return regexFind(root,toRegex(toFind),0);
		}
	}

	/**
	 *Recursively finds for exact match in trie
	 *@param node
	 *@param toFind
	 *@param pos
	 *@return 
	 */
	private String simpleFind(TrieNode node,String toFind,int pos) {
		if((node.getWord() != null) && (node.getWord().matches(toFind))) return node.getWord();
		if(node.getChildren() == null) return null;

		for(int i=0;i < toFind.length();i++) {
			for(int j=0;j < node.getChildren().size();j++) {
				if(node.getChildren().get(j).getData() == toFind.charAt(i)) {
					return simpleFind(node.getChildren().get(j),toFind,pos+1);
				}
			}
		}
		return null;
	}

	private static String toRegex(String word) {
		boolean flg = false;

		for(int i = 0;i < word.length();i++) {
			if((i < word.length()-1) && (word.charAt(i) == word.charAt(i+1))) {
				flg=true;
				word = replaceLetter(word,i+1,"");
				i = i-1;
			} else if(flg) {
				word = replaceLetter(word,i,word.charAt(i)+"+");
				flg=false;
				i++;
			}
		}

		for(int i = word.length()-1;i>=0;i--) {
			if(isVowel(word.charAt(i))) {
				word = replaceLetter(word,i,"[aeiou]");
			}
		}
		return word;
	}

	private static boolean isVowel(char toCheck) {
		if(toCheck == 'a' || toCheck == 'e' || toCheck == 'i' || toCheck == 'o' || toCheck == 'u') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This is a small helper function to replacing letters in a word.
	 * It is also used to delete letters by sending in an empty string in the place of a character in toAdd.
	 * @param word The word
	 * @param place The position (0-word.length()) to replace in the word
	 * @param toAdd The character to put in that place, or an empty string to delete a character
	 * @return the modified word
	 */
	public static String replaceLetter(String word, int place, String toAdd) {
		if(word.length() == 1) {
			if(place == 0) {
				return toAdd; //if we're replacing the first letter of a 1 letter word, just return the new letter
			}
		} else if(place == 0) {
			word = toAdd + word.substring(1); //replace the first character
		} else if(place == word.length()-1) {
			word = word.substring(0,word.length()-1) + toAdd; //replace the last character
		} else {
			word = word.substring(0, place) + toAdd + word.substring(place+1, word.length()); //replace a character in the middle of the word
		}
		return word;
	}	
	
	public String toString() {
		return traverseTrieNBC(this.root);
	}

	/**
	 * Traverses the Trie childen before node
	 * @param start
	 */
	private static String traverseTrieCBN(TrieNode start) {
		String toReturn = "";
		if(start != null) {
			for(int i = 0; i < start.getChildren().size(); i++) {
				toReturn += traverseTrieCBN(start.getChildren().get(i));
			}
			if(start.getWord() != null) toReturn += start.getWord()+"\n";
		}
		return toReturn;
	}
	
	/**
	 * Traverses the Trie node before children
	 * @param start
	 */
	private static String traverseTrieNBC(TrieNode start) {
		String toReturn = "";
		if(start != null) {
			if(start.getWord() != null) toReturn += start.getWord()+"\n";
			for(int i = 0; i < start.getChildren().size(); i++) {
				toReturn += traverseTrieCBN(start.getChildren().get(i));
			}
		}
		return toReturn;
	}	
	
	/**
	 * Recursively follows all possible paths given a RegEx statement
	 * Valid RegEx includes:
	 * 1. + for repeated characters 
	 * 2. [aeiou] for vowels
	 * 3. normal characters
	 * @param node
	 * @param toMatch
	 * @param pos
	 * @return
	 */
	private static String regexFind(TrieNode node, String toMatch, int pos) {
		String found;
		char tmpChar;
		
		if((node.getWord() != null) && (node.getWord().matches(toMatch))) return node.getWord(); //jump out as soon as we find a match
		if(node.getChildren() == null) return null;
		for(int i = pos; i < toMatch.length(); i++) {
			//System.out.print("Looking for: "+toMatch+". Looking at Position: "+i+". On Node: "+node.data+". Word stored: "+node.word+". Path is: "+node.parents()+". Children are:");
			//for(int j = 0; j < node.children.size(); j++) {
			//	System.out.print(" "+node.children.get(j).data);
			//}
			//System.out.print("\n");
			if(toMatch.charAt(i) == '[') { //a little trickery for vowels
				for(int j = 0; j < node.getChildren().size(); j++) {
					tmpChar = node.getChildren().get(j).getData();
					if(tmpChar == 'a' || tmpChar == 'e' || tmpChar == 'i' || tmpChar == 'o' || tmpChar == 'u') {
						found = regexFind(node.getChildren().get(j), toMatch, pos+7); //advance past the regex
						if(found != null) return found;
					}
				}
			} else if(toMatch.charAt(i) == '+') { //a little trickery for repeated characters
				for(int j = 0; j < node.getChildren().size(); j++) {
					if(node.getData() == node.getChildren().get(j).getData()) { //try repeated characters
						found = regexFind(node.getChildren().get(j), toMatch, pos);
						if(found != null) return found;
					}
				}
			} else { //just a normal character
				for(int j = 0; j < node.getChildren().size(); j++) {
					if(node.getChildren().get(j).getData() == toMatch.charAt(i)) {
						found = regexFind(node.getChildren().get(j), toMatch, pos+1);
						if(found != null) return found;
					}
				}
			}
		}
		return null;
	}
}
