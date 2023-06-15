package ir.alireza.sadeghi.shiraz.ai

import org.apache.logging.log4j.LogManager

/*
 * Contains an alpha beta method.
 * Stores the best node (the move the current player does).
 */   class AlphaBeta {
    var node: Node<GameState?>? = null
    fun performAB(node: Node<GameState?>?, depth: Int, alpha: Double, beta: Double, max: Boolean): Double {
        var alpha = alpha
        var beta = beta
        var value = 0.0
        if (depth == 0) {
            return node!!.returnData()!!.evaluatedValue
        }
        return if (max) {
            value = -Double.MAX_VALUE
            for (node2 in node!!.children!!) {
                value = Math.max(value, performAB(node2, depth - 1, alpha, beta, false))
                logger.trace("value $value")
                val oldAlpha = alpha
                alpha = Math.max(alpha, value)
                if (Math.max(alpha, value) != oldAlpha || this.node == null) {
                    this.node = node2
                }
                if (alpha >= beta) {
                    break
                }
            }
            value
        } else {
            value = Double.MAX_VALUE
            for (node2 in node!!.children!!) {
                value = Math.min(value, performAB(node2, depth - 1, alpha, beta, true))
                beta = Math.min(beta, value)
                if (alpha >= beta) {
                    break
                }
            }
            value
        }
    }

    companion object {
        private val logger = LogManager.getLogger(AlphaBeta::class.java)
    }
}