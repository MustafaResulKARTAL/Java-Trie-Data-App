package dictionary;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Node isimli class oluþturularak tree-trie node yapýsý tanýmlandý
class Node{
	
	private HashMap<Character, Node> chain =new HashMap<>();
	
	public void add(char c) {
		this.chain.put(c, new Node());
	}
	
	public boolean contains(char c) {
		return this.chain.containsKey(c);
		
	}
	public Node get(char c) {
		return this.chain.get(c);
	}
	public HashMap<Character, Node> getChain(){
		return new HashMap<>(this.chain);
		
	}
}
 
public class Trie {
	
	private Node root=new Node();
	

	public static void main(String[] args) {
		Trie trie=new Trie();

// 'sozuk.txt' dosyasý okunarak kelimeler tree-trie yapýsýna eklendi		
		System.out.println("Sözlük Yükleniyor Lütfen Bekleyiniz..");
		  try {
		      File myObj = new File("sozluk.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        trie.insert(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("'sozluk.txt' Dosyasý Bulunamadý");
		      e.printStackTrace();
		      System.exit(0);
		    }
		  Scanner scan = new Scanner(System.in);
		  System.out.println("Sözlük Yükledi");
		  System.out.println("Kelime aramasý yapmak için '1' \nKelime eklemek için '2'"
		  		+ " ye\nÇýkýþ yapmak için farklý herhangi bir tuþa basýnýz .. ");
		  
		  int option=scan.nextInt();
		  
// While Döngüsü içerisinde Kullanýcýlar yapmak istedikleri Ýþlemleri yapabilirken Bu iþlemler -
// arasýnda yönlendirilebildi.
		  while (option<3 && option>0) {
			
			  switch (option) {
			  case 1:
			    System.out.println("Kelime Arama");
				System.out.println("Arama Yapýlacak Kelimenin bir veya birkaç harfini giriniz");
				String letter=scan.next();
				letter=letter.toUpperCase();
				System.out.println(trie.autoComplete(letter));
			    break;
			  case 2:
			    System.out.println("Kelime Ekleme");
			    String word=scan.next();
			    trie.insert(word.toUpperCase());
			    System.out.println("Ekleme Baþarýlý");
			    break;

			}
			 System.out.println("Kelime aramasý yapmak için '1' \nKelime eklemek için '2'"
				  		+ " ye\nÇýkýþ yapmak için farklý herhangi bir sayý giriniz.. ");
			 option=scan.nextInt();
			
		}
	}

// Ýnsert isimli metotlarýmýzla Trie kelime aðacýmýza kelime ekleme iþlemleri yapýlýyor.
// Kullanýcýnýn aramak için veya eklemek için girdiði string ifadelerin tamamý büyük harf olacak -
//  þekilde güncellenerek arama ve ekleme iþlemleri yapýldý.
	public void insert(String word) {
		insert(word,root);
		
	}
	
	private void insert(String word, Node node) {
		
		if(word==null || word.isEmpty()) {
		     return;
		}
		char firstCharacter=word.charAt(0);
		if(!node.contains(firstCharacter)) {
			node.add(firstCharacter); 
		}
		insert(word.substring(1),node.get(firstCharacter));
		
	}

// autocomplate ve populatewords isimli fonksiyonlarýmýzda ilk harfleri girilen -
// kelimelerin opsiyonlarý döndürülürken tamamý girilen kelimenin kendisi dönderilmiþ oldu.
// Böylece Dynamic Search iþlemi gerçeklerþtirildi.
	
	public List<String> autoComplete(String suggestString){
		List<String> words=new ArrayList<String>();
		if(suggestString==null || suggestString.isEmpty()) {
			return words;
		}
		
		return autoComplete(suggestString,words,root,suggestString);
			
	}

	private List<String> autoComplete(String suggestString, List<String> words, Node node, String original) {
		if(suggestString==null || suggestString.isEmpty()) {
			return populateWords(original,node,words);
		}
		char fistCharacter=suggestString.charAt(0);
		if(!node.contains(fistCharacter)) {
			return words;
		}
		return autoComplete(suggestString.substring(1),words,node.get(fistCharacter),original);
	}

	private List<String> populateWords(String original, Node node, List<String> words) {
		
		if(node.getChain().size()==0) {
			words.add(original);
		} 
		node.getChain().forEach((c,SubNode)->populateWords(original+c, SubNode, words));
		return words;
	}

}
