package ir.alireza.sadeghi.shiraz.ai.weight.optimisation

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix
import org.apache.logging.log4j.LogManager

object SortResults {
    private val logger = LogManager.getLogger(SortResults::class.java)
    private var bestMatrices = 4
    fun sorting(): ArrayList<Array<DoubleArray>> {
        val list: ArrayList<Array<DoubleArray>> = ArrayList()
        val moves = Array(EvolutionaryAlgo.initialGenerationSize) { IntArray(2) }
        val marbles = Array(EvolutionaryAlgo.initialGenerationSize) { IntArray(2) }
        var compare: IntArray?
        val positions = Array(EvolutionaryAlgo.initialGenerationSize) { IntArray(2) }
        for (m in 1..EvolutionaryAlgo.initialGenerationSize) positions[m - 1][1] = m
        for (j in 1..3) {
            for (i in 1..EvolutionaryAlgo.initialGenerationSize) {
                compare =
                    ReadResult.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Results" + ReadMatrix.slash + "AIResult" + ReadMatrix.gen + "_" + i + "_" + j + ".txt")
                moves[i - 1][0] = compare[0]
                moves[i - 1][1] = i
                marbles[i - 1][0] = compare[1]
                marbles[i - 1][1] = i
            }
            quickSort(moves, 0, moves.size - 1)
            quickSort(marbles, 0, marbles.size - 1)
            for (i in moves.indices) {
                val x = moves[i][1] - 1
                positions[x][0] += i + 1
                val y = marbles[moves.size - i - 1][1] - 1
                positions[y][0] += i + 1
            }
        }
        quickSort(positions, 0, positions.size - 1)
        for (l in 0 until bestMatrices) {
            logger.trace("calling read in with :{}", System.getProperty("user.dir"))
            list.add(ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Matrices" + ReadMatrix.slash + "AInumber" + ReadMatrix.gen + "_" + positions[l][1] + ".txt"))
        }
        return list
    }

    private fun quickSort(arr: Array<IntArray>, begin: Int, end: Int) {
        if (begin < end) {
            val partitionIndex = partition(arr, begin, end)
            quickSort(arr, begin, partitionIndex - 1)
            quickSort(arr, partitionIndex + 1, end)
        }
    }

    private fun partition(arr: Array<IntArray>, begin: Int, end: Int): Int {
        val pivot = arr[end][0]
        var i = begin - 1
        for (j in begin until end) {
            if (arr[j][0] <= pivot) {
                i++
                val swapTemp = arr[i][0]
                val swapTemp2 = arr[i][1]
                arr[i][0] = arr[j][0]
                arr[i][1] = arr[j][1]
                arr[j][0] = swapTemp
                arr[j][1] = swapTemp2
            }
        }
        val swapTemp = arr[i + 1][0]
        val swapTemp2 = arr[i + 1][1]
        arr[i + 1][0] = arr[end][0]
        arr[i + 1][1] = arr[end][1]
        arr[end][0] = swapTemp
        arr[end][1] = swapTemp2
        return i + 1
    }
}