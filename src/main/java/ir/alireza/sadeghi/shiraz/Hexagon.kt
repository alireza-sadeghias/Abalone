package ir.alireza.sadeghi.shiraz

import javafx.scene.paint.Color
import javafx.scene.shape.Polygon

/*
 * The hexagons; the board consists of these.
 * They can contain a marble.
 * Each contains a list with the codes of every adjacent hexagon.
 */
class Hexagon : Polygon {
    var centerX = 0.0
    var centerY = 0.0
    var empty = true
    private var neighbours = ArrayList<String>()
    private var code: String? = null
    var marble: Marble? = null

    internal constructor() {
        fill = Color.ANTIQUEWHITE
        strokeWidth = 3.0
        stroke = Color.BLACK
    }

    internal constructor(x: Double, y: Double, code: String?) {
        /*
            creates the polygon, defined by an array of (x,y) coordinates
    		which represents the six points of a hexagon
    		*/
        points.addAll(
            x, y,
            x, y + radius,
            x + n, y + radius * 1.5,
            x + hexagonWidth, y + radius,
            x + hexagonWidth, y,
            x + n, y - radius * 0.5
        )
        fill = Color.ANTIQUEWHITE
        strokeWidth = 3.0
        stroke = Color.BLACK
        this.code = code

        //center point for marbles
        centerX = x + 0.5 * hexagonWidth
        centerY = y + 0.5 * radius
    }

    //link a marble to the hexagon it is in:
    fun setFull(m: Marble?) {
        empty = false
        if (m != null) {
            marble = m
        }
    }

    //remove the marble from this hexagon
    fun setEmpty() {
        empty = true
        marble = null
    }

    //returns "true" if another hexagon is adjacent
    fun adjacent(other: String): Boolean {
        return neighbours.contains(other)
    }

    //creates a list of all neighbours (strings) of a hexagon
    fun createNeighbourList() {
        val letterCode = this.code!![0]
        val number = this.code!![1]
        val numberCode = Character.getNumericValue(number)
        var tempLetter: Char
        var tempNumber: Int
        var code: String?
        for (i in 0..2) {
            for (j in 0..2) {
                tempLetter = (letterCode.code - 1 + i).toChar()
                if (i == 0 && j <= 1) {
                    tempNumber = numberCode - 1 + j
                    code = tempLetter.toString() + tempNumber.toString()
                    if (Board.hashBoard.containsKey(code)) {
                        neighbours.add(code)
                    }
                }
                if (i == 1 && j != 1) {
                    tempNumber = numberCode - 1 + j
                    code = tempLetter.toString() + tempNumber.toString()
                    if (Board.hashBoard.containsKey(code)) {
                        neighbours.add(code)
                    }
                }
                if (i == 2 && j > 0) {
                    tempNumber = numberCode - 1 + j
                    code = tempLetter.toString() + tempNumber.toString()
                    if (Board.hashBoard.containsKey(code)) {
                        neighbours.add(code)
                    }
                }
            }
        }
    }

    //deep clone this hexagon, including the marble inside it - also the whole neighbours list (as it is the same for hexagons with the same code)
    fun deepClone(): Hexagon {
        val clone = Hexagon()
        for (i in points.indices) {
            clone.points.addAll(points[i])
        }
        clone.centerX = centerX
        clone.centerY = centerY
        clone.empty = empty
        clone.code = this.code
        if (!clone.empty) {
            clone.marble = marble!!.deepClone()
        }
        val neighbourCopy = ArrayList<String>()
        for (i in neighbours.indices) {
            neighbourCopy.add(neighbours[i])
        }
        clone.neighbours = neighbourCopy
        return clone
    }

    companion object {
        var radius // the inner radius from hexagon center to outer corner
                = 0.0
        var n // the inner radius from hexagon center to middle of the axis
                = 0.0
        var hexagonHeight = 0.0
        var hexagonWidth = 0.0
    }
}