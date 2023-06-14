package ir.alireza.sadeghi.shiraz.ai.weight.optimisation;

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingInstances {

    private final static Logger logger = LogManager.getLogger(TrainingInstances.class);
    static double[][] AggressiveInstance = ReadMatrix.readIn(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\Aggressive.txt");
    static double[][] NeutralInstance = ReadMatrix.readIn(System.getProperty("user.dir") +  ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\Neutral.txt");
    static double[][] DefensiveInstance = ReadMatrix.readIn(System.getProperty("user.dir") +  ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai\\StartingAI\\Defensive.txt");

    public static void randInstanceCreation() {

        ReadMatrix.fileNumber = 0;

        double[][] RandNeutralInstance = NeutralInstance;
        double[][] RandAggressiveInstance = AggressiveInstance;
        double[][] RandDefensiveInstance = DefensiveInstance;
        double randomInterval = 0.1;

        for (int i = 0; i < RandNeutralInstance.length; i++) {
            for (int j = 0; j < RandNeutralInstance[0].length; j++) {

                RandNeutralInstance[i][j] = NeutralInstance[i][j] - randomInterval + Math.random() * 2 * randomInterval;       // midpoint of [a,b] is value of NeutralInstance[i][j]

                if (RandNeutralInstance[i][j] < 0) {
                    RandNeutralInstance[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < RandAggressiveInstance.length; i++) {
            for (int j = 0; j < RandAggressiveInstance[0].length; j++) {

                RandAggressiveInstance[i][j] = AggressiveInstance[i][j] - randomInterval + Math.random() * 2 * randomInterval;       // midpoint of [a,b] is value of AggressiveInstance[i][j]

                if (RandAggressiveInstance[i][j] < 0) {
                    RandAggressiveInstance[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < RandDefensiveInstance.length; i++) {
            for (int j = 0; j < RandDefensiveInstance[0].length; j++) {

                RandDefensiveInstance[i][j] = DefensiveInstance[i][j] - randomInterval + Math.random() * 2 * randomInterval;       // midpoint of [a,b] is value of DefensiveInstance[i][j]

                if (RandDefensiveInstance[i][j] < 0) {
                    RandDefensiveInstance[i][j] = 0;
                }
            }
        }

        normalize(RandNeutralInstance);
        normalize(RandAggressiveInstance);
        normalize(RandDefensiveInstance);

        ReadMatrix.readOut(RandNeutralInstance,"Trainers");
        ReadMatrix.readOut(RandAggressiveInstance,"Trainers");
        ReadMatrix.readOut(RandDefensiveInstance,"Trainers");
    }

    //Normalisation:
    private static void normalize(double[][] inputMatrix) {

        for (int l = 0; l < inputMatrix.length; l++) {
            double sum = 0;
            for (int m = 0; m < inputMatrix[0].length; m++) {
                sum += inputMatrix[l][m];
            }
            for (int n = 0; n < inputMatrix.length; n++) {
                inputMatrix[l][n] = (inputMatrix[l][n]) / sum;
            }
        }
    }
}