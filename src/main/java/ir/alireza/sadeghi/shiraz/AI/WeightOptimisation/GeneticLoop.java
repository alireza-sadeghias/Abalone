package ir.alireza.sadeghi.shiraz.AI.WeightOptimisation;

import ir.alireza.sadeghi.shiraz.AI.ReadMatrix;

import java.io.File;
import java.util.ArrayList;

public class GeneticLoop {
    public static int Gensize = 10;

    public static void start() {
        //emptying the folders (so we get no confusion and wrong results)
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.Slash + "ir/alireza/sadeghi/shiraz/AI" + ReadMatrix.Slash + "Trainers");
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.Slash + "ir/alireza/sadeghi/shiraz/AI" + ReadMatrix.Slash + "Matrices");
        deleteDirectory(System.getProperty("user.dir") + ReadMatrix.Slash + "ir/alireza/sadeghi/shiraz/AI" + ReadMatrix.Slash + "Results");

        //creating first trainers
        TrainingInstances.RandInstanceCreation();
        //creating first random generation
        EvolutionaryAlgo.initialGeneration();
        //training generation
        EvolutionaryAlgo.Selection();

        for (int i = 1; i < Gensize; i++) {
            System.out.println("________________GEN: "+ReadMatrix.gen);
            ArrayList ResultList = SortResults.sorting();
            Crossover.crossover(ResultList);
            TrainingInstances.RandInstanceCreation();
            EvolutionaryAlgo.Selection();
        }
        System.out.println("DONE!!!!");
    }

    static public void deleteDirectory(String directoryName) {
        File directory = new File(directoryName);

        // Get all files in directory
        File[] files = directory.listFiles();
        for (File file : files) {
            // Delete each file
            if (!file.delete()) {
                // Failed to delete file
                System.out.println("Failed to delete " + file);
            }
        }
    }
}
