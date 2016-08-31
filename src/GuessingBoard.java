//Yixiu Liu 110602460
import java.util.ArrayList;


import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
/*
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
*/

public class GuessingBoard {
	private Pane pane;
	private WordList wordStorage;
	private String currentWord;
	private ArrayList<String>letters;
	private ArrayList<Text>displayLetters;
	private ArrayList<String>usedLetters;
	private int remainingLetters;
	private String[]orderedList = {"E","N","A","I","O","H","R","T","S","L",
			"F","C","D","U","G","K","P","Y","M","B","V","W","J"};
	private AudioClip right = new AudioClip(getClass().getResource("right.wav").toString());
	private AudioClip wrong = new AudioClip(getClass().getResource("wrong.wav").toString());
	private AudioClip theEnd = new AudioClip(getClass().getResource("Gunshot.wav").toString());
	
	public GuessingBoard(){
		pane = new Pane();
		try {
			wordStorage = new WordList("WordFile.txt");
		} catch (Exception e) {e.printStackTrace();}
		generate();
	}
	
	public void generate(){
		pane.getChildren().clear();             
		currentWord = wordStorage.getRandomWord().toUpperCase();
		letters = new ArrayList<String>();
		usedLetters = new ArrayList<String>();
		displayLetters = new ArrayList<Text>();
		
		for(int i=0; i<currentWord.length(); i++){
			letters.add(Character.toString(currentWord.charAt(i)));
		}
		for(int i=0; i<letters.size();i++){
			Text letter = displayLetters.get(i);
			displayLetters.add(new Text(" "));
			letter.setFont(Font.font(44));
			letter.setX(i*50);
			letter.setY(50);
			letter.setFill(Color.WHITE);
			letter.setEffect(new Bloom(0.1));
			padding(letter, letters.get(i));
			pane.getChildren().add(letter);
		}
		remainingLetters = letters.size();
		occupySpaceChars();	
	}
	
	public int checkLetter(String input){
		String userInput = input.toUpperCase().substring(0,1);
		int multiplier=0;
		for(int i = 0; i<letters.size(); i++){
			if(letters.get(i).equals(userInput)){
				multiplier++;
				displayLetters.get(i).setText(letters.get(i));
			}
		}
		if(!usedLetters.contains(userInput)){
			usedLetters.add(userInput);
		}
		else{
			multiplier = 0;
		}
		playSound(input, multiplier);
		remainingLetters-=multiplier;
		return multiplier;
	}
	
	public int checkString(String input){
		int multiplier=0;
		if(currentWord.equals(input.toUpperCase())){
			multiplier = remainingLetters;
			for(int i = 0; i<currentWord.length(); i++){
				displayLetters.get(i).setText(Character.toString(currentWord.charAt(i)));
			}
			remainingLetters =  0;
			theEnd.play();
		}
		else{
			playSound(input, multiplier);
		}
		return multiplier;
	}
	
	private void occupySpaceChars(){
		checkLetter(" ");
	}
	
	private void padding(Text bindingText, String actualText){
		if(actualText.equals(" "))
			return;
		Rectangle temp = new Rectangle(39, 39);
		temp.xProperty().bind(bindingText.xProperty().subtract(7));
		temp.yProperty().bind(bindingText.yProperty().subtract(33));
		temp.setArcHeight(20);
		temp.setArcWidth(20);
		temp.setFill(Color.BLACK);
		temp.setRotationAxis(Rotate.Z_AXIS);
		temp.setEffect(new Reflection());
		pane.getChildren().add(temp);
		/*
		temp.setEffect(null);
		RotateTransition r = new RotateTransition();
		r.setAutoReverse(true);
		r.setDuration(Duration.seconds(1));
    	r.setInterpolator(Interpolator.LINEAR);
		r.setCycleCount(Animation.INDEFINITE);
		r.setToAngle(180);
		r.setNode(temp);
		r.play();
		//return temp;
		*/
	}
	
	private void playSound(String input, int multiplier){
		if(!input.equals(" ")){
			if(multiplier==0)
				wrong.play();
			else
				right.play();
		}
	}
	
	public double getGameProgress(){
		return (double)remainingLetters/currentWord.length();
	}
	
	public boolean isGameOver(){
		if(remainingLetters==0){
			return true;
		}
		return false;
	}
	
	public String getWord(){
		return currentWord.toUpperCase();
	}
	
	public ArrayList<String> getUsedLetters(){
		return usedLetters;
	}
	
	public Pane getBoardPane(){
		return pane;
	}
	
	public ArrayList<Text> getDisplayLetters(){
		return displayLetters;
	}
	
	public String[] getCommonLetters(){
		return orderedList;
	}
}
