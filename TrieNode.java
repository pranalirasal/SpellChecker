import java.util.LinkedList;

/**
 *	A Trie Node
 *
 *	@author Kartik Satoskar
 */

public class TrieNode {
	private char data;
	//private ArrayList<TrieNode> children;
	private LinkedList<TrieNode> children;
	private TrieNode parent;
	private String word;

	public TrieNode() {
		this.children=new LinkedList<TrieNode>();
		this.parent=null;
	}

	public TrieNode(char data,TrieNode parent) {
		this.data=data;
		this.parent=parent;
		this.children=new LinkedList<TrieNode>();
	}

	/**
	 * checks to see given character match with TrieNode's data
	 * @param data the char to match
	 * @return the child node or null if not found
	 */
	public TrieNode findChild(char data) {
		if(this.children != null) {
			for(int i=0;i<this.children.size();i++) {
				TrieNode child=this.children.get(i);
				if(child.data==data) {
					return child;
				}
			}
		}
		return null;
	}

	/**
	 *@return String
	 */
	public String toString() {
		String toReturn;
		if(this.children.size() > 0) {
			toReturn = "Node "+this.data+" has children: ";
			for(int i=0;i<this.children.size();i++) {
				char child=this.children.get(i).data;
				toReturn+=" "+child;
			}
			if(this.word != null) toReturn+=" and gives word "+this.word;
			toReturn+="\n";
			for(int i=0;i<this.children.size();i++) {
				toReturn+=this.children.get(i).toString();	
			}	
		} else {
			toReturn = "Node "+this.data+" has no children.";
			if(this.word != null) toReturn+=" and gives word "+this.word;
			toReturn+="\n";
		}
		return toReturn;
	}

	/**
	 *@return String of TrieNode's all parents
	 */
	public String parents() {
		String toReturn="";
		TrieNode look=this;
		while(look != null) {
			toReturn = look.data + " " + toReturn;
			look=look.parent;
		}
		return toReturn;
	}

	/**
	 *@return List of TrieNode's children
	 */
	public LinkedList<TrieNode> getChildren() {
		return this.children;
	}

	public char getData() {
		return this.data;
	}

	public String getWord() {
		return this.word;
	}

	public void setData(char data) {
		this.data=data;
	}

	public void setWord(String word) {
		this.word=word;
	}

}
