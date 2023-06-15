package ir.alireza.sadeghi.shiraz.ai.weight.optimisation

import ir.alireza.sadeghi.shiraz.ai.EvaluationFunction
import ir.alireza.sadeghi.shiraz.ai.ReadMatrix
import org.apache.logging.log4j.LogManager

object EvolutionaryAlgo {
    private val logger = LogManager.getLogger(
        EvaluationFunction::class.java
    )
    var initialGenerationSize = 10 // should be >10
    var modes = 5
    var amountWeights = 8
    private const val amountGames = 2
    fun initialGeneration() {
        ReadMatrix.fileNumber = 0
        val randWeightMatrix = Array<DoubleArray?>(modes) { DoubleArray(amountWeights) }
        for (i in 0 until initialGenerationSize) {
            for (j in 0 until modes) {
                for (k in 0 until amountWeights) {
                    randWeightMatrix[j]!![k] = Math.random()
                }
            }
            //Normalisation:
            for (l in 0 until modes) {
                var sum = 0.0
                for (m in 0 until amountWeights) {
                    sum += randWeightMatrix[l]!![m]
                }
                for (n in 0 until amountWeights) {
                    randWeightMatrix[l]!![n] = randWeightMatrix[l]!![n] / sum
                }
            }
            ReadMatrix.readOut(randWeightMatrix, "Matrices")
            ReadResult.readOut()
        }
    }

    fun selection() {
        for (i in 1..initialGenerationSize) {
            for (j in 1..3) {
                logger.trace("calling read in with :{}", System.getProperty("user.dir"))
                var player1 =
                    ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Trainers" + ReadMatrix.slash + "AInumber" + ReadMatrix.gen + "_" + j + ".txt")
                var player2 =
                    ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Matrices" + ReadMatrix.slash + "AInumber" + ReadMatrix.gen + "_" + i + ".txt")
                var trainer = -1
                for (k in 0 until amountGames) {
                    if (k % 2 != 0) {
                        val placeholder = player1
                        player1 = player2
                        player2 = placeholder
                        trainer *= -1
                    }
                    val result = GameEnvironment.prepare(player1 as Array<DoubleArray?>?, player2 as Array<DoubleArray?>?, trainer)
                    ReadResult.addResult(
                        System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Results" + ReadMatrix.slash + "AIResult" + ReadMatrix.gen + "_" + i + "_" + j + ".txt",
                        result
                    )
                }
            }
        }
    }
}