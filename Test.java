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
	 * creer et retourner des arbres qui a juest un node
	 */
	public static ArrayList<HuffmanTree> createNode(Map<Character,Integer> dic_fre) {
		HuffmanTree treeWithOneNode;
		ArrayList<HuffmanTree> list_trees = new ArrayList<HuffmanTree>();
		
		// dic_fre.get(carac) est le frequance pour cette carac
		for(Character carac : dic_fre.keySet()) {
			treeWithOneNode = new HuffmanTree(carac,dic_fre.get(carac),null,null);
			
			// ajouter le HuffmanTree dans la liste de HuffmanTree
			list_trees.add(treeWithOneNode);
		}
		// trier les arbres selon son frequant du plus petit au plus grand
		Collections.sort(list_trees);
		return list_trees;
		
	}
	
	/**
	 * creer et retourner l'arbre huffman
	 */
	public static HuffmanTree createHuffmanTree(ArrayList<HuffmanTree> list_trees) {
		
		//car on va ajouter nouveau arbre(composee par deux arbres) dans list_trees
        //donc,si list_trees.size() >1, il faut mise en ordre chaque fois selon ses frequant
		while (list_trees.size()>1) {
			
			Collections.sort(list_trees);
		
			// choisir les deux premier arbre dans list_trees (dont les frequant sont plus petit)
			HuffmanTree l_child = list_trees.get(0);
			HuffmanTree r_child = list_trees.get(1);
			
			//creer un nouveau arbre
			int fre = l_child.getFrequant() + r_child.getFrequant();
			HuffmanTree new_tree = new HuffmanTree(null,fre,l_child,r_child);
			
			//supprimer les deux premier arbres dans list_trees
	        list_trees.remove(l_child);
	        list_trees.remove(r_child);
	        
	        //ajouter new_tree dans list_trees
	        list_trees.add(new_tree);
		}
		//le premier arbre dans list_trees est l'arbre que on veut
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
		System.out.println("----------------dictionary_carac_fre-----------------");
		System.out.println(dic_fre + "\n");
		System.out.println("----------------dictionart_ASCII_fre-----------------");
		System.out.println(fichier_fre_txt.char2byte(dic_fre)+ "\n");
		
		
		System.out.println("--------------Huffmantree bien cree-------------------");
		list_tree_seul_node = createNode(dic_fre);
		huffTree = createHuffmanTree(list_tree_seul_node);
		System.out.println(huffTree + "\n"); 
		
		
		
		System.out.println("----------------dictionary_carac_codage---------------");
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
 		
 		
 		System.out.println("----------------taux compression------------------------");
 		int nb_carac_total_text = fichier_fre_txt.get_nb_carac_total_text(dic_fre); 
 		System.out.println("IL y a " + nb_carac_total_text +" carac en total");
 		int volume_init = fichier_fre_txt.get_volume_initial(fichier_fre_txt.get_nb_carac_total_text(dic_fre));
 		int volume_final = fichier_fre_txt.get_volume_final(dic_fre, map);
		System.out.println("volum initial : " + volume_init);
		System.out.println("volum final : " + volume_final);
		System.out.println("taux compression : " + fichier_fre_txt.taux_compression(volume_init, volume_final));
		System.out.println("le nombre moyen de bits de stockage : " + fichier_fre_txt.nb_moy_text_compresse(volume_final, nb_carac_total_text));
	
	}
		

}
