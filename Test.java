import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class Test {
	
	/**
	 * create and return trees that have a node.
	 */
	public static ArrayList<HuffmanTree> createNode(Map<Character,Integer> dic_fre) {
		HuffmanTree treeWithOneNode;
		ArrayList<HuffmanTree> list_trees = new ArrayList<HuffmanTree>();
		
		// dic_fre.get(charac) is the frequency for this char
		for(Character charac : dic_fre.keySet()) {
			treeWithOneNode = new HuffmanTree(charac,dic_fre.get(charac),null,null);
			
			// add the HuffmanTree to the HuffmanTreeList
			list_trees.add(treeWithOneNode);
		}
		// sort the trees according to their frequency from smallest to largest
		Collections.sort(list_trees);
		return list_trees;
		
	}
	
	/**
	 * create and return the HuffmanTree
	 */
	public static HuffmanTree createHuffmanTree(ArrayList<HuffmanTree> list_trees) {
		
		// because we are going to add new tree (composed by two trees) in list_trees,
        // therefore, if list_trees.size ()> 1, it is necessary to put in order each time according to its frequant
		while (list_trees.size()>1) {
			
			Collections.sort(list_trees);
		
			// choose the first two trees in list_trees (whose frequants are smaller)
			HuffmanTree l_child = list_trees.get(0);
			HuffmanTree r_child = list_trees.get(1);
			
			// create a new tree
			int fre = l_child.getFrequance() + r_child.getFrequance();
			HuffmanTree new_tree = new HuffmanTree(null,fre,l_child,r_child);
			
			// delete the first two trees in list_trees
	        list_trees.remove(l_child);
	        list_trees.remove(r_child);
	        
	        //add a new_tree into list_trees
	        list_trees.add(new_tree);
		}
		// the first tree in list_trees is the tree we want
		return list_trees.get(0);
		
	}

	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		Map<Character,Integer> dic_fre = new LinkedHashMap<Character,Integer>();
		ArrayList<HuffmanTree> list_tree_seul_node = new ArrayList<HuffmanTree>();
		HuffmanTree huffTree = new HuffmanTree(null,0, null, null);
		String filePath_txt = "/Users/yaoxin/eclipse-workspace/DecodageHuffman/donnees/exemple_freq.txt";
		String filePath_bin = "/Users/yaoxin/eclipse-workspace/DecodageHuffman/donnees/exemple_comp.bin";
		
		Fichier fichier_fre_txt = new Fichier(filePath_txt);
		dic_fre = fichier_fre_txt.readFileTxt(filePath_txt);
		System.out.println("----------------dictionary_charac_fre-----------------");
		System.out.println(dic_fre + "\n");
		System.out.println("----------------dictionary_ASCII_fre-----------------");
		System.out.println(fichier_fre_txt.char2byte(dic_fre)+ "\n");
		
		
		System.out.println("--------------Huffmantree created-------------------");
		list_tree_seul_node = createNode(dic_fre);
		huffTree = createHuffmanTree(list_tree_seul_node);
		System.out.println(huffTree + "\n"); 
		
		
		
		System.out.println("----------------dictionary_charac_codage---------------");
		Map<Character, String> map = new LinkedHashMap<Character, String>();
		String code = new String("");
		System.out.println(huffTree.codage(map,code) + "\n");
		//huffTree.codage(map,code);
		
		System.out.println("----------------read_file_bin-------------------------");
		String l_b;
		Fichier fichier_bin = new Fichier(filePath_bin);
		l_b = fichier_bin.readFileBin(filePath_bin);
		System.out.println(l_b + "\n");
		
		
		System.out.println("----------------decodage-------------------------------");
		
		Character[] c = fichier_bin.decodage2(l_b,huffTree.codage(map,code));
 		System.out.println(Arrays.toString(c) + "\n");
 		
 		Fichier decomp = new Fichier(null);
 		decomp.fichier_decompresse_txt(c);
 		
 		
 		System.out.println("----------------compression ratio------------------------");
 		int nb_carac_total_text = fichier_fre_txt.get_nb_char_total_text(dic_fre); 
 		System.out.println("There are " + nb_carac_total_text +" characters in total");
 		int volume_init = fichier_fre_txt.get_volume_initial(fichier_fre_txt.get_nb_char_total_text(dic_fre));
 		int volume_final = fichier_fre_txt.get_volume_final(dic_fre, map);
		System.out.println("initial volume : " + volume_init);
		System.out.println("final volume : " + volume_final);
		System.out.println("compression ratio : " + fichier_fre_txt.ratio_compression(volume_init, volume_final));
		System.out.println("the average number of storage bits : " + fichier_fre_txt.nb_average_text_compresse(volume_final, nb_carac_total_text));
	
	}
		

}
