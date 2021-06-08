import java.util.HashMap;
import java.util.Map;

public class HuffmanTree extends Node{
	private Character label;
	private HuffmanTree l_child;
	private HuffmanTree r_child;

	public HuffmanTree(Character label, int frequant, HuffmanTree l_child, HuffmanTree r_child) {
		super(frequant);
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
	 * retouner un dictionnaire qui compose par un carac et son codeHuffman pour un arbre huffman donnee
	 * je definie le type de codeHuffman est String
	 * @param map, un dictionnaire pour mettre les carac et ses codeHuffman
	 * @param code, initialiser par " "
	 * @return
	 */
	public Map<Character,String> codage(Map<Character,String> map,String code) {
		// parcourir l'arbre jusqu'a le noeud est une feuill
        // si ce noeud est une feuill, mettre le code dans le dictionnaire
		if((this.getL_child() == null) && (this.getR_child() == null)){
			map.put(this.getLabel(),code);
			//System.out.println(map);
		}
		else {
			// on ajoute '0' si parcour a gauche, ajoute '1' si a droite
			
			this.getL_child().codage(map,code+"0");
			this.getR_child().codage(map,code+"1");
		}
		return map;
	}



}
