import java.util.HashMap;
import java.util.Map;

public class HuffmanTree extends Node{
	private Character label;
	private HuffmanTree l_child;
	private HuffmanTree r_child;

	public HuffmanTree(Character label, int frequance, HuffmanTree l_child, HuffmanTree r_child) {
		super(frequance);
		this.label = label;
		this.l_child = l_child;
		this.r_child = r_child;
		// TODO Auto-generated constructor stub
	}
	
	public HuffmanTree getL_child() {
		return l_child;
	}

	
	public void setL_child(HuffmanTree l_child) {
		this.l_child = l_child;
	}


	public HuffmanTree getR_child() {
		return r_child;
	}


	public void setR_child(HuffmanTree r_child) {
		this.r_child = r_child;
	}

	public Character getLabel() {
		return label;
	}
	


	@Override
	public String toString() {
		return "HuffmanTree [label=" + label + ", l_child=" + l_child + ", r_child=" + r_child + "]";
	}

	/**
	 * Returns a dictionary of characters and corresponding codeHuffman.
	 * The codeHuffman is of type String
	 * @param map, a dictionary of characters and corresponding codeHuffman.
	 * @param code, The initial value is " "
	 * @return
	 */
	public Map<Character,String> codage(Map<Character,String> map,String code) {
		// walking through the tree until the node is a leaf.
        // if this node is a leaf, put the codeHumman in the dictionary.
		if((this.getL_child() == null) && (this.getR_child() == null)){
			map.put(this.getLabel(),code);
			//System.out.println(map);
		}
		else {
			// add '0' if traversed to the left, add '1' if to the right
			
			this.getL_child().codage(map,code+"0");
			this.getR_child().codage(map,code+"1");
		}
		return map;
	}



}
