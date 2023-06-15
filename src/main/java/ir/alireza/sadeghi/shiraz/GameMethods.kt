package ir.alireza.sadeghi.shirazimport

import ir.alireza.sadeghi.shiraz.*
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.DialogEvent
import javafx.scene.paint.Color
import org.apache.logging.log4j.LogManager
import java.util.*

import ir.alireza.sadeghi.shiraz.Move
import ir.alireza.sadeghi.shiraz.Hexagon
import ir.alireza.sadeghi.shiraz.GameData

/*
 * Some general methods needed to play the game or to make gamestates.
 * Includes:
 * -changing player
 * -changing the colours back to normal
 */
object GameMethods {
    private val logger = LogManager.getLogger(GameMethods::class.java)
    var alertON = true

    //changes a turn automatically
    fun changePlayer(playersTurn: Int): Int {
        var playersTurn = playersTurn
        if (playersTurn == 1) {
            playersTurn = 2
        } else {
            if (GameData.numberPlayers == 2 || GameData.numberPlayers == 3 && playersTurn == 3) {
                playersTurn = 1
            } else if (GameData.numberPlayers == 3 && playersTurn == 2) {
                if (playersTurn == 2) {
                    playersTurn = 3
                }
            }
        }
        return playersTurn
    }

    //changes the turn back
    fun changeBack(playerNr: Int): Int {
        return if (playerNr == 3) {
            2
        } else if (playerNr == 2) {
            1
        } else if (playerNr == 1 && GameData.numberPlayers == 3) {
            3
        } else {
            2
        }
    }

    fun coloursBackToNormal(board: Hashtable<String?, Hexagon>) {
        if (!Move.adding) {
            if (board[Move.first]!!.marble?.playerNumber == 1) {
                board[Move.first]!!.marble?.fill = Color.BLACK
                if (Move.second != null && !board[Move.second]!!.empty) {
                    board[Move.second]!!.marble?.fill = Color.BLACK
                }
                if (Move.third != null) {
                    board[Move.third]!!.marble?.fill = Color.BLACK
                }
            }
            if (board[Move.first]!!.marble?.playerNumber == 2) {
                board[Move.first]!!.marble?.fill = Color.GRAY
                if (Move.second != null && !board[Move.second]!!.empty) {
                    board[Move.second]!!.marble?.fill = Color.GRAY
                }
                if (Move.third != null && !board[Move.third]!!.empty) {
                    board[Move.third]!!.marble?.fill = Color.GRAY
                }
            }
            if (GameData.numberPlayers == 3) {
                if (board[Move.first]!!.marble?.playerNumber == 3) {
                    board[Move.first]!!.marble?.fill = Color.DARKGREEN
                    if (Move.second != null && !board[Move.second]!!.empty) {
                        board[Move.second]!!.marble?.fill = Color.DARKGREEN
                    }
                    if (Move.third != null && !board[Move.third]!!.empty) {
                        board[Move.third]!!.marble?.fill = Color.DARKGREEN
                    }
                }
            }
        }
    }

    //checking whether the game is finished and actually finishing it
    fun gameFinished() {
        GameGui.score_text1.setText(Move.point.toString())
        GameGui.score_text2.setText(Move.point2.toString())
        if (GameData.numberPlayers == 3) {
            GameGui.score_text3.setText(Move.point3.toString())
        }
        if (GameMethods.alertON && !Move.automaticGame) {
            val alert = Alert(AlertType.INFORMATION)
            alert.title = "بازی تمام شد!"
            alert.headerText = "برنده:"
            if (Move.point == 6) {
                val s = "بازیکن 1 برنده شد"
                alert.contentText = s
                alert.show()
                GameGui.winner_text.setText(s)
            }
            if (Move.point2 == 6) {
                val s = "بازیکن دو برنده شد!"
                alert.contentText = s
                alert.show()
                GameGui.winner_text.setText(s)
            }
            if (GameData.numberPlayers == 3) {
                if (Move.point3 == 6) {
                    val s = "بازیکن 3 برنده شد.!"
                    alert.contentText = s
                    alert.show()
                    GameGui.winner_text.setText(s)
                }
            }
            alert.onCloseRequest = EventHandler { event: DialogEvent? ->
                GameMethods.logger.trace("CLOSING")
                System.exit(0)
            }
        } else if (Move.automaticGame) {
            if (Move.point == 6) {
                Move.automaticGameEnd = true
                Move.winnerAutomaticGame = 1
            } else if (Move.point2 == 6) {
                Move.automaticGameEnd = true
                Move.winnerAutomaticGame = 2
            } else if (Move.point3 == 6) {
                Move.automaticGameEnd = true
                Move.winnerAutomaticGame = 3
            }
        }
    }
}