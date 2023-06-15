package ir.alireza.sadeghi.shiraz

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import java.util.*


/*
 * Handles the marbles with which a game is played. 
 * Things such as color are automatically set.
 */
class Marble internal constructor(
    centerX: Double,
    centerY: Double,
    var playerNumber: Int, //handle location keys (that are private) with a set and get method
    var locationKey: String,
    newM: Boolean
) : Ellipse(centerX, centerY, Hexagon.radius * 0.70, Hexagon.radius * 0.70) {
    //can automatically put the marble in the middle of a hexagon with the same key
    fun updateLocation(board: Hashtable<String?, Hexagon?>) {
        val hex = board[locationKey]
        val tempX = hex!!.centerX
        val tempY = hex.centerY
        centerX = tempX
        centerY = tempY
    }

    //make it clickable, so you can play the game
    private var ellipseOnMouseClicked: EventHandler<MouseEvent> = EventHandler<MouseEvent> {
        if (Move.playersTurn == 1 && !Move.player1AI || Move.playersTurn == 2 && !Move.player2AI || Move.playersTurn == 3 && !Move.player3AI) {
            GameData.move.select(locationKey, Board.hashBoard as Hashtable<String?, Hexagon>)
        }
    }

    //creates a marble at a certain space, with a certain key
    init {
        this.onMouseClicked = ellipseOnMouseClicked
        if (playerNumber == 0) {
            fill = Color.ANTIQUEWHITE
        }
        if (playerNumber == 1) {
            fill = Color.BLACK
        }
        if (playerNumber == 2) {
            fill = Color.GRAY
        }
        if (playerNumber == 3) {
            fill = Color.DARKGREEN
        }
        if (newM) {
            MarbleStorage.pieceGroup.children.add(this)
        }
    }

    // make a copy of a marble, with a different reference
    fun deepClone(): Marble {
        return Marble(centerX, centerY, playerNumber, locationKey, false)
    }
}