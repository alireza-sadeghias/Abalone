package ir.alireza.sadeghi.shiraz.ai.weight.optimisation

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix
import org.apache.logging.log4j.LogManager

object TrainingInstances {
    private val logger = LogManager.getLogger(
        TrainingInstances::class.java
    )
    private var aggressiveInstance =
        ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\Aggressive.txt")
    private var neutralInstance =
        ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\Neutral.txt")
    private var defensiveInstance =
        ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\Defensive.txt")

    fun randInstanceCreation() {
        logger.traceEntry()
        ReadMatrix.fileNumber = 0
        val randNeutralInstance = neutralInstance
        val randAggressiveInstance = aggressiveInstance
        val randDefensiveInstance = defensiveInstance
        val randomInterval = 0.1
        for (i in randNeutralInstance!!.indices) {
            for (j in randNeutralInstance[0]!!.indices) {
                randNeutralInstance[i]!![j] =
                    neutralInstance!![i]!![j] - randomInterval + Math.random() * 2 * randomInterval // midpoint of [a,b] is value of NeutralInstance[i][j]
                if (randNeutralInstance[i]!![j] < 0) {
                    randNeutralInstance[i]!![j] = 0.0
                }
            }
        }
        for (i in randAggressiveInstance!!.indices) {
            for (j in randAggressiveInstance[0]!!.indices) {
                randAggressiveInstance[i]!![j] =
                    aggressiveInstance!![i]!![j] - randomInterval + Math.random() * 2 * randomInterval // midpoint of [a,b] is value of AggressiveInstance[i][j]
                if (randAggressiveInstance[i]!![j] < 0) {
                    randAggressiveInstance[i]!![j] = 0.0
                }
            }
        }
        for (i in randDefensiveInstance!!.indices) {
            for (j in randDefensiveInstance[0]!!.indices) {
                randDefensiveInstance[i]!![j] =
                    defensiveInstance!![i]!![j] - randomInterval + Math.random() * 2 * randomInterval // midpoint of [a,b] is value of DefensiveInstance[i][j]
                if (randDefensiveInstance[i]!![j] < 0) {
                    randDefensiveInstance[i]!![j] = 0.0
                }
            }
        }
        normalize(randNeutralInstance as Array<DoubleArray?>?)
        normalize(randAggressiveInstance as Array<DoubleArray?>?)
        normalize(randDefensiveInstance as Array<DoubleArray?>?)
        ReadMatrix.readOut(randNeutralInstance, "Trainers")
        ReadMatrix.readOut(randAggressiveInstance, "Trainers")
        ReadMatrix.readOut(randDefensiveInstance, "Trainers")
    }

    //Normalisation:
    private fun normalize(inputMatrix: Array<DoubleArray?>?) {
        for (l in inputMatrix!!.indices) {
            var sum = 0.0
            for (m in inputMatrix[0]!!.indices) {
                sum += inputMatrix[l]!![m]
            }
            for (n in inputMatrix.indices) {
                inputMatrix[l]!![n] = inputMatrix[l]!![n] / sum
            }
        }
    }
}