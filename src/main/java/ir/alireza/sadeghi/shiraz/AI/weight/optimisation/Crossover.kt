package ir.alireza.sadeghi.shiraz.ai.weight.optimisation

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix

object Crossover {
    var mutationRate = 0.1
    var randomInterval = 0.15
    fun crossover(Best: ArrayList<Array<DoubleArray>>?) {
        ReadMatrix.fileNumber = 0
        ReadResult.fileNumber = 0
        ReadMatrix.gen++
        for (i in Best!!.indices) {
            for (j in i until Best.size) {
                val crossWeightMatrix =
                    Array<DoubleArray?>(EvolutionaryAlgo.modes) { DoubleArray(EvolutionaryAlgo.amountWeights) }
                for (k in crossWeightMatrix.indices) {
                    for (m in crossWeightMatrix[0]!!.indices) {
                        crossWeightMatrix[k]!![m] = Best[i][k][m] + Best[j][k][m]
                    }
                }
                normalise(crossWeightMatrix)
                mutation(crossWeightMatrix)
                normalise(crossWeightMatrix)
                ReadMatrix.readOut(crossWeightMatrix, "Matrices")
                ReadResult.readOut()
            }
        }
    }

    fun normalise(crossWeightMatrix: Array<DoubleArray?>): Array<DoubleArray?> {
        for (l in crossWeightMatrix.indices) {
            var sum = 0.0
            for (m in crossWeightMatrix[0]!!.indices) {
                sum += crossWeightMatrix[l]!![m]
            }
            for (n in crossWeightMatrix[0]!!.indices) {
                crossWeightMatrix[l]!![n] = crossWeightMatrix[l]!![n] / sum
            }
        }
        return crossWeightMatrix
    }

    fun mutation(Matrix: Array<DoubleArray?>): Array<DoubleArray?> {
        for (i in Matrix.indices) {
            a@ for (j in i until Matrix[0]!!.size) {
                val x = Math.random()
                if (x < mutationRate) {
                    Matrix[i]!![j] = Matrix[i]!![j] - randomInterval + Math.random() * 2 * randomInterval
                    if (Matrix[i]!![j] < 0) {
                        Matrix[i]!![j] = 0.0
                    }
                    break@a
                }
            }
        }
        return Matrix
    }
}