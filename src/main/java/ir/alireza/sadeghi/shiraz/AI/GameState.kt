package ir.alireza.sadeghi.shiraz.ai

import ir.alireza.sadeghi.shiraz.*
import java.util.*

class GameState {
    //all the information needed to store the move made (from the first one)(we need to store first, second and third and moveTo in case the move needs to be done again):
    //use this to store every valid game state
    //store which move needs to be done to get to this state - such as which player's turn it is and which move they perform
    //if there is no second selected, it means that it is null
    var first: String? = null
    var second: String? = null
    var third: String? = null
    var moveTo: String? = null
    private val move = GameData.move
    var turn: Int
    var evaluatedValue = 0.0
    var evaluateFrom = 0
    var valid = false

    //needed for the evaluation function
    private var point1: Int
    private var point2: Int

    //optional:
    private var point3: Int
    var terminal = false
    var winner = 0
    var boardState: Hashtable<String?, Hexagon?>?
    var oldGameState: GameState? = null

    //rootNode
    constructor(state: Hashtable<String?, Hexagon?>?, turn: Int) {
        boardState = BoardMethods.copyHashBoard(state ) as Hashtable<String?, Hexagon?>?
        point1 = Move.point
        point2 = Move.point2
        point3 = Move.point3
        //last one who moved
        this.turn = turn
        evaluateFrom = Move.playersTurn //AddNodes.changePlayer(turn);
    }

    constructor(first: String?, second: String?, third: String?, moveTo: String?, old: GameState?) {
        //needed if we want a more extended tree
        turn = GameMethods.changePlayer(old!!.turn)
        val save: Int = Move.playersTurn
        evaluateFrom = old.evaluateFrom
        Move.playersTurn = turn
        Move.adding = true
        Move.ai = true
        this.first = first
        this.second = second
        this.third = third
        this.moveTo = moveTo


        //make a deep copy of the current board
        boardState = BoardMethods.copyHashBoard(old.boardState ) as Hashtable<String?, Hexagon?>?
        if (first != null) {
            move.select(first, boardState as Hashtable<String?, Hexagon>)
            if (second != null) {
                move.select(second, boardState as Hashtable<String?, Hexagon>)
                if (third != null) {
                    move.select(third, boardState as Hashtable<String?, Hexagon>)
                } else {
                    move.select(first, boardState as Hashtable<String?, Hexagon>)
                }
            } else {
                move.select(first, boardState as Hashtable<String?, Hexagon>)
            }
        }
        move.select(moveTo as String, boardState as Hashtable<String?, Hexagon>)
        Move.resetMove()
        valid = !BoardMethods.compareHashTables(boardState, old.boardState)

        //scores old
        point1 = old.point1
        point2 = old.point2
        point3 = old.point3
        if (Move.pushed) {
            when (turn) {
                1 -> {
                    point1++
                }
                2 -> {
                    point2++
                }
                3 -> {
                    point3++
                }
            }
        }
        if (valid) {
            valid = BoardMethods.repetitionChecker(boardState)
        }
        if (point1 == 6 || point2 == 6 || point3 == 6) {
            terminal = true
            if (point1 == 6) {
                winner = 1
            } else if (point2 == 6) {
                winner = 2
            } else if (point3 == 6) {
                winner = 3
            }
        } else {
            terminal = false
        }
        oldGameState = old

        //set the turn back to the one that was actually needed
        Move.pushed = false
        Move.playersTurn = save
        Move.adding = false
        Move.ai = false
        val eval = EvaluationFunction(this)
        evaluatedValue = eval.evaluate()
    }
}