package ir.alireza.sadeghi.shiraz

import java.util.*

/*
 * Creates and contains lists of rows of boards.
 * Gives insights to the board structure.
 * NOTE: Needs to be created when the board is already in place.
 */
class BoardRows {
    var horizontal = ArrayList<ArrayList<String>>()
    var topLeft = ArrayList<ArrayList<String>>()
    var topRight = ArrayList<ArrayList<String>>()

    //automatically creates every row when this is called
    init {

        //horizontal rows
        var ch = 'A'
        while (ch <= 'I') {
            val letterCode = ch.toString()
            val row = ArrayList<String>()
            for (j in 0..9) {
                if (Board.hashBoard.containsKey(letterCode + j)) {
                    row.add(letterCode + j)
                }
            }
            horizontal.add(row)
            ch++
        }

        //starting from top left (going to bottom right)
        for (j in 0..9) {
            val row = ArrayList<String>()
            var currChar = 'A'
            while (currChar <= 'I') {
                val letterCode = currChar.toString()
                if (Board.hashBoard.containsKey(letterCode + j)) {
                    row.add(letterCode + j)
                }
                currChar++
            }
            topLeft.add(row)
        }

        //starting from top right (going to bottom left)
        var start = 'I'
        for (i in 13 downTo 5) {
            val row = ArrayList<String>()
            for (j in 0..8) {
                val letterCode = start.toString()
                val temp = i - j
                if (Board.hashBoard.containsKey(letterCode + temp)) {
                    row.add(letterCode + temp)
                }
                start = (start.code - 1).toChar()
            }
            start = 'I'
            topRight.add(row)
        }
    }

    //checks whether three marbles are in the same row
    fun sameRowThree(one: String, two: String, three: String): Boolean {
        var sameRow = false
        for (i in horizontal.indices) {
            if (sameRow) {
                break
            }
            if (horizontal[i].contains(one) && horizontal[i].contains(two) && horizontal[i].contains(three)) {
                sameRow = true
            }
        }
        for (i in topLeft.indices) {
            if (sameRow) {
                break
            }
            if (topLeft[i].contains(one) && topLeft[i].contains(two) && topLeft[i].contains(three)) {
                sameRow = true
            }
        }
        for (i in topRight.indices) {
            if (sameRow) {
                break
            }
            if (topRight[i].contains(one) && topRight[i].contains(two) && topRight[i].contains(three)) {
                sameRow = true
            }
        }
        return sameRow
    }

    //checks whether a movement is sideways or not
    fun sideways(first: String, second: String, moveTo: String): Boolean {
        return if (((direction(first, second) == 1) and (direction(first, moveTo) == 4) || direction(
                first,
                second
            ) == 4) and (direction(first, moveTo) == 1)
        ) {
            false
        } else if (((direction(first, second) == 2) and (direction(first, moveTo) == 5) || direction(
                first,
                second
            ) == 5) and (direction(first, moveTo) == 2)
        ) {
            false
        } else if (((direction(first, second) == 3) and (direction(first, moveTo) == 6) || direction(
                first,
                second
            ) == 6) and (direction(first, moveTo) == 3)
        ) {
            false
        } else direction(first, second) != direction(first, moveTo)
    }

    //if it's sideways with two-check whether two hexagons in a certain direction are still available
    fun twoFree(first: String, second: String, moveTo: String, board: Hashtable<String?, Hexagon>): Boolean {
        val direction = direction(first, moveTo)
        val letterFirst = first[0]
        val letterSecond = second[0]
        val letterFirstSt = first.substring(0, 1)
        val letterSecondSt = second.substring(0, 1)
        val numberFirst = first.substring(1)
        val numberSecond = second.substring(1)
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
        if (direction == 1) {
            if (board.containsKey(letterFirstSt + numberOneMinus) && board.containsKey(letterSecondSt + numberTwoMinus)) {
                if (board[letterFirstSt + numberOneMinus]!!.empty && board[letterSecondSt + numberTwoMinus]!!.empty) {
                    return true
                }
            } else {
                return false
            }
        } else if (direction == 2) {
            if (board.containsKey(letterOnePlusSt + numberOne) && board.containsKey(letterTwoPlusSt + numberTwo)) {
                if (board[letterOnePlusSt + numberOne]!!.empty && board[letterTwoPlusSt + numberTwo]!!.empty) {
                    return true
                }
            } else {
                return false
            }
        } else if (direction == 3) {
            if (board.containsKey(letterOnePlusSt + numberOnePlus) && board.containsKey(letterTwoPlusSt + numberTwoPlus)) {
                if (board[letterOnePlusSt + numberOnePlus]!!.empty && board[letterTwoPlusSt + numberTwoPlus]!!.empty) {
                    return true
                }
            } else {
                return false
            }
        } else if (direction == 4) {
            if (board.containsKey(letterFirstSt + numberOnePlus) && board.containsKey(letterSecondSt + numberTwoPlus)) {
                if (board[letterFirstSt + numberOnePlus]!!.empty && board[letterSecondSt + numberTwoPlus]!!.empty) {
                    return true
                }
            } else {
                return false
            }
        } else if (direction == 5) {
            if (board.containsKey(letterOneMinusSt + numberOne) && board.containsKey(letterTwoMinusSt + numberTwo)) {
                if (board[letterOneMinusSt + numberOne]!!.empty && board[letterTwoMinusSt + numberTwo]!!.empty) {
                    return true
                }
            } else {
                return false
            }
        } else if (direction == 6) {
            if (board.containsKey(letterOneMinusSt + numberOneMinus) && board.containsKey(letterTwoMinusSt + numberTwoMinus)) {
                if (board[letterOneMinusSt + numberOneMinus]!!.empty && board[letterTwoMinusSt + numberTwoMinus]!!.empty) {
                    return true
                }
            } else {
                return false
            }
        }
        return false
    }

    //if it's sideways with three - check whether the three adjacent hexagons in a certain direction are still empty
    fun threeFree(
        first: String,
        second: String,
        third: String,
        moveTo: String,
        board: Hashtable<String?, Hexagon>
    ): Boolean {
        val direction = direction(first, moveTo)
        val letterFirst = first[0]
        val letterSecond = second[0]
        val letterThird = third[0]
        val letterFirstSt = first.substring(0, 1)
        val letterSecondSt = second.substring(0, 1)
        val letterThirdSt = third.substring(0, 1)
        val numberFirst = first.substring(1)
        val numberSecond = second.substring(1)
        val numberThird = third.substring(1)
        val numberOne = numberFirst.toInt()
        val numberTwo = numberSecond.toInt()
        val numberThree = numberThird.toInt()

        //when the value is one bigger
        val numberOnePlus = numberOne + 1
        val numberTwoPlus = numberTwo + 1
        val numberThreePlus = numberThree + 1
        val letterOnePlus = (letterFirst.code + 1).toChar()
        val letterTwoPlus = (letterSecond.code + 1).toChar()
        val letterThreePlus = (letterThird.code + 1).toChar()
        val letterOnePlusSt = letterOnePlus.toString()
        val letterTwoPlusSt = letterTwoPlus.toString()
        val letterThreePlusSt = letterThreePlus.toString()


        //when the value is one smaller
        val numberOneMinus = numberOne - 1
        val numberTwoMinus = numberTwo - 1
        val numberThreeMinus = numberThree - 1
        val letterOneMinus = (letterFirst.code - 1).toChar()
        val letterTwoMinus = (letterSecond.code - 1).toChar()
        val letterThreeMinus = (letterThird.code - 1).toChar()
        val letterOneMinusSt = letterOneMinus.toString()
        val letterTwoMinusSt = letterTwoMinus.toString()
        val letterThreeMinusSt = letterThreeMinus.toString()
        if (direction == 1) {
            if (board.containsKey(letterFirstSt + numberOneMinus) && board.containsKey(letterSecondSt + numberTwoMinus) && board.containsKey(
                    letterThirdSt + numberThreeMinus
                )
            ) {
                if (board[letterFirstSt + numberOneMinus]!!.empty && board[letterSecondSt + numberTwoMinus]!!.empty && board[letterThirdSt + numberThreeMinus]!!.empty) {
                    return true
                }
            }
        } else if (direction == 2) {
            if (board.containsKey(letterOnePlusSt + numberOne) && board.containsKey(letterTwoPlusSt + numberTwo) && board.containsKey(
                    letterThreePlusSt + numberThree
                )
            ) {
                if (board[letterOnePlusSt + numberOne]!!.empty && board[letterTwoPlusSt + numberTwo]!!.empty && board[letterThreePlusSt + numberThree]!!.empty) {
                    return true
                }
            }
        } else if (direction == 3) {
            if (board.containsKey(letterOnePlusSt + numberOnePlus) && board.containsKey(letterTwoPlusSt + numberTwoPlus) && board.containsKey(
                    letterThreePlusSt + numberThreePlus
                )
            ) {
                if (board[letterOnePlusSt + numberOnePlus]!!.empty && board[letterTwoPlusSt + numberTwoPlus]!!.empty && board[letterThreePlusSt + numberThreePlus]!!.empty) {
                    return true
                }
            }
        } else if (direction == 4) {
            if (board.containsKey(letterFirstSt + numberOnePlus) && board.containsKey(letterSecondSt + numberTwoPlus) && board.containsKey(
                    letterThirdSt + numberThreePlus
                )
            ) {
                if (board[letterFirstSt + numberOnePlus]!!.empty && board[letterSecondSt + numberTwoPlus]!!.empty && board[letterThirdSt + numberThreePlus]!!.empty) {
                    return true
                }
            }
        } else if (direction == 5) {
            if (board.containsKey(letterOneMinusSt + numberOne) && board.containsKey(letterTwoMinusSt + numberTwo) && board.containsKey(
                    letterThreeMinusSt + numberThree
                )
            ) {
                if (board[letterOneMinusSt + numberOne]!!.empty && board[letterTwoMinusSt + numberTwo]!!.empty && board[letterThreeMinusSt + numberThree]!!.empty) {
                    return true
                }
            }
        } else if (direction == 6) {
            if (board.containsKey(letterOneMinusSt + numberOneMinus) && board.containsKey(letterTwoMinusSt + numberTwoMinus) && board.containsKey(
                    letterThreeMinusSt + numberThreeMinus
                )
            ) {
                if (board[letterOneMinusSt + numberOneMinus]!!.empty && board[letterTwoMinusSt + numberTwoMinus]!!.empty && board[letterThreeMinusSt + numberThreeMinus]!!.empty) {
                    return true
                }
            }
        }
        return false
    }

    //returns the number which is the direction in which two adjacent hexagons are
    fun direction(first: String, moveTo: String): Int {
        for (i in horizontal.indices) {
            if (horizontal[i].contains(first) && horizontal[i].contains(moveTo)) {
                val numberFirst = first.substring(1)
                val numberMoveTo = moveTo.substring(1)
                val numberOne = numberFirst.toInt()
                val numberTwo = numberMoveTo.toInt()
                return if (numberOne > numberTwo) {
                    1
                } else {
                    4
                }
            }
        }
        for (i in topLeft.indices) {
            if (topLeft[i].contains(first) && topLeft[i].contains(moveTo)) {
                val letterFirst = first[0]
                val letterMoveTo = moveTo[0]
                return if (letterFirst > letterMoveTo) {
                    5
                } else {
                    2
                }
            }
        }
        for (i in topRight.indices) {
            if (topRight[i].contains(first) && topRight[i].contains(moveTo)) {
                val numberFirst = first.substring(1)
                val numberMoveTo = moveTo.substring(1)
                val numberOne = numberFirst.toInt()
                val numberTwo = numberMoveTo.toInt()
                return if (numberOne > numberTwo) {
                    6
                } else {
                    3
                }
            }
        }
        return 0
    }

    //returns the code of an adjacent hexagon/marble in a certain direction from another code
    fun adjacentDirection(moveTo: String, direction: Int): String {
        val letterMoveTo = moveTo[0]
        val letterMoveToSt = moveTo.substring(0, 1)
        val numberMoveToSt = moveTo.substring(1)
        val numberMoveTo = numberMoveToSt.toInt()

        //when the value is one bigger
        val numberMoveToPlus = numberMoveTo + 1
        val letterMoveToPlus = (letterMoveTo.code + 1).toChar()
        val letterMoveToPlusSt = letterMoveToPlus.toString()

        //when the value is one smaller
        val numberMoveToMinus = numberMoveTo - 1
        val letterMoveToMinus = (letterMoveTo.code - 1).toChar()
        val letterMoveToMinusSt = letterMoveToMinus.toString()
        if (direction == 1) {
            return letterMoveToSt + numberMoveToMinus
        }
        if (direction == 2) {
            return letterMoveToPlusSt + numberMoveTo
        }
        if (direction == 3) {
            return letterMoveToPlusSt + numberMoveToPlus
        }
        if (direction == 4) {
            return letterMoveToSt + numberMoveToPlus
        }
        if (direction == 5) {
            return letterMoveToMinusSt + numberMoveTo
        }
        return if (direction == 6) {
            letterMoveToMinusSt + numberMoveToMinus
        } else ""
    }

    //return the opposite direction of a certain direction
    fun oppositeDirection(direction: Int): Int {
        var currentDirection = direction
        when (currentDirection) {
            1 -> {
                currentDirection = 4
            }
            2 -> {
                currentDirection = 5
            }
            3 -> {
                currentDirection = 6
            }
            4 -> {
                currentDirection = 1
            }
            5 -> {
                currentDirection = 2
            }
            6 -> {
                currentDirection = 3
            }
        }
        return currentDirection
    }
}