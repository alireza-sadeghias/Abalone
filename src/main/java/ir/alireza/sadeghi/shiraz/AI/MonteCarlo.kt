package ir.alireza.sadeghi.shiraz.ai

import ir.alireza.sadeghi.shiraz.BoardMethods
import ir.alireza.sadeghi.shiraz.GameMethods
import ir.alireza.sadeghi.shiraz.Hexagon
import org.apache.logging.log4j.LogManager
import java.util.*
import kotlin.math.ln
import kotlin.math.sqrt

/*
 * Performs a Monte-Carlo tree search.
 * Automatically cuts off after 100 consecutive moves, as to make it not too large (which would be computationally expensive).
 */
class MonteCarlo(root: Node<GameState?>) {
    //this tree stays the same during the whole game - although if it is really possible, it starts from scratch
    private var monteCarloTree: Tree<GameState?>
    private var random = Random()
    private var chooseForAIMove = false

    //need to cutoff when the game takes too long - about a hundred, to not make it too big
    private val cutoff = 100
    private var currentNode = 1

    //construct the tree, using the initial board state - automatically happens in move
    init {
        monteCarloTree = Tree(root)
    }

    //do the whole monte-carlo search-> for a set number of seconds-> stop expanding after this is reached
    fun mcts(numberSeconds: Int): Node<GameState?>? {
        //for a certain amount of time, this is performed
        chooseForAIMove = false

        //will still finish current simulation
        val curr = System.currentTimeMillis().toDouble()
        while (System.currentTimeMillis() - curr < numberSeconds * 1000) {
            logger.trace("cutoff $currentNode")
            currentNode = 1
            selection(monteCarloTree.root)
        }
        chooseForAIMove = true
        return sucChild(monteCarloTree.root)
    }

    //start from the root
    //select successive child-nodes, until a lead node is reached
    //then, if it terminates the tree, it back propagates or expands
    private fun selection(current: Node<GameState?>?) {
        var c = current
        while (!c!!.isLeaf && currentNode < cutoff) {
            currentNode++
            c = sucChild(c)
        }
        if (!c.data!!.terminal && currentNode < cutoff) {
            currentNode++
            simulation(c)
        }
    }

    //chooses successful child node - similar to the greedy algorithm
    private fun sucChild(parent: Node<GameState?>?): Node<GameState?>? {
        var bestChild: Node<GameState?>? = null
        var maxEval = Double.NEGATIVE_INFINITY
        val multiple = ArrayList<Node<GameState?>?>()
        for (i in parent!!.children!!.indices) {
            if (maxEval.coerceAtLeast(parent.children!![i].totValue) > maxEval && (!chooseForAIMove || maxEval < Double.POSITIVE_INFINITY)
            ) {
                multiple.clear()
                maxEval = parent.children!![i].totValue
                bestChild = parent.children!![i]
            } else if (maxEval.coerceAtLeast(parent.children!![i].totValue) == maxEval && (!chooseForAIMove || maxEval < Double.POSITIVE_INFINITY)
            ) {
                multiple.add(parent.children!![i])
            }
        }
        if (multiple.size > 1) {
            bestChild = multiple[random.nextInt(multiple.size)]
        }
        return bestChild
    }

    //expands the current node
    private fun expansion(expand: Node<GameState?>?) {
        AddNodes.addForOne(expand)
    }

    //simulation (also known as roll out)
    private fun simulation(current: Node<GameState?>?) {
        expansion(current)
        val newChoice = sucChild(current)
        if (newChoice!!.data!!.terminal || currentNode >= cutoff) {
            backPropagate(newChoice, newChoice.data!!.winner)
        } else {
            currentNode++
            simulation(newChoice)
        }
    }

    //backpropagation method - automatically calculate all the new values for it!!
    private fun backPropagate(toPropagate: Node<GameState?>?, winningPlayer: Int) {
        if (toPropagate!!.parent != null) {
            toPropagate.nVisits++
            //if it wins, add the win as well
            if (winningPlayer == 0) {
                toPropagate.wins = toPropagate.wins + 0.5
            }
            if (GameMethods.changePlayer(toPropagate.returnData()!!.turn) == winningPlayer) {
                toPropagate.wins++
            }
            toPropagate.calcMCTSvalue()
            backPropagate(toPropagate.parent, winningPlayer)
        }
    }

    //change the current root node to the one that is needed
    private fun changeRoot(changed: Node<GameState?>?) {
        monteCarloTree.root =changed
        changed!!.parent = null
    }

    //change the node from outside (so, the human player)
    fun changeRootOutside(board: Hashtable<String?, Hexagon?>?) {
        var isThere = false
        var i = 0
        while (!isThere && i < monteCarloTree.root?.children!!.size) {
            if (!monteCarloTree.root!!.isLeaf) {
                if (BoardMethods.compareHashTables(monteCarloTree.root!!.children?.get(i)?.data?.boardState, board)) {
                    changeRoot(monteCarloTree.root!!.children?.get(i))
                    isThere = true
                }
            }
            i++
        }
        if (!isThere) {
            val newRoot = GameState(board, GameMethods.changePlayer(monteCarloTree.root?.data!!.evaluateFrom))
            changeRoot(Node(newRoot))
        }
    }

    companion object {
        private val logger = LogManager.getLogger(MonteCarlo::class.java)
        private val explorationParam = sqrt(2.0)

        //wins = number of wins for the node considered after the i-th move
        //simulations = number of simulations for the node considered after the i-th move
        //parentSimulations = total number of simulations after the i-th move run by the parent node of the one considered
        fun calculateUCB(wins: Double, simulations: Int, parentSimulations: Int): Double {
            return wins / simulations + explorationParam * sqrt(ln(parentSimulations.toDouble()) / simulations)
        }
    }
}