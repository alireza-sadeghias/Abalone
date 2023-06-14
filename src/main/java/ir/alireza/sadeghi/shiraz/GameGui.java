package ir.alireza.sadeghi.shiraz;

import ir.alireza.sadeghi.shiraz.ai.EvaluationFunction;
import ir.alireza.sadeghi.shiraz.ai.GameState;
import ir.alireza.sadeghi.shiraz.ai.MonteCarlo;
import ir.alireza.sadeghi.shiraz.ai.Node;
import ir.alireza.sadeghi.shiraz.ai.weight.optimisation.GeneticLoop;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Handles the game itself. So when the game is being played, this screen is showed.
 */

public class GameGui extends Application{
	private final static Logger logger = LogManager.getLogger(GameGui.class);

	public Board board;
	static BorderPane MainScene;
	static Pane Screen;
	static AnchorPane pp;
	static Text winner_text = new Text("0");
    static Text player_text = new Text("0");
    static Text score_text1 = new Text("0");
    static Text score_text2 = new Text("0");
    static Text score_text3 = new Text("0");
    static boolean AIAI = false;

	public void start( Stage stage) {
		try {
			Button buttonAI = new Button("برای اجرای الگوریتم ژنتیک مرا دوبار فشار دهید");

			buttonAI.setOnAction(e -> {
				
				//need to create this
				if ((Move.initialBoard == null ) || Move.mcts && Move.initialBoard == null) {
					logger.trace("enter");
					Move.initialBoard = BoardMethods.copyHashBoard(Board.hashBoard);
					Move.initial = new GameState(Move.initialBoard, GameMethods.changeBack(Move.playersTurn));
					Move.monteCarlo = new MonteCarlo(new Node<GameState>(Move.initial));
				}
				else if((Move.greedy || Move.alphabeta) ) {
					EvaluationFunction.AITestingON = true;
					GeneticLoop.start();
				}
			});

		    Label winner_label = new Label("برنده:\t");
		     double MAX_FONT_SIZE = 30.0; // define max font size you need
		     winner_label.setFont(new Font(MAX_FONT_SIZE));		
		     winner_text.setFont(new Font(MAX_FONT_SIZE));		
		     player_text.setFont(new Font(MAX_FONT_SIZE));		
		     score_text1.setFont(new Font(MAX_FONT_SIZE));	
		     score_text2.setFont(new Font(MAX_FONT_SIZE));		
		     score_text3.setFont(new Font(MAX_FONT_SIZE));

		     HBox winner = new HBox(winner_label, winner_text);
			Label player_label = new Label("نوبت بازیکن:\t");
			player_label.setFont(new Font(MAX_FONT_SIZE));		

		    HBox playerBox = new HBox(player_label, player_text);
			Label score_label1 = new Label("امتیاز بازیکن 1 :\t");
			score_label1.setFont(new Font(MAX_FONT_SIZE));		
			Text tex = new Text("/6");
			tex.setFont(new Font(MAX_FONT_SIZE));		
		    HBox score = new HBox(score_label1, score_text1, tex);
		    
		    Label score_label2 = new Label("امتیاز بازیکن 2 :\t");
		    score_label2.setFont(new Font(MAX_FONT_SIZE));		
		    Text tex2 = new Text("/6");
			tex2.setFont(new Font(MAX_FONT_SIZE));	
			HBox score2 = new HBox(score_label2, score_text2, tex2);
			
			Label score_label3 = new Label("امتیاز بازیکن 3 :\t");
		    score_label3.setFont(new Font(MAX_FONT_SIZE));		
		    Text tex3 = new Text("/6");
			tex3.setFont(new Font(MAX_FONT_SIZE));	
			HBox score3 = new HBox(score_label3, score_text3, tex3);
			
			GridPane SubScene = new GridPane();
			HBox hbox3 = new HBox();
			Button reset = new Button("ریست");
			//hbox3.getChildren().add(reset);
			if (GameData.numberPlayers ==2) {
				GridPane.setRowIndex(hbox3, 5);
				GridPane.setRowIndex(winner, 4);
				GridPane.setRowIndex(playerBox, 1);
				GridPane.setRowIndex(score,2 );
				GridPane.setRowIndex(score2,3 );
				SubScene.getChildren().addAll(hbox3,winner,playerBox,score,score2); //add reset for the reset button
			}
			
			if (GameData.numberPlayers ==3) {
				GridPane.setRowIndex(hbox3, 6);
				GridPane.setRowIndex(winner, 5);
				GridPane.setRowIndex(playerBox, 1);
				GridPane.setRowIndex(score,2 );
				GridPane.setRowIndex(score2,3 );
				GridPane.setRowIndex(score3, 4);
				SubScene.getChildren().addAll(hbox3,winner,playerBox,score,score2, score3); //add reset for the reset button
			}

			if(AIAI) {
				if (GameData.numberPlayers ==2) {
					Label placeholder = new Label();
					GridPane.setRowIndex(placeholder, 6);
					Label placeholder2 = new Label();
					GridPane.setRowIndex(buttonAI, 7);
					GridPane.setRowIndex(placeholder2, 8);
					SubScene.getChildren().addAll(placeholder,buttonAI, placeholder2);
					Label Label3 = new Label("یا یک بازی دیگر با هوش مصنوعی!");
					GridPane.setRowIndex(Label3, 9);
					Label Label4 = new Label("بازیکن 1");
					GridPane.setRowIndex(Label4, 10);
					Label Label5 = new Label("بازیکن 2");
					GridPane.setRowIndex(Label5, 12);
	
					ChoiceBox choiceBox = new ChoiceBox();
	
					choiceBox.getItems().add("Neutral");
					choiceBox.getItems().add("Aggressive");
					choiceBox.getItems().add("Defensive");
					choiceBox.getItems().add("Final");
	
					ChoiceBox choiceBox2 = new ChoiceBox();
	
					choiceBox2.getItems().add("Neutral");
					choiceBox2.getItems().add("Aggressive");
					choiceBox2.getItems().add("Defensive");
					choiceBox2.getItems().add("Final");
					
	
					GridPane.setRowIndex(choiceBox, 11);
					GridPane.setRowIndex(choiceBox2, 13);
					SubScene.getChildren().addAll(Label3,Label4,choiceBox,Label5,choiceBox2);
					
					
					Button startAI = new Button("شروع بازی هوش مصنوعی");
					startAI.setOnAction(e -> {
						if(choiceBox.getValue() != null && choiceBox2.getValue()!= null) {
							EvaluationFunction.AITestingON = false;
							EvaluationFunction.Name1 = (String) choiceBox.getValue();
							EvaluationFunction.Name2 = (String) choiceBox2.getValue();
							while(Move.point != 6 && Move.point2 != 6 && Move.point3!= 6) {
								Move.checkAI(Board.hashBoard);
							}
							//AutomaticGamePlay.playGame(ir.alireza.sadeghi.shiraz.Board.hashBoard);
						}
					});
					GridPane.setRowIndex(startAI, 14);
					SubScene.getChildren().add(startAI);
				}
				else {
					Label placeholder = new Label();
					GridPane.setRowIndex(placeholder, 6);
					Label placeholder2 = new Label();
					GridPane.setRowIndex(buttonAI, 7);
					GridPane.setRowIndex(placeholder2, 8);
					SubScene.getChildren().addAll(placeholder,buttonAI, placeholder2);
					
					Label Label3 = new Label("یا یک بازی دیگر.");
					GridPane.setRowIndex(Label3, 9);
					Label Label4 = new Label("بازیکن 1");
					GridPane.setRowIndex(Label4, 10);
					Label Label5 = new Label("بازیکن 2");
					GridPane.setRowIndex(Label5, 12);
					Label Label6 = new Label("بازیکن 3");
					GridPane.setRowIndex(Label6, 14);
							
	
					ChoiceBox choiceBox = new ChoiceBox();
	
					choiceBox.getItems().add("Neutral");
					choiceBox.getItems().add("Aggressive");
					choiceBox.getItems().add("Defensive");
					choiceBox.getItems().add("Final");
	
					ChoiceBox choiceBox2 = new ChoiceBox();
	
					choiceBox2.getItems().add("Neutral");
					choiceBox2.getItems().add("Aggressive");
					choiceBox2.getItems().add("Defensive");
					choiceBox2.getItems().add("Final");
					
					ChoiceBox choiceBox3 = new ChoiceBox();
					
					choiceBox3.getItems().add("Neutral");
					choiceBox3.getItems().add("Aggressive");
					choiceBox3.getItems().add("Defensive");
					choiceBox3.getItems().add("Final");
					
	
					GridPane.setRowIndex(choiceBox, 11);
					GridPane.setRowIndex(choiceBox2, 13);
					GridPane.setRowIndex(choiceBox3, 15);
					SubScene.getChildren().addAll(Label3,Label4,choiceBox,Label5,choiceBox2, Label6, choiceBox3);
					
					
					Button startAI = new Button("بازی با هوش مصنوعی");
					startAI.setOnAction(e -> {
						if(choiceBox.getValue() != null && choiceBox2.getValue()!= null && choiceBox3.getValue() != null) {
							EvaluationFunction.AITestingON = false;
							EvaluationFunction.Name1 = (String) choiceBox.getValue();
							EvaluationFunction.Name2 = (String) choiceBox2.getValue();
							EvaluationFunction.Name3 = (String) choiceBox3.getValue();
							while(Move.point != 6 && Move.point2 != 6 && Move.point3!= 6) {
								Move.checkAI(Board.hashBoard);
							}
							//AutomaticGamePlay.playGame(ir.alireza.sadeghi.shiraz.Board.hashBoard);
						}
					});
					GridPane.setRowIndex(startAI, 16);
					SubScene.getChildren().add(startAI);
				}
			}
			player_text.setText("1");
			
			logger.trace(stage.getWidth()+" "+stage.getHeight());
			board = new Board(stage.getWidth() / 2,
					stage.getHeight() / 2);
			//ir.alireza.sadeghi.shiraz.MarbleStorage m = new ir.alireza.sadeghi.shiraz.MarbleStorage();
			Board.boardMarbles = new MarbleStorage();
			Scene scene = newScene(Board.boardMarbles, board,SubScene);
			stage.setTitle("بازی ابالون");

			stage.setScene(scene);
			stage.show();
			
			reset.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent actionEvent) {
	            	stage.close();

	                Platform.runLater( () -> {
						try {
							new Main().start( new Stage() );
						} catch (Exception e) {
							e.printStackTrace();
						}
					} );
	    		    stage.setScene(newScene(Board.boardMarbles, board,SubScene));

	            }
	        });

			scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
				if(e.getCode() == KeyCode.DOWN) {
					Move.checkAI(Board.hashBoard);
					logger.trace("--did ai move and deleted tree--");
				}
			});

		} catch (Exception e) {
			logger.error("Exception in GUI Creation" + e.getMessage());
		}

	}
	
	protected static Scene newScene(MarbleStorage m, Board board,GridPane SubScene) {
		Screen = new Pane();
		MainScene = board.add();
		pp = m.Balls();
		MainScene.getChildren().addAll(pp);
		Screen.getChildren().addAll(MainScene,SubScene);
		Scene scene = new Scene(Screen);
		return scene;
	}

	public void setAIAI(boolean x){
		AIAI = x;
	}
}