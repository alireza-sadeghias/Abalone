package ir.alireza.sadeghi.shiraz

import javafx.scene.layout.BorderPane
import java.util.*
import kotlin.math.sqrt

/*
 * A class to create the original board, stores information about it.
 * Methods to manipulate different boards are in ir.alireza.sadeghi.shiraz.BoardMethods (general, for all hashtables similar to this one).
 */
class Board     // the board is created starting with its center
    (//the board itself
    private val CenterX: Double, private val CenterY: Double
) : BorderPane() {
    //adding the marbles manually
    fun add(): BorderPane {
        val hexRowStart = 5
        val board = BorderPane()
        var hexRow = hexRowStart.toDouble()
        Hexagon.radius = CenterY / 10 // the inner radius from hexagon center to outer corner
        Hexagon.n =
            sqrt(Hexagon.radius * Hexagon.radius * 0.75) // the inner radius from hexagon center to middle of the axis
        Hexagon.hexagonHeight = 2 * Hexagon.radius
        Hexagon.hexagonWidth = 2 * Hexagon.n
        for (j in 0 until hexRowStart * 2 - 1) {
            val hexA = ('A'.code + (hexRowStart * 2 - 1 - j - 1)).toChar()
            val letterCode = hexA.toString()
            var i = 0
            while (i < hexRow) {
                var numberCode = i + 1
                if (j in 0..3) {
                    numberCode = numberCode + 4 - j
                }
                val neededForHash = numberCode.toString()
                var xCord: Double =
                    i * Hexagon.hexagonWidth + j % 2 * Hexagon.n + CenterX - Hexagon.hexagonWidth * (hexRowStart - 0.5)
                val yCord: Double =
                    j * Hexagon.hexagonHeight * 0.75 + CenterY - Hexagon.hexagonHeight * 2 - 3 * Hexagon.radius
                if (j == 0 || j == hexRowStart * 2 - 2) {
                    xCord += Hexagon.hexagonWidth * 2
                }
                if (j == 1 || j == 2 || j == hexRowStart * 2 - 4 || j == hexRowStart * 2 - 3) {
                    xCord += Hexagon.hexagonWidth
                }
                if (hexRowStart > 5) {
                    if (j in 5..hexRowStart) {
                        xCord -= Hexagon.hexagonWidth * ((j - 3) / 2)
                    }
                    if (j >= hexRowStart && j < hexRowStart * 2 - 6) {
                        xCord -= Hexagon.hexagonWidth * ((j - hexRowStart + 2) / 2)
                    }
                }
                val key = letterCode + neededForHash
                val hex = Hexagon(xCord, yCord, key)
                hashBoard[key] = hex
                hash.add(key)
                board.children.addAll(hex)
                i++
            }
            if (j < hexRowStart - 1) {
                hexRow++
            } else {
                hexRow--
            }
        }
        for (i in hash.indices) {
            hashBoard[hash[i]]!!.createNeighbourList()
        }
        GameData.rows = BoardRows()
        return board
    }

    companion object {
        var hashBoard = Hashtable<String?, Hexagon?>()
        var boardMarbles: MarbleStorage? = null
        var hash = ArrayList<String>()
    }
}