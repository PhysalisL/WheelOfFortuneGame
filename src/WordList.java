//Yixiu Liu 110602460

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class WordList{
	private ArrayList<String>tempStorage = new ArrayList<>();
	private String[]wordList;
	
	public WordList(String fileName) throws Exception{
		Scanner sc = new Scanner(new File(getClass().getResource(fileName).toURI()));
		while(sc.hasNextLine()){
			tempStorage.add(sc.nextLine());
		}
		wordList = new String[tempStorage.size()];
		for(int i=0; i<tempStorage.size(); i++){
			wordList[i] = tempStorage.get(i);
		}
		sc.close();
	}
	
	public String[] getAllWords(){
		return wordList;
	}
	
	public String getRandomWord(){
		return wordList[(int)(Math.random()*wordList.length)];
	}
	
	public int getCapacity(){
		return wordList.length;
	}
}
