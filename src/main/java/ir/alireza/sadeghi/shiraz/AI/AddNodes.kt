package ir.alireza.sadeghi.shiraz.ai

import ir.alireza.sadeghi.shiraz.*
import java.util.*
import kotlin.collections.ArrayList

/*
 * Can add layers to our game tree.
 * There is a method in which you can put in the node, and it will add every possible node to it.
 */
object AddNodes {
    var nodeNR = 0
    private fun everyGameState(node: Node<GameState?>?): ArrayList<GameState?> {
        val board = node!!.returnData()!!.boardState
        val hexagons: ArrayList<String> = Board.hash
        val oneMarbleMoves: ArrayList<GameState?> = ArrayList()
        val twoMarblesMoves: ArrayList<GameState?> = ArrayList()
        val threeMarblesMoves: ArrayList<GameState?> = ArrayList()
        for (i in hexagons.indices) {
            val currentString = hexagons[i]
            val current = board!![currentString]
            if (!current!!.empty) {
                if (current.marble?.playerNumber == GameMethods.changePlayer(node.returnData()!!.turn)) {
                    //run this for every direction
                    for (j in 1..6) {
                        val moveTo = GameData.rows.adjacentDirection(currentString, j)

                        //add every move with one marble -- is okay!!
                        if (Move.validMoveOne(board as Hashtable<String?, Hexagon>, currentString, moveTo) and Board.hash.contains(
                                moveTo
                            )
                        ) {
                            val oneM = GameState(currentString, null, null, moveTo, node.returnData())
                            if (oneM.valid) {
                                nodeNR++
                                oneMarbleMoves.add(oneM)
                            }
                        }
                        for (k in 1..6) {
                            val marble2 = GameData.rows.adjacentDirection(currentString, k)
                            if (Board.hash.contains(currentString) && Board.hash.contains(marble2) and Board.hash.contains(
                                    moveTo
                                )
                            ) {
                                if (!board[marble2]!!.empty) {
                                    if (board[marble2]!!.marble?.playerNumber == GameMethods.changePlayer(node.returnData()!!.turn)) {
                                        if (Move.validMoveTwo(board, currentString, marble2, moveTo)) {
                                            val twoM =
                                                GameState(currentString, marble2, null, moveTo, node.returnData())
                                            if (twoM.valid) {
                                                nodeNR++
                                                twoMarblesMoves.add(twoM)
                                            }
                                        }
                                    }
                                }
                            }
                            //add every move with three marbles
                            val marble3 = GameData.rows.adjacentDirection(marble2, k)
                            if (Board.hash.contains(currentString) && Board.hash.contains(marble2) && Board.hash.contains(
                                    marble3
                                ) and Board.hash.contains(moveTo)
                            ) {
                                if (!board[marble2]!!.empty && !board[marble3]!!.empty) {
                                    if (board[marble2]!!.marble?.playerNumber == GameMethods.changePlayer(node.returnData()!!.turn) && board[marble3]!!.marble?.playerNumber == GameMethods.changePlayer(
                                            node.returnData()!!.turn
                                        )
                                    ) {
                                        if (Move.validMoveThree(
                                                board,
                                                currentString,
                                                marble2,
                                                marble3,
                                                moveTo
                                            )
                                        ) {
                                            val threeM = GameState(
                                                currentString,
                                                marble2,
                                                marble3,
                                                moveTo,
                                                node.returnData()
                                            )
                                            if (threeM.valid) {
                                                nodeNR++
                                                threeMarblesMoves.add(threeM)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        val everyPossibleNode = ArrayList<GameState?>()
        for (i in threeMarblesMoves.indices) {
            everyPossibleNode.add(threeMarblesMoves[i])
            //node.addChild(threeMarblesMoves.get(i));
        }
        for (i in twoMarblesMoves.indices) {
            everyPossibleNode.add(twoMarblesMoves[i])
            //node.addChild(twoMarblesMoves.get(i));
        }
        for (i in oneMarbleMoves.indices) {
            everyPossibleNode.add(oneMarbleMoves[i])
            //node.addChild(oneMarbleMoves.get(i));
        }
        return everyPossibleNode
    }

    //add all the children of the node you pass as a parameter
    fun addForOne(node: Node<GameState?>?) {
        val children = everyGameState(node)
        for (i in children.indices) {
            node!!.addChild(children[i])
        }
    }

    //add children to all these children
    fun addForMultiple(nodes: List<Node<GameState?>?>) {
        for (i in nodes.indices) {
            addForOne(nodes[i])
        }
    }
}