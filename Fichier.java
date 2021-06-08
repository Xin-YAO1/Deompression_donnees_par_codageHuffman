import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Fichier {
	
	private String filePath;

	public Fichier(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * lire le fichier qui contient les caractere et ses frequence
	 * @param filePath
	 * @return un dictionaire de carac et le frequence
	 * @throws Exception
	 */
	public Map<Character,Integer> readFileTxt(String filePath) throws Exception {
		
		Map<Character,Integer> dic_fre = new LinkedHashMap<Character,Integer>();
		File file = new File(filePath);
        FileReader fr;
        fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        // on n'est pas besoin la premier ligne, qui est le nombre de caractere
        String line=null;
        while((line = br.readLine()) != null){
        	// pour chaque ligne, le deuxier element est le frequance
        	// transfere le String to Int
        	Integer fre = Integer.parseInt(line.substring(2));
        	// pour chaque ligne, le premier element est le carac
        	String c = line.substring(0,1);
        	// ajouter chaque carac et son frequan dans un dictionnaire
        	dic_fre.put(c.charAt(0),fre);
        }
        br.close();
        fr.close();
        return dic_fre;
        //System.out.print(dic_fre);
	}
	
	
	/**
	 * retourner un dictionnaire qui composee les carac (type Byte, Ascii) et ses frequance
	 */
	public Map<Byte,Integer> char2byte(Map<Character,Integer> dic_fre){
		Map<Byte,Integer> dic_byte_fre = new LinkedHashMap<Byte,Integer>();
		for(Character carac : dic_fre.keySet()) {
			// transfere les carac a type Byte
			int byteAscii = new Integer(carac);
			byte b = (byte)byteAscii;
			dic_byte_fre.put(b,dic_fre.get(carac));
		}
		return dic_byte_fre;
	}

	
	/**
	 * lire le fichier.bin et retouner les codes bilinaire en type String
	 */
	public String readFileBin(String filePath) {
		String s="";
		try {
			FileInputStream f = new FileInputStream(filePath);
			try {
				int first = f.read();
				while (first != -1) {
					//System.out.println(Integer.toBinaryString(first));
					s = s + Integer.toBinaryString(first);
					first = f.read();
				}
			}
			finally {
				f.close();
				}
			} catch (FileNotFoundException e) {
				System.out.println("Not found");
			} catch (IOException e) {
				// TODO: handle exception
			}
		return s;
	}
	
	
	/**
	 * retouner les lettres selon les codes on a obtenu en fichier.bin
	 * @param codes from .bin
	 * @param huffmanCode, le dictionaire qui a compose les carac et ses codeHuffman
	 * @return
	 */
	public Character[] decodage2(String codes,Map<Character,String> huffmanCode) {
		// inverse le Key et Value du dictionnaire , par exemple o=00 -> 00=o, b=010 -> 010=b
		Map<String, Character> map = new LinkedHashMap<String,Character>();
		for(Map.Entry<Character, String> entry:huffmanCode.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		
		List<Character> list_carac= new ArrayList<>();
		// on a codes 1011111011111101010000000
		// on commence par le premier byte 1, et puis parcourir 10, 101, 1011...
		// puis selon le map, on peux obtenir le lettre associe le code
		for(int i=0; i<codes.length()-1;) {
			// pour chaque i fixe, count est pour parcourir le code suivant
			int count = 1;
			boolean NoFinded = true;
			Character b = null;
			while(NoFinded) {
				String key = codes.substring(i, i+count);
				b = map.get(key);
				// si trouve pas le lettre associe le code, on passe le code suivant
				if(b==null) {
					count++;
				}else {
					NoFinded = false;
				}
			}
			// jouter les lettre dans la liste
			list_carac.add(b);
			// i commence par i+count
			i = i +count;
		}
		// ajouter les lettre dans character[]
		Character c[] = new Character[list_carac.size()];
		for(int i=0; i<c.length;i++) {
			c[i] = list_carac.get(i);
		}
		
		return c;
	}
	
	/**
	 * ecrire les lettre (text decompresstion) dans un fichier.txt et le afficher
	 */
	public void fichier_decompresse_txt(Character[] c) {
		try {
            BufferedWriter out = new BufferedWriter(new FileWriter("decompress.txt"));
            for (int i = 0; i< c.length;i++) {
            	out.write(c[i]);
            }
            out.close();
            System.out.println("le fichier decompress.txt est bien cree" + "\n");
        } catch (IOException e) {
        }
	}
	
	
	
	//le taux de compression
	/**
	 * retourner le nb de carac_total dans le text
	 */
	public int get_nb_carac_total_text(Map<Character,Integer> dic_fre) {
		int nb_carac = 0;
		// parcourir le dictionnaire carac et ses frequance
		// on fait le somme de frequance
		for(Map.Entry<Character, Integer> entry_fre : dic_fre.entrySet()) {
			nb_carac = nb_carac + entry_fre.getValue();
		}
        return nb_carac;
	}
	
	/**
	 * retourner le volume initial de texte donnee
     * d'apres ASCII, chaque caractere a 8 bits
     * volume initial = 8 * nb_carac_text
	 */
	public int get_volume_initial(int nb_carac_total_text) {
		return nb_carac_total_text * 8;
	}
	
	/**
	 * retourner le volume final de texte donnee
	 */
	public int get_volume_final(Map<Character,Integer> dic_fre,Map<Character, String> dic_codeHuffman) {
		int volume = 0;
		
		// parcourir deux dictionnaires dic_fre et dic_codeHuffman
		// on trouve le meme carac et obtenir ses codeHuffman et ses frequancce
		// volume final = somme de volume pour chaque carac
        // volume pour chaque carac = frequan_carac * nb_codage (utiliser length())
		for(Map.Entry<Character, Integer> entry_fre : dic_fre.entrySet()) {
			for(Map.Entry<Character, String> entry_code : dic_codeHuffman.entrySet()) {
				if(entry_fre.getKey().equals(entry_code.getKey())) {
					volume = volume + entry_fre.getValue() * entry_code.getValue().length();
				}
			}
		}
		return volume;
	}
	
	/**
	 * retourner le taux_compression
	 */
	public float taux_compression(int volume_init,int volume_final) {
		return 1-(float)volume_final/volume_init;
	}
	
	/**
	 * retourner le nombre moyen de bits de stockage d’un caractère
	 */
	public float nb_moy_text_compresse(int volume_final,int nb_carac_total_text) {
	    //retourner le nombre moyen de bits de stockage d’un caractère
	    return (float)volume_final/nb_carac_total_text;
	}
	
}
