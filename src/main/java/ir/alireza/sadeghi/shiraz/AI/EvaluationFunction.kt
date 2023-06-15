package ir.alireza.sadeghi.shiraz.ai

import ir.alireza.sadeghi.shiraz.Board
import ir.alireza.sadeghi.shiraz.ai.weight.optimisation.GameEnvironment
import org.apache.logging.log4j.LogManager
import kotlin.math.pow
import kotlin.math.sqrt

class EvaluationFunction(gameState: GameState) {
    private val f1: Double
    private val f2: Double
    private val f3: Double
    private val f4: Double
    private val f5: Double
    private var f6 = 0.0
    private val f7: Double
    private val f8: Double
    private val w1: Double
    private val w2: Double
    private val w3: Double
    private val w4: Double
    private val w5: Double
    private val w6: Double
    private val w7: Double
    private val w8: Double
    private var old: Strategies? = null
    private var modeDet: ModeDetermination? = null

    init {
        if (gameState.oldGameState != null) {
            old = Strategies(gameState.oldGameState)
        }
        val strategies = Strategies(gameState)

        //VALUE NORMALIZATION -----------------------------------------------------------------
        val max = sqrt(
            (Board.hashBoard["E5"]!!.centerX - Board.hashBoard["A1"]!!.centerX).pow(2.0) + (Board.hashBoard["E5"]!!.centerY - Board.hashBoard["A1"]!!.centerY).pow(2.0)
        )
        val min = (sqrt(
            (Board.hashBoard["E5"]!!.centerX - Board.hashBoard["D4"]!!.centerX).pow(2.0) + (Board.hashBoard["E5"]!!.centerY - Board.hashBoard["D4"]!!.centerY).pow(2.0)
        ) * 6 + sqrt(
            (Board.hashBoard["E5"]!!.centerX - Board.hashBoard["C4"]!!.centerX).pow(2.0) + (Board.hashBoard["E5"]!!.centerY - Board.hashBoard["C4"]!!.centerY).pow(
                2.0
            )
        ) * 2) / 9
        //scaling between 0-1
        f1 = (strategies.closingDistance(gameState) - max) / (min - max)
        //Min and Max are swapped
        f7 = (strategies.closingDistanceOpp(gameState) - min) / (max - min)

        //      Value Range cohesion : 64 - 0
        f2 = strategies.cohesion() / 64

        //       Value Range breakGroup : 10 - 0
        f3 = strategies.breakGroup() / 10

        //       Value Range strengthenGroup : 32 - 0
        f4 = strategies.strengthenGroup() / 32

        //       Value Range compareMarblesWon : 1 - 0
        f5 = strategies.compareMarblesWon().toDouble()

        //Marbles in danger
        f8 = (1 - strategies.danger() / strategies.amountOwnMarbles()).toDouble()


        //Value Range compareMarblesLost : 0 - 1
        val marblesLostEvaluationValue = strategies.compareMarblesLost().toDouble()
        f6 = if (marblesLostEvaluationValue == 0.0) {
            1.0
        } else {
            0.0
        }
        if (!AITestingON) {
            var name: String? = null
            if (gameState.evaluateFrom == 1) {
                name = Name1
            }
            if (gameState.evaluateFrom == 2) {
                name = Name2
            }
            if (gameState.evaluateFrom == 3) {
                name = Name3
            }
            logger.trace("calling read in with :{}", name)
            modeDet =
                ModeDetermination(ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\" + name + ".txt"))
        } else {
            modeDet = if (gameState.evaluateFrom == 1) {
                ModeDetermination(GameEnvironment.player1 as Array<DoubleArray>) 
            } else {
                ModeDetermination(GameEnvironment.player2 as Array<DoubleArray>)
            }
        }
        normalization(
            modeDet!!.determineMode(
                f1,
                strategies.amountOppMarbles().toDouble(),
                strategies.amountOwnMarbles().toDouble()
            )
        )
        val weightArray = modeDet!!.determineMode(
            f1,
            strategies.amountOppMarbles().toDouble(),
            strategies.amountOwnMarbles().toDouble()
        )
        w1 = weightArray[0]
        w2 = weightArray[1]
        w3 = weightArray[2]
        w4 = weightArray[3]
        w5 = weightArray[4]
        w6 = weightArray[5]
        w7 = weightArray[6]
        w8 = weightArray[7]
    }

    /*assigns a numeric value to each GameState in the Tree, based on the linear equation
    */
    fun evaluate(): Double {
        return w1 * f1 + w2 * f2 + w3 * f3 + w4 * f4 + w5 * f5 + w6 * f6 + w7 * f7 + w8 * f8
    }

    private fun normalization(weights: DoubleArray) {
        var sum = 0.0
        for (i in weights.indices) {
            sum += weights[i]
        }
        for (j in weights.indices) {
            weights[j] = weights[j] / sum
        }
    }

    companion object {
        private val logger = LogManager.getLogger(
            EvaluationFunction::class.java
        )
        var AITestingON = false
        var Name1 = "Final"
        var Name2 = "Final"
        var Name3 = "Final"
    }
}