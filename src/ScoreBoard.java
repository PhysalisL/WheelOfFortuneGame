//Yixiu Liu 110602460

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreBoard {
	private Text playerScore;
	private Text AIScore;
	private Pane pane;
	private Text playerLabel;
	private Text AILabel;
	private Rectangle padding;
	
	public ScoreBoard(){
		playerScore = new Text(0, 0, "_");
		playerLabel = new Text("Player Score: ");
		
		AIScore = new Text(0, 50, "_");
		AILabel = new Text("AI Score: ");

		playerScore.setFont(Font.font(50));
		playerScore.setFill(Color.WHITE);
		
		AIScore.setFont(Font.font(50));
		AIScore.setFill(Color.WHITE);
		
		playerLabel.translateXProperty().bind(playerScore.xProperty().subtract(300));
		playerLabel.translateYProperty().bind(playerScore.yProperty());
		playerLabel.fontProperty().bind(playerScore.fontProperty());
		playerLabel.fillProperty().bind(playerScore.fillProperty());
		
		AILabel.xProperty().bind(AIScore.xProperty().subtract(300));
		AILabel.yProperty().bind(AIScore.yProperty());
		AILabel.fontProperty().bind(AIScore.fontProperty());
		AILabel.fillProperty().bind(AIScore.fillProperty());
		
		padding = new Rectangle();
		padding.setArcHeight(20);
		padding.setArcWidth(20);
		padding.setHeight(120);
		padding.setWidth(500);
		padding.xProperty().bind(playerLabel.xProperty().subtract(320));
		padding.yProperty().bind(playerLabel.yProperty().subtract(50));
		padding.setFill(Color.BLACK);
		
		//addEffects();
		pane = new Pane();
		pane.getChildren().addAll( playerScore, playerLabel, AIScore, AILabel);
		pane.setEffect(new DropShadow(10, Color.BLACK));
	}
	
	public void setPlayerScore(int score){
		playerScore.setText(Integer.toString(score));
	}
	
	public void setAIScore(int score){
		AIScore.setText(Integer.toString(score));
	}
	
	public Pane getScorePane(){
		return pane;
	}
	
}
