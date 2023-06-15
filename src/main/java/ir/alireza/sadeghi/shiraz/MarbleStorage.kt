package ir.alireza.sadeghi.shiraz

import javafx.scene.layout.AnchorPane
import java.util.LinkedList

/*
 * The marbles are stored here.
 * It's a LinkedList, when a marble is removed here, it disappears from the board. (When it is redrawn)
 */
class MarbleStorage {
    //place the marbles manually
    fun balls(): AnchorPane {
        //firstly, the clickable layer beneath it
        var ch = 'A'
        while (ch <= 'I') {
            val letterCode = Character.toString(ch)
            for (j in 0..9) {
                if (Board.hashBoard[letterCode + j] != null) {
                    val p = Marble(
                        Board.hashBoard[letterCode + j]?.centerX as Double,
                        Board.hashBoard[letterCode + j]?.centerY as Double, 0, letterCode + j, true
                    )
                    storage.add(p)
                }
            }
            ch++
        }

        //if number of players = 2, then add marbles this way
        if (GameData.numberPlayers == 2) {
            for (i in 0..9) {
                if (Board.hashBoard["A$i"] != null) {
                    val p = Marble(
                        Board.hashBoard["A$i"]?.centerX as Double,
                        Board.hashBoard["A$i"]?.centerY as Double,
                        1,
                        "A$i",
                        true
                    )
                    Board.hashBoard["A$i"]?.setFull(p)
                    storage.add(p)
                }
                if (Board.hashBoard["B$i"] != null) {
                    val p = Marble(
                        Board.hashBoard[("B$i")]?.centerX as Double,
                        Board.hashBoard["B$i"]?.centerY as Double,
                        1,
                        "B$i",
                        true
                    )
                    Board.hashBoard["B$i"]?.setFull(p)
                    storage.add(p)
                }
                if (Board.hashBoard["C$i"] != null) {
                    if (i in 3..5) {
                        val p = Marble(
                            Board.hashBoard["C$i"]?.centerX as Double,
                            Board.hashBoard["C$i"]?.centerY as Double,
                            1,
                            "C$i",
                            true
                        )
                        Board.hashBoard["C$i"]?.setFull(p)
                        storage.add(p)
                    }
                }
                if (Board.hashBoard["I$i"] != null) {
                    val p = Marble(
                        Board.hashBoard["I$i"]?.centerX as Double,
                        Board.hashBoard["I$i"]?.centerY as Double,
                        2,
                        "I$i",
                        true
                    )
                    Board.hashBoard["I$i"]?.setFull(p)
                    storage.add(p)
                }
                if (Board.hashBoard["H$i"] != null) {
                    val p = Marble(
                        Board.hashBoard["H$i"]?.centerX as Double,
                        Board.hashBoard["H$i"]?.centerY as Double,
                        2,
                        "H$i",
                        true
                    )
                    Board.hashBoard["H$i"]?.setFull(p)
                    storage.add(p)
                }
                if (Board.hashBoard["G$i"] != null) {
                    if (i in 5..7) {
                        val p = Marble(
                            Board.hashBoard["G$i"]?.centerX as Double,
                            Board.hashBoard["G$i"]?.centerY as Double,
                            2,
                            "G$i",
                            true
                        )
                        Board.hashBoard["G$i"]?.setFull(p)
                        storage.add(p)
                    }
                }
            }
        } else {
            //player 1
            for (i in 0..9) {
                if (Board.hashBoard["A$i"] != null) {
                    val p = Marble(
                        Board.hashBoard["A$i"]?.centerX as Double,
                        Board.hashBoard["A$i"]?.centerY as Double,
                        1,
                        "A$i",
                        true
                    )
                    Board.hashBoard["A$i"]?.setFull(p)
                    storage.add(p)
                }
                if (Board.hashBoard["B$i"] != null) {
                    val p = Marble(
                        Board.hashBoard["B$i"]?.centerX as Double,
                        Board.hashBoard["B$i"]?.centerY as Double,
                        1,
                        "B$i",
                        true
                    )
                    Board.hashBoard["B$i"]?.setFull(p)
                    storage.add(p)
                }
            }
            //player 2
            var start = 'I'
            for (i in 6 downTo 5) {
                for (j in 0..8) {
                    val letterCode = start.toString()
                    val temp = i - j
                    if (Board.hashBoard.containsKey(letterCode + temp)) {
                        val p = Marble(
                            Board.hashBoard[letterCode + temp]!!.centerX,
                            Board.hashBoard[letterCode + temp]!!.centerY,
                            2,
                            letterCode + temp,
                            true
                        )
                        Board.hashBoard.get(letterCode + temp)!!.setFull(p)
                        storage.add(p)
                    }
                    start = (start.code - 1).toChar()
                }
                start = 'I'
            }
            //player 3
            for (j in 8..9) {
                var ch = 'A'
                while (ch <= 'I') {
                    val letterCode = ch.toString()
                    if (Board.hashBoard.containsKey(letterCode + j)) {
                        val p = Marble(
                            Board.hashBoard[letterCode + j]!!.centerX,
                            Board.hashBoard[letterCode + j]!!.centerY,
                            3,
                            letterCode + j,
                            true
                        )
                        Board.hashBoard[letterCode + j]!!.setFull(p)
                        storage.add(p)
                    }
                    ch++
                }
            }
        }
        return pieceGroup
    }

    //deep clone the whole marble storage
//    fun clone(): MarbleStorage {
//        val marbles = MarbleStorage()
//        storage = LinkedList<Marble>()
//        for (i in storage.indices) {
//            storage.add(storage[i].deepClone())
//        }
//        return marbles
//    }

    companion object {
        var pieceGroup = AnchorPane()
        var storage = LinkedList<Marble>()
    }
}