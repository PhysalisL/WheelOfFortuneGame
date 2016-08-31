//Yixiu Liu 110602460
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class FortuneWheel{
	private ImageView wheelImg;
	private ImageView arrow;
	private Line pointer;
	private Pane pane;
	private Circle[]slots;
	private RotateTransition path = new RotateTransition();
	private int angle;
	private int duration;
	private int moneyValue=0;
	private final int BANKRUPT = 1;
	private final int LOSETURN = 2;
	private final int MONEYANDFREETURN = 3;
	private AudioClip clickSound = new AudioClip(getClass().getResource("wheelTurn.wav").toString());

	public FortuneWheel(double wheelHeight){
		pane = new Pane();
		wheelImg = new ImageView(getClass().getResource("wheel.png").toString());
		arrow = new ImageView(getClass().getResource("arrow.png").toString());
		path = new RotateTransition();
		pointer = new Line(60, 335, 60, 335);
		slots = new Circle[24];
		createHitboxes();
		wheelImg.setPreserveRatio(true);
		wheelImg.setFitHeight(wheelHeight);
		wheelImg.setRotationAxis(Rotate.Z_AXIS);
		arrow.setPreserveRatio(true);
		arrow.setFitHeight(100);
		arrow.setRotationAxis(Rotate.Z_AXIS);
		arrow.setRotate(-45);
		arrow.setX(-50);
		arrow.setY(320);
		
		//pane.setEffect(new InnerShadow(20, Color.BLACK));
		pane.setEffect(new DropShadow(400, 20, 20,Color.BLACK));
		
		pane.getChildren().addAll(wheelImg, pointer, arrow);
	}

	public void activate(){
		angle = (int)(Math.random()*3001+2000);
		//angle = 360+360+280;  //test angle
		clickSound.play();
		animate(angle);
		spinLogic(angle);
	}
	
	private void animate(int angle){
		duration = angle/1000;
		path.setNode(wheelImg);
		path.setDuration(Duration.seconds(duration));
		path.setByAngle(angle);
		path.setInterpolator(Interpolator.EASE_BOTH);
		path.play();
		path.setOnFinished(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				//STATIC FROM WHEELOFFORTUNE CLASS PRESENT
				if(WheelOfFortune.playerTurn&&moneyValue!=BANKRUPT&&moneyValue!=LOSETURN){
					WheelOfFortune.enterLetterButton.setDisable(false);
					WheelOfFortune.enterStringButton.setDisable(false);
				}
			}
		});
	}
	
	private void spinLogic(int angle){
		for(int i=0; i<24; i++){
			slots[i].getTransforms().add(new Rotate(angle%360, 200, 200));
		}
		for(int i=0; i<24; i++){
			if(slots[i].getBoundsInParent().intersects(pointer.getBoundsInParent())){
				//slots[i].setFill(Color.WHITE);
				landedOn(i);
				break;
			}
		}
	}
	
	private void createHitboxes(){
		for(int i=0; i<24; i++){
			slots[i] = new Circle(200-200*Math.cos(i*Math.PI/12),200-200*Math.sin(i*Math.PI/12),26);
			slots[i].setFill(Color.TRANSPARENT);
			pane.getChildren().add(slots[i]);
		}
	}
	
	private void landedOn(int slotIndex){
		switch(slotIndex){
		case 0 : moneyValue = MONEYANDFREETURN; break;
		case 1 : moneyValue = 700; break;
		case 2 : moneyValue = BANKRUPT; break;
		case 3 : moneyValue = 100; break;
		case 4 : moneyValue = 300; break;
		case 5 : moneyValue = 200; break;
		case 6 : moneyValue = LOSETURN; break;
		case 7 : moneyValue = 100; break;
		case 8 : moneyValue = 500; break;
		case 9 : moneyValue = 400; break;
		case 10 : moneyValue = 300; break;
		case 11 : moneyValue = 200; break;
		case 12 : moneyValue = 600; break;
		case 13 : moneyValue = 100; break;
		case 14 : moneyValue = 200; break;
		case 15 : moneyValue = 300; break;
		case 16 : moneyValue = 400; break;
		case 17 : moneyValue = 500; break;
		case 18 : moneyValue = 300; break;
		case 19 : moneyValue = 200; break;
		case 20 : moneyValue = 100; break;
		case 21 : moneyValue = 400; break;
		case 22 : moneyValue = 300; break;
		case 23 : moneyValue = 400;
		}
	}

	public int getDuration(){
		return duration;
	}
	
	public int getMoneyValue(){//value only change if spinned again (it will not reset to 0) aka need wheel-key-wheel-key-wheel-key
		return moneyValue;
	}
	
	public Pane getWheelPane(){
		return pane;
	}
	
	
	
}
