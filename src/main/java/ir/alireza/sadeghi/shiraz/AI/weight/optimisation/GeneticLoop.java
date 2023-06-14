package ir.alireza.sadeghi.shiraz.ai.weight.optimisation;

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

public class GeneticLoop {
    private final static Logger logger = LogManager.getLogger(GeneticLoop.class);
    public static int genSize = 10;

    public static void start() {
        logger.trace("user.dir: {}",System.getProperty("user.dir"));
        //emptying the folders (so we get no confusion and wrong results)
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Trainers");
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Matrices");
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Results");

        //creating first trainers
        TrainingInstances.randInstanceCreation();
        //creating first random generation
        EvolutionaryAlgo.initialGeneration();
        //training generation
        EvolutionaryAlgo.selection();

        for (int i = 1; i < genSize; i++) {
            logger.trace("________________GEN: "+ReadMatrix.gen);
            ArrayList ResultList = SortResults.sorting();
            Crossover.crossover(ResultList);
            TrainingInstances.randInstanceCreation();
            EvolutionaryAlgo.selection();
        }
        logger.info("DONE!!!!");
    }

    static public void deleteDirectory(String directoryName) {
        logger.trace("directory name:{} ",directoryName);
        File directory = new File(directoryName);

        // Get all files in directory
        File[] files = directory.listFiles();
        logger.trace("files:{}" ,files);
        if(files!=null){
            for (File file : files) {
                // Delete each file
                if (!file.delete()) {
                    // Failed to delete file
                   logger.error("Failed to delete " + file);
                }
            }
        }
    }
}
