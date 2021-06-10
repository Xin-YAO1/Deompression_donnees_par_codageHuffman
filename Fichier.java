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
	 * read the file which contains the characters and their frequencies
	 * @param filePath
	 * @return a dictionary contains the characters and their frequencies
	 * @throws Exception
	 */
	public Map<Character,Integer> readFileTxt(String filePath) throws Exception {
		
		Map<Character,Integer> dic_fre = new LinkedHashMap<Character,Integer>();
		File file = new File(filePath);
        FileReader fr;
        fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        // we don't need the first line, which is the number of characters
        String line=null;
        while((line = br.readLine()) != null){
        	// for each row, the second element is the frequency
        	// transfers the String to Int
        	Integer fre = Integer.parseInt(line.substring(2));
        	// for each line, the first element is the char
        	String c = line.substring(0,1);
        	// add each character and its frequan in a dictionary
        	dic_fre.put(c.charAt(0),fre);
        }
        br.close();
        fr.close();
        return dic_fre;
        //System.out.print(dic_fre);
	}
	
	
	/**
	 * return a dictionary which composes the characters (type Byte, Ascii) and its frequencies
	 */
	public Map<Byte,Integer> char2byte(Map<Character,Integer> dic_fre){
		Map<Byte,Integer> dic_byte_fre = new LinkedHashMap<Byte,Integer>();
		for(Character carac : dic_fre.keySet()) {
			// transfers chars to type Byte
			int byteAscii = new Integer(carac);
			byte b = (byte)byteAscii;
			dic_byte_fre.put(b,dic_fre.get(carac));
		}
		return dic_byte_fre;
	}

	
	/**
	 * read the .bin file and return the binary codes in type String
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
	 * return the letters according to the codes we obtained in file.bin
	 * @param codes from .bin
	 * @param huffmanCode, the dictionary which composed the chars and their Huffmancodes
	 * @return
	 */
	public Character[] decodage2(String codes,Map<Character,String> huffmanCode) {
		// inverts the Key and Value of the dictionary, for example o=00 -> 00=o, b=010 -> 010=b
		Map<String, Character> map = new LinkedHashMap<String,Character>();
		for(Map.Entry<Character, String> entry:huffmanCode.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		System.out.println(map + "\n");
		
		List<Character> list_carac= new ArrayList<>();
		// we have codes 1011111011111101010000000
		// we start with the first byte 1, and then go through 10, 101, 1011 ...
		// then depending on the map, we can get the letter associates the code
		for(int i=0; i<codes.length()-1;) {
			// for each fixed i, count is to iterate through the following code
			int count = 1;
			boolean NoFinded = true;
			Character b = null;
			while(NoFinded) {
				String key = codes.substring(i, i+count);
				b = map.get(key);
				// if not find the letter associates the code, we pass the following code
				if(b==null) {
					count++;
				}else {
					NoFinded = false;
				}
			}
			// add the letters to the list
			list_carac.add(b);
			// i starts with i + count
			i = i +count;
		}
		// add the letters in character []
		Character c[] = new Character[list_carac.size()];
		for(int i=0; i<c.length;i++) {
			c[i] = list_carac.get(i);
		}
		
		return c;
	}
	
	/**
	 * write the letters (text decompresstion) in a .txt file and we can open it in the same root as the program	
	 */
	public void fichier_decompresse_txt(Character[] c) {
		try {
            BufferedWriter out = new BufferedWriter(new FileWriter("decompress.txt"));
            for (int i = 0; i< c.length;i++) {
            	out.write(c[i]);
            }
            out.close();
            System.out.println("the decompress.txt file is well created" + "\n");
        } catch (IOException e) {
        }
	}
	
	
	
	//compression ratio
	/**
	 * return the number of char_total in the text
	 */
	public int get_nb_char_total_text(Map<Character,Integer> dic_fre) {
		int nb_char = 0;
		// go through the dictionary which composed the chars and their frequency
		// make the sum for each chars
		for(Map.Entry<Character, Integer> entry_fre : dic_fre.entrySet()) {
			nb_char = nb_char + entry_fre.getValue();
		}
        return nb_char;
	}
	
	/**
	 * return the initial volume of text given
     * according to ASCII, each character has 8 bits
     * volume initial = 8 * nb_char_text
	 */
	public int get_volume_initial(int nb_char_total_text) {
		return nb_char_total_text * 8;
	}
	
	/**
	 * return the final volume of text given
	 */
	public int get_volume_final(Map<Character,Integer> dic_fre,Map<Character, String> dic_codeHuffman) {
		int volume = 0;
		
		// go through two dictionaries dic_fre and dic_codeHuffman
		// we can find the same char and get their codeHuffman and their frequency
		// volume final = sum of volume for each char
        // volume for each char = frequan_carac * nb_codage (use length())
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
	 * return compression ratio
	 */
	public float ratio_compression(int volume_init,int volume_final) {
		return 1-(float)volume_final/volume_init;
	}
	
	/**
	 * return the average number of storage bits of a character
	 */
	public float nb_average_text_compresse(int volume_final,int nb_carac_total_text) {
	    return (float)volume_final/nb_carac_total_text;
	}
	
}
