package ir.alireza.sadeghi.shiraz.ai

import ir.alireza.sadeghi.shiraz.Board
import ir.alireza.sadeghi.shiraz.GameData
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Strategies(private val gameState: GameState?) {
    /* class contains methods that define each strategy, like moving to the center etc.
     */
    //not covered here is the possibility of drawing from a database of openings, this would stronlgy mitigate the importance of the closingDistance strategy
    private val player = ArrayList<String>()
    private val opponent = ArrayList<String>()
    private val killMoves = ArrayList<String>()
    private var old: Strategies? = null
    private var centerX: Double = Board.hashBoard["E5"]!!.centerX
    private var centerY: Double = Board.hashBoard["E5"]!!.centerY

    init {
        if (gameState!!.oldGameState != null) {
            old = Strategies(gameState.oldGameState)
        }
        val boardState = gameState.boardState
        for (i in 0 until boardState!!.size) {
            if (!boardState!![Board.hash[i]]!!.empty) {
                if (gameState.evaluateFrom == boardState[Board.hash[i]]!!.marble?.playerNumber) {
                    player.add(Board.hash[i])
                } else {
                    opponent.add(Board.hash[i])
                }
            }
        }
    }

    fun closingDistance(boardState: GameState): Double {
        var playerDisAv = 0.0
        for (i in player.indices) {
            playerDisAv += Math.sqrt(
                Math.pow(boardState.boardState!![player[i]]!!.centerX - centerX, 2.0) + Math.pow(
                    boardState.boardState!![player[i]]!!.centerY - centerY, 2.0
                )
            )

            /* Distance to Center, use Euclidean Distance:
            (centerPointX - MarbleX )^2 + (centerPointY - MarbleY )^2 = distance^2 ;sqrt.dist^2 = distance
            */
        }
        playerDisAv /= player.size
        return playerDisAv
    }

    fun cohesion(): Double {

        //determine the number of neighbouring teammates for each marble for each player, adds them together
        var cohesion = 0
        for (i in player.indices) {
            for (j in 1 until player.size) {
                if (i != j) {
                    if (Math.abs(player[i][0].code - player[j][0].code) < 2 && abs(player[i][1].code - player[j][1].code) < 2) {
                        cohesion++
                    }
                }
            }
        }
        return cohesion.toDouble()
    }

    fun breakGroup(): Double {

        /*In order to determine value for a player each marble (of this player) is checked for an opponent marble at one adjacent side of the marble and an
         opponent marble at the opposing adjacent side. */
        var groupBreaks = 0.0
        for (i in player.indices) {
            for (j in opponent.indices) {
                if (Math.abs(player[i][0].code - opponent[j][0].code) < 2 && abs(player[i][1].code - opponent[j][1].code) < 2 && player[i][0].code - opponent[j][0].code + player[i][1].code - opponent[j][1].code != 0) {
                    a@ for (k in opponent.indices) {
                        if (k != j && player[i][0].code - opponent[k][0].code == -(player[i][0].code - opponent[j][0].code) && player[i][1].code - opponent[k][1].code == -(player[i][1].code - opponent[j][1].code)) {
                            groupBreaks++
                            break@a
                        }
                    }
                }
            }
        }
        return groupBreaks / 2
    }

    fun strengthenGroup(): Double {
        /* Determinates how many possibilities the AI has to push the Opponent.
         */
        var groupStrengh = 0.0
        for (i in player.indices) {
            for (j in 1 until player.size) {
                //if we have a neighbor (at least 2 marbles are needed to even consider a push)
                if (i != j && Math.abs(player[i][0].code - player[j][0].code) < 2 && Math.abs(player[i][1].code - player[j][1].code) < 2 && player[i][0].code - player[j][0].code + player[i][1].code - player[j][1].code != 0) {
                    for (k in opponent.indices) {
                        if (player[i][0].code - opponent[k][0].code == -(player[i][0].code - player[j][0].code) && player[i][1].code - opponent[k][1].code == -(player[i][1].code - player[j][1].code)) {
                            //found a marble to potentially push, needs checking maybe Opp has more marbles
                            var possible = true
                            //if AI can kill, pushing is possible and no further checking needed
                            if (opponent[k][0].code - (player[i][0].code - opponent[k][0].code) < 'A'.code || opponent[k][0].code - (player[i][0].code - opponent[k][0].code) > 'I'.code || opponent[k][1].code - (player[i][1].code - opponent[k][1].code) < '1'.code || opponent[k][1].code - (player[i][1].code - opponent[k][1].code) > '9'.code) {
                                killMoves.add(player[i])
                            } else {
                                for (m in 1 until opponent.size) {
                                    if (m != k && opponent[k][0].code - player[i][0].code == -(opponent[k][0].code - opponent[m][0].code) && opponent[k][1].code - player[i][1].code == -(opponent[k][1].code - opponent[m][1].code)) {
                                        //can we still push? atm 2v2
                                        possible = false
                                        for (n in 1 until player.size) {
                                            if (n != i && n != j && player[j][0].code - player[i][0].code == -(player[j][0].code - player[n][0].code) && player[j][1].code - player[i][1].code == -(player[j][1].code - player[n][1].code)) {
                                                //we have three marbles. does the Opponent has as well 3?
                                                var possible2 = true
                                                //if AI can kill, pushing is possible and no further checking needed
                                                if (opponent[m][0].code - (opponent[k][0].code - opponent[m][0].code) < 'A'.code || opponent[m][0].code - (opponent[k][0].code - opponent[m][0].code) > 'I'.code || opponent[m][1].code - (opponent[k][1].code - opponent[m][1].code) < '1'.code || opponent[m][1].code - (opponent[k][1].code - opponent[m][1].code) > '9'.code) {
                                                    killMoves.add(player[i])
                                                } else {
                                                    for (p in 1 until opponent.size) {
                                                        if (p != m && p != k && opponent[m][0].code - opponent[k][0].code == -(opponent[m][0].code - opponent[p][0].code) && opponent[m][1].code - opponent[k][1].code == -(opponent[m][1].code - opponent[p][1].code)) {
                                                            //no pushing here possible
                                                            possible2 = false
                                                        }
                                                    }
                                                }
                                                if (possible2) {
                                                    groupStrengh++
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (possible) {
                                groupStrengh++
                            }
                        }
                    }
                }
            }
        }
        return groupStrengh
    }

    fun amountOppMarbles(): Int {
        return opponent.size
    }

    fun amountOwnMarbles(): Int {
        return player.size
    }

    fun compareMarblesWon(): Int {
        return if (old != null) {
            old!!.amountOppMarbles() - amountOppMarbles()
        } else 0
    }

    fun compareMarblesLost(): Int {
        return if (old != null) {
            old!!.amountOwnMarbles() - amountOwnMarbles()
        } else 0
    }

    fun closingDistanceOpp(boardState: GameState): Double {
        var playerDisAv = 0.0
        for (i in opponent.indices) {
            playerDisAv += sqrt(
                (boardState.boardState!![opponent[i]]!!.centerX - centerX).pow(2.0) + Math.pow(
                    boardState.boardState!![opponent[i]]!!.centerY - centerY, 2.0
                )
            )
        }
        playerDisAv /= opponent.size
        return playerDisAv
    }

    fun danger(): Int {
        val board = gameState!!.boardState
        val rows = GameData.rows
        var sum = 0
        sum += dangerRow(rows.horizontal as ArrayList<ArrayList<String?>?>?)
        sum += dangerRow(rows.topLeft as ArrayList<ArrayList<String?>?>?)
        sum += dangerRow(rows.topRight as ArrayList<ArrayList<String?>?>?)
        return sum
    }

    private fun dangerRow(row: ArrayList<ArrayList<String?>?>?): Int {
        //at the "start" of a row -> so spot 0, 1, 2, 3
        //these are the player numbers
        var sum = 0
        var bFirst = 0
        var bSecond = 0
        var bThird = 0
        var bFourth = 0
        var bFifth = 0

        //at the "end" of a row -> so spot size -4, size - 3, size -2, size -1
        var endFirst = 0
        var endSecond = 0
        var endThird = 0
        var endFourth = 0
        var endFifth = 0
        val board = gameState!!.boardState
        for (i in row!!.indices) {
            if (row[i]!!.size > 4) {
                if (!board!![row[i]!![0]]!!.empty) {
                    bFirst = board[row[i]!![0]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![1]]!!.empty) {
                    bSecond = board[row[i]!![1]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![2]]!!.empty) {
                    bThird = board[row[i]!![2]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![3]]!!.empty) {
                    bFourth = board[row[i]!![3]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![4]]!!.empty) {
                    bFifth = board[row[i]!![4]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![row[i]!!.size - 1]]!!.empty) {
                    endFirst = board[row[i]!![row[i]!!.size - 1]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![row[i]!!.size - 2]]!!.empty) {
                    endSecond = board[row[i]!![row[i]!!.size - 2]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![row[i]!!.size - 3]]!!.empty) {
                    endThird = board[row[i]!![row[i]!!.size - 3]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![row[i]!!.size - 4]]!!.empty) {
                    endFourth = board[row[i]!![row[i]!!.size - 4]]!!.marble?.playerNumber!!
                }
                if (!board[row[i]!![row[i]!!.size - 5]]!!.empty) {
                    endFifth = board[row[i]!![row[i]!!.size - 5]]!!.marble?.playerNumber!!
                }
            }

            //now check whether there is an endangered marble
            if (bFirst == gameState.evaluateFrom) {
                if (bSecond == gameState.evaluateFrom) {
                    if (bThird > 0 && bThird != gameState.evaluateFrom && bThird == bFourth && bThird == bFifth) {
                        sum++
                    }
                } else if (bSecond > 0 && bSecond != gameState.evaluateFrom && bSecond == bThird) {
                    sum++
                }
            }
            if (endFirst == gameState.evaluateFrom) {
                if (endSecond == gameState.evaluateFrom) {
                    if (endThird > 0 && endThird != gameState.evaluateFrom && endThird == endFourth && endThird == endFifth) {
                        sum++
                    }
                } else if (endSecond > 0 && endSecond != gameState.evaluateFrom && endSecond == endThird) {
                    sum++
                }
            }
        }
        return sum
    }
}