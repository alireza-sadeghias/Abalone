package ir.alireza.sadeghi.shiraz

import ir.alireza.sadeghi.shiraz.ai.*
import ir.alireza.sadeghi.shiraz.ai.weight.optimisation.GeneticLoop
import javafx.application.Application
import javafx.application.Platform
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
            buttonAI.onAction = EventHandler {

                //need to create this
                if (Move.initialBoard == null || Move.mcts && Move.initialBoard == null) {
                    logger.trace("enter")
                    Move.initialBoard = BoardMethods.copyHashBoard(Board.hashBoard)
                    Move.initial =
                        GameState(Move.initialBoard as Hashtable<String?, Hexagon?>?, GameMethods.changeBack(Move.playersTurn))
                    Move.monteCarlo = MonteCarlo(Node(Move.initial))
                } else if (Move.greedy || Move.alphaBeta) {
                    EvaluationFunction.AITestingON = true
                    GeneticLoop.start()
                }
            }
            val winnerLabel = Label("برنده:\t")
            val maxFontSize = 30.0 // define max font size you need
            winnerLabel.font = Font(maxFontSize)
            winnerText.font = Font(maxFontSize)
            player_text.font = Font(maxFontSize)
            score_text1.font = Font(maxFontSize)
            score_text2.font = Font(maxFontSize)
            score_text3.font = Font(maxFontSize)
            val winner = HBox(winnerLabel, winnerText)
            val playerLabel = Label("نوبت بازیکن:\t")
            playerLabel.font = Font(maxFontSize)
            val playerBox = HBox(playerLabel, player_text)
            val scoreLabel1 = Label("امتیاز بازیکن 1 :\t")
            scoreLabel1.font = Font(maxFontSize)
            val tex = Text("/6")
            tex.font = Font(maxFontSize)
            val score = HBox(scoreLabel1, score_text1, tex)
            val scoreLabel2 = Label("امتیاز بازیکن 2 :\t")
            scoreLabel2.font = Font(maxFontSize)
            val tex2 = Text("/6")
            tex2.font = Font(maxFontSize)
            val score2 = HBox(scoreLabel2, score_text2, tex2)
            val scoreLabel3 = Label("امتیاز بازیکن 3 :\t")
            scoreLabel3.font = Font(maxFontSize)
            val tex3 = Text("/6")
            tex3.font = Font(maxFontSize)
            val score3 = HBox(scoreLabel3, score_text3, tex3)
            val subScene = GridPane()
            val hBox3 = HBox()
            val reset = Button("ریست")
            if (GameData.numberPlayers == 2) {
                GridPane.setRowIndex(hBox3, 5)
                GridPane.setRowIndex(winner, 4)
                GridPane.setRowIndex(playerBox, 1)
                GridPane.setRowIndex(score, 2)
                GridPane.setRowIndex(score2, 3)
                subScene.children.addAll(hBox3, winner, playerBox, score, score2) //add reset for the reset button
            }
            if (GameData.numberPlayers == 3) {
                GridPane.setRowIndex(hBox3, 6)
                GridPane.setRowIndex(winner, 5)
                GridPane.setRowIndex(playerBox, 1)
                GridPane.setRowIndex(score, 2)
                GridPane.setRowIndex(score2, 3)
                GridPane.setRowIndex(score3, 4)
                subScene.children.addAll(
                    hBox3,
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
                    subScene.children.addAll(placeholder, buttonAI, placeholder2)
                    val label3 = Label("یا یک بازی دیگر با هوش مصنوعی!")
                    GridPane.setRowIndex(label3, 9)
                    val label4 = Label("بازیکن 1")
                    GridPane.setRowIndex(label4, 10)
                    val label5 = Label("بازیکن 2")
                    GridPane.setRowIndex(label5, 12)
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
                    subScene.children.addAll(label3, label4, choiceBox, label5, choiceBox2)
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
                    subScene.children.add(startAI)
                } else {
                    val placeholder = Label()
                    GridPane.setRowIndex(placeholder, 6)
                    val placeholder2 = Label()
                    GridPane.setRowIndex(buttonAI, 7)
                    GridPane.setRowIndex(placeholder2, 8)
                    subScene.children.addAll(placeholder, buttonAI, placeholder2)
                    val label3 = Label("یا یک بازی دیگر.")
                    GridPane.setRowIndex(label3, 9)
                    val label4 = Label("بازیکن 1")
                    GridPane.setRowIndex(label4, 10)
                    val label5 = Label("بازیکن 2")
                    GridPane.setRowIndex(label5, 12)
                    val label6 = Label("بازیکن 3")
                    GridPane.setRowIndex(label6, 14)
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
                    subScene.children.addAll(label3, label4, choiceBox, label5, choiceBox2, label6, choiceBox3)
                    val startAI = Button("بازی با هوش مصنوعی")
                    startAI.onAction = EventHandler {
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
                    subScene.children.add(startAI)
                }
            }
            player_text.text = "1"
            logger.trace(stage.width.toString() + " " + stage.height)
            board = Board(
                stage.width / 2,
                stage.height / 2
            )
            //ir.alireza.sadeghi.shiraz.MarbleStorage m = new ir.alireza.sadeghi.shiraz.MarbleStorage();
            Board.boardMarbles = MarbleStorage()
            val scene: Scene = newScene(Board.boardMarbles!!, board!!, subScene)
            stage.title = "بازی ابالون"
            stage.scene = scene
            stage.show()
            reset.onAction = EventHandler {
                stage.close()
                Platform.runLater {
                    try {
                        Main().start(Stage())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                stage.scene = newScene(Board.boardMarbles!!, board!!, subScene)
            }
            scene.addEventFilter(KeyEvent.KEY_PRESSED) { e: KeyEvent ->
                if (e.code == KeyCode.DOWN) {
                    Move.checkAI(Board.hashBoard)
                    logger.trace("--did ai move and deleted tree--")
                }
            }
        } catch (e: Exception) {
            logger.error("Exception in GUI Creation" + e.message)
        }
    }

    fun setAI(x: Boolean) {
        isAi = x
    }

    companion object {
        private val logger = LogManager.getLogger(GameGui::class.java)
        lateinit var MainScene: BorderPane
        lateinit var Screen: Pane
        lateinit var pp: AnchorPane
        var winnerText = Text("0")
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