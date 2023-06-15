package ir.alireza.sadeghi.shiraz.ai.weight.optimisation

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix
import org.apache.logging.log4j.LogManager
import java.io.File

object GeneticLoop {
    private val logger = LogManager.getLogger(GeneticLoop::class.java)
    private var genSize = 10
    fun start() {
        logger.trace("user.dir: {}", System.getProperty("user.dir"))
        //emptying the folders (so we get no confusion and wrong results)
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Trainers")
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Matrices")
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Results")

        //creating first trainers
        TrainingInstances.randInstanceCreation()
        //creating first random generation
        EvolutionaryAlgo.initialGeneration()
        //training generation
        EvolutionaryAlgo.selection()
        for (i in 1 until genSize) {
            logger.trace("________________GEN: " + ReadMatrix.gen)
            val resultList: ArrayList<Array<DoubleArray>>? = SortResults.sorting()
            Crossover.crossover(resultList)
            TrainingInstances.randInstanceCreation()
            EvolutionaryAlgo.selection()
        }
        logger.info("DONE!!!!")
    }

    fun deleteDirectory(directoryName: String?) {
        logger.trace("directory name:{} ", directoryName)
        val directory = File(directoryName)

        // Get all files in directory
        val files = directory.listFiles()
        logger.trace("files:{}", *files)
        if (files != null) {
            for (file in files) {
                // Delete each file
                if (!file.delete()) {
                    // Failed to delete file
                    logger.error("Failed to delete $file")
                }
            }
        }
    }
}