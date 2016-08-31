//Yixiu Liu 110602460
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class WheelOfFortune extends Application{
	public static void main(String[]a){
		launch(a);
	}
	
	private Stage stage;
	private Pane layout = new Pane();
	private Scene scene = new Scene(layout);
	public static final int stageWidth = 1585, stageHeight = 785;
	private final int wheelRatioHeight = 400;
	private static int playerScore = 0;
	private static int AIScore = 0;
	public static boolean playerTurn = true;
	private static boolean stringInputUsed = false;
	private int moneyValue;
	private FortuneWheel wheel = new FortuneWheel(wheelRatioHeight);
	private GuessingBoard board = new GuessingBoard();
	private ScoreBoard scoreBoard = new ScoreBoard();
	private Button wheelButton = new Button("Spin Wheel");
	public static Button enterLetterButton = new Button("Enter a letter");
	public static Button enterStringButton = new Button("Enter a word");
	private Button startButton = new Button("Restart");
	private Button continueButton = new Button("Continue");
	private Button exitButton = new Button("Quit");
	private Text menuText = new Text("CLICK ANYWHERE TO BEGIN");
	private Text menuText2 = new Text();
	private Text AIStatus = new Text("............");
	private TextField letterInput = new TextField();
	private TextField stringInput = new TextField();
	private ImageView background = new ImageView(getClass().getResource("assets/bg4.png").toString());
	private ImageView startMenu = new ImageView(getClass().getResource("assets/bg2.png").toString());
	private MediaPlayer bgm = new MediaPlayer(new Media(getClass().getResource("assets/bgm.mp3").toString()));
	private AudioClip doorOpen = new AudioClip(getClass().getResource("assets/DoorOpen.wav").toString());
	private AudioClip doorClose = new AudioClip(getClass().getResource("assets/DoorClose.wav").toString());
	private AudioClip finalAnswer = new AudioClip(getClass().getResource("assets/redtruth.wav").toString());
	private DropShadow shadowEffect  = new DropShadow(20, Color.BLACK);
	private Text finalAnswerTxt = new Text("FINAL ANSWER!");
	private Text rightOrDieTxt = new Text("RIGHT OR  DIE!");

	
	public void start(Stage pstage){
		stage = pstage;
		
		//Components
		layout.getChildren().add(background);
		layout.getChildren().add(wheel.getWheelPane());
		layout.getChildren().add(board.getBoardPane());
		layout.getChildren().add(wheelButton);
		layout.getChildren().add(scoreBoard.getScorePane());
		layout.getChildren().add(enterLetterButton);
		layout.getChildren().add(enterStringButton);
		layout.getChildren().add(letterInput);
		layout.getChildren().add(stringInput);
		layout.getChildren().add(AIStatus);
		layout.getChildren().add(startMenu);
		layout.getChildren().add(menuText);
		layout.getChildren().add(menuText2);
		layout.getChildren().add(startButton);
		layout.getChildren().add(exitButton);
		layout.getChildren().add(continueButton);
		
		//Customization
		stage.setTitle("Wheel of Fortune");
		stage.setResizable(false);
		layout.setMaxSize(stageWidth, stageHeight);
		layout.setMinSize(stageWidth, stageHeight);
		enterLetterButton.setDisable(true);
		enterStringButton.setDisable(true);
		letterInput.setDisable(true);
		stringInput.setDisable(true);
		wheel.getWheelPane().setTranslateX(375);
		wheel.getWheelPane().setTranslateY(280);
		wheelButton.setTranslateX(850);
		wheelButton.setTranslateY(450);
		wheelButton.setTextFill(Color.WHITE);
		board.getBoardPane().setTranslateX(10);
		scoreBoard.getScorePane().setTranslateX(1000);
		scoreBoard.getScorePane().setTranslateY(700);
		enterStringButton.setTranslateX(900);
		enterStringButton.setTranslateY(500);
		enterStringButton.setTextFill(Color.WHITE);
		enterLetterButton.setTranslateX(800);
		enterLetterButton.setTranslateY(550);
		enterLetterButton.setTextFill(Color.WHITE);
		stringInput.translateXProperty().bind(enterStringButton.translateXProperty().add(110));
		stringInput.translateYProperty().bind(enterStringButton.translateYProperty());
		letterInput.translateXProperty().bind(enterLetterButton.translateXProperty().add(110));
		letterInput.translateYProperty().bind(enterLetterButton.translateYProperty());
		AIStatus.setFont(Font.font(44));
		AIStatus.setFill(Color.BLACK);
		AIStatus.setX(350);
		AIStatus.setY(150);
		menuText.setFont(Font.font(44));
		menuText.translateXProperty().bind(startMenu.translateXProperty().add(500));
		menuText.translateYProperty().bind(startMenu.translateYProperty().add(50));
		menuText2.setFont(Font.font(44));
		menuText2.translateXProperty().bind(startMenu.translateXProperty().add(330));
		menuText2.translateYProperty().bind(startMenu.translateYProperty().add(130));
		startButton.setOpacity(0);
		startButton.setDisable(true);
		startButton.setTextFill(Color.WHITE);
		startButton.translateXProperty().bind(startMenu.translateXProperty().add(650));
		startButton.translateYProperty().bind(startMenu.translateYProperty().add(170));
		exitButton.setOpacity(0);
		exitButton.setDisable(true);
		exitButton.setTextFill(Color.WHITE);
		exitButton.translateXProperty().bind(startMenu.translateXProperty().add(900));
		exitButton.translateYProperty().bind(startMenu.translateYProperty().add(170));
		continueButton.setOpacity(0);
		continueButton.setDisable(true);
		continueButton.setTextFill(Color.WHITE);
		continueButton.translateXProperty().bind(startMenu.translateXProperty().add(760));
		continueButton.translateYProperty().bind(startMenu.translateYProperty().add(170));
		bgm.setCycleCount(MediaPlayer.INDEFINITE);
	
		//Effects
		AIStatus.setEffect(shadowEffect);
		menuText.setEffect(shadowEffect);
		menuText2.setEffect(shadowEffect);
		stringInput.setEffect(shadowEffect);
		letterInput.setEffect(shadowEffect);
		enterStringButton.setEffect(shadowEffect);
		enterLetterButton.setEffect(shadowEffect);
		wheelButton.setEffect(shadowEffect);
		
		//Style
		layout.setStyle("-fx-font-style: italic; -fx-font-weight:bold;");
		wheelButton.setStyle("-fx-background-color: Gray; -fx-background-radius:50;");
		enterLetterButton.setStyle("-fx-background-color: Gray; -fx-background-radius:50;");
		enterStringButton.setStyle("-fx-background-color: Gray; -fx-background-radius:50;");
		startButton.setStyle("-fx-background-color: linear-gradient(LightGray, Black);-fx-background-radius:50;");
		exitButton.setStyle("-fx-background-color: linear-gradient(LightGray, Black);-fx-background-radius:50;");
		continueButton.setStyle("-fx-background-color: linear-gradient(LightGray, Black);-fx-background-radius:50;");
		stringInput.setStyle("-fx-background-radius:50;");
		letterInput.setStyle("-fx-background-radius:50;");
		
		//MISC
		layout.getChildren().add(9,finalAnswerTxt);
		finalAnswerTxt.setOpacity(0);
		finalAnswerTxt.setX(stageWidth/2);
		finalAnswerTxt.setY(stageHeight/2);
		finalAnswerTxt.setFont(Font.font(90));
		finalAnswerTxt.setFill(Color.RED);
		finalAnswerTxt.setStyle("-fx-font-style: italic;");
		finalAnswerTxt.setEffect(new Bloom(0.1));
		finalAnswerTxt.setEffect(shadowEffect);
		layout.getChildren().add(9,rightOrDieTxt);
		rightOrDieTxt.setOpacity(0);
		rightOrDieTxt.setX(stageWidth/2+30);
		rightOrDieTxt.setY(stageHeight/2);
		rightOrDieTxt.setFont(Font.font(90));
		rightOrDieTxt.setFill(Color.RED);
		rightOrDieTxt.setStyle("-fx-font-style: italic;");
		rightOrDieTxt.setEffect(new Bloom(0.1));
		rightOrDieTxt.setEffect(shadowEffect);
		
		//WHEEL BUTTON
		wheelButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				wheel.activate();
				wheelButton.setDisable(true);
				switch(wheel.getMoneyValue()){
				case 1:moneyValue = 0; playerScore = 0; switchTurn(); scoreBoard.setPlayerScore(0);break;
				case 2:moneyValue = 0; switchTurn(); break;
				case 3:moneyValue = 100;break;
				default: moneyValue = wheel.getMoneyValue(); 
				}
			}
		});
		
		//OPTION BUTTON
		enterLetterButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				enterStringButton.setDisable(true);
				enterLetterButton.setDisable(true);
				letterInput.setDisable(false);
			}
		});
		enterStringButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				//finalAnswerEffect().play();
				declareStringEffect().play();
				finalAnswer.play();
				enterStringButton.setDisable(true);
				enterLetterButton.setDisable(true);
				stringInput.setDisable(false);
			}
		});
		
		//TextFields
		letterInput.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				if(e.getCode().toString().equals("ENTER")){
					setScore(moneyValue*board.checkLetter(letterInput.getText()));
					letterInput.clear();
					letterInput.setDisable(true);
					scoreBoard.setPlayerScore(playerScore);
					checkGameOver();
					if(wheel.getMoneyValue() != 3){
						switchTurn();
					}else{
						wheelButton.setDisable(false);
					}
				}
			}
		});
		stringInput.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				if(e.getCode().toString().equals("ENTER")){
					setScore(moneyValue*board.checkString(stringInput.getText()));
					stringInput.clear();
					stringInput.setDisable(true);
					scoreBoard.setPlayerScore(playerScore);
					checkGameOver();
					if(wheel.getMoneyValue() != 3 &&!board.isGameOver()){
						switchTurn();
					}else{
						wheelButton.setDisable(false);
					}
					if(board.isGameOver())
						finalAnswerEffect().play();
					stringInputUsed = true;
				}
			}
		});
		
		//Menu
		startMenu.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				reset(false);
				moveMenu();
				bgm.play();
			}
		});
		
		//Menu Buttons
		startButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				reset(false);
				moveMenu();
			}
		});
		continueButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				reset(true);
				moveMenu();
			}
		});
		exitButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				System.exit(0);
			}
		});
		
		//Scene Key
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				if(e.getCode().toString().equals("ESCAPE"))
					System.exit(0);
			}
		});
				 
		
		Thread AIThread = new Thread(new AI());
		AIThread.start();
		stage.setScene(scene);
		stage.show();
	}
	
	private void checkGameOver(){
		if(board.isGameOver()){
			moveMenu();
		}
	}
	
	private void reset(boolean withContinue){
		board.generate();
		playerTurn = true;
		moneyValue = 0;
		if(!withContinue){
			playerScore = 0;
			AIScore = 0;
			scoreBoard.setPlayerScore(0);
			scoreBoard.setAIScore(0);
		}
		wheelButton.setDisable(false);
		enterLetterButton.setDisable(true);
		enterStringButton.setDisable(true);
		letterInput.setDisable(true);
		stringInput.setDisable(true);
		stringInputUsed = false;
	}

	private void setScore(int score){
		if(playerTurn){
			playerScore += score;
		}
		else{
			AIScore += score;
		}
	}
	
	private void moveMenu(){
		TranslateTransition t = new TranslateTransition();
		t.setDuration(Duration.seconds(4));
		t.setInterpolator(Interpolator.EASE_IN);
		t.setNode(startMenu);
		if(!board.isGameOver()){
			t.setFromY(0);
			t.setToY(stage.getHeight());
			doorOpen.play();
		}
		else{
			t.setFromY(stage.getHeight());
			t.setToY(0);
			menuText.setText("");
			if(playerScore>AIScore)
				menuText2.setText("YOU WIN... Player: "+playerScore+" AI: "+AIScore+" ...PLAY AGAIN?");
			else if(playerScore<AIScore)
				menuText2.setText("YOU LOSE... Player: "+playerScore+" AI: "+AIScore+" ...PLAY AGAIN?");
			else
				menuText2.setText("ROUND DRAW... Player: "+playerScore+" AI: "+AIScore+" ...PLAY AGAIN?");
			startButton.setOpacity(1);
			exitButton.setOpacity(1);
			continueButton.setOpacity(1);
			startButton.setDisable(false);
			exitButton.setDisable(false);
			continueButton.setDisable(false);
			doorClose.play();
		}
		t.play();
	}
	
	/*Desync
	private FillTransition backgroundEffect(){
		Rectangle r = new Rectangle(0, 0, stageWidth, stageHeight);
		r.setFill(Color.WHITE);
		FillTransition f = new FillTransition(Duration.seconds(2), r, Color.WHITE, Color.BLACK);
		f.setCycleCount(Animation.INDEFINITE);
		f.setAutoReverse(true);
		layout.getChildren().add(0, r);
		return f;
	}
	*/
	
	private FadeTransition finalAnswerEffect(){
		FadeTransition f = new FadeTransition();
		f.setDuration(Duration.seconds(1));
		f.setToValue(0);
		f.setFromValue(1);
		f.setAutoReverse(true);
		f.setNode(finalAnswerTxt);
		return f;
	}
	
	private FadeTransition declareStringEffect(){
		FadeTransition f = new FadeTransition();
		f.setDuration(Duration.seconds(1));
		f.setToValue(0);
		f.setFromValue(1);
		f.setAutoReverse(true);
		f.setNode(rightOrDieTxt);
		return f;
	}
	
	private void switchTurn(){
		playerTurn = !playerTurn;
	}
	
	
	class AI implements Runnable{
		private boolean run = true;
		
		private void makeInput(){
			String returnString = "A";
			if((stringInputUsed||board.getGameProgress()<0.25)/*&&(int)(Math.random()*2+1)==1*/){
				finalAnswerEffect().play();
				returnString = board.getWord();
				setScore(moneyValue*board.checkString(returnString));
			}
			else{
				for(String i: board.getCommonLetters()){
					if(!(board.getUsedLetters().contains(i))){
						returnString = i;
						break;
					}
				}
				setScore(moneyValue*board.checkLetter(returnString));
			}
			scoreBoard.setAIScore(AIScore);
			AIStatus.setText("AI declared " + returnString+"!"); //STILL DECLARE WHEN EVEN BACNKRUNPT OR SKIP
		}

		private void chatterings(){//not used -- high desync chance
			int option = (int)(Math.random()*2+1);
			switch(option){
			case 1: AIStatus.setText(">_>"); break;
			case 2: AIStatus.setText("miss...misss..."); break;
			}
		}
		
		private void AISleep(){
			try {Thread.sleep(wheel.getDuration()*1000+500);} catch (InterruptedException e) {e.printStackTrace();}
		}
		
		public void run(){
			while(run){
				
				if(!playerTurn&&!board.isGameOver()){//by the time player-turn is over, duration would be set
					if(wheel.getMoneyValue()==2||wheel.getMoneyValue()==1){
						//chatterings();
						AISleep();//what if player lose turn on the first turn?? AI need this sleep to avoid desync
					}
					AIStatus.setText("AI spins the wheel...");
					
					wheel.activate();
					switch(wheel.getMoneyValue()){
						case 1:moneyValue = 0; AIScore = 0; switchTurn(); break;
						case 2:moneyValue = 0; switchTurn(); break;
						case 3:moneyValue = 100; break;
						default: moneyValue = wheel.getMoneyValue(); 
					}
					AISleep();
					if(wheel.getMoneyValue()==1)
						scoreBoard.setAIScore(0);
					if(!playerTurn){
						makeInput();
						checkGameOver();
						if(wheel.getMoneyValue()!=3){
							playerTurn = true;
							wheelButton.setDisable(false);
						}
					}
					else{
						wheelButton.setDisable(false);
					}
				}
				else{
					//check the conditions every second
					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
					AIStatus.setText("............");
				}
			}
		}	
		
		
	}
}
	


