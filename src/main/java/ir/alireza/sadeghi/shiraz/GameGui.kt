package ir.alireza.sadeghi.shirazimport

import ir.alireza.sadeghi.shiraz.*
import ir.alireza.sadeghi.shiraz.ai.*
import ir.alireza.sadeghi.shiraz.ai.weight.optimisation.GeneticLoop
import javafx.application.Application
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import java.util.*

import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.AnchorPane

/*
 * Handles the game itself. So when the game is being played, this screen is showed.
 */
open class GameGui : Application() {
    var board: Board? = null
    override fun start(stage: Stage) {
        try {
            val buttonAI = Button("برای اجرای الگوریتم ژنتیک مرا دوبار فشار دهید")
            buttonAI.onAction = EventHandler<ActionEvent> { e: ActionEvent? ->

                //need to create this
                if (Move.initialBoard == null || Move.mcts && Move.initialBoard == null) {
                    logger.trace("enter")
                    Move.initialBoard = BoardMethods.copyHashBoard(Board.hashBoard)
                    Move.initial =
                        GameState(Move.initialBoard as Hashtable<String?, Hexagon?>?, GameMethods.changeBack(Move.playersTurn))
                    Move.monteCarlo = MonteCarlo(Node(Move.initial))
                } else if (Move.greedy || Move.alphabeta) {
                    EvaluationFunction.AITestingON = true
                    GeneticLoop.start()
                }
            }
            val winner_label = Label("برنده:\t")
            val MAX_FONT_SIZE = 30.0 // define max font size you need
            winner_label.font = Font(MAX_FONT_SIZE)
            winner_text.setFont(Font(MAX_FONT_SIZE))
            player_text.setFont(Font(MAX_FONT_SIZE))
            score_text1.setFont(Font(MAX_FONT_SIZE))
            score_text2.setFont(Font(MAX_FONT_SIZE))
            score_text3.setFont(Font(MAX_FONT_SIZE))
            val winner = HBox(winner_label, winner_text)
            val player_label = Label("نوبت بازیکن:\t")
            player_label.font = Font(MAX_FONT_SIZE)
            val playerBox = HBox(player_label, player_text)
            val score_label1 = Label("امتیاز بازیکن 1 :\t")
            score_label1.font = Font(MAX_FONT_SIZE)
            val tex = Text("/6")
            tex.font = Font(MAX_FONT_SIZE)
            val score = HBox(score_label1, score_text1, tex)
            val score_label2 = Label("امتیاز بازیکن 2 :\t")
            score_label2.font = Font(MAX_FONT_SIZE)
            val tex2 = Text("/6")
            tex2.font = Font(MAX_FONT_SIZE)
            val score2 = HBox(score_label2, score_text2, tex2)
            val score_label3 = Label("امتیاز بازیکن 3 :\t")
            score_label3.font = Font(MAX_FONT_SIZE)
            val tex3 = Text("/6")
            tex3.font = Font(MAX_FONT_SIZE)
            val score3 = HBox(score_label3, score_text3, tex3)
            val SubScene = GridPane()
            val hbox3 = HBox()
            val reset = Button("ریست")
            //hbox3.getChildren().add(reset);
            if (GameData.numberPlayers == 2) {
                GridPane.setRowIndex(hbox3, 5)
                GridPane.setRowIndex(winner, 4)
                GridPane.setRowIndex(playerBox, 1)
                GridPane.setRowIndex(score, 2)
                GridPane.setRowIndex(score2, 3)
                SubScene.children.addAll(hbox3, winner, playerBox, score, score2) //add reset for the reset button
            }
            if (GameData.numberPlayers == 3) {
                GridPane.setRowIndex(hbox3, 6)
                GridPane.setRowIndex(winner, 5)
                GridPane.setRowIndex(playerBox, 1)
                GridPane.setRowIndex(score, 2)
                GridPane.setRowIndex(score2, 3)
                GridPane.setRowIndex(score3, 4)
                SubScene.children.addAll(
                    hbox3,
                    winner,
                    playerBox,
                    score,
                    score2,
                    score3
                ) //add reset for the reset button
            }
            if (isAi) {
                if (GameData.numberPlayers == 2) {
                    val placeholder = Label()
                    GridPane.setRowIndex(placeholder, 6)
                    val placeholder2 = Label()
                    GridPane.setRowIndex(buttonAI, 7)
                    GridPane.setRowIndex(placeholder2, 8)
                    SubScene.children.addAll(placeholder, buttonAI, placeholder2)
                    val Label3 = Label("یا یک بازی دیگر با هوش مصنوعی!")
                    GridPane.setRowIndex(Label3, 9)
                    val Label4 = Label("بازیکن 1")
                    GridPane.setRowIndex(Label4, 10)
                    val Label5 = Label("بازیکن 2")
                    GridPane.setRowIndex(Label5, 12)
                    val choiceBox: ChoiceBox<String> = ChoiceBox()
                    choiceBox.items.add("Neutral")
                    choiceBox.items.add("Aggressive")
                    choiceBox.items.add("Defensive")
                    choiceBox.items.add("Final")
                    val choiceBox2: ChoiceBox<String> = ChoiceBox()
                    choiceBox2.items.add("Neutral")
                    choiceBox2.items.add("Aggressive")
                    choiceBox2.items.add("Defensive")
                    choiceBox2.items.add("Final")
                    GridPane.setRowIndex(choiceBox, 11)
                    GridPane.setRowIndex(choiceBox2, 13)
                    SubScene.children.addAll(Label3, Label4, choiceBox, Label5, choiceBox2)
                    val startAI = Button("شروع بازی هوش مصنوعی")
                    startAI.onAction = EventHandler {
                        if (choiceBox.value != null && choiceBox2.value != null) {
                            EvaluationFunction.AITestingON = false
                            EvaluationFunction.Name1 = choiceBox.value as String
                            EvaluationFunction.Name2 = choiceBox2.value as String
                            while (Move.point != 6 && Move.point2 != 6 && Move.point3 != 6) {
                                Move.checkAI(Board.hashBoard)
                            }
                            //AutomaticGamePlay.playGame(ir.alireza.sadeghi.shiraz.Board.hashBoard);
                        }
                    }
                    GridPane.setRowIndex(startAI, 14)
                    SubScene.children.add(startAI)
                } else {
                    val placeholder = Label()
                    GridPane.setRowIndex(placeholder, 6)
                    val placeholder2 = Label()
                    GridPane.setRowIndex(buttonAI, 7)
                    GridPane.setRowIndex(placeholder2, 8)
                    SubScene.children.addAll(placeholder, buttonAI, placeholder2)
                    val Label3 = Label("یا یک بازی دیگر.")
                    GridPane.setRowIndex(Label3, 9)
                    val Label4 = Label("بازیکن 1")
                    GridPane.setRowIndex(Label4, 10)
                    val Label5 = Label("بازیکن 2")
                    GridPane.setRowIndex(Label5, 12)
                    val Label6 = Label("بازیکن 3")
                    GridPane.setRowIndex(Label6, 14)
                    val choiceBox: ChoiceBox<String> = ChoiceBox()
                    choiceBox.items.add("Neutral")
                    choiceBox.items.add("Aggressive")
                    choiceBox.items.add("Defensive")
                    choiceBox.items.add("Final")
                    val choiceBox2: ChoiceBox<String> = ChoiceBox()
                    choiceBox2.items.add("Neutral")
                    choiceBox2.items.add("Aggressive")
                    choiceBox2.items.add("Defensive")
                    choiceBox2.items.add("Final")
                    val choiceBox3: ChoiceBox<String> = ChoiceBox()
                    choiceBox3.items.add("Neutral")
                    choiceBox3.items.add("Aggressive")
                    choiceBox3.items.add("Defensive")
                    choiceBox3.items.add("Final")
                    GridPane.setRowIndex(choiceBox, 11)
                    GridPane.setRowIndex(choiceBox2, 13)
                    GridPane.setRowIndex(choiceBox3, 15)
                    SubScene.children.addAll(Label3, Label4, choiceBox, Label5, choiceBox2, Label6, choiceBox3)
                    val startAI = Button("بازی با هوش مصنوعی")
                    startAI.onAction = EventHandler<ActionEvent> { e: ActionEvent? ->
                        if (choiceBox.value != null && choiceBox2.value != null && choiceBox3.value != null) {
                            EvaluationFunction.AITestingON = false
                            EvaluationFunction.Name1 = choiceBox.value as String
                            EvaluationFunction.Name2 = choiceBox2.value as String
                            EvaluationFunction.Name3 = choiceBox3.value as String
                            while (Move.point != 6 && Move.point2 != 6 && Move.point3 != 6) {
                                Move.checkAI(Board.hashBoard)
                            }
                            //AutomaticGamePlay.playGame(ir.alireza.sadeghi.shiraz.Board.hashBoard);
                        }
                    }
                    GridPane.setRowIndex(startAI, 16)
                    SubScene.children.add(startAI)
                }
            }
            player_text.setText("1")
            logger.trace(stage.width.toString() + " " + stage.height)
            board = Board(
                stage.width / 2,
                stage.height / 2
            )
            //ir.alireza.sadeghi.shiraz.MarbleStorage m = new ir.alireza.sadeghi.shiraz.MarbleStorage();
            Board.boardMarbles = MarbleStorage()
            val scene: Scene = newScene(Board.boardMarbles!!, board!!, SubScene)
            stage.title = "بازی ابالون"
            stage.scene = scene
            stage.show()
            reset.onAction = EventHandler<ActionEvent?> {
                stage.close()
                Platform.runLater {
                    try {
                        Main().start(Stage())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                stage.scene = newScene(Board.boardMarbles!!, board!!, SubScene)
            }
            scene.addEventFilter<KeyEvent>(KeyEvent.KEY_PRESSED) { e: KeyEvent ->
                if (e.code == KeyCode.DOWN) {
                    Move.checkAI(Board.hashBoard)
                    logger.trace("--did ai move and deleted tree--")
                }
            }
        } catch (e: Exception) {
            logger.error("Exception in GUI Creation" + e.message)
        }
    }

    fun setAIAI(x: Boolean) {
        isAi = x
    }

    companion object {
        private val logger = LogManager.getLogger(GameGui::class.java)
        lateinit var MainScene: BorderPane
        lateinit var Screen: Pane
        lateinit var pp: AnchorPane
        var winner_text = Text("0")
        var player_text = Text("0")
        var score_text1 = Text("0")
        var score_text2 = Text("0")
        var score_text3 = Text("0")
        var isAi = false
        protected fun newScene(m: MarbleStorage, board: Board, SubScene: GridPane?): Scene {
            Screen = Pane()
            MainScene = board.add()
            pp = m.balls()
            MainScene.children.addAll(pp)
            Screen.children.addAll(MainScene, SubScene)
            return Scene(Screen)
        }
    }
}