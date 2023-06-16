package ir.alireza.sadeghi.shiraz

import ir.alireza.sadeghi.shiraz.ai.GameState
import ir.alireza.sadeghi.shiraz.ai.MonteCarlo
import ir.alireza.sadeghi.shiraz.ai.PerformAIAction
import javafx.scene.paint.Color
import java.util.*


/*
 * Whenever a move on the board or in a game state needs to be performed, this class's methods are used.
 */
class Move {
    init {
        first = null
        second = null
        third = null
        moveTo = null
    }

    //select marbles
    fun select(code: String, board: Hashtable<String?, Hexagon>) {

        //check whether a marble from the player is selected, then set that as a code and add it to the selection:
        if (first == null && !board[code]!!.empty) {
            if (board[code]!!.marble?.playerNumber == playersTurn) {
                board[code]!!.marble?.fill = Color.PURPLE
                first = code
                nrSelected++
                selectedMarbles.add(code)
            }
        } else if (second == null && !board[code]!!.empty && !selected) {
            if (board[code]!!.marble?.playerNumber == playersTurn) {
                if (first == code) {
                    selected = true
                    board[code]!!.marble?.fill = Color.ORANGE
                } else if (!board[code]!!.adjacent(first as String)) {
                    selectedMarbles.clear()
                    GameMethods.coloursBackToNormal(board)
                    first = code
                    board[code]!!.marble?.fill = Color.PURPLE
                    selectedMarbles.add(code)
                } else {
                    second = code
                    board[code]!!.marble?.fill = Color.AQUAMARINE
                    nrSelected++
                    selectedMarbles.add(code)
                }
            }
        } else if (third == null && !selected && !board[code]!!.empty) {
            if (board[code]!!.marble?.playerNumber as Int == playersTurn) {
                if (first == code || second == code) {
                    board[first]!!.marble?.fill = Color.ORANGE
                    board[second]!!.marble?.fill = Color.YELLOW
                    selected = true
                } else if (!board[code]!!.adjacent(second as String) || !GameData.rows.sameRowThree(
                        first as String,
                        second as String,
                        code
                    )
                ) {
                    selectedMarbles.clear()
                    GameMethods.coloursBackToNormal(board)
                    first = code
                    board[first]!!.marble?.fill = Color.PURPLE
                    second = null
                    nrSelected = 1
                    selectedMarbles.add(code)
                } else {
                    third = code
                    nrSelected++
                    selectedMarbles.add(code)
                    selected = true
                    board[first]!!.marble?.fill = Color.ORANGE
                    board[second]!!.marble?.fill = Color.YELLOW
                    board[third]!!.marble?.fill = Color.YELLOW
                }
            }
        } else {
            if (selected && board[code]!!.adjacent(first as String)) {
                if (!board[code]!!.empty) {
                    if (board[code]!!.marble?.playerNumber == playersTurn) {
                        selectedMarbles.clear()
                        GameMethods.coloursBackToNormal(board)
                        selected = false
                        first = code
                        board[first]!!.marble?.fill = Color.PURPLE
                        second = null
                        third = null
                        nrSelected = 1
                        selectedMarbles.add(code)
                    } else {
                        moveTo = code
                    }
                } else {
                    moveTo = code
                }
            } else if (!board[code]!!.empty && board[code]!!.marble?.playerNumber == playersTurn) {
                selectedMarbles.clear()
                GameMethods.coloursBackToNormal(board)
                selected = false
                first = code
                board[first]!!.marble?.fill = Color.PURPLE
                second = null
                third = null
                nrSelected = 1
                selectedMarbles.add(code)
            }
        }
        if (moveTo != null && Board.hash.contains(moveTo)) {
            GameMethods.coloursBackToNormal(board)
            move(board)
        }
        if (board == Board.hashBoard) {
            GameGui.player_text.text = playersTurn.toString()
        }
    }

    fun move(board: Hashtable<String?, Hexagon>) {
        //check if valid -> if not, reset, else: perform movement, change player, resetMove
        if (nrSelected == 1) {
            if (validMoveOne(board, first, moveTo)) {
                addToTb(board)
                performMovementOne(board)
                moveForAll(board)
                addMoveToTb(board)
                resetMove()
            }
        } else if (nrSelected == 2) {
            if (validMoveTwo(
                    board,
                    first,
                    second,
                    moveTo
                )
            ) {
                addToTb(board)
                performMovementTwo(board)
                moveForAll(board)
                addMoveToTb(board)
                resetMove()
            }
        } else if (nrSelected == 3) {
            if (validMoveThree(
                    board,
                    first,
                    second,
                    third,
                    moveTo
                )
            ) {
                addToTb(board)
                performMovementThree(board)
                moveForAll(board)
                addMoveToTb(board)
                resetMove()
            }
        }
    }

    //add both the move and the hashtable to the traceback
    private fun addToTb(board: Hashtable<String?, Hexagon>) {
        if (!adding && board == Board.hashBoard || automaticGame && !ai) {
            GameData.tb.add(board as Hashtable<String?, Hexagon?>?)
            if (first != null) {
                one = Board.hashBoard[first]!!.marble
            }
            if (second != null) {
                two = Board.hashBoard[second]!!.marble
            }
            if (third != null) {
                three = Board.hashBoard[third]!!.marble
            }
        }
    }

    private fun addMoveToTb(board: Hashtable<String?, Hexagon>) {
        if (!adding && board == Board.hashBoard) {
            var tbOne: String? = null
            var tbTwo: String? = null
            var tbThree: String? = null
            if (one != null) {
                tbOne = one!!.locationKey
            }
            if (two != null) {
                tbTwo = two!!.locationKey
            }
            if (three != null) {
                tbThree = three!!.locationKey
            }
            GameData.tb.addForPlayer(
                GameMethods.changeBack(playersTurn),
                tbOne ,
                tbTwo ,
                tbThree ,
                GameData.rows.direction(first, moveTo)
            )
            one = null
            two = null
            three = null
        }
    }

    private fun moveForAll(board: Hashtable<String?, Hexagon>) {
        if (board == Board.hashBoard && !adding || automaticGame && !ai) {
            GameMethods.gameFinished()
            playersTurn = GameMethods.changePlayer(playersTurn)
            pushed = false
            if (!player1AI && (greedy || GameData.numberPlayers == 3) && !automaticGame && PvC) {
                checkAI(board as Hashtable<String?, Hexagon?>?)
            }
            if (!automaticGame && !ai && mcts) {
                monteCarlo!!.changeRootOutside(board as Hashtable<String?, Hexagon?>?)
            }
        }
    }

    //move a marble from one place (see code) to the other
    private fun moveMarble(from: String?, to: String?, board: Hashtable<String?, Hexagon>) {
        val moving = board[from]!!.marble
        board[from]!!.setEmpty()
        board[to]!!.setFull(moving)
        moving!!.locationKey = to as String
        moving.updateLocation(board as Hashtable<String?, Hexagon?>)
    }

    //moves one single marble
    private fun performMovementOne(board: Hashtable<String?, Hexagon>) {
        moveMarble(first, moveTo, board)
    }

    //moves two marbles, either sideways or in the same direction
    private fun performMovementTwo(board: Hashtable<String?, Hexagon>) {
        //if it is moves sideways, then it can never push another marble
        if (GameData.rows.sideways(first as String, second as String, moveTo as String)) {
            moveSideways(board)
        } else if (board[moveTo]!!.empty) {
            moveMarble(second, moveTo, board)
        } else if ( board[moveTo]!!.marble?.playerNumber != playersTurn) {
            val newHex = GameData.rows.adjacentDirection(
                moveTo as String,
                GameData.rows.direction(first as String, moveTo as String)
            )
            if (board.containsKey(newHex)) {
                if (board[newHex]!!.empty) {
                    doPushOne(board)
                }
            } else {
                doPushOne(board)
            }
        }
    }

    //moves three marbles, either sideways or in the same direction
    private fun performMovementThree(board: Hashtable<String?, Hexagon>) {
        val newHex = GameData.rows.adjacentDirection(
            moveTo as String,
            GameData.rows.direction(first as String, moveTo as String)
        )
        //if it is moves sideways, then it can never push another marble
        if (GameData.rows.sideways(first as String, second as String, moveTo as String)) {
            moveSideways(board)
        } else if (board[moveTo]!!.empty) {
            moveMarble(third, moveTo, board)
        } else if ( board[moveTo]!!.marble?.playerNumber as Int != playersTurn) {
            if (board.containsKey(newHex)) {
                if (board[newHex]!!.empty) {
                    doPushOne(board)
                } else if (board[newHex]!!.marble?.playerNumber != playersTurn) {
                    val newHexCalculated = GameData.rows.adjacentDirection(
                        newHex,
                        GameData.rows.direction(first  as String, moveTo  as String)
                    )
                    if (board.containsKey(newHexCalculated)) {
                        if (board[newHexCalculated]!!.empty) {
                            doPushTwo(board)
                        }
                    } else {
                        doPushTwo(board)
                    }
                }
            } else {
                doPushOne(board)
            }
        }
    }

    //Move marbles sideways (either two or three)
    private fun moveSideways(board: Hashtable<String?, Hexagon>) {
        val direction = GameData.rows.direction(first as String, moveTo as String)
        val letterFirst: Char = first!![0]
        val letterSecond: Char = second!![0]
        val letterFirstSt: String = first!!.substring(0, 1)
        val letterSecondSt: String = second!!.substring(0, 1)
        val numberFirst: String = first!!.substring(1)
        val numberSecond: String = second!!.substring(1)
        val numberOne = numberFirst.toInt()
        val numberTwo = numberSecond.toInt()

        //when the value is one bigger
        val numberOnePlus = numberOne + 1
        val numberTwoPlus = numberTwo + 1
        val letterOnePlus = (letterFirst.code + 1).toChar()
        val letterTwoPlus = (letterSecond.code + 1).toChar()
        val letterOnePlusSt = letterOnePlus.toString()
        val letterTwoPlusSt = letterTwoPlus.toString()

        //when the value is one smaller
        val numberOneMinus = numberOne - 1
        val numberTwoMinus = numberTwo - 1
        val letterOneMinus = (letterFirst.code - 1).toChar()
        val letterTwoMinus = (letterSecond.code - 1).toChar()
        val letterOneMinusSt = letterOneMinus.toString()
        val letterTwoMinusSt = letterTwoMinus.toString()
        var keyOne: String? = null
        var keyTwo: String? = null
        var keyThree: String? = null
        when (direction) {
            1 -> {
                keyOne = letterFirstSt + (numberOneMinus)
                keyTwo = letterSecondSt + (numberTwoMinus)
            }
            (2) -> {
                keyOne = letterOnePlusSt + (numberOne)
                keyTwo = letterTwoPlusSt + numberTwo
            }
            (3) -> {
                keyOne = letterOnePlusSt + (numberOnePlus)
                keyTwo = letterTwoPlusSt + (numberTwoPlus)
            }
            (4) -> {
                keyOne = letterFirstSt + (numberOnePlus)
                keyTwo = letterSecondSt + (numberTwoPlus)
            }
            (5) -> {
                keyOne = letterOneMinusSt + (numberOne)
                keyTwo = letterTwoMinusSt + numberTwo
            }
            (6) -> {
                keyOne = letterOneMinusSt + (numberOneMinus)
                keyTwo = letterTwoMinusSt + (numberTwoMinus)
            }
        }
        if (third != null) {
            val letterThird: Char = third!![0]
            val letterThirdSt: String = third!!.substring(0, 1)
            val numberThird: String = third!!.substring(1)
            val numberThree = (numberThird.toInt())
            val numberThreePlus = (numberThree + 1)
            val letterThreePlus = (letterThird.code + 1).toChar()
            val letterThreePlusSt = letterThreePlus.toString()
            val numberThreeMinus = (numberThree - 1)
            val letterThreeMinus = (letterThird.code - 1).toChar()
            val letterThreeMinusSt = letterThreeMinus.toString()
            if (direction == 1) {
                keyThree = letterThirdSt + (numberThreeMinus)
            }
            if (direction == (2)) {
                keyThree = letterThreePlusSt + (numberThree)
            }
            if (direction == (3)) {
                keyThree = letterThreePlusSt + (numberThreePlus)
            }
            if (direction == (4)) {
                keyThree = letterThirdSt + (numberThreePlus)
            }
            if (direction == (5)) {
                keyThree = letterThreeMinusSt + (numberThree)
            }
            if (direction == (6)) {
                keyThree = letterThreeMinusSt + (numberThreeMinus)
            }
            moveMarble(third, keyThree, board)
        }
        moveMarble(first, keyOne, board)
        moveMarble(second, keyTwo, board)
    }

    //removes a marble from the board - used in push methods
    private fun removeMarble(removing: Marble?, board: Hashtable<String?, Hexagon>) {
        if (automaticGame && !ai) {
            (
                    when (playersTurn) {
                        1 -> {
                            point++
                        }
                        2 -> {
                            point2++
                        }
                        else -> {
                            point3++
                        }
                    }
            )
        }
        if (board == Board.hashBoard && !adding) {
            (GameData.score[playersTurn - 1]++)
            GameGui.MainScene.children.remove(removing)
            MarbleStorage.storage.remove(removing)
            GameGui.Screen.children.remove(removing)
            GameGui.pp.children.remove(removing)
            (
                    when (playersTurn) {
                        1 -> {
                            point++
                        }
                        2 -> {
                            point2++
                        }
                        else -> {
                            point3++
                        }
                    }
            )
        } else {
            pushed = true
        }
    }

    //push one marble
    private fun doPushOne(board: Hashtable<String?, Hexagon>) {
        if (third == null) {
            val removing = board[moveTo]!!.marble
            board[moveTo]!!.setEmpty()
            moveMarble(second, moveTo, board)
            val keyAdj = GameData.rows.adjacentDirection(
                moveTo  as String,
                (GameData.rows.direction(first  as String, moveTo as String))
            )
            if (board.containsKey(keyAdj)) {
                board[keyAdj]!!.setFull(removing)
                removing?.locationKey = keyAdj
                removing?.updateLocation(board as Hashtable<String?, Hexagon?>)
            } else {
                removeMarble(removing, board)
            }
        } else {
            val moving = board[third]!!.marble
            val removing = board[moveTo]!!.marble
            board[third]!!.setEmpty()
            board[moveTo]!!.setEmpty()
            board[moveTo]!!.setFull(moving)
            val keyAdj = GameData.rows.adjacentDirection(
                moveTo as String,
                (GameData.rows.direction(first  as String, moveTo  as String))
            )
            if (board.containsKey(keyAdj)) {
                board[keyAdj]!!.setFull(removing)
                removing?.locationKey = keyAdj
                removing?.updateLocation(board as Hashtable<String?, Hexagon?>)
            } else {
                removeMarble(removing, board)
            }
            moving?.locationKey = moveTo as String
            moving?.updateLocation(board as Hashtable<String?, Hexagon?>)
        }
    }

    //push two marbles
    private fun doPushTwo(board: Hashtable<String?, Hexagon>) {
        val moving = board[third]!!.marble
        val removing = board[moveTo]!!.marble
        board[third]!!.setEmpty()
        board[moveTo]!!.setEmpty()
        board[moveTo]!!.setFull(moving)
        val keyAdj = GameData.rows.adjacentDirection(
            moveTo as String,
            (GameData.rows.direction(first as String, moveTo as String))
        )
        val keyAdjTwo = GameData.rows.adjacentDirection(
            keyAdj,
            (GameData.rows.direction(first as String, moveTo as String))
        )

        //as it's already determined it is possible to move it, doesn't need to be checked
        if (board.containsKey(keyAdjTwo)) {
            board[keyAdjTwo]!!.setFull(removing)
            removing?.locationKey = keyAdjTwo
            removing?.updateLocation(board as Hashtable<String?, Hexagon?>)
        } else {
            removeMarble(removing, board)
        }
        moving?.locationKey = moveTo as String
        moving?.updateLocation(board as Hashtable<String?, Hexagon?>)
    }

    companion object {
        //first, second, and third marble code that need to be moved (the hexagon in the hash board contains the marbles of the same code which can be removed and added quickly)
        var first: String? = null
        var second: String? = null
        var third: String? = null
        var point = 0
        var point2 = 0
        var point3 = 0
        var pushed = false
        var adding = false

        //following arraylist can be used to find the ones that need to be moved:
        var selectedMarbles = ArrayList<String>()
        var nrSelected = 0 //keep track of how many marbles are selected

        //code of the hexagon the marble needs to be moved to
        var moveTo: String? = null

        //when the selection process is done, this will be true:
        private var selected = false

        //keep track of the player that needs to move a marble
        var playersTurn = 1

        //if THIS MOVE is AI or not
        var ai = false

        //which players are AI players
        var player1AI = false
        var player2AI = false
        var player3AI = false

        //choose if we use greedy or not
        var greedy = true

        //separately
        private const val greedyPlayer1 = false
        private const val greedyPlayer2 = false

        //temporarily store
        var one: Marble? = null
        var two: Marble? = null
        var three: Marble? = null
        var initial: GameState? = null
        var monteCarlo: MonteCarlo? = null
        private const val repOff = true
        var alphaBeta = false
        var mcts = false
        var automaticGame = false
        var automaticGameEnd = false
        var winnerAutomaticGame = 0
        var initialBoard: Hashtable<String, Hexagon>? = null
//        var need = true
        var PvC = false

        //automatically perform the move for the AI -> create tree, search and perform the move!!
        fun checkAI(board: Hashtable<String?, Hexagon?>?) {
            if (playersTurn == 1  && player1AI ||  playersTurn == 2 && player2AI || playersTurn == 3 && player3AI) {
                performAI(board)
            }
        }

        //checks whether it needs to be greedy and then performs the move
        private fun performAI(board: Hashtable<String?, Hexagon?>?) {
            val state = GameState(BoardMethods.copyHashBoard(board) as Hashtable<String?, Hexagon?>?,
                GameMethods.changeBack(playersTurn)
            )
            if (greedy || GameData.numberPlayers == 3 || playersTurn == 1 && greedyPlayer1 || playersTurn == 2 && greedyPlayer2) {
                PerformAIAction.createGameTree(state, 1)
                PerformAIAction.perform(greedy = true, alphaBeta = false, board = board)
            } else if (alphaBeta) {
                PerformAIAction.createGameTree(state, 2)
                PerformAIAction.perform(greedy = false, alphaBeta = true, board = board)
            } else {
                PerformAIAction.perform(greedy = false, alphaBeta = false, board = board)
            }
        }

        //resets the move
        fun resetMove() {
            first = null
            second = null
            third = null
            moveTo = null
            selected = false
            nrSelected = 0
            selectedMarbles.clear()
            pushed = false
        }

        //test if it is possible to move one, else it resets the move
        fun validMoveOne(board: Hashtable<String?, Hexagon>, first: String?, moveTo: String?): Boolean {

           return if (board.containsKey(first) && board.containsKey(moveTo) && (BoardMethods.moveRepetitionChecker(
                        first,
                        null,
                        null,
                        playersTurn,
                        GameData.rows.direction(first as String, moveTo as String)
                    ) || repOff)
                ) {
                    if (board[first]!!.adjacent(moveTo) && board[moveTo]!!.empty) {
                        true
                    } else {
                        resetMove()
                        false
                    }
                } else {
                    resetMove()
                    false
                }
        }

        //checks whether a move with two marbles is valid
        fun validMoveTwo(
            board: Hashtable<String?, Hexagon>,
            first: String?,
            second: String?,
            moveTo: String?
        ): Boolean {

            return if (first != null && second != null && moveTo != null && BoardMethods.moveRepetitionChecker(
                    first,
                    second,
                    null,
                    playersTurn,
                    GameData.rows.direction(first, moveTo)
                ) || repOff
            ) {
                if (board.containsKey(first) && board.containsKey(second) && board.containsKey(moveTo)) {
                    return if (board[first]!!.adjacent(second as String) && board[first]!!.adjacent(moveTo as String)) {
                        val newHex = GameData.rows.adjacentDirection(moveTo, GameData.rows.direction(first as String, moveTo))
                        if (board[first]!!.adjacent(second) && board[first]!!.adjacent(moveTo)) {
                            //if it needs to move sideways and if there are two free space where they are needed, the move is valid
                            if (GameData.rows.sideways(first, second, moveTo)) {
                                return if (GameData.rows.twoFree(first, second, moveTo, board)) {
                                    true
                                } else {
                                    resetMove()
                                    false
                                }
                            } else if (board[moveTo]!!.empty && GameData.rows.direction(
                                    moveTo,
                                    first
                                ) == GameData.rows.direction(first, second)
                            ) {
                                return true
                            } else if (board[moveTo]!!.marble?.playerNumber != playersTurn && GameData.rows.direction(
                                    moveTo,
                                    first
                                ) == GameData.rows.direction(first, second)
                            ) {
                                return if (board.containsKey(newHex)) {
                                    if (board[newHex]!!.empty) {
                                        true
                                    } else {
                                        resetMove()
                                        false
                                    }
                                } else {
                                    true
                                }
                            }
                            resetMove()
                            false
                        } else {
                            resetMove()
                            false
                        }
                    } else {
                        resetMove()
                        false
                    }
                }
                resetMove()
                false
            } else {
                resetMove()
                false
            }

        }

        //checks whether a move with three marbles is possible
        fun validMoveThree(
            board: Hashtable<String?, Hexagon>,
            first: String?,
            second: String?,
            third: String?,
            moveTo: String?
        ): Boolean {

            return if (first != null && second != null && moveTo != null && third != null && (BoardMethods.moveRepetitionChecker(
                    first,
                    second,
                    third,
                    playersTurn,
                    GameData.rows.direction(first, moveTo)
                ) || repOff)
            ) {
                if (board.containsKey(first) && board.containsKey(second) && board.containsKey(moveTo) && board.containsKey(
                        third
                    )
                ) {
                    if (board[first]!!.adjacent(second) && board[first]!!
                            .adjacent(moveTo) && board[second]!!.adjacent(third)
                    ) {
                        val newHex = GameData.rows.adjacentDirection(moveTo, GameData.rows.direction(first, moveTo))
                        val newNewHex =
                            GameData.rows.adjacentDirection(newHex, GameData.rows.direction(first, moveTo))
                        if (GameData.rows.sideways(first, second, moveTo)) {
                            return if (GameData.rows.threeFree(first, second, third, moveTo, board)) {
                                true
                            } else {
                                resetMove()
                                false
                            }
                        } else if (board[moveTo]!!.empty && GameData.rows.direction(
                                moveTo,
                                first
                            ) == GameData.rows.direction(first, second) && GameData.rows.direction(
                                first,
                                second
                            ) == GameData.rows.direction(second, third)
                        ) {
                            return true
                        } else if (board[moveTo]!!.marble?.playerNumber != playersTurn && GameData.rows.direction(
                                moveTo,
                                first
                            ) == GameData.rows.direction(first, second) && GameData.rows.direction(
                                first,
                                second
                            ) == GameData.rows.direction(second, third)
                        ) {
                            if (board.containsKey(newHex)) {
                                if (board[newHex]!!.empty) {
                                    return true
                                } else if (board[newHex]!!.marble?.playerNumber != playersTurn) {
                                    if (board.containsKey(newNewHex)) {
                                        if (board[newNewHex]!!.empty) {
                                            return true
                                        }
                                    } else {
                                        return true
                                    }
                                }
                            } else {
                                return true
                            }
                        } else {
                            resetMove()
                            return false
                        }
                    }
                }
                resetMove()
                false
            } else {
                resetMove()
                false
            }

        }
    }

}