package ir.alireza.sadeghi.shiraz

import java.util.*

/*
 * Stores a traceback, in hashtable form.
 * The traceback is an arraylist -> with index 1 being the board at the start of the game.
 * Also stores a separate traceback for every player - to make sure they won't do the same thing twice.
 */
class Traceback {
    private val traceBackSize = 30

    //add a hash board to the traceback - this one is for board states
    fun add(board: Hashtable<String?, Hexagon?>?) {
        if (current < traceBackSize - 1) {
            current++
        } else {
            current = 1
        }
        if (traceback.size < traceBackSize) {
            traceback.add(BoardMethods.copyHashBoard(board))
        } else {
            traceback[current] = BoardMethods.copyHashBoard(board)
        }
        totalMoves++
    }

    //add a move to a player's log
    fun addForPlayer(playerNumber: Int, first: String?, second: String?, third: String?, direction: Int) {
        when (playerNumber) {
            1 -> {
                oldMovePlayer1?.clear()
                oldMovePlayer1?.add(first as String)
                oldMovePlayer1?.add(second as String)
                oldMovePlayer1?.add(third as String)
                direct1 = direction
            }
            2 -> {
                oldMovePlayer2?.clear()
                oldMovePlayer2?.add(first as String)
                oldMovePlayer2?.add(second as String)
                oldMovePlayer2?.add(third as String)
                direct2 = direction
            }
            3 -> {
                oldMovePlayer3?.clear()
                oldMovePlayer3?.add(first as String)
                oldMovePlayer3?.add(second as String)
                oldMovePlayer3?.add(third as String)
                direct3 = direction
            }
        }
    }

    //return the whole arraylist
    val tB: ArrayList<Hashtable<String, Hexagon>>
        get() = traceback

    // reset the traceback fully
    fun reset() {
        traceback = ArrayList()
        oldMovePlayer1 = ArrayList()
        oldMovePlayer2 = ArrayList()
        oldMovePlayer3 = ArrayList()
        direct1 = 0
        direct2 = 0
        direct3 = 0
        current = 1
        totalMoves = 1
    }

    companion object {
        private var traceback = ArrayList<Hashtable<String, Hexagon>>()
        var oldMovePlayer1: ArrayList<String>? = null
        var oldMovePlayer2: ArrayList<String>? = null
        var oldMovePlayer3: ArrayList<String>? = null
        var direct1 = 0
        var direct2 = 0
        var direct3 = 0
        var current = 1
        var totalMoves = 1
    }
}