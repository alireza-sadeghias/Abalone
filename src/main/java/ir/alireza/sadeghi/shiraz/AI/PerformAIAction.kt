package ir.alireza.sadeghi.shiraz.ai

import ir.alireza.sadeghi.shiraz.GameData
import ir.alireza.sadeghi.shiraz.Hexagon
import ir.alireza.sadeghi.shiraz.Move
import java.util.*

object PerformAIAction {
    var tree: GameTree? = null

    //get the best node and use this action
    fun perform(greedy: Boolean, alphaBeta: Boolean, board: Hashtable<String?, Hexagon?>?) {
        //build the tree and perform search - make a new node a root node?
        var needed: Node<GameState?>? = null
        needed = if (greedy) {
            choose2()
        } else if (alphaBeta) {
            choose()
        } else {
            chooseMctsSearch()
        }
        if (needed!!.returnData()!!.first != null) {
            GameData.move.select(needed.returnData()!!.first as String, board as Hashtable<String?, Hexagon>)
            if (needed.returnData()!!.second != null) {
                GameData.move.select(needed.returnData()!!.second as String, board as Hashtable<String?, Hexagon>)
                if (needed.returnData()!!.third != null) {
                    GameData.move.select(needed.returnData()!!.third as String, board as Hashtable<String?, Hexagon>)
                } else {
                    GameData.move.select(needed.returnData()!!.first as String, board as Hashtable<String?, Hexagon>)
                }
            } else {
                GameData.move.select(needed.returnData()!!.first as String, board as Hashtable<String?, Hexagon>)
            }
            GameData.move.select(needed.returnData()!!.moveTo as String, board as Hashtable<String?, Hexagon>)
        }
        if (alphaBeta || greedy) {
            DeleteLayers.deleteBranch(tree?.root as Node<GameState?>)
        }
    }

    fun choose2(): Node<GameState?>? {
        var bestMove: Node<GameState?>? = null
        var maxEval = Double.NEGATIVE_INFINITY
        for (i in tree?.root?.children?.indices!!) {
            if (maxEval.coerceAtLeast(tree!!.root?.children?.get(i)?.returnData()?.evaluatedValue as Double) > maxEval) {
                maxEval = tree!!.root?.children?.get(i)?.returnData()?.evaluatedValue as Double
                bestMove = tree!!.root?.children?.get(i)!!
            }
        }
        return bestMove
    }

    private fun choose(): Node<GameState?>? {
        val alphaBeta = AlphaBeta()
        alphaBeta.performAB(
            tree?.root,
            2,
            -Double.MAX_VALUE,
            Double.MAX_VALUE,
            true
        )
        return alphaBeta.node
    }

    fun createGameTree(state: GameState, layers: Int) {
        val current = Node(state)
        //always create a new tree
        tree = GameTree(current as Node<GameState?>?)
        tree!!.buildFullTree(layers)
    }

    //accidentally changed some stuff in node, which is why it said it had errors.
    private fun chooseMctsSearch(): Node<GameState?> {
        return Move.monteCarlo?.mcts(10) as Node<GameState?>
    }
}