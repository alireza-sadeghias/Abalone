package ir.alireza.sadeghi.shiraz

import org.apache.logging.log4j.LogManager
import java.util.*

/*
 * Has methods which with you can:
 * -clone a hash board
 * -compare two boards
 * -get the marbles in one board from a certain player
 */
object BoardMethods {
    var hash: ArrayList<String> = Board.hash
    private val logger = LogManager.getLogger(BoardMethods::class.java)

    //copies a whole hash board - depends on the one you get as input
    fun copyHashBoard(hex: Hashtable<String?, Hexagon?>?): Hashtable<String, Hexagon> {
        logger.traceEntry()
        logger.trace("hash size " + hash.size)
        val newBoard: Hashtable<String, Hexagon> = Hashtable()
        var i = 0
        while (hash.size > i) {
            newBoard[hash[i]] = hex!![hash[i]]!!.deepClone()
            i++
        }
        return newBoard
    }

    //compares two hashTables, if they contain the same marbles (from the same player), at the same spot, then it returns true
    fun compareHashTables(one: Hashtable<String?, Hexagon?>?, two: Hashtable<String?, Hexagon?>?): Boolean {
        for (i in hash.indices) {
            if (!one!![hash[i]]!!.empty && !two!![hash[i]]!!.empty) {
                if (one[hash[i]]!!.marble?.playerNumber != two[hash[i]]!!.marble?.playerNumber) {
                    return false
                }
            } else if (one[hash[i]]!!.empty && !two!![hash[i]]!!.empty || !one[hash[i]]!!.empty && two!![hash[i]]!!.empty) {
                return false
            }
        }
        return true
    }

    // creates and returns an array list with all the marbles from a certain player
    //	public static ArrayList<String> getMarblesPlayer(Hashtable<String, Hexagon> board, int playerNumber){
    //		ArrayList<String> marbles = new ArrayList<>();
    //		for (int i = 0; i < hash.size(); i++) {
    //			if (!board.get(hash.get(i)).empty) {
    //				if (board.get(hash.get(i)).marble.playerNumber == playerNumber) {
    //					marbles.add(hash.get(i));
    //				}
    //			}
    //		}
    //		return marbles;
    //	}
    //repetition checker - return false if it is a repetition
    fun repetitionChecker(current: Hashtable<String?, Hexagon?>?): Boolean {
        val tb = GameData.tb.tB as ArrayList<Hashtable<String?, Hexagon?>>
        for (i in tb.indices) {
            if (compareHashTables(tb[i], current)) {
                return false
            }
        }
        return true
    }

    //repetition checker - return false if it is a repetition
    fun moveRepetitionChecker(
        first: String?,
        second: String?,
        third: String?,
        playerNumber: Int,
        direction: Int
    ): Boolean {
        var check: ArrayList<String>?
        var oldDirection = 0
        when (playerNumber) {
            1 -> {
                check = Traceback.oldMovePlayer1
                oldDirection = Traceback.direct1
            }
            2 -> {
                check = Traceback.oldMovePlayer2
                oldDirection = Traceback.direct2
            }
            else -> {
                check = Traceback.oldMovePlayer3
                oldDirection = Traceback.direct3
            }
        }
        if (first != null && second == null && third == null) {
            if (check?.size == 1) {
                if (check[0] === first && GameData.rows?.oppositeDirection(oldDirection) == direction) {
                    return false
                }
            }
        }
        if (first != null && second != null && third == null) {
            if (check?.size == 2) {
                if ((check[0] === second && check[1]!! === first || check[0] === first && check[1] === second) && direction == GameData.rows?.oppositeDirection(
                        oldDirection
                    )
                ) {
                    return false
                }
            }
        }
        if (first != null && second != null && third != null) {
            if (check?.size == 3) {
                if (check.contains(first) && check.contains(second) && check.contains(third) && direction == GameData.rows?.oppositeDirection(
                        oldDirection
                    )
                ) {
                    return false
                }
            }
        }
        return true
    }
}